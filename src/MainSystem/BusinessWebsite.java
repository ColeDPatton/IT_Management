package MainSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BusinessWebsite {
    String business;
    String id;
    String url;

    public BusinessWebsite(String business, String id, String url) {
        this.business = business;
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int testConnection() {
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

            httpClient.setRequestMethod("GET");

            int responseCode = httpClient.getResponseCode();

            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean addToFile() {
        File f = new File("../IT_Management/database/businessWebsite.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(f, true));
            String toWrite = business + " " + id + " " + url;
            bw.write(toWrite + "\n");
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<BusinessWebsite> getWebsitesByBusiness(String business) {
        ArrayList<BusinessWebsite> returner = new ArrayList<BusinessWebsite>();
        try {
            File f = new File("../IT_Management/database/businessWebsite.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if (business.equals(words[0])) {
                    BusinessWebsite e = new BusinessWebsite(words[0], words[1], words[2]);
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