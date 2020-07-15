package InstantMessenger;

import MainSystem.Employee;
import MainSystem.IT_Personnel;
import MainSystem.Message;
import MainSystem.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Server extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    Employee host;
    Employee receiver;

    public Server() {
        super("Instant Messenger");
        userText = new JTextField();

        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(500, 400);


        userText.setEditable(false);

        userText.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );

        boolean foundUser = false;

        Scanner in = new Scanner(System.in);

        System.out.println("Enter your ID: ");

        //Employee host = null;
        //Employee receiver = null;

        while (!foundUser) {
            int Eid =  in.nextInt();
            try {
                host = Employee.getEmployee(Eid);
                if (host == null) {
                    System.out.println("User not found try again: ");
                } else {
                    System.out.println("Welcome "+ host.getFirstName() + " enter the ID of the User you wish to communicate with:");
                    foundUser = true;
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("User not found try again");
            }
        }

        Boolean foundReceiver = false;

        Random rand = new Random();
        int nid = rand.nextInt(50000);

        while (!foundReceiver) {
            int Eid =  in.nextInt();
            try {
                receiver = Employee.getEmployee(Eid);
                if (receiver == null) {
                    System.out.println("User not found try again: ");
                } else {
                    System.out.println(receiver.getFirstName() + " has been found and notified");

                    Notification newNote = new Notification(nid, receiver.getIdentifier(),host.getFirstName() + " is trying to connect with you via instant message", false);
                    Notification.addNotificationToFile(newNote);
                    foundReceiver = true;
                }
            } catch (NumberFormatException | IOException e) {
                showMessage("That was not a number, please enter a number");
            }
        }
        setVisible(true);
    }

    public void startRunning() throws IOException {
        try {
            server = new ServerSocket(6789, 50);
            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                } catch (EOFException eofException) {
                    showMessage("\n Server ended the connection! ");
                } finally {
                    closeConnection();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        showMessage(" Waiting for someone to connect... \n");
        connection = server.accept();
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        showMessage("\n Streams are now setup \n");
    }

    private void whileChatting() throws IOException {
        String message = " Welcome to the chat! " + host.getFirstName() + " is the HOST and " + receiver.getFirstName() + " is the CLIENT";
        sendMessage(message);
        ableToType(true);

        Random rand = new Random();

        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);



                String withName = message;
                withName.replace("Client - ", receiver.getFirstName() + " - ");

                Message newMessage = new Message(rand.nextInt(50000), host.getIdentifier(), receiver.getIdentifier(), withName);
                Message.addMessageToFile(newMessage);

            } catch (ClassNotFoundException classNotFoundException) {
                showMessage("The user has sent an unknown object!");
            }
        } while (!message.equals("CLIENT - END"));
    }

    public void closeConnection() throws IOException {
        showMessage("\n Closing Connections... \n");
        ableToType(false);

        ArrayList<Message> mList = Message.getAllMessages(host.getIdentifier(), receiver.getIdentifier());

        for (Message message : mList) {
            System.out.println(message.getMessage());
        }

        try {
            output.close();
            input.close();
            connection.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            output.writeObject("HOST - " + message);
            output.flush();
            showMessage("\nHOST - " + message);

            Random rand = new Random();

            String withName = host.getFirstName() + " - " + message;

            Message newMessage = new Message(rand.nextInt(50000), host.getIdentifier(), receiver.getIdentifier(), withName);
            Message.addMessageToFile(newMessage);
        } catch (IOException ioException) {
            chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }

    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append(text);
                    }
                }
        );
    }

    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }
}