package edu.calvin.cs262.pilot.knight_ranker;

/**
 * This class implements a Sport Data-Access Object (DAO) class for the Sport relation.
 * This provides an object-oriented way to represent and manipulate sport "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class Sport {

    private int id;
    private String type, name;


    public Sport() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }

    public Sport(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}