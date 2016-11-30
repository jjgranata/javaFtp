package cs380;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private FileEvent fileEvent;
    private File dstFile = null;
    private FileOutputStream fileOutputStream = null;

    public Server() {

    }

    /**
     * Accept connection
     */
    public void makeConnect() {

        try {

            serverSocket = new ServerSocket(4445);
            socket = serverSocket.accept();
            inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    /**
     * Reading the FileEvent object and copying
     */
    public void dLoad() {

        try {

            fileEvent = (FileEvent) inputStream.readObject();
            if (fileEvent.getState().equalsIgnoreCase("Error")) {

                System.out.println("Error; exiting");
                System.exit(0);

            }

            String outputFile = fileEvent.getDestFolder() + fileEvent.getFilename();
            if (!new File(fileEvent.getDestFolder()).exists()) {

                new File(fileEvent.getDestFolder()).mkdirs();
            }

            dstFile = new File(outputFile);
            if(dstFile.isFile()){
            	System.out.println("File is good.");
            }
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("file: " + outputFile + " is saved");
            Thread.sleep(3000);
            System.exit(0);

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

    public static void main(String[] args) {

        Server server = new Server();
        server.makeConnect();
        server.dLoad();

    }
}