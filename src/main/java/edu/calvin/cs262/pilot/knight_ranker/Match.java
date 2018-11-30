package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Match Data-Access Object (DAO) class for the Match relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class Match {

    private int id;
    private int mySportID, playerID, opponentID, playerScore, opponentScore, myWinner;
    private String myTime, myVerified;


    public Match() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Match(int id, int sportID, int playerID, int opponentID, int playerScore, int opponentScore, int winner, String time, String verified) {
        this.id = id;
        this.mySportID = sportID;
        this.playerID = playerID;
        this.opponentID = opponentID;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.myWinner = winner;
        this.myTime = time;
        this.myVerified = verified;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getSportID() {
        return this.mySportID;
    }

    public void setSportID(int sportID) {
        this.mySportID = sportID;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getOpponentID() {
        return this.opponentID;
    }

    public void setOpponentID(int opponentID) {
        this.opponentID = opponentID;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getOpponentScore() {
        return this.opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public int getWinner() {
        return this.myWinner;
    }

    public void setWinner(int winner) {
        this.myWinner = winner;
    }

    public String getTime() {
        return this.myTime;
    }

    public void setTime(String time) {
        this.myTime = time;
    }

    public String getVerified() {
        return this.myVerified;
    }

    public void setVerified(String verified) {
        this.myVerified = verified;
    }
}
