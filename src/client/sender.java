
package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.FileInputStream;


public class sender {

    private Socket s;

    public sender(String host, int port, String file) {

        try {

            s = new Socket(host, port);
            sendFile(file);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void sendFile(String file) throws IOException {

        DataOutputStream d = new DataOutputStream(s.getOutputStream());
        FileInputStream f = new FileInputStream(file);

        byte[] buffer = new byte[4096];

        while (f.read(buffer) > 0) {
            d.write(buffer);
        }

        f.close();
        d.close();
    }

    public static void main(String[] args) {
        client.sender fc = new client.sender("localhost", 1988, "test.txt");
    }

}
