# Simple SPORT SCOREBOARD Application using Spring Boot + H2 Database

### Use Java8 or higher version

## Building app 
1. Compile and Build 
   `./gradlew clean build`  
 
## Running spring boot app locally  
1. Bring up app  
   `./gradlew bootRun`  
   
   This will start the app locally at port:8080

## Rest API
Once the spring boot app is running, then you can use the service as below
###1. To Create score board   

   URL : `localhost:8080/soccer/scoreboard/`

   HTTP-Method: `POST`

   Json-body: ` {
   "homeTeamName": "Arsenal",
   "awayTeamName": "Chelsea"
   } `

  Copy the response of this api which gives unique UUID, which can be used to update the score or get latest score
  
###2. To Update the Scoreboard
   URL : `localhost:8080/soccer/scoreboard/`

   Http-Method: `PUT`

   Json-Body: ` {
   "id": "cae54dd5-e7b5-4138-8b48-b7b38dce2952",
   "homeTeamName": "Arsenal",
   "homeTeamScore": 5,
   "awayTeamName": "Chelsea",
   "awayTeamScore": 0
   }`
   
###3. To Get match score

   URL: `localhost:8080/soccer/scoreboard/cae54dd5-e7b5-4138-8b48-b7b38dce2952`

   Http-Method: `GET`
   