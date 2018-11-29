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
public class SportResource {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET
     * This method gets the full list of sports from the Sport table.
     *
     * @return JSON-formatted list of sport records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path = "sports", httpMethod = GET)
    public List<Sport> getSports() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Sport> result = new ArrayList<Sport>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectSports(statement);
            while (resultSet.next()) {
                Sport p = new Sport(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3)
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
     * This method gets the sport from the Sport table with the given ID.
     *
     * @param id the ID of the requested sport
     * @return if the sport exists, a JSON-formatted sport record, otherwise an invalid/empty JSON entity
     * @throws SQLException
     */
    @ApiMethod(path = "sport/{id}", httpMethod = GET)
    public Sport getSport(@Named("id") int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Sport result = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectSport(id, statement);
            if (resultSet.next()) {
                result = new Sport(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3)
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

    /**
     * SQL Utility Functions
     *********************************************/

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function gets the sport with the given id using the given JDBC statement.
     */
    private ResultSet selectSport(int id, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT * FROM Sport WHERE id=%d", id)
        );
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////


    /*
     * This function gets the sports using the given JDBC statement.
     */
    private ResultSet selectSports(Statement statement) throws SQLException {
        return statement.executeQuery(
                "SELECT * FROM Sport"
        );
    }

}
