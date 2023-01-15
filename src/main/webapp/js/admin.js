
var operating_poll;
var number_pages = 0;

var focus_text;

const map = new Map();

$(document).ready(function () {

	number_pages = 0;
	retrievePolls();

	//$(".tab-pane").append("<div class='d-flex justify-content-center' id='spinner'"+this.id+"'><div class='spinner-border text-primary' role='status'><span class='visually-hidden'>Loading...</span></div></div>");
	$("#form_links").hide();
	$("#form_links").submit(createLinks);

	$("#select").on("click", "option", function () {
		retrieveLinks(this);
		retrieveAnswers(this);

	});


});


function retrieveLinks(element) {

	var poll = element.getAttribute("value");

	// Put Spinner  
	$("#link").append("<div class='d-flex justify-content-center' id='spinnerlink'><div class='spinner-border text-primary' role='status'><span class='visually-hidden'>Loading...</span></div></div>");
	$('#pages').html("");
	$("#form_links").hide();

	$.getJSON("http://195.231.83.161:8080/poll-webapp-0.1/admin/", { operation: "listLinks", poll: poll })
		.done(function (json) {
			$('#spinnerlink').remove();

			//Save the Json with links in a map
			map.set(poll, json.links);

			var lenght = Object.keys(json.links).length;


			if ((lenght % 10) == 0) {
				number_pages = lenght / 10;
			}
			else {
				number_pages = Math.trunc((lenght / 10) + 1);
			}

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

	$.getJSON("http://195.231.83.161:8080/poll-webapp-0.1/admin/", { operation: "listAnswers", poll: poll })
		.done(function (json) {
			$.each(json.answers, function (i, data) {
				$('#spinneranswers').remove();
				$("#answer_table tbody").append("<tr><td>" + data.text + "</td><td>" + data.votes + "</td></tr>");
			});
		});
}

function retrievePolls() {

	$.getJSON("http://195.231.83.161:8080/poll-webapp-0.1/admin/", { operation: "listPolls" })
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
	//var old_pages=Math.trunc((old_length/10)+1);

	$.post("http://195.231.83.161:8080/poll-webapp-0.1/admin/", { operation: "createLinks", number: number, poll: operating_poll })
		.done(function (json) {
			new_links = old_links.concat(json.links);
			console.log(new_links);
			map.set(operating_poll, new_links);

			var ex1 = (old_length % 10);
			if ((parseInt(ex1) + parseInt(number)) <= 10) {
				printLinks(operating_poll, number_pages);
			}
			else {
				var new_pages = number_pages + 1;
				$("#pages").append("<li class='page-item' id=linkpage" + new_pages + "><a class='page-link' href='#'>" + new_pages + "</a></li>");
				$('#linkpage' + new_pages).click(function () {
					printLinks(poll, new_pages);
					number_pages++;
				});
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
		$.post("http://195.231.83.161:8080/poll-webapp-0.1/admin/", { operation: "updateNotes", link: id, notes: current })
			.done(function () {
				console.log('ok');


			});
	}
}

function printLinks(poll, page) {

	var links = map.get(poll);
	var index = (page - 1) * 10;
	var end_index = index + 10;
	var length = links.length;
	//I clean the page
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












