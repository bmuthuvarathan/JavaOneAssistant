Native Android client for a Java EE 7 server-side backend consisting of a chat WebSocket API and a to do list REST API implemented using the Java API for WebSocket, JSON-P, JAX-RS 2, CDI, Bean Validation, EJB 3 and JPA.

The WebSocket part (Chat feature) interacts over HTTP. The REST API calls (for the To Do List feature) interacts over HTTPS. For this a self-signed SSL cert will need to extracted from the server key store and added to the Android (instructions below).

Before you start on the Android client, install GlassFish and install the JEE 7 server application from the parent directory. See read me in that directory for detailed instructions.

Installation instructions:
1. Download and install Android SDK and Android Eclipse IDE (ADT) 
2. Clone this project to desktop
3. Import the project into ADT (Android Developer Tools Eclipse IDE) as an Existing Eclipse Project
4. Export the self-signed SSL cert from GlassFish server keystore to the following file: selfSigned.cer
	cd ~/glassfish/domains/domain1/config
	//List the certs, default keystone password is changeit, and notedown the cert alias
	keytool -list -keystore keystore.jks -v
	//Export cert
        keytool  -export  -keystore keystore.jks  -storepass changeit -alias slas -file selfSigned.cer
5. Copy the selfSigned.cer to assets directory of the Android app project
6. Create a new Android Virtual Device under Android Virtual Device Manager in Eclipse ADT
7. Start the mewly created virtual device
7. Under Run -> Configurations in Eclipse, configure the application to run on the newly created Virtual Device
8. Run the application on the virtual device.
9. This should install the application and start the app on the Emulator
10. From Emulator Home Page, go to Apps page and click on JavaOne application
11. Login in as bmuthuvarathan/secret1. 
12. From the home screen, you can go to Session To Do, to add, update, delete To Do items.
13. From the home screen, you can go to Java One Chatter to chat with all the other clients connected to the server.

P.S: The app has no error handling other than just logging it. So you may see the next Activity (screen) even if an error happened. For example, you will see the home screen even if login fails. Pay close attention to LogCat and Console in Eclipse. TODO: Error handling.


	