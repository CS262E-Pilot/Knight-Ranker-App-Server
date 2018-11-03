Knight-Ranker RESTful Web Service API README.txt
=======================================================================================
Author: Joseph Jinn
version 0.2 beta.

=======================================================================================
ChangeLog:

-Resolved how to use all CRUD operations in GitBash for windows (remove all forward slashes)

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
Sport Table CRUD Operations
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
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/player/8

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

where ID = valid id of a existing player
=======================================================================================
curl --request DELETE \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sport/5

=======================================================================================
curl --request GET \ https://calvin-cs262-fall2018-pilot.appspot.com/knightranker/v1/sports

=======================================================================================
=======================================================================================
=======================================================================================