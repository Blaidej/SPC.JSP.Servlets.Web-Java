/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MasterMind;
import model.MasterMind.Status;

/**
 *
 * @author blaid
 */
public class MasterMindServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        //Servlet Properties
        ArrayList<String> errMsgs = new ArrayList<>();
        ArrayList<String> infoMsgs = new ArrayList<>();
        String url = "/mastermind.jsp";
        ServletContext sc = getServletContext();
        HttpSession session = request.getSession();
        String masterCodeVis = "hidden";

        //1. Get Info
        MasterMind game = (MasterMind) request.getSession().getAttribute("game");
        String action = request.getParameter("action");
        
        //Bean info
        Status gameStatus = game.getGameStatus();

        //controlling logic if actions are made in the game.
        if ((action != null) && (infoMsgs.isEmpty()))
        {

            switch (action.toLowerCase())
            {
                //Start Game is called whenever a new Game is submitted    
                case "startgame":
                    game.setMasterCode();
                    game.setNewBoards();
                    MasterMind.Peg[] masterCode = game.getMasterCode();
                    session.setAttribute("masterCode", masterCode);
                    //loging the masterString to the Console
                    String masterString = Arrays.toString(masterCode);
                    log("MASTERCODE: " + masterString);
                    /*  this builds the board for the key Pegs and the Pegs
                        to be put into
                     */
                    MasterMind.Peg[] pegBoard = game.getPegBoard();
                    MasterMind.KeyPeg[] keyBoard = game.getKeyBoard();
                    session.setAttribute("pegBoard", pegBoard);
                    session.setAttribute("keyBoard", keyBoard);
                    //gives access to the peg values in the bean at the JSP level
                    MasterMind.Peg[] pegValues = MasterMind.Peg.values();
                    session.setAttribute("pegValues", pegValues);
                    break;
                case "checkguess":

                    if ((game.isMasterCodeSet()) && (gameStatus == Status.PLAY))
                    {

                        MasterMind.Peg[] guessRow = new MasterMind.Peg[4];

                        //an array of Strings to hold the guesses
                        String[] guess = new String[4];
                        for (int i = 0; i < 4; i++)
                        {

                            guess[i] = request.getParameter(String.format(
                                    "guess%d", i + 1));
                            //checking to see if any of the guess's are null
                            if (guess[i] != null)
                            {
                                /*  ValueOf returns the enum constant with the 
                                specified name assigning it to the new position 
                                of the Pegrow  */
                                MasterMind.Peg enumPeg = MasterMind.Peg.valueOf(
                                        guess[i]);
                                guessRow[i] = enumPeg;
                            } else
                            {
                                errMsgs.add(String.format(
                                        "Oops, you forget to make"
                                        + " a full guess, you missed Peg number %d.",
                                        i + 1));
                            }
                        }
                        if (errMsgs.isEmpty())
                        {

                            game.makeGuess(guessRow);
                            MasterMind.KeyPeg[] keyPeg = game.getKeyPegArray();
                            session.setAttribute("keyPeg", keyPeg);
                            gameStatus = game.getGameStatus();

                            //will enter this if the player wins or looses
                            if (gameStatus == Status.LOSE)
                            {
                                infoMsgs.add("Sorry You Lost");
                                masterCodeVis = "visible";
                            } else if (gameStatus == Status.WIN)
                            {
                                infoMsgs.add("Congratulation you Won! <br>"
                                        + "YOU GENIUS!!!");
                                masterCodeVis = "visible";
                            }

                        }
                    } else
                        /*  will enter if the user isn't started a game and the
                            masterCode isn't set yet.
                        */
                    {
                        if (gameStatus == Status.LOSE)
                        {
                           infoMsgs.add("You Already Lost.  "
                                    + "<br> Do you want to Play Again?");
                        } else if (gameStatus == Status.WIN)
                        {
                            infoMsgs.add("You Already Won! "
                                    + "<br> Do you want to Play Again?");
                        } else
                        {
                             errMsgs.add(
                                    "You need to start a game before submitting"
                                    + " a guess.");
                        }
                    }
                    break;

            }
        }
        //a call to the guesses made and remain, after the guess would of taken place
        int guessesMade = game.getGuessesMade();
        int guessesRemain = game.getGuessesRemain();
        
        //2. validate info
        //3. do it
        //4. add info to session
        // Send the PegRow as a codeGuess when the user inputs a new guess.  
        session.setAttribute("game", game);
        session.setAttribute("errMsgs", errMsgs);
        session.setAttribute("infoMsgs", infoMsgs);
        session.setAttribute("masterCodeVis", masterCodeVis);
        session.setAttribute("guessesMade", guessesMade);
        session.setAttribute("guessesRemain", guessesRemain);

        //5. forward control
        sc.getRequestDispatcher(url).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
