package org.project;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres?user=postgres&password=admin");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"Test\"");
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getString(i + 1) + " ");
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}