import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Connected to server...");
            JFrame frame = new JFrame();
            JLabel label = new JLabel();
            frame.add(label);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] sizeAr = new byte[4];
                inputStream.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).getInt();
                byte[] imageAr = new byte[size];
                int offset = 0;
                while (offset < size) {
                    int bytesRead = inputStream.read(imageAr, offset, size - offset);
                    if (bytesRead == -1) {
                        break; 
                    }
                    offset += bytesRead;
                }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageAr);
                BufferedImage image = ImageIO.read(byteArrayInputStream);
                if (image != null) {
                    ImageIcon icon = new ImageIcon(image);
                    label.setIcon(icon);
                    frame.repaint();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}