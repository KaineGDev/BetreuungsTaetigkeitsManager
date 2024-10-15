package com.btm.btmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TaskController {
    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField startTimeField;

    @FXML
    private TextField endTimeField;

    @FXML
    private ComboBox<Integer> startHourComboBox;

    @FXML
    private ComboBox<Integer> startMinuteComboBox;

    @FXML
    private ComboBox<Integer> endHourComboBox;

    @FXML
    private ComboBox<Integer> endMinuteComboBox;


    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> startTimeColumn;

    @FXML
    private TableColumn<Task, String> endTimeColumn;

    @FXML
    private Button saveButton;

    @FXML
    private Label totalHoursLabel;


    @FXML
    private void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startzeit"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endzeit"));

        for (int i = 0; i < 24; i++) {
            startHourComboBox.getItems().add(i);
            endHourComboBox.getItems().add(i);
        }

        for (int i = 0; i < 60; i++) {
            startMinuteComboBox.getItems().add(i);
            endMinuteComboBox.getItems().add(i);
        }

        saveButton.setOnAction(event -> handleSaveTask());
    }

    private void calculateTotalTime() {
        int startHour = startHourComboBox.getValue() != null ? startHourComboBox.getValue() : 0;
        int startMinute = startMinuteComboBox.getValue() != null ? startMinuteComboBox.getValue() : 0;
        int endHour = endHourComboBox.getValue() != null ? endHourComboBox.getValue() : 0;
        int endMinute = endMinuteComboBox.getValue() != null ? endMinuteComboBox.getValue() : 0;

        int totalStartMinutes = startHour * 60 + startMinute;
        int totalEndMinutes = endHour * 60 + endMinute;
        int durationMinutes = totalEndMinutes - totalStartMinutes;

        int durationHours = durationMinutes / 60;
        int durationRemainingMinutes = durationMinutes % 60;

        totalHoursLabel.setText("Dauer: " + durationHours + " Stunden und " + durationRemainingMinutes + " Minuten");
    }

    public TaskController() {
        this.taskRepository = new TaskRepository();
        this.employeeRepository = new EmployeeRepository();
        if (EmployeeRepository.getEmpty()) {
            this.employeeRepository.save(new Employee(1,"User","Test"));
        }
    }

    public void createTask(String description, LocalTime startTime, LocalTime endTime) {
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);

        Task task = new Task(description, startTime, endTime);
        taskRepository.save(task);
    }

    public List<Task> getTasksBetweenDates(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDateRange(startDate, endDate);
    }

    @FXML
    private void handleSaveTask() {
        String description = descriptionField.getText();
        Integer startHour = startHourComboBox.getValue();
        Integer startMinute = startMinuteComboBox.getValue();
        Integer endHour = endHourComboBox.getValue();
        Integer endMinute = endMinuteComboBox.getValue();
        String startTimeString = String.format("%02d:%02d:00", startHour, startMinute);
        String endTimeString = String.format("%02d:%02d:00", endHour, endMinute);

        java.sql.Time startTime = java.sql.Time.valueOf(startTimeString);
        java.sql.Time endTime = java.sql.Time.valueOf(endTimeString);

        calculateTotalTime();

        taskRepository.save(new Task(description, startTime.toLocalTime(), endTime.toLocalTime()));
        /*
        try (Connection connection = DriverManager.getConnection(TaskRepository.getURL(), TaskRepository.getUSER(), TaskRepository.getPASSWORD())){
            String sql = "INSERT INTO taetigkeit (taetigkeit_id, m_id,beschreibung,startzeit, endzeit) VALUES (?,?,?,?,?)";
            String sql_tid = "SELECT MAX(taetigkeit_id) FROM taetigkeit";
            int new_tid = 1;
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql_tid)){
                if (resultSet.next()) {
                    int maxId = resultSet.getInt(1);
                    new_tid = maxId +1;
                }
            }

            String sql_mid = "SELECT MAX(m_id) FROM mitarbeiter";
            int new_mid = 1;

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql_mid)){
                if (resultSet.next()) {
                    int maxId = resultSet.getInt(1);
                    new_mid = maxId;
                }
            }


            try (PreparedStatement statement = connection.prepareStatement(sql)){

                statement.setInt(1,new_tid);
                statement.setInt(2, new_mid);
                statement.setString(3, descriptionField.getText());
                statement.setTime(4, startTime);
                statement.setTime(5, endTime);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            totalHoursLabel.setText("Fehler beim Speichern: "+e.getMessage());
        } */
    }

    @FXML
    private void handleShowTaskButton(ActionEvent event) {
        handleLoadTasksButton();
        SelectionModel<Task> selectionModel = taskTable.getSelectionModel();
        Task selectedTask = selectionModel.getSelectedItem();

        if (selectedTask != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tätigkeits-Details");
            alert.setHeaderText("Details zur ausgewählten Aufgabe");
            alert.setContentText("Beschreibung: "+selectedTask.getBeschreibung() +
            "\nStartzeit: " + selectedTask.getStartzeit() +
                    "\nEndzeit: "+ selectedTask.getEndzeit());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Keine Aufgabe ausgewählt");
            alert.setContentText("Bitte wähle eine Aufgabe aus der Liste aus.");
            alert.showAndWait();
        }
    }

    private void handleLoadTasksButton() {
        List<Task> tasks = taskRepository.loadAllTasks();
        taskTable.getItems().clear();
        taskTable.getItems().addAll(tasks);
    }
}
