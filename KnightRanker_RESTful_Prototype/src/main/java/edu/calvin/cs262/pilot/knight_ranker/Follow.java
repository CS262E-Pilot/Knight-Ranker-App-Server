package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Follow Data-Access Object (DAO) class for the Follow relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class Follow {

    //private int id;
    private int mySportID, myPlayerID, myRank;

    public Follow() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Follow(int sportID, int playerID, int rank) {
        //this.id = id;
        this.mySportID = sportID;
        this.myPlayerID = playerID;
        this.myRank = rank;
    }

//    public int getID() {
//        return this.id;
//    }
//
//    public void setID(int id) {
//        this.id = id;
//    }

    public int getSportID() {

        return this.mySportID;
    }

    public void setSportID(int sportID) {
        this.mySportID = sportID;
    }

    public int getPlayerID() {
        return this.myPlayerID;
    }

    public void setPlayerID(int playerID) {
        this.myPlayerID = playerID;
    }

    public int getRank() {
        return this.myRank;
    }

    public void setRank(int rank) {
        this.myRank = rank;
    }
}
