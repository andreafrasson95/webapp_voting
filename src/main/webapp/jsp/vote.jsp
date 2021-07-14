<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Voting</title>
	
	<!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<!-- CSS Bootstrap -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Custom CSS -->
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	
</head>

<body>
 <!-- Esperimenti Navbar -->
 
 <nav class="navbar navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">
      <img src="../resources/logo.png" alt="" width="90" height="72" class="d-inline-block align-text-center">
      Rotaract Club Venezia Riviera del Brenta Voting System v 0.1
    </a>
  </div>
 </nav>
 <br/><br/>
 
 <!--INIZIO PARTE SERIA -->
 <div id="corpo" class="container text-center" >
 <c:choose>
   <c:when test="${not empty message}">
      ${message.message} <br/>
   </c:when>

   <c:otherwise>    
      <form id="form" class="row g-3 needs-validation" novalidate>

		 <p class="fs-1">${poll.question}</p>
           
	         <c:forEach var="item" varStatus="loop" items="${requestScope.answers}">
        
                      <div class="form-check">
                        <input class="form-check-input" type="radio" name="${poll.id}" value="${item.id}" id="${item.id}" required>
					
				  	    <label class="form-check-label" for="${item.id}">
                            ${item.text}
                        </label>
						
						<c:if test="${loop.last}">
			               <div class="invalid-feedback">Ne devi Selezionare uno mona</div>
						</c:if>
						
					  </div>
             </c:forEach>
		   
			 <div class="text-center">
			   <button class="btn btn-primary col-md-2" type="submit" id="submit">Invio</button>
             </div>
	 
      </form>
   </c:otherwise>
 </c:choose>
 
 </div> 
 <!-- Javascript Script for the page --> 
 <script  src="../js/vote.js"></script>
 <!-- Javascript Bootstrap -->
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


</body>
</html>