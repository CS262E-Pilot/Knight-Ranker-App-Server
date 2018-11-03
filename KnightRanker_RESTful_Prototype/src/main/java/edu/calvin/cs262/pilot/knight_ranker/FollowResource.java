package edu.calvin.cs262.pilot.knight_ranker;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.google.api.server.spi.config.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.PUT;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.DELETE;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * This Java annotation specifies the general configuration of the Google Cloud endpoint API.
 * The name and version are used in the URL: https://PROJECT_ID.appspot.com/knightranker/v1/ENDPOINT.
 * The namespace specifies the Java package in which to find the API implementation.
 * The issuers specifies boilerplate security features that we won't address in this course.
 * <p>
 * You should configure the name and namespace appropriately.
 */
@Api(
        name = "knightranker",
        version = "v1",
        namespace =
        @ApiNamespace(
                ownerDomain = "knight_ranker.pilot.cs262.calvin.edu",
                ownerName = "knight_ranker.pilot.cs262.calvin.edu",
                packagePath = ""
        ),
        issuers = {
                @ApiIssuer(
                        name = "firebase",
                        issuer = "https://securetoken.google.com/calvin-cs262-fall2018-pilot",
                        jwksUri =
                                "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system"
                                        + ".gserviceaccount.com"
                )
        }
)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * This class implements a RESTful service for the player table of the monopoly database.
 * Only the player and sport relations are supported, not the match or follow relations.
 *
 * You can test the GET endpoints using a standard browser.
 *
 * % curl --request GET \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/players
 *
 * % curl --request GET \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sports
 *
 * % curl --request GET \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/1
 *
 * % curl --request GET \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/1
 *
 * You can test the full REST API using the following sequence of cURL commands (on Linux):
 * (Run get-players between each command to see the results.)
 *
 * // Add a new player (probably as unique generated ID #N).
 * % curl --request POST \
 *    --header "Content-Type: application/json" \
 *    --data '{"accountCreationDate":"2018-11-03 00:53:57.048546", "emailAddress":"test email..."}' \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player
 *
 * // Add a new sport (probably as unique generated ID #N).
 * % curl --request POST \
 *    --header "Content-Type: application/json" \
 *    --data '{"name":"test name...", "type":"test type..."}' \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport
 *
 * // Edit the new player (assuming ID #1).
 * % curl --request PUT \
 *    --header "Content-Type: application/json" \
 *    --data '{"accountCreationDate":"2019-11-03 00:53:57.048546", "emailAddress":"new test email..."}' \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/8
 *
 * // Edit the new sport (assuming ID #1).
 * % curl --request PUT \
 *    --header "Content-Type: application/json" \
 *    --data '{"name":"new test name...", "type":"new test type..."}' \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/5
 *
 * // Delete the new player (assuming ID #1).
 * % curl --request DELETE \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/8
 *
 * // Delete the new sport (assuming ID #1).
 * % curl --request DELETE \
 *    https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/5
 */
