
var admin_url='http://195.231.83.161:8080/poll-webapp-0.1/admin/';

var operating_poll;
var number_pages;
var question_number;

var focus_text;

const map = new Map();

$(document).ready(function () {

	number_pages = 1;
	question_number=0;
	retrievePolls();

	//$(".tab-pane").append("<div class='d-flex justify-content-center' id='spinner'"+this.id+"'><div class='spinner-border text-primary' role='status'><span class='visually-hidden'>Loading...</span></div></div>");
	$("#form_links").hide();
	$("#form_links").submit(createLinks);

	$("#select").on("click", "option", function () {
		retrieveLinks(this);
		retrieveAnswers(this);

	});

	$("#PollForm").submit(addPoll);
    $('#adder').click(addAnswerBox);
	  
	  
});


function retrieveLinks(element) {

	var poll = element.getAttribute("value");

	// Put Spinner  
	$("#link").append("<div class='d-flex justify-content-center' id='spinnerlink'><div class='spinner-border text-primary' role='status'><span class='visually-hidden'>Loading...</span></div></div>");
	$('#pages').html("");
	$("#form_links").hide();

	$.getJSON(admin_url, { operation: "listLinks", poll: poll })
		.done(function (json) {
			$('#spinnerlink').remove();

			//Save the Json with links in a map
			map.set(poll, json.links);

			var length = Object.keys(json.links).length;

			if(length==0) number_pages=1;
			else{
			if ((length % 10) == 0) {
				number_pages = length / 10;
			}
			else {
				number_pages = Math.trunc((length / 10)) + 1;
			}
		   }
            console.log(number_pages);
			//I print the first Page
			printLinks(poll, 1);

			for (let i = 1; i <= number_pages; i++) {
				$("#pages").append("<li class='page-item' id=linkpage" + i + "><a class='page-link' href='#'>" + i + "</a></li>");
				$('#linkpage' + i).click(function () {
					printLinks(poll, i);
				});

			}

			$("#form_links").show();

		});
	//Put the poll id in the table
	$('#link_table').attr("poll", poll);
	operating_poll = poll;
}


function retrieveAnswers(element) {

	$("#answer_table tbody").html("");
	$("#answer").append("<div class='d-flex justify-content-center' id='spinneranswers'><div class='spinner-border text-primary' role='status'><span class='visually-hidden'>Loading...</span></div></div>");

	var poll = element.getAttribute("value");

	$.getJSON(admin_url, { operation: "listAnswers", poll: poll })
		.done(function (json) {
			$.each(json.answers, function (i, data) {
				$('#spinneranswers').remove();
				$("#answer_table tbody").append("<tr><td>" + data.text + "</td><td>" + data.votes + "</td></tr>");
			});
		});
}

function retrievePolls() {

	$.getJSON(admin_url, { operation: "listPolls" })
		.done(function (json) {
			$.each(json.polls, function (i, data) {

				$("#select").append("<option value=" + data.id + ">" + data.name + "</option>");
			});
		});
}

function createLinks(event) {

	event.preventDefault();

	var number = $("#inputlinks").val()
	var old_links = map.get(operating_poll);
	var old_length = old_links.length;
	console.log(old_length);
	//var old_pages=Math.trunc((old_length/10)+1);

	$.ajax({
		type: 'POST',
		url: admin_url,
		data: JSON.stringify({'operation': 'createLinks', 'number': number, 'poll': operating_poll}), 
		contentType: "application/json",
		dataType: 'json'
	}).done(function (json) {
			var new_links = old_links.concat(json.links);
			//console.log(new_links);
			map.set(operating_poll, new_links);

			var ex1 = (old_length % 10);
			//Ugly for now
			if(ex1==0 && old_length>0) ex1=10;
			if ((parseInt(ex1) + parseInt(number)) <= 10) {
				printLinks(operating_poll, number_pages);
			}
			else {
				var new_pages = number_pages + 1;
				$("#pages").append("<li class='page-item' id=linkpage" + new_pages + "><a class='page-link' href='#'>" + new_pages + "</a></li>");
				printLinks(operating_poll, new_pages);
				$('#linkpage' + new_pages).click(function () {
					printLinks(operating_poll, new_pages);
				});
				number_pages++;
			}
		});


}

function updateNotes(event) {

	event.preventDefault();

	var current = $(event.target).val()
	var id = ($(event.target).attr('id')).substr(4);

	if (current == focus_text) {
		console.log("Invariato");
	}
	else {
		$.ajax({
			type: 'POST',
			url: admin_url,
			data: JSON.stringify({'operation': 'updateNotes', 'link': id, 'notes': current}), 
			contentType: "application/json",
			dataType: 'json'
		}).done(function () {
				console.log('ok');
		});
	}
}

function printLinks(poll, page) {

	var links = map.get(poll);
	console.log(links);
	var index = (page - 1) * 10;
	var end_index = index + 10;
	var length = links.length;
	//I clean the page
	console.log(index);console.log(end_index);console.log(length);
	$("#link_table tbody").html("");
	// Print Links
	for (let j = index; j < end_index && j < length; j++) {

		$("#link_table tbody").append("<tr id=row" + links[j]['id'] + "><td>" + links[j]['id'] + "</td><td>" + links[j]['state'] + "</td><td><input type='text' id=note" + links[j]['id'] + "></td></tr>");
		$('#note' + links[j]['id']).val(links[j]['notes']);
		//Register Focusin handler
		$('#note' + links[j]['id']).focusin(function () {
			focus_text = $('#note' + links[j]['id']).val();
		});
		//Register focus out for notes updateCommands
		$('#note' + links[j]['id']).focusout(updateNotes);

	}
}

function addPoll(event){
  
	event.preventDefault();
	$("#submitbutton").attr('disabled', true);
	$("#submitbutton").html("<span class='spinner-border spinner-border-sm' role='status' aria-hidden='true'></span>  Loading...");

	var json={};
	var answers=[];
	var data = $("#PollForm").serializeArray(); 
	console.log(data);
	$.each(data , function (i, data) {
	  if(data["name"] == "answer") answers.push(data["value"]);
	  else
	  json[data["name"]]=data["value"];
	  
	});
	json["answers"]=answers;
	json["operation"]="addPoll";
	
	$.ajax({
		type: 'POST',
		url: admin_url,
		data: JSON.stringify(json), 
		contentType: "application/json",
		dataType: 'json'
	}).done(function () {
		    $("#Modal").modal("hide");
			console.log('Poll Added');
			$("#submitbutton").attr('disabled', false);
			$("#submitbutton").html("Submit");
			retrievePolls();

	});

  
	console.log(JSON.stringify(json));


}

function addAnswerBox(){
	question_number++;
    $("<div class='input-group mb-3'><input type='text' class='form-control' name='answer' placeholder='Inserisci una Risposta' aria-label='Recipien username' aria-describedby='button-addon2'><button class='btn btn-outline-secondary' type='button' id='button-addon2'>Button</button></div>").insertBefore($('#adder'));

}










