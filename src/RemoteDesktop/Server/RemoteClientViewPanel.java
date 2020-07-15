package RemoteDesktop.Server;

import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoteClientViewPanel extends JPanel implements Runnable {

    private static Socket server;
    private boolean runRemoteViewing;

    public RemoteClientViewPanel() {
        setLayout(new GridBagLayout());
        startRemoteViewing();

        Thread t = new Thread(this);
        t.start();

    }

    public void startRemoteViewing() {
        runRemoteViewing = true;
    }

    public void stopRemoteViewing() {
        runRemoteViewing = false;
    }

    public void run() {
        try {
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
            JLabel picLabel = new JLabel();

            while (runRemoteViewing) {
                server = serverSocket.accept();
                BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
                if (this.getComponentCount() >= 1) {
                    this.remove(picLabel);
                }
                if (img != null) {
                    BufferedImage scaledImage = new BufferedImage(1000, 650, img.getType());
                    Graphics2D graphics2D = scaledImage.createGraphics();
                    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    graphics2D.drawImage(img, 0, 0, 1000, 650, null);
                    graphics2D.dispose();
                    picLabel = new JLabel(new ImageIcon(scaledImage));
                    this.add(picLabel);
                    revalidate();
                    setVisible(true);
                }
            }
            server.close();
            serverSocket.close();
        } catch (BindException e) {
            System.out.println("Server still running");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}