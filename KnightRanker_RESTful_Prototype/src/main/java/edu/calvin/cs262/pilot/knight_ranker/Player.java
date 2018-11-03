package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Player Data-Access Object (DAO) class for the Player relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class Player {

    private int id;
    private String emailAddress, accountCreationDate;


    public Player() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Player(int id, String emailAddress, String accountCreationDate) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.accountCreationDate = accountCreationDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAccountCreationDate() {
        return this.accountCreationDate;
    }

    public void setAccountCreationDate(String accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

}