<%-- 
    Document   : home
    Created on : Apr 6, 2016, 12:39:39 PM
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
        <title>Web Book App</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link href="myCss.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="row">
            <div class ="container">
                <div id="cont">
                    <br>
                    <h1 class="text-center">Admin Page</h1>
                    <h3 class="text-center">JPA Authors/Books</h3>
                    <br>
                    <div class="col-md-4" id="content">
                        <form id="authors" name="authors" method="POST" action="AuthorController?action=list">
                            <h3>Display Authors</h3>
                            <p>This will return all of the authors in our Database.</p>
                            <input class="btn btn-info" type="submit" value="View Authors"/>
                        </form>
                    </div>
                    <div class="col-md-4 col-md-offset-4" id="content">
                        <form id="resume" name="resume" method="POST" action="BookController?action=list">
                            <h3>Display Books</h3>
                            <p>This will return all of the books in our Database.</p>
                            <input class="btn btn-info" type="submit" value="View Books">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class ="container">
                <div id="cont">
                    <div class="col-md-4" id="content">
                        <form id="delete" name="delete" method="POST" action="AuthorController">
                            <h3>Delete Author by ID</h3>
                            <p>This will return the rest of the records in the database.</p>
                            <input type="text" name="authorId" placeholder="Record ID to Deleted">
                            <input class="btn btn-info" type="submit" name="submit" value="Delete">
                        </form>
                    </div>
                    <div class="col-md-4" id="content">
                        <form id="update" name="update" method="POST" action="AuthorController">
                            <h3>Update Author by ID</h3>
                            <p>This will return all records in Database - Including update.</p>
                            <input type="text" name="authorId" placeholder="Record ID">
                            <input type="text" name="authorName" placeholder="Name">
                            <br>
                            <br>
                            <input type="date" name="authorDate" placeholder="Date">
                            <br>
                            <br>
                            <input class="btn btn-info" type="submit" name="submit" value="Update">
                        </form>
                    </div>
                    <div class="col-md-3" id="content">
                        <form id="create" name="create" method="POST" action="AuthorController">
                            <h3>Create a new Author</h3>
                            <p>This will return all records in the database, including new record added.</p>
                            <input type="text" name="authorName" placeholder="Record Name">
                            <input class="btn btn-info" type="submit" name="submit" value="Create">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <footer class="footer">
                <div class="container">
                    Created by Scott Muth
                </div>
            </footer>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    </body>
</html>