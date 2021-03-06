<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : addAthlete
    Created on : Feb 27, 2018, 8:50:30 PM
    Author     : blaid
    This page sends the it's info to RosterServlet to get added to the 
    AthleteRoster.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Athlete</title>
    </head>
    <body>

        <h1>Add Athlete</h1>

        <!-- This tests if there are any error messages and prints them -->
        <c:if test="${errMsgs.size() > 0}" var="errMsg">
            <h2><span style="color:red">Error</span></h2>
            <c:forEach var="errMsg" items="${errMsgs}">
                <span style="color:red">${errMsg}</span><br>
            </c:forEach>
        </c:if>


        <!-- Form to input user info -->
        <form action="viewRoster">
            <table border="1">
                <tbody>
                    <tr>
                        <td><label>National ID</label></td>
                        <td>
                            <input type="text" name="nationalID" value="" />
                            ("aa999")
                        </td>
                    </tr>
                    <tr>
                        <td><label>First Name</label></td>
                        <td>
                            <input type="text" name="firstName" value="" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>Last Name</label></td>
                        <td>
                            <input type="text" name="lastName" value="" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>Date Of Birth</label></td>
                        <td>
                            <input type="text" name="DateOfBirth" value="" />
                            <span style="color:red">(mm/dd/yyyy)</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Add" name="action" />
                            <input type="submit" value="Cancel" name ="action" />
                        </td>
                    </tr>
                </tbody>

            </table>
        </form>









    </body>
</html>
