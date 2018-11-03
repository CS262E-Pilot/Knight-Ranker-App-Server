Knight-Ranker RESTful Web Service API README.txt

-Short guide on how to use RESTful web service API and CRUD operations.
=======================================================================================

Author: Joseph Jinn
version v1.0.

=======================================================================================
ChangeLog:

-Resolved how to use all CRUD operations in GitBash for windows (remove all forward slashes)

-Modified POST/PUT for Match and Follow as failed to insert proper values when using
	capital first letter for each column variable name.

=======================================================================================
=======================================================================================
Currently working CRUD commands and Endpoints:
=======================================================================================
=======================================================================================
=======================================================================================
IMPORTANT NOTE: GET/DELETE work in Windows Command Shell but not PUT/POST (formatting issues)
	-Remove all "\" forward slashes for these commands in Windows Command Shell

IMPORTANT NOTE: GET/DELETE/PUT/POST work fine in GitBash for Windows as long as you remove all
	forward slashes from the below commands.

IMPORTANT NOTE: Results may depend on user's local machine and build/set-up for shells.

Summary: Depending on which shell and how you set up that shell, you may or may not get
	all CRUD operations working, as different shells requires different formats for
	these queries.
=======================================================================================
=======================================================================================
=======================================================================================
Player Table CRUD Operations
=======================================================================================
=======================================================================================
curl --request POST \
         --header "Content-Type: application/json" \
         --data '{"accountCreationDate":"2018-11-03 00:53:57.048546", "emailAddress":"test email..."}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player

Note: accountCreationDate must be a correctly formatted "time" type in PostgreSQL
Note: adds a new tuple with automatically assigned ID

=======================================================================================
curl --request PUT \
         --header "Content-Type: application/json" \
         --data '{"accountCreationDate":"2019-11-03 00:53:57.048546", "emailAddress":"new test email..."}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/ID

where ID = valid id of a existing player

=======================================================================================
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/ID

where ID = valid id of a existing player

=======================================================================================
curl --request GET \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/players

=======================================================================================
=======================================================================================
=======================================================================================
Sport Table CRUD Operations
=======================================================================================
=======================================================================================
curl --request POST \
         --header "Content-Type: application/json" \
         --data '{"name":"test name...", "type":"test type..."}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport

Note: adds a new tuple with automatically assigned ID

=======================================================================================
curl --request PUT \
         --header "Content-Type: application/json" \
         --data '{"name":"new test name...", "type":"new test type..."}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/ID

where ID = valid id of a existing sport

=======================================================================================
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/ID

where ID = valid id of a existing sport

=======================================================================================
curl --request GET \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sports

=======================================================================================
=======================================================================================
=======================================================================================
Match Table CRUD Operations
=======================================================================================
=======================================================================================
curl --request POST \
         --header "Content-Type: application/json" \
         --data '{"sportID":"1", "playerOneID":"1", "playerTwoID":"2", "playerOneScore":"10", "playerTwoScore":"20", "winner":"2", "time":"2018-11-03 00:53:57.048546", "verified":"true"}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/match

Note: time must be a correctly formatted "time" type in PostgreSQL
Note: adds a new tuple with automatically assigned ID

=======================================================================================
curl --request PUT \
         --header "Content-Type: application/json" \
         --data '{"sportID":"2", "playerOneID":"3", "playerTwoID":"4", "playerOneScore":"30", "playerTwoScore":"40", "winner":"4", "time":"2019-11-03 00:53:57.048546", "verified":"false"}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/match/ID

Note: time must be a correctly formatted "time" type in PostgreSQL
where ID = valid id of a existing match

=======================================================================================
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/match/ID

where ID = valid id of a existing match

=======================================================================================
curl --request GET \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/matches

=======================================================================================
=======================================================================================
=======================================================================================
Follow Table CRUD Operations
=======================================================================================
=======================================================================================
curl --request POST \
         --header "Content-Type: application/json" \
         --data '{"sportID":"1", "playerID":"1", "rank":"1"}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/follow

Note: adds a new tuple with automatically assigned ID

=======================================================================================
curl --request PUT \
         --header "Content-Type: application/json" \
         --data '{"sportID":"2", "playerID":"2", "rank":"2"}' \
         https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/follow/ID

where ID = valid id of a existing follow

=======================================================================================
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/follow/ID

where ID = valid id of a existing follow

=======================================================================================
curl --request GET \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/follows

=======================================================================================
=======================================================================================
=======================================================================================