<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>MasterMind</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="mastermind.css">
        <style>
            .hideMaster {
                visibility : ${masterCodeVis};
            }
        </style>
    </head>
    <body>

        <div class="wholeboard">
            <h1>MasterMind</h1>
            <div class="gameboard">

                <form action="getMasterMind" method="POST">
                    <table class="pegTable">
                        <thead class="hideMaster">
                            <tr>
                                <c:forEach var="masterPeg" items="${masterCode}">
                                    <th class ="hideMaster ${masterPeg}">${masterPeg}</th>

                                </c:forEach>
                            </tr>

                        </thead>

                        <tbody>

                            <tr>

                                <c:forEach var="pegs" items="${pegBoard}" varStatus="pegStat">

                                    <td class="${pegs}">


                                    </td>

                                    <c:if test="${pegStat.count % 4 == 0 && !pegStat.last}">
                                    <tr></tr>
                                </c:if>

                            </c:forEach>

                            <tr>
                                <td>
                                    <div class="styled-select blue semi-square">
                                        <select name="guess1" size="6">
                                            <c:forEach begin="0" end="5" var="peg" items="${pegValues}" varStatus="enumStat">
                                                <option label="${peg}" value="${peg}" class="${peg}">
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>

                                <td>
                                    <div class="styled-select blue semi-square">
                                        <select name="guess2" size="6">
                                            <c:forEach begin="0" end="5" var="peg" items="${pegValues}" varStatus="enumStat">
                                                <option label="${peg}" value="${peg}" class="${peg}">
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>

                                <td>
                                    <div class="styled-select blue semi-square">
                                        <select name="guess3" size="6">
                                            <c:forEach begin="0" end="5" var="peg" items="${pegValues}" varStatus="enumStat">
                                                <option label="${peg}" value="${peg}" class="${peg}">
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>

                                <td>
                                    <div class="styled-select blue semi-square">
                                        <select name="guess4" size="6">
                                            <c:forEach begin="0" end="5" var="peg" items="${pegValues}" varStatus="enumStat">
                                                <option label="${peg}" value="${peg}" class="${peg}">
                                                </option>
                                            </c:forEach>

                                        </select>
                                    </div>
                                </td>

                            </tr>

                            <tr>
                                <td colspan="6">
                                    <button type="submit" value="checkGuess" name="action">Check Guess</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6"><button type="submit" value="startGame" name="action">New Game!</button></td>
                            </tr>
                        </tbody>
                    </table>
            </div>  

            <div class="keyPegBoard">
                <table class="keyPegTable">
                    <thead>
                    <th colspan="4">
                        <c:choose>
                            <c:when test="${masterCodeVis == 'hidden'}">
                                KeyPegs
                            </c:when>
                            <c:otherwise>
                                < MasterCode
                            </c:otherwise>
                        </c:choose>


                    </th>
                    </thead>
                    <tbody>

                        <c:forEach var="keyPegs" items="${keyBoard}" varStatus="keyPegStat">
                        <td class="${keyPegs}">
                            <%--${keyPegs}--%>


                        </td>
                        <c:if test="${keyPegStat.count % 4 == 0 && !keyPegStat.last}">
                            <tr></tr>
                        </c:if>
                    </c:forEach>
                    <tr><td colspan="4">Guesses</td></tr>
                    <tr>
                        <td colspan="4">
                            <h5>Made: ${guessesMade}</h5>
                            <h5>Remaining: ${guessesRemain}</h5>

                        </td>
                    </tr>
                    </tbody>


                </table> 
            </div>
            <div class="info">
                <c:if test="${infoMsgs.size() > 0}" var="infoMsg">
                    <c:forEach var="infoMsg" items="${infoMsgs}">
                        <h2 class="Error">${infoMsg}</h2><br>
                    </c:forEach>
                </c:if>
                <c:if test="${errMsgs.size() > 0}" var="errMsg">
                    <h2><span class="Error">Error</span></h2>
                    <c:forEach var="errMsg" items="${errMsgs}">
                        <span class="Error">${errMsg}</span><br>
                    </c:forEach>
                </c:if>
            </div>
        </div>







    </body>
</html>
