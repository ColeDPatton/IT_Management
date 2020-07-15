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

public class Comment {
    int cid;
    int tid;
    int userid;
    String message;
    int replySpot;
    int replyId;


    public Comment(int cid,int tid,int userid, String message, int replySpot,int replyId) {
        this.cid=cid;
        this.tid=tid;
        this.userid=userid;
        this.message=message;
        this.replySpot=replySpot;
        this.replyId=replyId;
    }

    public static boolean deleteCommentInFile(Comment c) {
        File f = new File("../IT_Management/database/comment.txt");
        // File f = new File("database/comment.txt");

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
                if(potPid!=c.getCid()) {
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

    public static boolean addReply (Comment reply, Comment originalComment) {
        reply.setReplyId(originalComment.getCid());
        reply.setReplySpot(originalComment.getReplySpot() + 1);
        return addCommentToFile(reply);
    }

    public static boolean updateCommentInfile (Comment c) throws IOException {
        int toBeUpdatedId = c.cid;
        int tid = c.tid;
        int userid = c.userid;

        String message = c.message.replaceAll(" ", "_");

        int replySpot = c.replySpot;
        int replyId = c.replySpot;

        if (message == null || message.isEmpty())
            message = "_";

        File f = new File("../IT_Management/database/comment.txt");
        // File f = new File("database/comment.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String toWrite = "";
        String line;

        Boolean wasUpdated = false;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            int potTid = Integer.parseInt(words[0]);

            if (potTid == toBeUpdatedId) {
                toWrite += toBeUpdatedId + " " + tid + " " + userid + " " + message + " " + replySpot + " " + replyId + "\n";
                wasUpdated = true;
            } else {
                toWrite += words[0] + " " + words[1] + " " + words[2] + " " + words[3] + " " + words[4] + " " + words[5] + "\n";
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

    public static boolean addCommentToFile(Comment c) {
        int cid = c.cid;
        int userid = c.userid;
        int tid = c.tid;

        String message = c.message.replaceAll(" ", "_");

        int replySpot = c.replySpot;
        int replyId = c.replyId;

        if (message == null || message.isEmpty())
            message = "_";

        File f = new File("../IT_Management/database/comment.txt");
        // File f = new File("database/comment.txt");

        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            BufferedReader br = new BufferedReader(new FileReader(f));
            String toWrite = cid + " " + tid + " " + userid + " " + message + " " + replySpot + " " + replyId;
            bw.write(toWrite+"\n");
            br.close();
            bw.close();

            ArrayList<Employee> employees = Employee.getAllAssignedToTaskEmployees(tid);

            String newMessage = "New Comment by:  " + Employee.getEmployee(userid).getFirstName() + " on task: " + Task.getTask(tid).getTitle();

            Random rand = new Random();

            for (Employee employee : employees) {

                int randomNum = 0;
                while (Notification.checkUnique(randomNum) != -1) {
                    randomNum = rand.nextInt(10000);
                }
                Notification newNote = new Notification(randomNum, employee.getIdentifier(), newMessage, false);
                Notification.addNotificationToFile(newNote);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Comment> getAllCommentsByTask (int tid) throws IOException {
        Comment c;
        int potTid;
        int cid;
        int userid;

        ArrayList<Comment> cList = new ArrayList<>();

        String message;

        int replySpot;
        int replyId;

        File f = new File("../IT_Management/database/comment.txt");
        // File f = new File("database//comment.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            potTid = Integer.parseInt(words[1]);

            if (potTid == tid) {
                cid = Integer.parseInt(words[0]);
                userid = Integer.parseInt(words[2]);
                message = words[3].replaceAll("_"," ");
                replySpot = Integer.parseInt(words[4]);
                replyId = Integer.parseInt(words[5]);

                c = new Comment(cid, potTid, userid, message, replySpot, replyId);

                cList.add(c);
            }
        }
        br.close();
        return sortCommentsWithReplies(cList);
    }

    public static ArrayList<Comment> sortCommentsWithReplies (ArrayList<Comment> cList) throws IOException {
        ArrayList<Comment> sortedComments = new ArrayList<Comment>();

        for (Comment comment : cList) {
            if (comment.getReplySpot() == 0) {
                sortedComments.add(comment);

                recursiveFindReplys(sortedComments, getReplys(comment.getCid(), cList), cList);

            }
        }

        return sortedComments;
    }

    private static ArrayList<Comment> getReplys(int cid, ArrayList<Comment> cList) {
        ArrayList<Comment> replys = new ArrayList<Comment>();
        for (Comment comment : cList) {
            if (comment.replyId == cid) {
                replys.add(comment);
            }
        }
        return replys;
    }

    public static boolean recursiveFindReplys(ArrayList<Comment>sortedComments, ArrayList<Comment>replys, ArrayList<Comment> cList) {
        if (replys.isEmpty()) {
            return false;
        }
        if (replys.get(0) == null) {
            return false;
        }

        for (Comment reply : replys) {
            sortedComments.add(reply);

            recursiveFindReplys(sortedComments, getReplys(reply.getCid(), cList), cList);
        }
        return true;
    }

    public static Comment getComment(int cid) throws IOException {
        Comment c;
        int tid;
        int userid;

        String message;

        int replySpot;
        int replyId;

        File f = new File("../IT_Management/database/comment.txt");
        // File f = new File("database//comment.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");

            int potTid = Integer.parseInt(words[0]);

            if (potTid == cid) {
                tid = Integer.parseInt(words[1]);
                userid = Integer.parseInt(words[2]);
                message = words[3].replaceAll("_"," ");
                replySpot = Integer.parseInt(words[4]);
                replyId = Integer.parseInt(words[5]);

                c = new Comment(cid, potTid, userid, message, replySpot, replyId);
                br.close();
                return c;
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

    public int getCid() {
        return cid;
    }
    public void setTid(int tid) {
        this.tid = tid;
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
    public int getReplySpot() { return replySpot; }
    public void setReplySpot(int replySpot) {
        this.replySpot = replySpot;
    }
    public int getReplyId() { return replyId; }
    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }
}
