package edu.calvin.cs262.pilot.knight_ranker;

/**
 * The client data that is sent to record a match
 */
public class NewMatch {

    private int opponentID, playerScore, opponentScore;
    private String sport;


    public NewMatch() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public NewMatch(String sport, int opponentID, int playerScore, int opponentScore) {
        this.sport = sport;
        this.opponentID = opponentID;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
    }

    public int getOpponentID() {
        return opponentID;
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

    public void setOpponentID(int opponentID) {
        this.opponentID = opponentID;
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
}
