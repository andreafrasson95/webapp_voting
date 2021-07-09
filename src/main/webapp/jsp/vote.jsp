<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Voting</title>
	
</head>

<body>
 <form method="POST" class="row g-3 needs-validation" action= "<c:url value="/vote/${numero.id}"/>">

     
           <h1>${poll.question}</h1>
            
	       <c:forEach var="item" items="${requestScope.answers}">
        
                    <div class="form-check">
                      <input type="radio" name="${poll.id}" value="${item.id}">
					
					  <label class="form-check-label" for="${item.id}">
                            ${item.text}
                      </label>
					</div>
                 
            </c:forEach>
			
			<button type="submit">Submit</button>
     

 </form>
  
</body>
</html>