
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * MasterMind is the Bean for this program, that simulates the popular MasterMind
 * game. It does the heavy processes and contains the properties of the game.
 * @author blaid
 */
public class MasterMind implements Serializable {

    //the size of the guess and masterCode arrays
    private final int ROWSIZE = 4;
    //the masterCode 
    private Peg[] masterCode;
    //masterCode is set Flag
    private boolean masterCodeFlag;
    //a random number generator used to build the masterCode
    private final Random rand = new Random();
    
    //Board for the Pegs
    private Peg[] pegBoard;
    //tracker for the last peg position 
    private int lastPegPosition;

    //Board for the keyPegs
    private KeyPeg[] keyBoard;
    //tracker for the last keypeg position 
    private int lastKeyPosition;
    //boardsize could be changed to have a bigger or smaller board
    private int BOARDSIZE;

    //the user's guess
    private Peg[] guessCode = new Peg[ROWSIZE];

    //redKeyCount & whiteKeyCount are the Counts of keyPegs
    private int redKeyCount;
    private int whiteKeyCount;
    //holds the keyPegs for the user guesses
    private KeyPeg[] keyPegArray = new KeyPeg[ROWSIZE];
    //Game playing status
    private Status gameStatus = Status.PLAY;
    //Game guess counters
    private int guessesMade = 0;
    private int guessesRemain = 10;
    
    


    /**
     * Default no-Argument Constructor for MasterMind
     */
    public MasterMind() {

    }

    /**
     * Pegs represent the multiColored pegs used in the game
     */
    public enum Peg {

        /**
         * 0|Magenta
         */
        M,

        /**
         * 1|Green 
         */
        G,

        /**
         * 2|Orange
         */
        O, 

        /**
         * 2|Orange
         */
        Y, 

        /**
         * 4|Purple
         */
        P,  

        /**
         * 5|Blue
         */
        B, 

        /**
         * 6| a Peg that represents an empty peg spot
         */
        NoPeg 
    }

    /**
     * KeyPeg represent the key pegs for feedback if answers are correct
     */
    public enum KeyPeg {

        /**
         * White keyPeg
         */
        Wh,

        /**
         * Red keyPeg
         */
        R, 

        /**
         *  a keyPeg that represents an empty key spot
         */
        NoKey
    }

    /**
     * Represents the Status of the player
     */
    public enum Status {

        /**
         * Play is when the player is actively playing the game
         * and hasn't won or lost yet.
         */
        PLAY,

        /**
         * Win only happens if you get all the Pegs in the correct spot
         */
        WIN,

        /**
         * Lose happens when you run out of guesses
         */
        LOSE
    }

    /**
     * this resets the values and arrays for a new game to be played
     */
    public void setNewBoards() {

        BOARDSIZE = 40;
        pegBoard = new Peg[BOARDSIZE];
        //Board for the keyPegs - used and unused
        keyBoard = new KeyPeg[BOARDSIZE];
        for (int i = 0; i < BOARDSIZE; i++) {
            pegBoard[i] = Peg.NoPeg;
            keyBoard[i] = KeyPeg.NoKey;
        }
        lastPegPosition = (pegBoard.length - 1);
        lastKeyPosition = (pegBoard.length - 1);
        guessesMade = 0;
        guessesRemain = 10;
        gameStatus = Status.PLAY;

    }

    /**
     * Returns the amount of Guesses Made since the beginning of the game.
     * @return the amount of guesses Made
     */
    public int getGuessesMade() {
        return guessesMade;
    }

    /**
     * Returns the amount of guesses which remain since the beginning of the game.
     * @return the amount of guesses that remain
     */
    public int getGuessesRemain() {
        return guessesRemain;
    }

    /**
     * Returns a pegboard to be filled with the user's guesses so you can see
     * a history of correct or incorrect guesses
     * @return the pegBoard with user submitted guesses
     */
    public Peg[] getPegBoard() {
        return pegBoard;
    }

