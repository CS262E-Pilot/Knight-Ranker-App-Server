package edu.calvin.cs262.pilot.knight_ranker;

import com.google.api.server.spi.config.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;

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

public class LeaderboardResource {
    private static final Logger log = Logger.getLogger(LeaderboardResource.class.getName());
    /**
     * GET
     * Gets the leaderboard for a given sport
     * @return JSON-formatted list of PlayerRanks
     * @throws SQLException
     */
    @ApiMethod(path = "leaderboard", httpMethod = GET)
    public List<PlayerRank> getLeaderboard (@Named("sport") String sport) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<PlayerRank> result = new ArrayList<>();
        try {
            log.info("Sport:" + sport);
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectPlayerRanks(statement, sport);
            log.info("Results: " + resultSet.toString());
            while (resultSet.next()) {
                PlayerRank p = new PlayerRank(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2)
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

    /**
     * Gets the leaderboard for a given sport
     * @param statement
     * @param sport
     * @return
     * @throws SQLException
     */
    private ResultSet selectPlayerRanks(Statement statement, String sport) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT SportRank.eloRank, Player.name " +
                                "FROM Player, Sport, SportRank " +
                                "WHERE Sport.name = '%s' " +
                                "AND SportRank.sportID = Sport.id " +
                                "AND SportRank.playerID = Player.id " +
                                "ORDER BY SportRank.eloRank ASC",
                    sport
                )
        );
    }
}
