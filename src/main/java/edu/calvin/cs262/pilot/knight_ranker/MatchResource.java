package edu.calvin.cs262.pilot.knight_ranker;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.google.api.server.spi.config.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.PUT;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

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
public class MatchResource {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET
     * This method gets the full list of matches from the ConfirmMatch table.
     *
     * @return JSON-formatted list of match records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path = "matches", httpMethod = GET)
    public List<ConfirmMatch> getMatches(@Named("token") String token) throws SQLException {
        Player player = TokenVerifier.verifyPlayer(token);
        if (player != null) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            List<ConfirmMatch> result = new ArrayList<ConfirmMatch>();
            try {
                connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                statement = connection.createStatement();
                resultSet = selectMatches(player, statement);
                while (resultSet.next()) {
                    ConfirmMatch p = new ConfirmMatch(
                            Integer.parseInt(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            Integer.parseInt(resultSet.getString(6)),
                            resultSet.getString(7)
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
        } else {
            // We couldn't verify the user to return an empty list
            return Collections.emptyList();
        }

    }

    /**
     * GET
     * This method gets the full list of matches from the ConfirmMatch table.
     *
     * @return JSON-formatted list of match records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path = "matches/confirm", httpMethod = GET)
    public List<ConfirmMatch> getConfirmMatches(@Named("token") String token) throws SQLException {
        Player player = TokenVerifier.verifyPlayer(token);
        if (player != null) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            List<ConfirmMatch> result = new ArrayList<ConfirmMatch>();
            try {
                connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                statement = connection.createStatement();
                resultSet = selectConfirmedMatches(player, statement);
                while (resultSet.next()) {
                    ConfirmMatch p = new ConfirmMatch(
                            Integer.parseInt(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            Integer.parseInt(resultSet.getString(6)),
                            resultSet.getString(7)
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
        } else {
            // We couldn't verify the user to return an empty list
            return Collections.emptyList();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * GET
     * This method gets the sport from the ConfirmMatch table with the given ID.
     *
     * @param id the ID of the requested match
     * @return if the match exists, a JSON-formatted sport record, otherwise an invalid/empty JSON entity
     * @throws SQLException
     */
    @ApiMethod(path = "match/{id}", httpMethod = GET)
    public ConfirmMatch getMatch(@Named("id") int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ConfirmMatch result = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectMatch(id, statement);
            if (resultSet.next()) {
                result = new ConfirmMatch(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        Integer.parseInt(resultSet.getString(5)),
                        Integer.parseInt(resultSet.getString(6)),
                        resultSet.getString(7)
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
     * Confirms a match and updates the elo rankings
     * @param token the user token
     * @param matchID the ID of the match to confirm
     * @return new/updated match entity
     * @throws SQLException
     */
    @ApiMethod(path = "match/{id}", httpMethod = PUT)
    public ResultStatus putMatch(@Named("token") String token, @Named("id") int matchID) throws SQLException {
        Player player = TokenVerifier.verifyPlayer(token);
        if (player != null) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                statement = connection.createStatement();
                // Confirm that the match happened
                resultSet = confirmMatch(player, matchID, statement);
                if (resultSet.next()) {
                    // Build the match object
                    Match match = new Match(
                            Integer.parseInt(resultSet.getString(1)),
                            Integer.parseInt(resultSet.getString(2)),
                            Integer.parseInt(resultSet.getString(3)),
                            Integer.parseInt(resultSet.getString(4)),
                            Integer.parseInt(resultSet.getString(5)),
                            Integer.parseInt(resultSet.getString(6)),
                            Integer.parseInt(resultSet.getString(7)),
                            resultSet.getString(8),
                            resultSet.getString(9)
                    );
                    // Make sure a sport rank exists for the player
                    resultSet = findOrCreateSportRank(match.getSportID(), match.getPlayerID(), statement);
                    if (resultSet.next()) {
                        int playerEloRank = Integer.parseInt(resultSet.getString(1));
                        // Make sure a sport rank exists for the opponent
                        resultSet = findOrCreateSportRank(match.getSportID(), match.getOpponentID(), statement);
                        if (resultSet.next()) {
                            int opponentEloRank = Integer.parseInt(resultSet.getString(1));
                            // Update the elo rating of the player
                            updateSportRank(match.getSportID(), match.getPlayerID(),
                                    Elo.EloRating(playerEloRank, opponentEloRank, match.getPlayerScore(), match.getOpponentScore()),
                                    statement
                            );
                            // Update hte elo rating of the opponent
                            updateSportRank(match.getSportID(), match.getOpponentID(),
                                    Elo.EloRating(opponentEloRank, playerEloRank, match.getOpponentScore(), match.getPlayerScore()),
                                    statement
                            );
                        }
                    }
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
            return new ResultStatus("Confirmed match");
        } else {
            return new ResultStatus("Invalid player");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * POST
     * Recieves NewMatch from a user and inserts it into database
     *
     * @param newMatch a JSON representation of the match to be created
     * @return new match entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path = "match", httpMethod = POST)
    public ResultStatus postMatch(NewMatch newMatch, @Named("token") String token) throws SQLException {
        Player player = TokenVerifier.verifyPlayer(token);
        if (player != null) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                statement = connection.createStatement();
                // We are only given a sport name so we have to get the id from the database
                resultSet = selectSport(newMatch.getSport(), statement);
                if (resultSet.next()) {
                    Sport sport = new Sport(
                            Integer.parseInt(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    );
                    insertMatch(sport, player, newMatch, statement);
                } else {
                    return new ResultStatus("Invalid sport");
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
            return new ResultStatus("Created match");
        } else {
            return new ResultStatus("Invalid player");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * SQL Utility Functions
     *********************************************/

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * This function gets the match with the given id using the given JDBC statement.
     */
    private ResultSet selectMatch(int id, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT Match.ID, Sport.name, Player.name, Opponent.name, Match.playerScore, " +
                                "Match.opponentScore, Match.time" +
                                "FROM Match " +
                                "INNER JOIN Player ON Match.playerID = Player.ID " +
                                "INNER JOIN Player AS Opponent ON Match.opponentID = Opponent.ID " +
                                "INNER JOIN Sport ON Match.sportID = Sport.ID" +
                                "WHERE Match.ID = %d",
                        id
                )
        );
    }

    /**
     * Gets all the unconfirmed matches for a user. Since the user is confirming the match,
     * that means the user is actually the opponent.
     * @param player
     * @param statement
     * @return
     * @throws SQLException
     */
    private ResultSet selectMatches(Player player, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT Match.ID, Sport.name, Player.name, Opponent.name, Match.playerScore, " +
                                "Match.opponentScore, Match.time " +
                                "FROM Match " +
                                "INNER JOIN Player ON Match.playerID = Player.ID " +
                                "INNER JOIN Player AS Opponent ON Match.opponentID = Opponent.ID " +
                                "INNER JOIN Sport ON Match.sportID = Sport.ID " +
                                "WHERE Match.verified = FALSE " +
                                "AND Match.opponentID = %d " +
                                "ORDER BY Match.time",
                        player.getId()
                )
        );
    }

    /**
     * Gets all the unconfirmed matches for a user. Since the user is confirming the match,
     * that means the user is actually the opponent.
     * @param player
     * @param statement
     * @return
     * @throws SQLException
     */
    private ResultSet selectConfirmedMatches(Player player, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT Match.ID, Sport.name, Player.name, Opponent.name, Match.playerScore, " +
                                "Match.opponentScore, Match.time " +
                                "FROM Match " +
                                "INNER JOIN Player ON Match.playerID = Player.ID " +
                                "INNER JOIN Player AS Opponent ON Match.opponentID = Opponent.ID " +
                                "INNER JOIN Sport ON Match.sportID = Sport.ID " +
                                "WHERE Match.verified = TRUE " +
                                "AND Match.opponentID = %d " +
                                "ORDER BY Match.time",
                        player.getId()
                )
        );
    }

    /**
     * Updates the ConfirmMatch to be verified
     * @param player
     * @param matchID
     * @param statement
     * @throws SQLException
     */
    private ResultSet confirmMatch(Player player, int matchID, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("UPDATE Match SET verified = TRUE " +
                                "WHERE ID = %d " +
                                "AND opponentID = %d"+
                        matchID,
                        player.getId()
                )
        );
        return selectMatch(matchID, statement);
    }

    /*
     * This function inserts the given match using the given JDBC statement.
     */
    private void insertMatch(Sport sport, Player player, NewMatch newMatch, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Match VALUES (DEFAULT, %d, %d, %d, %d, %d, %d, NOW(), FALSE)",
                        sport.getId(),
                        player.getId(),
                        newMatch.getOpponentID(),
                        newMatch.getPlayerScore(),
                        newMatch.getOpponentScore(),
                        player.getId()
                )
        );
    }

    private ResultSet findOrCreateSportRank(int sportId, int playerId, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO SportRank VALUES (DEFAULT, %d, %d, %d) ON CONFLICT DO NOTHING", sportId, playerId, Elo.StartingRank)
        );
        return statement.executeQuery(String.format("SELECT eloRank FROM SportRank WHERE sportID=%d AND playerID=%d", sportId, playerId));
    }

    private void updateSportRank(int sportId, int playerId, int elo, Statement statement) throws SQLException {
        statement.executeUpdate(String.format("UPDATE SportRank SET eloRank=%d WHERE sportID=%d playerID=%d", elo, sportId, playerId));
    }

    private ResultSet selectSport(String name, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT * FROM Sport WHERE Sport.name = '%s'", name)
        );
    }

}
