<%-- 
    Document   : Books
    Created on : Apr 6, 2016, 9:41:59 AM
    Author     : Scott
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <title>Book List</title>
     </head>
     <body>
          <h1>Book List</h1>
          <div class="row">
               <div class ="container">
                    <div class="panel panel-default" style="background-color:lightblue">
                         <div class="panel-heading" style="background-color:lightblue"><div class="text-center"><h1>Book List</h1></div></div>
                         <div class="panel-body">
                              <div class="text-center">
                                   <p>Displays a List of Book objects (and related Authors) collected from a database.</p>
                              </div>
                              <form method="POST" action="BookController">
                                   <div name="buttons" class="text-center">
                                        &nbsp;<input type="submit" value="Add" name="submit" class="btn btn-success" />&nbsp;
                                        <input type="submit" value="Edit" name="submit" class="btn btn-warning" />&nbsp;
                                        <input type="submit" value="Delete" name="submit" class="btn btn-danger"/>
                                   </div>
                                   <br>
                                   <table class="table table-hover" width="600" border="1" cellspacing="2" cellpadding="5">
                                        <tr>
                                             <th  class=" ">ID</th>
                                             <th  class=" ">Title</th>
                                             <th  class=" ">ISBN</th>
                                             <th  class=" ">Author</th>
                                        </tr>
                                        <c:forEach var="b" items="${books}" varStatus="rowCount">
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
                                        </c:forEach>
                                   </table>
                              </form>
                         </div>
                    </div>
               </div>
          </div>
     </form>
</body>
</html>
