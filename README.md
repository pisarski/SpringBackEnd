# SpringSampleApp

This is sample application which is used to play with technology.

After each merge, content is deployed to http://tomcat-pisarski.rhcloud.com/SpringSampleApp. It is deployed to openshift on free account, so it will go idle after 24 hours. When you access mentioned link you can get 503 Service Unavaliable. Just refresh page once again and application should respons (openshift will start VM).

It was inspired by [Spring in Action 4th Edition](http://www.amazon.com/Spring-Action-Craig-Walls/dp/161729120X)

# Running locally

To run this project locally, please download tomcat 7 and do the following:
 1. create directory _certs_ in tomcat main directory
 2. in _certs_ directory invoke:		
	
 Windows:

 `"%JAVA_HOME%\bin\keytool.exe" -genkey -alias SpringSampleApp -keyalg RSA -keystore keystore`

 Unix:

 `$JAVA_HOME/bin/keytool -genkey -alias SpringSampleApp -keyalg RSA -keystore keystore`

 3. edit server.xml from tomcat conf directory. Uncomment SSL connector and change it to:

 ```xml
<!-- Define a SSL Coyote HTTP/1.1 Connector on port 8443 -->
<Connector
           protocol="org.apache.coyote.http11.Http11Protocol"
           port="8443" maxThreads="200"
           scheme="https" secure="true" SSLEnabled="true"
           keystoreFile="<PATH_TO_KEYSTORE>" keystorePass="<PASS_TO_KEYSTORE>"
           clientAuth="false" sslProtocol="TLS"/>
```
		   
 4. add tomcat to your IDE and start application, or build it using maven and manually deploy _SpringSampleApp.war_ from _webapp_ project. You can access spittr on https://localhost:8443/SpringSampleApp

# REST API
Swagger documentation of REST API can be found here: https://tomcat-pisarski.rhcloud.com/SpringSampleApp/api-doc/. To try it you have to do one of the following:

 - login to Spittr - then you will be invoking REST calls as logged user
 - use oauth access_token

# OAuth 2
OAuth was configured to support the following grant types:

 - client credentials
 - password
 - refresh token

Client login and password are **spittr**.

To get access token please send POST request to the following address: 
https://tomcat-pisarski.rhcloud.com/SpringSampleApp/oauth/token?grant_type=XXX
with Basic Auth hedaer set to client login & password (spittr:spittr).


