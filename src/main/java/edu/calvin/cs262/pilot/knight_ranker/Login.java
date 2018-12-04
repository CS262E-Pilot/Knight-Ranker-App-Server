package edu.calvin.cs262.pilot.knight_ranker;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.config.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

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

/**
 * This class handles the login for clients.
 */
public class Login {
    private static final String CLIENT_ID = "192654854744-u69k627oqgsl18jfho5a8o3435umuevk.apps.googleusercontent.com";

    private static final JacksonFactory jsonFactory = new JacksonFactory();

    private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

    /**
     * POST
     * This method will login a player. If a player's email is not already
     * registered with the database, a new player is created.
     *
     * @param idTokenString the token that the client has received from Google
     * @return new player entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path = "login", httpMethod = POST)
    public Token login(@Named("idToken") String idTokenString) throws SQLException {
        if (idTokenString != null) {
            try {
                GoogleIdToken idToken = verifier.verify(idTokenString);
                if (idToken != null) {
                    Player player = new Player();
                    GoogleIdToken.Payload payload = idToken.getPayload();

                    // Get profile information from payload
                    /*String email = payload.getEmail();
                    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                    String name = (String) payload.get("name");
                    String pictureUrl = (String) payload.get("picture");
                    String locale = (String) payload.get("locale");
                    String familyName = (String) payload.get("family_name");
                    String givenName = (String) payload.get("given_name");*/

                    // Set the email of our player
                    player.setEmailAddress(payload.getEmail());
                    player.setName((String) payload.get("name"));
                    Connection connection = null;
                    Statement statement = null;
                    ResultSet resultSet = null;
                    try {
                        connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                        statement = connection.createStatement();
                        // Check if the player already has been registered
                        resultSet = findOrCreatePlayer(player, statement);
                        if (resultSet.next()) {
                            // Create and return the token
                            player.setId(Integer.parseInt(resultSet.getString(1)));
                            String token = SecureTokenGenerator.nextToken();
                            insertToken(token, player, statement);
                            return new Token(token);
                        } else {
                            throw new SQLException("Failed to add player to database");
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
                } else {
                    System.out.println("Invalid ID token.");
                }

            } catch (IOException | GeneralSecurityException e) {
                System.out.println(e.getLocalizedMessage());
            }

        }
        return null;
    }

    private ResultSet findOrCreatePlayer(Player player, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Player VALUES (DEFAULT, '%s', NOW(), '%s') ON CONFLICT DO NOTHING",
                        player.getEmailAddress(),
                        player.getName()
                )
        );
        return statement.executeQuery(String.format("SELECT * FROM Player WHERE emailAddress = '%s'", player.getEmailAddress()));
    }

    private void insertToken(String token, Player player, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO PlayerToken VALUES ('%s', %d)",
                        token,
                        player.getId()
                )
        );
    }
}