    /**
     * Returns a keyPegBoard filled with the user's guesses so you can see a
     * history of keyPegs corresponding to the user's guesses
     * @return the keyBoard with the keyPegs that the user has been awarded for 
     * incorrect, correct, or almost correct responses
     */
    public KeyPeg[] getKeyBoard() {
        return keyBoard;
    }

    /**
     * Returns a copy of the masterCode that the user will try to guess.
     * @return a copy of the masterCode or sometimes considered a hiddenCode
     */
    public Peg[] getMasterCode() {
        masterCode = masterCode.clone();

        return masterCode;
    }

    /**
     * Sets the masterCode for that specific game. Sets a flag if it has been set.
     */
    public void setMasterCode() {

        masterCode = new Peg[ROWSIZE];

        int randNum;

        for (int i = 0; i < masterCode.length; i++) {
            randNum = rand.nextInt(6);
            masterCode[i] = Peg.values()[randNum];

        }
        masterCodeFlag = true;
    }

    /**
     * Returns true if the master code was set, false otherwise.
     * @return a boolean if the MasterCode was set
     */
    public boolean isMasterCodeSet() {

        return masterCodeFlag;
    }

    /**
     * Increments the guessesMade and decrements the guessesRemain. Takes and 
     * stores the guess into the pegBoard array for viewing your submissions.
     * Takes and reports how many keyPegs you should be awarded, whether red, 
     * or white
     * @param guess an Array of Pegs submitted by the user, intending to match 
     * the MasterCode.
     */
    public void makeGuess(Peg[] guess) {

        guessCode = guess;
        guessesMade++;
        guessesRemain--;
        //inserts the guess into the pegBoard Array
        for (int i = 3; i >= 0; i--) {
            pegBoard[lastPegPosition] = guessCode[i];
            lastPegPosition -= 1;
        }
        
        ArrayList<Peg> guessList = new ArrayList<>(Arrays.asList(guessCode));
        ArrayList<Peg> masterList = new ArrayList<>(Arrays.asList(masterCode));
        for (int i = guessList.size() - 1; i >= 0; i--) {
            if (guessList.get(i).equals(masterList.get(i))) {
                masterList.remove(i);
                guessList.remove(i);
                redKeyCount++;
            }
        }
        
        for (int i = 0; i < guessList.size(); i++) {
            if (masterList.contains(guessList.get(i))) {
                masterList.remove(guessList.get(i));
                whiteKeyCount++;
            }
        }
        
        if (redKeyCount == 4) {
            gameStatus = Status.WIN;
        }
    }

    /**
     * Returns what the user's Status is during gamePlay. PLAY | WIN | LOSE
     * @return the user's status PLAY || WIN || LOSE
     */
    public Status getGameStatus() {

        return gameStatus;
    }

    /**
     * Processes the keyPegs that were set by the guess, giving the user an 
     * array of keyPegs that aren't in any corresponding order. Also puts the 
     * values into the keyBoard array for continued viewing.
     * @return an array of keyPegs depending on correct choices made by the 
     * user's guess.
     */
    public KeyPeg[] getKeyPegArray() {
        
        for (int i = 0; i < keyPegArray.length; i++) {

            if (redKeyCount > 0) {
                keyPegArray[i] = KeyPeg.R;
                redKeyCount -= 1;
            } else if (whiteKeyCount > 0) {
                keyPegArray[i] = KeyPeg.Wh;
                whiteKeyCount -= 1;
            } else {
                keyPegArray[i] = KeyPeg.NoKey;

            }

        }
        //puts the keyPegs into an array for displaying
        //also changes the game status to LOSE if the user gets to Zero
        for (int i = 3; i >= 0; i--) {
            
            if (lastKeyPosition == 0) {
            gameStatus = Status.LOSE;
            }else{
                keyBoard[lastKeyPosition] = keyPegArray[i];
                lastKeyPosition -= 1;
            }
        }
        
        return keyPegArray;
    }

}
