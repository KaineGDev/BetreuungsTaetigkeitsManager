package com.btm.btmanager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaskRepository {
    private static String URL = "jdbc:postgresql://localhost:5432/taetigkeitDB";
    private static String USER = "";
    private static String PASSWORD = "";
    private int taskcount;

    public TaskRepository() {
        this.loginUser(loadProperties());
        this.taskcount = 0;
    }

    public void save(Task task) {
        String sql = "INSERT INTO taetigkeit (taetigkeit_id, m_id, beschreibung, startzeit, endzeit) VALUES (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            String sql_tid = "SELECT MAX(taetigkeit_id) FROM taetigkeit";
            int new_tid = 1;
            try (Statement statement2 = connection.createStatement(); ResultSet resultSet = statement2.executeQuery(sql_tid)){
                if (resultSet.next()) {
                    int maxId = resultSet.getInt(1);
                    new_tid = maxId +1;
                }
            }
            String sql_mid = "SELECT MAX(m_id) FROM mitarbeiter";
            int new_mid = 1;

            try (Statement statement3 = connection.createStatement(); ResultSet resultSet = statement3.executeQuery(sql_mid)){
                if (resultSet.next()) {
                    new_mid = resultSet.getInt(1);
                }
            }

            statement.setInt(1, new_tid);
            statement.setInt(2, new_mid);
            statement.setString(3, task.getBeschreibung());
            statement.setTimestamp(4, Timestamp.valueOf(task.getStartzeit()));
            statement.setTimestamp(5, Timestamp.valueOf(task.getEndzeit()));
            if (taskcount == 0) {
                taskcount = 1;
            } else {
                taskcount++;
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Task> loadAllTasks() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM taetigkeit";

        try (Connection connection = DriverManager.getConnection(getURL(), getUSER(), getPASSWORD()); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                int id = resultSet.getInt("taetigkeit_id");
                int mid = resultSet.getInt("m_id");
                String description = resultSet.getString("beschreibung");
                LocalDateTime startTime = resultSet.getTimestamp("startzeit").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("endzeit").toLocalDateTime();

                Task task = new Task(description, startTime, endTime, id, mid);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getResourceAsStream("/config.properties")){
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public void loginUser(Properties properties) {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        if (!url.isEmpty()) {
            URL = url;
        }
        if (!username.isEmpty() ) {
            USER = username;
        }
        if (!password.isEmpty()) {
            PASSWORD = password;
        }
    }

    public List<Task> findByDateRange(LocalDate startDate, LocalDate endDate)  {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM taetigkeit WHERE startzeit >= ? AND endzeit <= ?";

        try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaetigkeit_id(resultSet.getInt("id"));
                task.setBeschreibung(resultSet.getString("beschreibung"));
                task.setStartzeit(resultSet.getTimestamp("startzeit").toLocalDateTime());
                task.setEndzeit(resultSet.getTimestamp("endzeit").toLocalDateTime());

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  tasks;
    }

    public int getTaskCount() {
        return taskcount;
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
}
