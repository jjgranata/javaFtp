
package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class receiver extends Thread {

    private ServerSocket serSock;

    public receiver(int port) {
        try {

            serSock = new ServerSocket(port);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public void run() {

        while (true) {

            try {

                Socket clientSock = serSock.accept();
                save(clientSock);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void save(Socket clientSock) throws IOException {

        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("test.jpg");
        byte[] buffer = new byte[4096];

        int filesize = 15123;
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }

    public static void main(String[] args) {
        server.receiver fs = new server.receiver(1988);
        fs.start();
    }

}
