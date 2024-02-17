import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1111);
            System.out.println("Server waiting for client connection...");
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket);
            OutputStream outputStream = socket.getOutputStream();
            while (true) {
                long startTime = System.currentTimeMillis();
                BufferedImage image = robot.createScreenCapture(screenRect);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                outputStream.write(size);
                outputStream.write(byteArrayOutputStream.toByteArray());
                outputStream.flush();
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime < 100) { 
                    Thread.sleep(100 - elapsedTime);
                }
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}