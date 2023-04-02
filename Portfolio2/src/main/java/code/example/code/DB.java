package code.example.code;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    //These are used for the GUI, for the user to be able to see the various options
    ArrayList<Integer> departDateArr = new ArrayList<>();
    ArrayList<Integer> arrivalDateArr = new ArrayList<>();
    ArrayList<String> companyNameArr = new ArrayList<>();
    ArrayList<String> localArr = new ArrayList<>();

    final String url = "jdbc:sqlite:identifier.sqlite";

    Connection conn = null;

    public DB() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");

            createListFromQuery(conn, getIniQuery("departDate"), "departDate");
            createListFromQuery(conn, getIniQuery("arrivalDate"), "arrivalDate");
            createListFromQuery(conn, getIniQuery("companyName"), "companyName");
            createListFromQuery(conn, getIniQuery("departLocal"), "local");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) conn = null;
        }
    }

    //returns the query needed for the list initialization
    private String getIniQuery(String column) {
        return "select distinct " + column + " from voyage order by " + column + " asc";
    }

    //parseDate() and unparseDate() is used to make the date either more readable for the user or
    //compatible with the database's values
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

    //getData() is used when creating the buttons for the booking
    public ArrayList<String> getData(String[] chosenElements) {
        ArrayList<String> out = new ArrayList<>();
        String query = "SELECT * from voyage ";

        int count = 0;

        String[] refIdx = {"departDate", "arrivalDate", "companyName", "departLocal", "arrivalLocal", "curCap",};

        for (int i = 0; i < chosenElements.length; i++) {
            if (chosenElements[i] != null) {
                String sym = "";
                switch (i) {
                    case 0, 1, 2, 3, 4 -> sym = " = ";
                    case 5 -> sym = " > ";
                }

                query += queryCounter(count) + refIdx[i] + sym + chosenElements[i];
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
                    for (int i = 1; i <= 7; i++) {
                        str += getVoyageString(rs.getString(i), i);
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

    //this method is a messy copy of getData, but returns the query that the buttons that are pressed
    //will run
    public String getBookingQuery(String[] chosenElements) {
        String out = "";
        String query = "SELECT * from voyage ";

        int count = 0;

        String[] refIdx = {"departDate", "arrivalDate", "companyName", "departLocal", "arrivalLocal", "curCap",};

        for (int i = 0; i < chosenElements.length; i++) {
            if (chosenElements[i] != null) {
                String sym = "";
                switch (i) {
                    case 0, 1, 2, 3, 4 -> sym = " = ";
                    case 5 -> sym = " > ";
                }

                query += queryCounter(count) + refIdx[i] + sym + chosenElements[i];
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
                    //The math is wrong, so what should be 25 + 3 ended up being 34
                    int newCurCap = rs.getInt(7) + Integer.parseInt(chosenElements[5]);
                    String outQuery = "UPDATE voyage SET curCap = " + newCurCap;
                    //ArrayList<String> column = getColumns();

                    outQuery += " WHERE departDate = " + rs.getInt(1);
                    outQuery += " AND arrivalDate = " + rs.getInt(2);
                    outQuery += " AND companyName = " + "'" + rs.getString(3) + "'";
                    outQuery += " AND departLocal = " + "'" + rs.getString(4) + "'";
                    outQuery += " AND arrivalLocal = " + "'" + rs.getString(5) + "'";
                    outQuery += " AND maxCap = " + rs.getInt(6);
                    outQuery += " AND curCap = " + rs.getInt(7);

                    out = outQuery;
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

    //messy way of formatting text from database to be more readable
    private String getVoyageString(String in, int index) {
        return switch (index) {
            case 1 -> "Departure Date: " + parseDate(Integer.parseInt(in)) + ",   ";
            case 2 -> "Arrival Date: " + parseDate(Integer.parseInt(in)) + ",   ";
            case 3 -> "Company Name: " + in + ",   \n";
            case 4 -> "Departure Location: " + in + ",   ";
            case 5 -> "Arrival Location: " + in + ",   ";
            case 6 -> "Maximum Capacity: " + in + ",   ";
            case 7 -> "Current Capacity: " + in;
            default -> "";
        };
    }

    //very silly method used in getData() because I was tired and about to give up
    private String queryCounter(int count) {
        if (count == 0) {
            return "where ";
        } else {
            return " AND ";
        }
    }

    //executes a given query
    public void runQuery(String query) {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                System.out.println("Running: " + query);
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) conn = null;
        }
    }

    //the method used to make those lists from the constructor
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
                case "departDate" -> {
                    while (rs.next()) {
                        int num = rs.getInt(1);
                        departDateArr.add(num);
                    }
                }
                case "arrivalDate" -> {
                    while (rs.next()) {
                        int num = rs.getInt(1);
                        arrivalDateArr.add(num);
                    }
                }
                case "companyName" -> {
                    while (rs.next()) {
                        String str = rs.getString(1);
                        companyNameArr.add(str);
                    }
                }
            }
        } catch (SQLException e) {
            throw new Error("problem", e);
        }
    }
}