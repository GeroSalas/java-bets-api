# java-bets-api

The backend of FootyBetsApp is based on a JSON REST API to serve multiple requestor Clients, working like BaaS. <br />
The architecture was build on the following JavaEE Stack: Spring MVC Framework + JPA (Hibernate ORM) + MySQL DB <br />
As you can see, is a Maven project so all the dependencies that are used by this code was defined in the manager file (pom.xml) <br />

To login and have access on the Application you need to send a request like this: <br />

 Endpoint URL: (POST) http://java-bets-api.herokuapp.com/api/login/  <br />
 (Header: 'X-BetsAPI-Key: PRO10590E8C8N2E482FB37A254275C2016 , Content-Type: application/json') <br />
 Body:  
  {
   "username": "example@mailinator.com",
   "password": "Secret123"
  }
 
 <br /> 
 If success will return an 'X-AUTH-TOKEN' to use as 'Authorization Header' on any requests to access secure API resources. <br />
 For example, if you want now to retrieve the complete fixture of 'JJOO 2016' you need to build and execute this HTTP request: <br />
  Endpoint URL: (GET) http://java-bets-api.herokuapp.com/api/v1a/matches  <br />
  That will returns a long JSON response will all the matches scheduled for the fixture coming soon on June 2016. <br /><br />
  
  To contact support email: geronimo.perez.salas@gmail.com

 
