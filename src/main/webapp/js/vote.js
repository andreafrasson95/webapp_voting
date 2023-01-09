
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
		  $("#corpo").html("<div class='alert alert-danger' role='alert'>"+data.message+"</div>");
		 
		}
        else{
      	  $("#corpo").empty();
		  $("#corpo").html("<div class='alert alert-success' role='alert'>"+data.message+"</div>");
		}		  
		
	  });
	}
	else{
		
		event.preventDefault();
		event.stopPropagation();
	    $(".form-check-input").addClass("is-invalid");
		
		$(".form-check-input").click(function(){
		$(".form-check-input").removeClass("is-invalid");
	});
	}
	
	//$("#form").addClass('was-validated');


}