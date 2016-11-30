package cs380;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private Socket socket = null;
    private ObjectOutputStream os = null;
    //private DataInputStream inputStream = null;
    private boolean isConnected = false;
    private String source = "src/cs380/"; //change the source file path
    private FileEvent fileEvent = null;
    private String destination = "src/cs380/"; //change the destination path
    

    public Client() {

    }

    /**
     * Connect with server
     */
    public void connections() {
        while (!isConnected) {
            try {
            	
                socket = new Socket("localHost", 4449);
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
    	
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();
            
            
            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                //File newFile = new File(f.getParent(), name + "."
                //        + String.format("%03d", partCounter++));
                //files.add(newFile);
            	
            	System.out.println(tmp);
            
            	fileEvent = new FileEvent(buffer);
                //fileEvent.setData(buffer); 
            	//fileEvent.fileData = buffer;
                for(int i = 0; i < 5; i++){
            		System.out.print(buffer[i] + "=");
            	}
                fileEvent.setSize(tmp);
                fileEvent.setFilename(name + "."
                        + String.format("%03d", partCounter++));
                System.out.println(fileEvent.getFilename());
                
                fileEvent.setDestFolder(destination);
                os.writeObject(fileEvent);
                
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
    


    public static void main(String[] args) {

        Client c = new Client();
        c.connections();
        //c.send();

    }

}
