package cs380;

import java.io.BufferedOutputStream;
import java.io.File;
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


public class Server {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private FileEvent fileEvent;
    private File dstFile = null;
    private FileOutputStream fileOutputStream = null;
    private List<File> files = new ArrayList<File>();
   

    public Server() {

    }

    /**
     * Accept connection
     */
    public void makeConnect() {

        try {

            serverSocket = new ServerSocket(4454);
            socket = serverSocket.accept();
            //os = new ObjectOutputStream(socket.getOutputStream());
            
            
            
            while(true)
            {
            	handle();
            }

        } catch (IOException e) {

        	System.out.println("Serverside Ex");
            e.printStackTrace();

        } 

    }

    /**
     * Reading the FileEvent object and copying
     */
   /* public void dLoad() {

        try {

        	
        	boolean shouldWrite = true;
        	os.writeBoolean(true);
        	while(shouldWrite)
        	{
                if(inputStream.readBoolean())
                {
                	os.writeBoolean(false);
                	fileEvent = (FileEvent) inputStream.readObject();
                    String outputFile = fileEvent.getDestFolder() + fileEvent.getFilename();
                    
                	dstFile = new File(outputFile);
                	if(fileOutputStream == null)
                	{
                		if(dstFile.isFile()){
                			System.out.println("File is good.");
                		}
                		else {
                			System.out.println("Creating new file...");
                			fileOutputStream = new FileOutputStream(fileEvent.getFilename());
                		}
                	} 
                	fileOutputStream.write(fileEvent.getData());
                	System.out.println("Received...Waiting.");
                	os.writeBoolean(true);
                }
                if(inputStream.readInt() == 3)
                {
                	shouldWrite = false;
                	fileOutputStream.flush();
                	fileOutputStream.close();
                	System.out.println("file is saved");
                	Thread.sleep(3000);
                	System.exit(0);
                }
        	}

        

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }*/
    
    public void handle()
    {
    	try {

                	fileEvent = (FileEvent) inputStream.readObject();
                	if(fileEvent != null)
                	{
                		System.out.println("Going");
                	}
                	else{
                		System.out.println("NotGoing");
                	}
                	for(int i = 0; i < 10; i++)
                	{
                		System.out.print(fileEvent.getData()[i]);
                	}
                	System.out.println(fileEvent.getFilename());
                	System.out.println(fileEvent.getSize());
                	String outputFile = fileEvent.getDestFolder() + fileEvent.getFilename();
                	File newFile = new File(outputFile);
                	
                	fileOutputStream = new FileOutputStream(newFile);
                	
                	fileOutputStream.write(fileEvent.getData(), 0 ,(int)fileEvent.getSize());
                	fileOutputStream.flush();
                	inputStream.
                	files.add(newFile);
               
            
    	}catch (IOException e) {
    		closeSocket();
    		System.out.println("IOExceptionnn");
        	System.out.println("file is saved");
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
    		System.out.println("Attempting merge");
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
        }
    }

    public static void main(String[] args) {

        Server server = new Server();
        server.makeConnect();
        
        //server.dLoad();

    }
}