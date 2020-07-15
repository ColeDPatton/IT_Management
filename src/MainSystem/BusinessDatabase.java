package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusinessDatabase {
    private String business;
    private String id;
    private String url;
    private String port;
    private String dbName;
    private String username;
    private String password;

    public BusinessDatabase(String business, String id, String url, String port, String dbName, String username,
            String password) {
        this.business = business;
        this.id = id;
        this.url = url;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public String getDBName() {
        return dbName;
    }

    public boolean testConnection() {
        boolean success = false;
        try {
            //Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
            String dbUrl = "jdbc:mysql://" + url + ":" + port + "/" + dbName;
            Connection con = DriverManager.getConnection(dbUrl, username, password);
            success = con.isValid(10);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return success;
    }

    public boolean addToFile() {
        File f = new File("../IT_Management/database/businessDatabase.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            String toWrite = business + " " + id + " " + url + " " + port + " " + dbName + " " + username + " "
                    + password;
            bw.write(toWrite + "\n");
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<BusinessDatabase> getDatabasesByBusiness(String business) {
        ArrayList<BusinessDatabase> returner = new ArrayList<BusinessDatabase>();
        try {
            File f = new File("../IT_Management/database/businessDatabase.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if (business.equals(words[0])) {
                    BusinessDatabase e = new BusinessDatabase(words[0], words[1], words[2], words[3], words[4],
                            words[5], words[6]);
                    returner.add(e);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returner;
    }
}