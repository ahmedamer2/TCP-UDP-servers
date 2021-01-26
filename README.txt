Instructions on running the program:
---------------------------------------------------------------------------------------------------------------------
	1. Compile every class found in the zip file using either your IDE or by using a console window and
	typing the following.
		a. “javac Client.java MasterServer.java LowerServer.java ….”
		b. Replace “…” with the rest of the file names and do not include the quotation marks
	2. To run the program, I recommend having 8 different terminals open (unless using IDE to
	compile)
	3. For the Servers: Each terminal should run “java MasterServer” with no command line arguments
		a. Note replace MasterServer with the 6 mini server filenames to start up the rest
	4. The servers will remain active unless you close one of the terminal windows or you terminate
	the program.
	5. For the client: Use one terminal to run the command “java Client hostname port”
		a. The user must change the hostname to the host name that the server is running on (use
		“localhost” if the server is on the same machine”
		b. The user must use the port number that the server is bound to this is 8000
	6. At this point the program is running and ready to receive inputs from client.
---------------------------------------------------------------------------------------------------------------------
Using the program
	1. This program is purely console based. You will only need the terminal which will has the Client
	program running and the terminal with the MasterServer running. (The rest can be minimized).
	2. The MasterServer simply outputs status messages to ensure that the requests are being read
	properly.
	3. The user should only interact with the Client terminal
	4. Once program starts you will be greeted, and it will ask for the sentence you want to perform
	the transformation on
	5. Next you will see a menu that will ask you to choose an option from 1-7 based on what type of
	transformation you want to be done one your sentence.
	6. You can keep performing transformations till you are done then you can type in 7 to close the
	client side
	7. Once the client side is closed the MasterServer will close the socket and go back to listening for
	a new connection (Until a new Client connects)

****Last note: Please do not close the Client program by closing the terminal screen. This will result in
you having to restart the MasterServer class as it will crash.