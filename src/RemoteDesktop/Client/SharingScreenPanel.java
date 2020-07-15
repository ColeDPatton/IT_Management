package RemoteDesktop.Client;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.MouseInfo;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SharingScreenPanel extends JPanel implements Runnable {

    private static Socket client;
    private boolean connected;
    public JLabel statusMessage;

    public SharingScreenPanel() {
        setLayout(new GridBagLayout());
        connected = false;
        statusMessage = new JLabel("Waiting for IT personnel");
        this.add(statusMessage);
        revalidate();
        repaint();
        Thread t = new Thread(this);
        t.start();
    }

    public void socketConnected() {
        statusMessage.setText("IT Personnel can now view your screen");
        connected = true;
        revalidate();
        repaint();
    }

    public void stopSharing(){
        connected =false;
    }

    public void run() {
        try {
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            Robot bot = new Robot();

            boolean scanning = true;
            while (scanning) {
                try {
                    client = new Socket(address, port);
                    scanning = false;
                } catch (SocketException e) {
                    System.out.println("Connect failed, waiting and trying again");
                    try {
                        Thread.sleep(2000);// 2 seconds
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
            socketConnected();

            while (connected) {
                client = new Socket(address, port);
                Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage image = bot.createScreenCapture(capture);

                // Image cursor = ImageIO.read(new File("c:/cursor.gif"));
                int x = MouseInfo.getPointerInfo().getLocation().x;
                int y = MouseInfo.getPointerInfo().getLocation().y;

                Graphics2D graphics2D = image.createGraphics();
                // graphics2D.drawImage(cursor, x, y, 16, 16, null);
                graphics2D.setColor(Color.RED);
                graphics2D.fillRect(x, y, 10, 10);

                ImageIO.write(image, "JPG", client.getOutputStream());
                client.close();
            }
        } catch (SocketException e) {
            statusMessage.setText("Connection closed. Request new assistance if needed.");
            connected = true;
            revalidate();
            repaint();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}