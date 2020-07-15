package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Random;

public class Message {
    int mid;
    int hid;
    int rid;
    String message;

    public Message(int mid, int hid, int rid, String message) {
        this.mid = mid;
        this.hid = hid;
        this.rid = rid;
        this.message = message;
    }

    public static boolean addMessageToFile(Message m) {
        int mid = m.mid;
        int hid = m.hid;
        int rid = m.rid;

        String message = m.message.replaceAll(" ", "_");

        if (message == null || message.isEmpty())
            message = "_";

        File f = new File("../IT_Management/database/message.txt");
        //File f = new File("database/notification.txt");

        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            BufferedReader br = new BufferedReader(new FileReader(f));
            String toWrite = mid + " " + hid + " " + rid + " " + message;
            bw.write(toWrite+"\n");
            br.close();
            bw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Message> getAllMessages (int inputHid, int inputRid) throws IOException {
        Message m;
        int mid;
        int hid;
        int rid;

        ArrayList<Message> mList = new ArrayList<>();

        String message;

        File f = new File("../IT_Management/database/message.txt");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            hid = Integer.parseInt(words[1]);
            rid = Integer.parseInt(words[2]);

            if (hid == inputHid && rid == inputRid) {
                mid = Integer.parseInt(words[0]);
                message = words[3].replaceAll("_"," ");

                m = new Message(mid, hid, rid, message);

                mList.add(m);
            }
        }
        br.close();
        return mList;
    }

    public int getMid() {
        return mid;
    }
    public void setMid(int mid) { this.mid = mid; }
    public int getHid() {
        return hid;
    }
    public void setHid(int hid) {
        this.hid = hid;
    }
    public int getRid() {
        return rid;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}