package edu.calvin.cs262.pilot.knight_ranker;

import java.sql.*;

/**
 * This class implements a Match Data-Access Object (DAO) class for the Match relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Knight-Ranker database.
 */
public class TokenVerifier {
    public static Player getPlayer(String token) throws SQLException, SecurityException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = findPlayerFromToken(token, statement);
            if (resultSet.next()) {
                return new Player(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            } else {
                // If we can't find a token, that means somebody is sending invalid tokens
                throw new SecurityException("Invalid token");
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
    }

    /**
     * Get a player who is associated with a particular token
     * @param token
     * @param statement
     * @return ResultSet
     * @throws SQLException
     */
    private static ResultSet findPlayerFromToken(String token, Statement statement) throws SQLException {
        return statement.executeQuery(
                String.format("SELECT Player.ID, Player.emailAddress, Player.accountCreationDate, Player.name " +
                        "FROM PlayerToken, Player " +
                        "WHERE token = '%s' " +
                        "AND playerID = Player.ID", token)
        );
    }
}
