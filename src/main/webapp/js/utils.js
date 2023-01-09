

var contextPath = 'http://localhost:8080/poll-webapp-0.1/';

$(document).ready(function(){
	
	loadnavbar();
	
});

function loadnavbar(){
	
	$.get(contextPath+'html/navbar.html').done(function(data){
	$("nav").html(data);});
}