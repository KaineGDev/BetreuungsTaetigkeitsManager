package com.btm.btmanager;

import java.sql.*;

public class EmployeeRepository {
    private static String URL = "jdbc:postgresql://localhost:5432/taetigkeitDB";
    private static String USER = "";
    private static String PASSWORD = "";
    private static boolean isEmpty;

    public EmployeeRepository() {
        this.modelUser();
    }

    public void save(Employee employee) {
        String sql = "INSERT INTO mitarbeiter (m_id, m_name, m_vorname) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, employee.getM_id());
            statement.setString(2, employee.getM_name());
            statement.setString(3, employee.getM_vorname());
            isEmpty = false;
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modelUser() {
        USER = "postgres";
        PASSWORD = "rootpw";
    }

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static boolean getEmpty() {
        return isEmpty;
    }
}
