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

    final String url = "jdbc:sqlite:identifier.sqlite";

    Connection conn = null;

    public DB() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");

            createListFromQuery(conn, getIniQuery("startDate"), "startDate");
            createListFromQuery(conn, getIniQuery("endDate"), "endDate");
            createListFromQuery(conn, getIniQuery("companyName"), "companyNm");
            createListFromQuery(conn, getIniQuery("startLocal"), "local");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) conn = null;
        }
    }

    private String getIniQuery(String column) {
        return "select distinct " + column + " from voyage order by " + column + " asc";
    }

    public String parseDate(int num) {
        String str = String.valueOf(num);

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            count++;

            if (count == 2 && i != str.length() - 1) {
                String start = "";
                String end = "";

                for (int i2 = 0; i2 <= i; i2++) {
                    start = start + str.charAt(i2);
                }

                for (int i3 = i + 1; i3 < str.length(); i3++) {
                    end = end + str.charAt(i3);
                }

                str = start + "/" + end;
                count = -1;
            }
        }
        return str;
    }

    public String unparseDate(String date) {
        if (date != null) {
            return date.replaceAll("/", "");
        } else {
            return "";
        }
    }

    public ArrayList<String> getData(String[] chosenElements) {
        ArrayList<String> out = new ArrayList<>();
        String query = "SELECT * from voyage ";

        int count = 0;

        String[] refIdx = {"startLocal", "startDate", "endLocal", "endDate"};

        for (int i = 0; i < chosenElements.length; i++) {
            if (chosenElements[i] != null) {
                query += queryCounter(count) + refIdx[i] + " = " + chosenElements[i];
                count++;
            }
        }
        System.out.println(query);

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String str = "";
                    for (int i = 1; i <= 5; i++) {
                        str += getVoyageString(rs.getString(i),i);
                    }
                    out.add(str);
                }

            } catch (SQLException e) {
                throw new Error("problem", e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) conn = null;
        }

        return out;
    }

    private String getVoyageString(String in, int index) {
        return switch (index) {
            case 1 -> "Departure Date: " + parseDate(Integer.parseInt(in)) + ", ";
            case 2 -> "Arrival Date: " + parseDate(Integer.parseInt(in)) + ", ";
            case 3 -> "Company Name: " + in + ", ";
            case 4 -> "Departure Location: " + in + ", ";
            case 5 -> "Arrival Location: " + in + ", ";
            default -> "";
        };
    }

    private String queryCounter(int count) {
        if (count == 0) {
            return "where ";
        } else {
            return " AND ";
        }
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
}