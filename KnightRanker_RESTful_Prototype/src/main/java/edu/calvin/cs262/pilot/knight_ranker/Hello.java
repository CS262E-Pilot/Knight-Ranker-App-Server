package edu.calvin.cs262.pilot.knight_ranker;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;

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
 * This class implements a simple hello-world endpoint using Google Endpoints.
 */
public class Hello {

    /**
     * This method returns a simple hello-world message.
     * N.b., a Google Endpoint must return an entity (not, e.g., a String).
     *
     * @return a hello-world entity in JSON format
     */
    @ApiMethod(httpMethod = GET)
    public Player hello() {
        return new Player(-1, "Hello, endpoints!", null);
    }

}
