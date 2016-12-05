package cs380;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private Socket socket = null;
    private ObjectOutputStream os = null;
    //private DataInputStream inputStream = null;
    private boolean isConnected = false;
    private String source = "src/cs380/"; //change the source file path
    private FileEvent fileEvent = null;
    private String destination = "src/cs380/"; //change the destination path
    private static sha hash;
    private static XOR cipher;
    private static Base64 b64;
    

    public Client() {
    	hash = new sha();
    	cipher = new XOR();
    	b64 = new Base64();
    }

    /**
     * Connect with server
     */
    public void connections() {
        while (!isConnected) {
            try {
            	
                socket = new Socket("localHost", 4222);
                //inputStream = new ObjectInputStream(socket.getInputStream());

                os = new ObjectOutputStream(socket.getOutputStream());
                //inputStream = new DataInputStream(socket.getInputStream());
                
            
                isConnected = true;
                File file = new File("src/cs380/mp4file.mp4");
                File file2 = new File("src/cs380/newmergedmp4.mp4");
                splitFile(file);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   
    
    public List<File> splitFile(File f) throws IOException {
    	List<File> files = new ArrayList<File>();
    	long len = f.length();
    	boolean doBase64 = false;
    	
    	String key = "";
    	Scanner s1 = new Scanner(new FileInputStream("C:/Users/Zach/workspace/cs380/src/cs380/key.txt"));
    	while(s1.hasNext())
    	{
    		key = s1.next();
    	}
    	
    	
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...

        long count = 1;
        int sizeOfFiles = 1024 * 64;
        byte[] buffer = new byte[sizeOfFiles];

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();
            
            
            int tmp = 0;
            while ((tmp = bis.read(buffer)) != -1) {
                //write each chunk of data into separate file with different number in name
                //File newFile = new File(f.getParent(), name + "."
                //        + String.format("%03d", partCounter++));
                //files.add(newFile);
            	
            	System.out.println("Sending: " + tmp + "bytes.");
            	
            	fileEvent = new FileEvent(count);
            	fileEvent.setnewData(buffer);
            	//fileEvent.setsmallbyte(buffer[0]);

//luis start
                doBase64 = b64.promptUser();
                // do ascii armoring with base64
                if(doBase64) {
                	fileEvent.setAsciiArmorString(b64.encode(buffer));
                }
      
//luis end               
                fileEvent.setHash(XOR.xorMessage(hash.perform(buffer), key));
                fileEvent.setSize(tmp);
                fileEvent.setFilename(name + "."
                        + String.format("%03d", partCounter++));
                //System.out.println(fileEvent.getFilename());
                
                fileEvent.setDestFolder(destination);
                os.writeObject(fileEvent);
                count++;
                
                try {
                    Thread.sleep(500);
                    os.flush();
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            	
                //os.writeBoolean(true);
                //try (FileOutputStream out = new FileOutputStream(newFile)) {
                //    out.write(buffer, 0, tmp);//tmp is chunk size
                //}
            }
            os.writeBoolean(false);
        }catch (Exception e) {

            e.printStackTrace();
            fileEvent.setState("Error");

        }
        return files;
    }
    
    
    public static void run() throws IOException {
        Scanner s1,s2;
        s1=new Scanner(new FileInputStream("C:/Users/Zach/workspace/cs380/src/cs380/user.txt")); //change filepath to get user.txt
        s2=new Scanner(System.in);
        boolean flag=false;
        String name,pword,n,p;
        System.out.println("Enter name:");
        n=s2.next();
        System.out.println("Enter password:");
        p=s2.next();
        while(s1.hasNext()) {
            name=s1.next();
            pword=s1.next();
            if(n.equals(name) && p.equals(pword)) {
                System.out.println("You are logged in."); //we can execute our code here
                Client c = new Client();
                c.connections();
                flag=true;
                break;
            }
        }
        if (!flag)
        {
            System.out.println("Incorrect user and/or password.");
            run();
        }
    }


    public static void main(String[] args) throws IOException {

        run();

    }

}
