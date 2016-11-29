import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket = null;
    private ObjectOutputStream os = null;
    private boolean isConnected = false;
    private String source = "E:/temp/test.txt"; //change the source file path
    private FileEvent fileEvent = null;
    private String destination = "C:/downloads/"; //change the destination path

    public Client() {

    }

    /**
     * Connect with server
     */
    public void connections() {
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 4445);
                os = new ObjectOutputStream(socket.getOutputStream());
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sending FileEvent
     */
    public void send() {

        fileEvent = new FileEvent();
        String name = source.substring(source.lastIndexOf("/") + 1, source.length());
        String path = source.substring(0, source.lastIndexOf("/") + 1);
        fileEvent.setDestFolder(destination);
        fileEvent.setFilename(name);
        fileEvent.setSrcFolder(source);
        File file = new File(source);

        if (file.isFile()) {

            try {

                DataInputStream dataStreams = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = dataStreams.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                    read = read + numRead;

                }
                fileEvent.setSize(len);
                fileEvent.setData(fileBytes);
                fileEvent.setState("Success");

            } catch (Exception e) {

                e.printStackTrace();
                fileEvent.setState("Error");

            }

        } else {
            System.out.println("incorrect path");
            fileEvent.setState("Error");

        }

        try {

            os.writeObject(fileEvent);
            System.out.println("Done; exiting");
            Thread.sleep(3000);
            System.exit(0);

        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Client c = new Client();
        c.connections();
        c.send();

    }

}
