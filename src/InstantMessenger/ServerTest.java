package InstantMessenger;

import javax.swing.*;
import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        Server testServer = new Server();
        testServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testServer.startRunning();
    }
}