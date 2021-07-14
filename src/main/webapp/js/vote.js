
$(document).ready(function(){
	
	$("#form").submit(sendData);

});

function sendData(event){
	
	
	event.preventDefault();
	
	if((document.querySelector('.needs-validation')).checkValidity() == true){
	
	  event.preventDefault();
	
	  var dati=$("#form").serialize();
	  var url=window.location.href; 
	
	  $.post(url,dati).done(function(data){
	  	if(data.isError == true){
		  $("#corpo").empty();
		  $("#corpo").html(data.message);
		}
        else{
      	  $("#corpo").empty();
		  $("#corpo").html(data.message);
		}		  
		
	  });
	}
	else{
		
		event.preventDefault();
		event.stopPropagation();
	}
	
	$("#form").addClass('was-validated');


}