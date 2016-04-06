<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <title>Author Table</title>
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
          <link href="myCss.css" rel="stylesheet" type="text/css"/>
     </head>
     <body>
          <div class="row">
               <div class ="container">
                    <div class="panel panel-default" style="background-color:lightblue">
                         <div class="panel-heading" style="background-color:lightblue"><div class="text-center"><h1>Author List</h1></div></div>
                         <div class="panel-body">
                              <div class="text-center">
                                   <p>Displays a List of Author objects collected from a database.</p>
                              </div>
                         </div>
                         <form method="POST" action="AuthorController?action=addEditDelete">
                              <hr>
                              <div name="buttons" class="text-center">
                                   <input type="submit" value="Add/Edit" name="submit" />&nbsp;
                              <input type="submit" value="Delete" name="submit" />
                              </div>
                              <br>
                              <table class="table table-hover" width="600" border="1" cellspacing="2" cellpadding="5">
                                   <tr style="background-color: black;color:white;">
                                        <th align="left" class="tableHead">ID</th>
                                        <th align="left" class="tableHead">Author Name</th>
                                        <th align="right" class="tableHead">Date Added</th>
                                   </tr>
                                   <c:forEach var="a" items="${authors}" varStatus="rowCount">
                                        <tr>
                                             <td><input type="checkbox" name="authorId" value="${a.authorId}" /></td>
                                             <td align="left">${a.authorName}</td>
                                             <td align="right">
                                                  <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                                                  </td>
                                             </tr>
                                   </c:forEach>
                              </table>
                              <br>
                              <div name="buttons" class="text-center">
                              <input type="submit" value="Add/Edit" name="submit" />&nbsp;
                              <input type="submit" value="Delete" name="submit" />
                              </div>
                         </form>
                         <center>
                              <form id="back" name="back" method="POST" action="home.jsp" style="padding:10px;">
                                   <input class="btn btn-info" type="submit" name="submit" value="Take me Home">
                              </form>
                         </center>
                    </div>
               </div>
          </div>
          <p>Click here to go back to Home Page <a href="index.html">Home</a></p>

          <c:if test="${errMsg != null}">
               <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                    ${errMsg}</p>
               </c:if>
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
     </body>
</html>
</body>
</html>
