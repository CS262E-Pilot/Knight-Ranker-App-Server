package edu.calvin.cs262.pilot.knight_ranker;

/**
 * The data necessary for a user to confirm a match
 */
public class ConfirmMatch {

    private int id;
    private int playerScore, opponentScore;
    private String sport, playerName, myTime;


    public ConfirmMatch() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public ConfirmMatch(int id, String sport, String playerName, int playerScore, int opponentScore, String time) {
        this.id = id;
        this.sport = sport;
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.myTime = time;
    }

    public int getId() {
        return id;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public String getSport() {
        return sport;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMyTime() {
        return myTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setMyTime(String myTime) {
        this.myTime = myTime;
    }
}
