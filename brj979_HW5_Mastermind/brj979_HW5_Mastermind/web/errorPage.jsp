<%-- 
    Document   : errorPage
    Created on : Apr 24, 2018, 9:19:40 PM
    Author     : CompSciStudent
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <style>
            .btn-group .button {
                width: 12.5%;
                border: 1px solid green;
                color: white;
                padding: 50% 5px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 20px;
                cursor: pointer;
                float: left;
                font-weight: bolder;
                height: 60%
            }
            .btn-group .button:not(:last-child) {
                border-right: none; /* Prevent double borders */
            }
            
        </style>
        <link rel="stylesheet" type="text/css" href="mastermind.css">
    </head>
    <body>
        <h1>Sorry, it looks like we ran into an Error.</h1>
        <div class="btn-group">
            <button class="button M">Sorry,</button>
            <button class="button G">we</button>
            <button class="button O">ran</button>
            <button class="button Y" style="color:black;">into</button>
            <button class="button P">a</button>
            <button class="button B">problem.</button>
            <button class="button Wh" style="color:black;">Please</button>
            <button class="button R">Forgive Us</button>
            
        </div>
    </body>
</html>
