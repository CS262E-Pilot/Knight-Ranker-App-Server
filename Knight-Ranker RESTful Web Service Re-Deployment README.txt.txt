Short Guide on how to re-deploy to Google Cloud Platform
======================================================================
Author: Joseph Jinn
version 1.1
======================================================================
Changelog:

-minor edit in how to test if API works.
======================================================================
======================================================================
Link to Server GitHub Repository:

https://github.com/CS262E-Pilot/knight-rank-server
======================================================================

Note: This guide assumes you have installed Java JDK, Google Cloud SDK, IntelliJ IDEA, etc.
Note2: This guide assumes you have done the preliminary setup for working with
	Google Cloud Platform.

Note3: If note 1 and note 2 do not apply to you, refer to the README.md in the base
	directory of the IntelliJ IDEA project files.
======================================================================

1.  Open a Google Cloud SDK Shell.

2.  Navigate (cd....) into the base directory containing the IntelliJ IDEA project files.

3.  Type: "mvn clean package" (excluding the double quotes)

	-If any "ERROR" lines pop up, you broke something.

4.  Type: "mvn endpoints-framework:openApiDocs" (excluding the double quotes)

	-If any "ERROR" lines pop up, you broke something.

5.  Type: "gcloud endpoints services deploy target/openapi-docs/openapi.json" (excluding the double quotes)

	-If any "ERROR" lines pop up, you broke something.

6.  Type: "mvn appengine:run" (excluding the double quotes)

	-If any "ERROR" lines pop up, you broke something.

7.  Go to: https://console.cloud.google.com/home/dashboard?project=calvin-cs262-fall2018-pilot

8.  Click the Navigation Menu --> App Egnine --> Version --> *will list all uploaded versions*

9.  Delete any previous/older versions. (will know by the date/time stamp)

10.  The most recent uploaded version should by default by active with 100% Traffice Allocation.

11.  Done!!!

======================================================================
======================================================================
This is a example of when you broke something:


[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.892 s
[INFO] Finished at: 2018-11-02T19:03:06-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal com.google.cloud.tools:endpoints-framework-maven-plugin:1.0.2:openApiDocs (default-cli) on project knight_ranker: Endpoints Tool Error: Multiple conflicting definitions found for issuer firebase -> [Help 1]
org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute goal com.google.cloud.tools:endpoints-framework-maven-plugin:1.0.2:openApiDocs (default-cli) on project knight_ranker: Endpoints Tool Error

======================================================================
======================================================================
This is a example of when you did NOT break something:

[INFO] GCLOUD: Beginning deployment of service [default]...
[INFO] GCLOUD: #============================================================#
[INFO] GCLOUD: #= Uploading 4 files to Google Cloud Storage                =#
[INFO] GCLOUD: #============================================================#
[INFO] GCLOUD: File upload done.
[INFO] GCLOUD: Updating service [default]...
[INFO] GCLOUD: ........done.
[INFO] GCLOUD: Setting traffic split for service [default]...
[INFO] GCLOUD: .......done.
[INFO] GCLOUD: Stopping version [calvin-cs262-fall2018-pilot/default/20181102t182837].
[INFO] GCLOUD: Sent request to stop version [calvin-cs262-fall2018-pilot/default/20181102t182837]. This operation may take some time to complete. If you would like to verify that it succeeded, run:
[INFO] GCLOUD:   $ gcloud app versions describe -s default 20181102t182837
[INFO] GCLOUD: until it shows that the version has stopped.
[INFO] GCLOUD: Deployed service [default] to [https://calvin-cs262-fall2018-pilot.appspot.com]
[INFO] GCLOUD:
[INFO] GCLOUD: You can stream logs from the command line by running:
[INFO] GCLOUD:   $ gcloud app logs tail -s default
[INFO] GCLOUD:
[INFO] GCLOUD: To view your application in the web browser run:
[INFO] GCLOUD:   $ gcloud app browse
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 33.484 s
[INFO] Finished at: 2018-11-02T19:05:58-04:00
[INFO] ------------------------------------------------------------------------
PS D:\Dropbox\CS262_TeamE_Server\KnightRanker_RESTful_Prototype>

======================================================================
======================================================================
As of the writing of v1.0 of this guide, you can test if your API works by:

https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/INSERT ENDPOINT HERE

Example: (say we have the method below)

To test that the below method works, you would use the URL:

https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/players

Note the "players" portion of the URL; this is obtained from:

    @ApiMethod(path="players", httpMethod=GET)

In other words, the (path = "SOME STRING OR ANOTHER"

Note: For more in-depth instructions, refer to the Comments Header in PlayerResource.java
======================================================================

  /**
     * GET
     * This method gets the full list of players from the Player table.
     *
     * @return JSON-formatted list of player records (based on a root JSON tag of "items")
     * @throws SQLException
     */
    @ApiMethod(path="players", httpMethod=GET)
    public List<Player> getPlayers() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Player> result = new ArrayList<Player>();
        try {
            connection = DriverManager.getConnection(System.getProperty("cloudsql"));
            statement = connection.createStatement();
            resultSet = selectPlayers(statement);
            while (resultSet.next()) {
                Player p = new Player(
                        Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                result.add(p);
            }
        } catch (SQLException e) {
            throw(e);
        } finally {
            if (resultSet != null) { resultSet.close(); }
            if (statement != null) { statement.close(); }
            if (connection != null) { connection.close(); }
        }
        return result;
    }

======================================================================
======================================================================
======================================================================