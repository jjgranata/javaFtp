package cs380;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Server {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    //private FileEvent fileEvent;
    private File dstFile = null;
    private FileOutputStream fileOutputStream = null;
    private List<File> files = new ArrayList<File>();
    private static sha hash;
    private static XOR cipher;

    public Server() {
    	hash = new sha();
    	cipher = new XOR();
    }

    /**
     * Accept connection
     */
    public void makeConnect() {

        try {

            serverSocket = new ServerSocket(4222);
            socket = serverSocket.accept();
            //os = new ObjectOutputStream(socket.getOutputStream());
            
            inputStream = new ObjectInputStream(socket.getInputStream());

            String key = "";
        	Scanner s1 = new Scanner(new FileInputStream("C:/Users/Zach/workspace/cs380/src/cs380/key.txt"));
        	while(s1.hasNext())
        	{
        		key = s1.next();
        	}
            
            while(true)
            {
            	handle(key);
            }

        } catch (IOException e) {

        	System.out.println("Serverside Ex");
            e.printStackTrace();

        } 

    }

    
    public void handle(String key)
    {
    	try {

    				
    			
                	FileEvent fileEvent = (FileEvent) inputStream.readObject();
                	//System.out.println(fileEvent.getsmallbyte());
                	
                	String inHash = cipher.xorMessage(fileEvent.getHash(), key);
                	String ourHash = hash.perform(fileEvent.getnewData());
                	//System.out.println(inHash);
                	//System.out.println(ourHash);
                	
                	if(inHash.equals(ourHash))
                	{
                		System.out.println("Integrity good. " + fileEvent.getSize() + " bytes received.");
                		
                    	byte[] buf = new byte[(int)fileEvent.getSize()];
                    	buf = fileEvent.getnewData();

                    	//System.out.println(fileEvent.getFilename());
                    	//System.out.println(fileEvent.getSize());
                    	String outputFile = fileEvent.getDestFolder() + fileEvent.getFilename();
                    	File newFile = new File(outputFile);
                    	
                    	fileOutputStream = new FileOutputStream(newFile);
                    	
                    	fileOutputStream.write(fileEvent.getnewData(), 0 ,(int)fileEvent.getSize());
                    	fileOutputStream.flush();
                    	files.add(newFile);
                    	
                    	
                    
                		
                	}
                	else {
                		System.out.println("Integrity bad.");
                	}

    	}catch (IOException e) {
    		closeSocket();
        	System.out.println("File transfer complete. Closing...");
        	System.exit(0);
    		
            //e.printStackTrace();

        }catch (ClassNotFoundException e) {
        	System.out.println("CNFE");
            //e.printStackTrace();
        }
    }
    
    public void closeSocket()
    {
    	try
    	{
    		System.out.println("Attempting merge...");
    		File file2 = new File("src/cs380/newmergedmp4.mp4");
    		mergeFiles(files, file2);
    		fileOutputStream.flush();
        	fileOutputStream.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("IO in closeSocket");
    	}
    }

    public void mergeFiles(List<File> files, File into)
            throws IOException {
        try (BufferedOutputStream mergingStream = new BufferedOutputStream(
                new FileOutputStream(into))) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
            }
            System.out.println("Merge Complete.");
        }
    }

    public static void main(String[] args) {

        Server server = new Server();
        server.makeConnect();

    }
}