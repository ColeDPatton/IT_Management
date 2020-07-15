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

public class Notification {
    int nid;
    int userid;
    String message;
    boolean read;



    public Notification(int nid,int userid,String message,Boolean read) {
        this.nid=nid;
        this.userid=userid;
        this.message=message;
        this.read=read;
    }

    public static boolean toggleRead(Notification n) {
        Boolean read = n.read;
        
        if (!read)
            n.setRead(true);
        else if (read)
            n.setRead(false);

        try {
            return updateNotificationInfile(n);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getUnreadCount(int ITid) throws IOException {
        int userid;

        boolean read;

        File f = new File("../IT_Management/database/notification.txt");
        // File f = new File("database//notification.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        int count = 0;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            userid = Integer.parseInt(words[1]);

            if (userid == ITid) {
                read = Boolean.parseBoolean(words[3]);

                if (!read) {
                   count += 1;
                }
            }
        }
        br.close();
        return count;
    }

    public static boolean deleteNotificationInFile(Notification n) {
        File f = new File("../IT_Management/database/notification.txt");
        // File f = new File("database/task.txt");

        BufferedWriter bw;
        boolean flag = false;
        String toWrite="";
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            line = br.readLine();
            while(line !=null) {
                String[] words = line.split(" ");
                int potPid = Integer.parseInt(words[0]);
                if(potPid!=n.getNid()) {
                    toWrite+=line+"\n";
                }
                else
                    flag=true;
                line=br.readLine();
            }
            br.close();
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(toWrite);
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean updateNotificationInfile (Notification n) throws IOException {
        int toBeUpdatedId = n.nid;

        int userid = n.userid;

        String message = n.message.replaceAll(" ", "_");
        boolean read = n.read;

        if (message == null || message.isEmpty())
            message = "_";

        File f = new File("../IT_Management/database/notification.txt");
        // File f = new File("database/task.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String toWrite = "";
        String line;

        Boolean wasUpdated = false;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            int potTid = Integer.parseInt(words[0]);

            if (potTid == toBeUpdatedId) {
                toWrite += toBeUpdatedId + " " + userid + " " + message + " " + read + "\n";
                wasUpdated = true;
            } else {
                toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + "\n";
            }
        }
        br.close();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));

            bw.write(toWrite);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wasUpdated;
    }

    public static boolean addNotificationToFile(Notification n) {
        int nid = n.nid;
        int userid = n.userid;

        String message = n.message.replaceAll(" ", "_");
        boolean read = n.read;

        if (message == null || message.isEmpty())
            message = "_";

        File f = new File("../IT_Management/database/notification.txt");
        //File f = new File("database/notification.txt");

        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            BufferedReader br = new BufferedReader(new FileReader(f));
            String toWrite = nid + " " + userid + " " + message + " " + read;
            bw.write(toWrite+"\n");
            br.close();
            bw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    // public static boolean addNotificationToCustomerFile(Notification n) {
    //     int nid = n.nid;
    //     int userid = n.userid;

    //     String message = n.message.replaceAll(" ", "_");
    //     boolean read = n.read;

    //     if (message == null || message.isEmpty())
    //         message = "_";

    //     File f = new File("I../T_Management/database/customerNotification.txt");

    //     BufferedWriter bw;

    //     try {
    //         bw = new BufferedWriter(new FileWriter(f, true));
    //         BufferedReader br = new BufferedReader(new FileReader(f));
    //         String toWrite = nid + " " + userid + " " + message + " " + read;
    //         bw.write(toWrite+"\n");
    //         br.close();
    //         bw.close();

    //         return true;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }

    public static ArrayList<Notification> getAllNotificationsByUser (int ITid) throws IOException {
        Notification n;
        int potTid;
        int userid;

        ArrayList<Notification> nList = new ArrayList<>();

        String message;
        boolean read;

        File f = new File("../IT_Management/database/notification.txt");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            userid = Integer.parseInt(words[1]);

            if (userid == ITid) {
                potTid = Integer.parseInt(words[0]);
                message = words[2].replaceAll("_"," ");
                read = Boolean.parseBoolean(words[3]);

                n = new Notification(potTid, userid, message, read);

                nList.add(n);
            }
        }
        br.close();
        return nList;
    }
    public static Notification getNotification(int nid) throws IOException {
        Notification n;
        int userid;

        String message;
        boolean read;

        File f = new File("../IT_Management/database/notification.txt");
        // File f = new File("database//notification.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            int potTid = Integer.parseInt(words[0]);

            if (potTid == nid) {
                userid = Integer.parseInt(words[1]);
                message = words[2].replaceAll("_"," ");
                read = Boolean.parseBoolean(words[3]);

                n = new Notification(potTid, userid, message, read);
                br.close();
                return n;
            }
        }
        br.close();
        return null;
    }

    public static int checkUnique(int id) throws IOException {
        File f = new File("../IT_Management/database/notification.txt");
        // File f = new File("database/task.txt");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while((line = br.readLine()) !=null) {
            String[] words = line.split(" ");
            int potId = Integer.parseInt(words[0]);
            if(potId==id) {
                return potId;
            }
        }
        br.close();
        return -1;
    }

    public int getNid() {
        return nid;
    }
    public void setNid(int nid) {
        this.nid = nid;
    }
    public int getUserid() { return userid; }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean getRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
