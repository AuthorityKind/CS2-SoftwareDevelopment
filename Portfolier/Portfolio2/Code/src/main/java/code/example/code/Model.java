package code.example.code;

import java.sql.*;
import java.util.ArrayList;

public class Model {
    private static final String url = "jdbc:sqlite:identifier.sqlite";
    static Connection conn = null;

    //runs a query that the function is given
    //meant for running queries that don't need to retrieve data from the database
    //takes a string and queries it to the database
    public static void runQuery(String query) {
        try {
            conn = DriverManager.getConnection(url);
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

    //returns an array of shipments from the database
    //is used to obtain general information from the database
    //it takes a string as a query and queries that to the database, then returns all shipments that match the query
    public static String[] retrieveData(String query) {
        ArrayList<String> temp = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    StringBuilder str = new StringBuilder();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                        str.append(rs.getString(i)).append(" ");
                    temp.add(str.toString());
                }
            } catch (SQLException e) {
                throw new Error("problem", e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) conn = null;
        }
        return temp.toArray(String[]::new);
    }

    //returns a string which is a query for a given shipment where it "books" a new shipment for the customer
    //is used for the buttons when booking a shipment
    //it queries the database and from the data it retrieves it comprises the output
    public static String getBookingQuery(String query, QueryValue[] queryValues) {
        String out = "";
        try {
            conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int newCurCap = 0;
                    for (QueryValue qval : queryValues)
                        if (qval.type.equals("curCap") && qval.val != null && !qval.val.equals("")) {
                            newCurCap = rs.getInt("curCap") + Integer.parseInt(qval.val);
                            break;
                        }
                    out = "UPDATE voyage SET curCap = " + newCurCap +
                            " WHERE departDate = " + rs.getInt("departDate")
                            + " AND arrivalDate = " + rs.getInt("arrivalDate")
                            + " AND companyName = " + "'" + rs.getString("companyName") + "'"
                            + " AND departLocal = " + "'" + rs.getString("departLocal") + "'"
                            + " AND arrivalLocal = " + "'" + rs.getString("arrivalLocal") + "'"
                            + " AND maxCap = " + rs.getInt("maxCap")
                            + " AND curCap = " + rs.getInt("curCap");
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
}