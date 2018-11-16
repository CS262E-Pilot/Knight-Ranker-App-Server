package edu.calvin.cs262.pilot.knight_ranker;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.config.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

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
    private static final JacksonFactory jsonFactory = new JacksonFactory();

    private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList("192654854744-6khc09kjs2ctrb8sqeoips5gpgmg5fgo.apps.googleusercontent.com"))
            .build();


    /**
     * POST
     * This method will login a player. If a player's email is not already
     * registered with the database, a new player is created.
     * @param idTokenString the token that the client has received from Google
     * @return new player entity with a system-generated ID
     * @throws SQLException
     */
    @ApiMethod(path = "login", httpMethod = POST)
    public Player login(@Named("idToken") String idTokenString) throws SQLException {
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
                    Connection connection = null;
                    Statement statement = null;
                    ResultSet resultSet = null;
                    try {
                        connection = DriverManager.getConnection(System.getProperty("cloudsql"));
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery("SELECT MAX(ID) FROM Player");
                        if (resultSet.next()) {
                            player.setId(resultSet.getInt(1) + 1);
                        } else {
                            throw new RuntimeException("failed to find unique ID...");
                        }
                        insertPlayer(player, statement);
                        return player;
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

            } catch (GeneralSecurityException | IOException e) {
                System.out.println(e.getLocalizedMessage());
            }

        }
        return null;
    }

    private void insertPlayer(Player player, Statement statement) throws SQLException {
        statement.executeUpdate(
                String.format("INSERT INTO Player VALUES (%d, '%s')",
                        player.getId(),
                        player.getEmailAddress()
                )
        );
    }
}
