package com.example.code;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    public void dudeidk(){

        Connection conn;

        try {
            String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);
            System.out.println("connected to database");


            String query = "select startLocal from voyage";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                ArrayList<String> arr = new ArrayList<String>();
                int index = 1;
                while(rs.next()){
                    String str = rs.getString(index);
                    arr.add(str);

                }
                for (String s : arr) {
                    System.out.println(s);
                }


            } catch (SQLException e) {throw new Error("problem", e);}
        } catch (SQLException e) { System.out.println(e.getMessage());}
    }
}