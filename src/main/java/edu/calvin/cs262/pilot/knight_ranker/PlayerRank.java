package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Player Data-Access Object (DAO) class for the Player relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class PlayerRank {

    private int eloRank;
    private String name;


    public PlayerRank() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public PlayerRank(int eloRank, String name) {
        this.eloRank = eloRank;
        this.name = name;
    }

    public int getEloRank() {
        return eloRank;
    }

    public void setRank(int eloRank) {
        this.eloRank = eloRank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}