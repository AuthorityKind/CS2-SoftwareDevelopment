package code.example.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    ArrayList<Integer> startDateArr = new ArrayList<Integer>();
    ArrayList<Integer> endDateArr = new ArrayList<Integer>();
    ArrayList<String> companyNmArr = new ArrayList<String>();
    ArrayList<String> localArr = new ArrayList<String>();

    public DB() {
        Connection conn;

        try {
            final String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");

            final String startDateQuery = "select distinct startDate from voyage order by startDate asc";
            final String endDateQuery = "select distinct endDate from voyage order by endDate asc";
            final String companyNmQuery = "select distinct companyName from voyage order by companyName asc";
            final String localQuery = "select distinct startLocal from voyage order by startLocal asc";

            createListFromQuery(conn, startDateQuery, "startDate");
            createListFromQuery(conn, endDateQuery, "endDate");
            createListFromQuery(conn, companyNmQuery, "companyNm");
            createListFromQuery(conn, localQuery, "local");

            //System.out.println(startDateArr);
            //System.out.println(endDateArr);
            //System.out.println(companyNmArr);
            //System.out.println(localArr);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String parseDate(int num) {
        String str = String.valueOf(num);

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count++;

            if(count == 2 && i != str.length()-1){
                String start = "";
                String end = "";

                for(int i2 = 0; i2 <= i; i2++){
                    start = start + str.charAt(i2);
                }

                for(int i3 = i+1; i3 < str.length(); i3++){
                    end = end + str.charAt(i3);
                }

                str = start + "/" + end;
                count = -1;
            }
        }
        return str;
    }

    private void createListFromQuery(Connection conn, String query, String type) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            switch (type) {
                case "local" -> {
                    while (rs.next()) {
                        String str = rs.getString(1);
                        localArr.add(str);
                    }
                }
                case "startDate" -> {
                    while (rs.next()) {
                        int num = rs.getInt(1);
                        startDateArr.add(num);
                    }
                }
                case "endDate" -> {
                    while (rs.next()) {
                        int num = rs.getInt(1);
                        endDateArr.add(num);
                    }
                }
                case "companyNm" -> {
                    while (rs.next()) {
                        String str = rs.getString(1);
                        companyNmArr.add(str);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Error("problem", e);
        }
    }

    /*
    private boolean checkUniqueEntry(ArrayList<String> array, String name) {
        for (String s : array) {
            if (name.equals(s)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUniqueEntry(ArrayList<Integer> array, int num) {
        for (Integer i : array) {
            if (num == i) {
                return false;
            }
        }
        return true;
    }
     */

}