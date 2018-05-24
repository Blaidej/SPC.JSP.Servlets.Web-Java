<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : TempConvertResult
    Created on : Aug 16, 2014, 11:32:58 PM
    Author     : rfoy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temperature Converter</title>
        <style>
            body{ 
                width: 300px;
                background-color: beige;
                margin-left: auto;
                margin-right: auto;
                border: 15px solid black;
                padding-left: 3em;
                }
            div{width: 240px; margin-bottom: 1em; border: 5px solid black}
            label{font-weight: bold}
            .orig{background-color: lightcoral}
            .conv{background-color: lightblue; }
            .errmsg {background-color: red; font-size: 200%; color: white;}
        </style>
    </head>
    <body>
        
        <c:choose>
            <c:when test="${!empty errMsg}">
                <div class="errmsg">${errMsg}</div>
            </c:when>
            <c:otherwise>
                <h1>Converted Temperature</h1>
                <div class="orig"><label>Original Temp:</label> ${tempConverter.origTemp}</div>
                <div class="orig"><label>Original Unit:</label> &deg;${tempConverter.origUnit}</div>
                <div class="conv"><label>Converted Temp:</label> ${tempConverter.convertedTemp}</div>
                <div class="conv"><label>Converted Unit:</label> &deg;${tempConverter.convertedUnit}</div>
            </c:otherwise>
        </c:choose>
                <a href="TempEntry.html">Back</a>
    </body>
</html>
