

var contextPath = 'http://195.231.83.161:8080/poll-webapp-0.1/';

$(document).ready(function(){
	
	loadnavbar();
	
});

function loadnavbar(){
	
	$.get(contextPath+'html/navbar.html').done(function(data){
	$("nav").html(data);});
}