public class FollowResource {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET
     * This method gets the full list of follows from the Follow table.
     *
     * @return JSON-formatted list of follow records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path = "follows", httpMethod = GET)
    public List<Follow> getFollows() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Follow> result = new ArrayList<Follow>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectFollows(statement);
            while (resultSet.next()) {
                Follow p = new Follow(
                        Integer.parseInt(resultSet.getString(1)),
                        Integer.parseInt(resultSet.getString(2)),
                        Integer.parseInt(resultSet.getString(3)),
                        Integer.parseInt(resultSet.getString(4))
                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET
     * This method gets the follow from the Follow table with the given id.
     *
     * @param id the id of the requested follow
     * @return if the follow exists, a JSON-formatted sport record, otherwise an invalid/empty JSON entity
     * @throws SQLException
     */
    @ApiMethod(path = "follow/{id}", httpMethod = GET)
    public Follow getFollow(@Named("id") int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Follow result = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectFollow(id, statement);
            if (resultSet.next()) {
                result = new Follow(
                        Integer.parseInt(resultSet.getString(1)),
                        Integer.parseInt(resultSet.getString(2)),
                        Integer.parseInt(resultSet.getString(3)),
                        Integer.parseInt(resultSet.getString(4))
                );
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * PUT
     * This method creates/updates an instance of Follow with a given ID.
     * If the follow doesn't exist, create a new follow using the given field values.
     * If the follow already exists, update the fields using the new follow field values.
     * We do this because PUT is idempotent, meaning that running the same PUT several
     * times is the same as running it exactly once.
     * Any follow ID value set in the passed follow data is ignored.
     *
     * @param id     the ID for the follow, assumed to be unique
     * @param follow a JSON representation of the follow; The id parameter overrides any id specified here.
     * @return new/updated follow entity
     * @throws SQLException
     */
    @ApiMethod(path = "follow/{id}", httpMethod = PUT)
    public Follow putFollow(Follow follow, @Named("id") int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            follow.setID(id);
            resultSet = selectFollow(id, statement);
            if (resultSet.next()) {
                updateFollow(follow, statement);
            } else {
                insertFollow(follow, statement);
            }
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return follow;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * POST
     * This method creates an instance of Follow with a new, unique ID
     * number. We do this because POST is not idempotent, meaning that running
     * the same POST several times creates multiple objects with unique IDs but
     * otherwise having the same field values.
     * <p>
     * The method creates a new, unique ID by querying the follow table for the
     * largest ID and adding 1 to that. Using a DB sequence would be a better solution.
     * This method creates an instance of Follow with a new, unique ID.
     *
     * @param follow a JSON representation of the follow to be created
     * @return new follow entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path = "follow", httpMethod = POST)
    public Follow postFollow(Follow follow) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(ID) FROM Follow");
            if (resultSet.next()) {
                follow.setID(resultSet.getInt(1) + 1);
            } else {
                throw new RuntimeException("failed to find unique ID...");
            }
            insertFollow(follow, statement);
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return follow;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * DELETE
     * This method deletes the instance of Follow with a given ID, if it exists.
     * If the follow with the given ID doesn't exist, SQL won't delete anything.
     * This makes DELETE idempotent.
     *
     * @param id the ID for the follow, assumed to be unique
     * @return the deleted follow, if any
     * @throws SQLException
     */
    @ApiMethod(path = "follow/{id}", httpMethod = DELETE)
    public void deleteFollow(@Named("id") int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            deleteFollow(id, statement);
        } catch (SQLException e) {
            throw (e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * SQL Utility Functions
     *********************************************/

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function gets the follow with the given id using the given JDBC statement.
     */
    private ResultSet selectFollow(int id, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT * FROM Follow WHERE id=%d", id)
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function gets the follows using the given JDBC statement.
     */
    private ResultSet selectFollows(Statement statement) throws SQLException {
        return statement.executeQuery(
                "SELECT * FROM Follow"
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function modifies the given follow using the given JDBC statement.
     */
    private void updateFollow(Follow follow, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("UPDATE Follow SET rank=%d, PlayerID=%d, sportID=%d WHERE id=%d",
                        follow.getRank(),
                        follow.getPlayerID(),
                        follow.getSportID(),
                        follow.getID()
                )
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function inserts the given follow using the given JDBC statement.
     */
    private void insertFollow(Follow follow, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Follow VALUES (%d, %d, %d, %d)",
                        follow.getID(),
                        follow.getSportID(),
                        follow.getPlayerID(),
                        follow.getRank()
                )
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function deletes the follow with the given id using the given JDBC statement.
     */
    private void deleteFollow(int id, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("DELETE FROM Follow WHERE id=%d", id)
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function returns a value literal suitable for an SQL INSERT/UPDATE command.
     * If the value is NULL, it returns an unquoted NULL, otherwise it returns the quoted value.
     */
    private String getValueStringOrNull(String value) {
        if (value == null) {
            return "NULL";
        } else {
            return "'" + value + "'";
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
}
