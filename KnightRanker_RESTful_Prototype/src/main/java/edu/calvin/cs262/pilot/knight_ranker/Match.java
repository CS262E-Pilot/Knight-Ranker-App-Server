package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Match Data-Access Object (DAO) class for the Match relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class Match {

    private int id;
    private int mySportID, myPlayerOneID, myPlayerTwoID, myPlayerOneScore, myPlayerTwoScore, myWinner;
    private String myTime, myVerified;


    public Match() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Match(int id, int sportID, int playerOneID, int playerTwoID, int playerOneScore, int playerTwoScore, int winner, String time, String verified) {
        this.id = id;
        this.mySportID = sportID;
        this.myPlayerOneID = playerOneID;
        this.myPlayerTwoID = playerTwoID;
        this.myPlayerOneScore = playerOneScore;
        this.myPlayerTwoScore = playerTwoScore;
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

    public int getPlayerOneID() {
        return this.myPlayerOneID;
    }

    public void setPlayerOneID(int playerOneID) {
        this.myPlayerOneID = playerOneID;
    }

    public int getPlayerTwoID() {
        return this.myPlayerTwoID;
    }

    public void setPlayerTwoID(int playerTwoID) {
        this.myPlayerTwoID = playerTwoID;
    }

    public int getPlayerOneScore() {
        return this.myPlayerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.myPlayerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return this.myPlayerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.myPlayerTwoScore = playerTwoScore;
    }

    public int getWinner() {
        return this.myWinner;
    }

    public void setWinner(int winner) {
        this.myWinner = winner;
    }

    public String geTime() {
        return this.myTime;
    }

    public void seTime(String time) {
        this.myTime = time;
    }

    public String getVerified() {
        return this.myVerified;
    }

    public void setVerified(String verified) {
        this.myVerified = verified;
    }
}
