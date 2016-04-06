<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Table</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link href="myCss.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="row">
               <div class ="container">
                    <div class="panel panel-default" style="background-color:lightblue">
                         <div class="panel-heading" style="background-color:lightblue"><div class="text-center"><h1>Book List</h1></div></div>
                         <div class="panel-body">
                              <div class="text-center">
                                   <p>Displays a List of Book objects, and Authors, collected from a database.</p>
                              </div>
                         </div>
                         <form method="POST" action="BookController?action=addEditDelete">
                              <div name="buttons" class="text-center">
                              <input type="submit" value="Add/Edit" name="submit" />&nbsp;
                              <input type="submit" value="Delete" name="submit" />
                              </div>
                              <br><br>
                              <table class="table table-hover" style="width: 100%;" border="1" cellspacing="2" cellpadding="5">
                                   <tr style="background-color: black;color:white;">
                                        <th align="left" class="tableHead">ID</th>
                                        <th align="left" class="tableHead">Title</th>
                                        <th align="right" class="tableHead">ISBN</th>
                                        <th align="right" class="tableHead">Author</th>
                                   </tr>
                                   <c:forEach var="b" items="${books}" varStatus="rowCount">
                                        <tr>
                                             <td><input type="checkbox" name="bookId" value="${b.bookId}" /></td>
                                             <td align="left">${b.title}</td>
                                             <td align="left">${b.isbn}</td>
                                             <td align="left">
                                                  <c:choose>
                                                       <c:when test="${not empty b.authorId}">
                                                            ${b.authorId.authorName}
                                                       </c:when>
                                                       <c:otherwise>
                                                            None
                                                       </c:otherwise>
                                                  </c:choose>
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
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
            </c:if>
    </body>
</html>
