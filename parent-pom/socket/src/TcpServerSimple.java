
import java.io.*; 
import java.net.*; 
import java.net.Socket;



class TCPServer {    
	public static void main(String argv[]) throws Exception
   {          
	{
	
	String clientSentence; 
   String capitalizedSentence; 
   ServerSocket welcomeSocket = new ServerSocket(6789); 
   while(true)          {            
	   Socket connectionSocket = welcomeSocket.accept();        
	   BufferedReader inFromClient =                new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));    
	   DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());  
	   clientSentence = inFromClient.readLine();        
	   System.out.println("Received: " + clientSentence);    
	   capitalizedSentence = clientSentence.toUpperCase() + '\n';    
	   outToClient.writeBytes(capitalizedSentence);         
	   }       } } 
}
//- See more at: https://systembash.com/a-simple-java-tcp-server-and-tcp-client/#sthash.yNZP9cmN.dpuf
		   