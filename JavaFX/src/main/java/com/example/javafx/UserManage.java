package com.example.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserManage {
    DatabaseConnect Connect = new DatabaseConnect();
    @FXML private TableView<User> userTableView;
    ObservableList<User> users = FXCollections.observableArrayList();
    @FXML private TableColumn<User, Integer> idCol;
    @FXML private TableColumn<User, String> usernameCol;
    @FXML private TableColumn<User, String> passwordCol;
    @FXML private TableColumn<User, String> firstnameCol;
    @FXML private TableColumn<User, String> lastnameCol;
    @FXML private TableColumn<User, Integer> dayOfBirthCol;
    @FXML private TableColumn<User, Integer> monthOfBirthCol;
    @FXML private TableColumn<User, Integer> yearOfBirthCol;
    @FXML private TableColumn<User, String> recoveryCodeCol;
    @FXML private TableColumn<User, String> avatarCol;
    @FXML private TableColumn<User, String> signUpDateCol;
    @FXML private TableColumn<User, Integer> isSaveCol;

    @FXML private TextField id;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private TextField dayOfBirth;
    @FXML private TextField monthOfBirth;
    @FXML private TextField yearOfBirth;
    @FXML private TextField recoveryCode;
    @FXML private TextField avatarLink;
    @FXML private ImageView avatar;
    @FXML private TextField signUpDate;
    @FXML private TextField isSave;
    @FXML private Label errorMessage;

    @FXML
    private Button modeDecision;

    @FXML
    public void initialize() throws Exception {
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        passwordCol.setCellValueFactory(data -> {
            String password = data.getValue().getPassword();
            return new SimpleStringProperty("●".repeat(password.length()));
        });
        firstnameCol.setCellValueFactory(new PropertyValueFactory<User,String>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<User,String>("lastname"));
        dayOfBirthCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("dayOfBirth"));
        monthOfBirthCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("monthOfBirth"));
        yearOfBirthCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("yearOfBirth"));
        recoveryCodeCol.setCellValueFactory(new PropertyValueFactory<User,String>("recoveryCode"));
        avatarCol.setCellValueFactory(new PropertyValueFactory<User,String>("avatarLink"));
        signUpDateCol.setCellValueFactory(new PropertyValueFactory<User,String>("dayIn"));
        isSaveCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("isSave"));

        errorMessage.setVisible(false);
        userTableView.setItems(users);
        loadUsers();
    }

    public List<User> getAllUsers(Connection connect) throws SQLException {
        PreparedStatement query = connect.prepareStatement("select * from users");
        ResultSet res = query.executeQuery();
        List<User> result = new ArrayList<>();
        while (res.next()) {
            int idusers = res.getInt("idusers");
            String userName = res.getString("username");
            String passWord = res.getString("password");
            String firstName = res.getString("first_name");
            String lastName = res.getString("last_name");
            int dayBirth = Integer.parseInt(res.getString("dayOfBirth"));
            int monthBirth = Integer.parseInt(res.getString("monthOfBirth"));
            int yearBirth = Integer.parseInt(res.getString("yearOfBirth"));
            String recoverCode = res.getString("recoveryCode");
            String avatar = res.getString("avatar");
            String signUpDate = res.getString("currentDate");
            Integer isSave = res.getInt("isSave");
            User newUser = new User(idusers, firstName, lastName, userName, passWord, dayBirth, monthBirth,
                    yearBirth, recoverCode, avatar, signUpDate, isSave);
            result.add(newUser);
        }
        return result;
    }

    public void loadUsers() {
        users.clear();
        userTableView.getItems().clear();
        try {
            List<User> userList = getAllUsers(Connect.connect());
            users.addAll(userList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkempty() {
        return id.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()
                || firstname.getText().isEmpty() || lastname.getText().isEmpty() || dayOfBirth.getText().isEmpty()
                || monthOfBirth.getText().isEmpty() || yearOfBirth.getText().isEmpty() || recoveryCode.getText().isEmpty()
                || signUpDate.getText().isEmpty() || isSave.getText().isEmpty();
    }

    @FXML
    public void reset() {
        id.setText("");
        username.setText("");
        password.setText("");
        firstname.setText("");
        lastname.setText("");
        dayOfBirth.setText("");
        monthOfBirth.setText("");
        yearOfBirth.setText("");
        recoveryCode.setText("");
        avatarLink.setText("");
        avatar.setImage(null);
        signUpDate.setText("");
        isSave.setText("");
        errorMessage.setVisible(false);
        userTableView.getSelectionModel().clearSelection();
        loadUsers();
    }

    private void addDB(Connection conn) {
        String Q = "INSERT INTO users (username, password, first_name, last_name, dayOfBirth, " +
                "monthOfBirth, yearOfBirth, recoveryCode, avatar, currentDate, isSave) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String Username = username.getText();
        String Password = password.getText();
        String Firstname = firstname.getText();
        String Lastname = lastname.getText();
        int DayOfBirth = Integer.parseInt(dayOfBirth.getText());
        int MonthOfBirth = Integer.parseInt(monthOfBirth.getText());
        int YearOfBirth = Integer.parseInt(yearOfBirth.getText());
        String RecoveryCode = recoveryCode.getText();
        String AvatarLink = avatarLink.getText();
        String SignUpDate = signUpDate.getText();
        Integer IsSave = Integer.valueOf(isSave.getText());

        try (PreparedStatement stm = conn.prepareStatement(Q)) {
            stm.setString(1, Username);
            stm.setString(2, Password);
            stm.setString(3, Firstname);
            stm.setString(4, Lastname);
            stm.setInt(5, DayOfBirth);
            stm.setInt(6, MonthOfBirth);
            stm.setInt(7, YearOfBirth);
            stm.setString(8, RecoveryCode);
            stm.setString(9, AvatarLink);
            stm.setString(10, SignUpDate);
            stm.setInt(11, IsSave);

            stm.executeUpdate();
            System.out.println("User added successfully to the database!");
        } catch (SQLException e) {
            System.out.println("Failed to add user to the database");
            e.printStackTrace();
        }
    }


    @FXML
    private void add() throws Exception {
        if(checkempty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("One or more information is missing!");
            return;
        }
        String Username = username.getText();
        String Password = password.getText();
        String Firstname = firstname.getText();
        String Lastname = lastname.getText();
        int DayOfBirth = Integer.parseInt(dayOfBirth.getText());
        int MonthOfBirth = Integer.parseInt(monthOfBirth.getText());
        int YearOfBirth = Integer.parseInt(yearOfBirth.getText());
        String RecoveryCode = recoveryCode.getText();
        String AvatarLink = avatarLink.getText();
        String SignUpDate = signUpDate.getText();
        Integer IsSave = Integer.valueOf(isSave.getText());

        StringBuilder error = new StringBuilder();
        for (User u : users) {
            if (Username.equals(u.getUsername())) {
                error.append(" Username ").append(u.getUsername()).append(" already exists! ");
            }
        }
        if (!error.isEmpty()) {
            errorMessage.setText(error.toString());
            errorMessage.setVisible(true);
        } else {
            User newU = new User(Firstname, Lastname, Username, Password, DayOfBirth, MonthOfBirth, YearOfBirth,
                    RecoveryCode, AvatarLink, SignUpDate, IsSave);
            users.add(newU);
            addDB(Connect.connect());
            reset();
        }
    }

    private void delDB(Connection conn) {
        try (PreparedStatement stm = conn.prepareStatement("Delete from users where idusers = ?")) {
            stm.setString(1, id.getText());
            stm.executeUpdate();
            System.out.println("User deleted successfully from the database!");
        } catch (SQLException e) {
            System.out.println("Deletion failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void delete() throws Exception {
        if(checkempty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Something is missing! Please check and then delete again.");
            return;
        }
        StringBuilder error = new StringBuilder();
        if (id.getText().isEmpty()) {
            System.out.println("ID is empty!");
            error.append("ID is empty!");
            errorMessage.setText(error.toString());
            errorMessage.setVisible(true);
        } else {
            int targetId;
            User u = userTableView.getSelectionModel().getSelectedItem();
            if (u == null) {
                targetId = Integer.parseInt(id.getText());
            } else {
                targetId = u.getId();
            }
            Iterator<User> it = users.iterator();
            while (it.hasNext()) {
                User next = it.next();
                if (next.getId() == targetId) {
                    it.remove();
                    delDB(Connect.connect());
                    reset();
                }
            }
        }
    }

    private void upDB(Connection connect) {
        String Q = "update users set username = ?, password = ?, first_name = ?" +
                "last_name = ?, dayOfBirth = ?, monthOfBirth = ?, yearOfBirth = ?, recoveryCode = ?, avatar = ?, " +
                "currentDate = ?, isSave = ? WHERE idusers = ?";
        try (PreparedStatement stm = connect.prepareStatement(Q)) {
            stm.setString(1,username.getText());
            stm.setString(2,password.getText());
            stm.setString(3,firstname.getText());
            stm.setString(4,lastname.getText());
            stm.setInt(5, Integer.parseInt(dayOfBirth.getText()));
            stm.setInt(6, Integer.parseInt(monthOfBirth.getText()));
            stm.setInt(7, Integer.parseInt(yearOfBirth.getText()));
            stm.setString(8,recoveryCode.getText());
            stm.setString(9,avatarLink.getText());
            stm.setString(10,signUpDate.getText());
            stm.setInt(11, Integer.parseInt(isSave.getText()));
            stm.executeUpdate();
            System.out.println("Update successfully on database");
        } catch (SQLException e) {
            System.out.println("Update failed on database");
            e.printStackTrace();
        }
    }

    @FXML
    private void update() throws Exception {
        if(checkempty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Something is missing! Please check and then update again.");
            return;
        }
        User u = userTableView.getSelectionModel().getSelectedItem();
        if (u == null) {
            System.out.println("unselected");
            errorMessage.setText("There is nothing to update");
            errorMessage.setVisible(true);
            return;
        }
        String Username = username.getText();
        String Password = password.getText();
        String Firstname = firstname.getText();
        String Lastname = lastname.getText();
        int DayOfBirth = Integer.parseInt(dayOfBirth.getText());
        int MonthOfBirth = Integer.parseInt(monthOfBirth.getText());
        int YearOfBirth = Integer.parseInt(yearOfBirth.getText());
        String RecoveryCode = recoveryCode.getText();
        String AvatarLink = avatar.getImage().getUrl();
        String SignUpDate = signUpDate.getText();
        Integer IsSave = Integer.valueOf(isSave.getText());

        u.setUsername(Username);
        u.setPassword(Password);
        u.setFirstname(Firstname);
        u.setLastname(Lastname);
        u.setDayOfBirth(DayOfBirth);
        u.setMonthOfBirth(MonthOfBirth);
        u.setYearOfBirth(YearOfBirth);
        u.setRecoveryCode(RecoveryCode);
        u.setAvatarLink(AvatarLink);
        u.setDayIn(SignUpDate);
        u.setIsSave(IsSave);
        userTableView.refresh();
        upDB(Connect.connect());
    }

    @FXML
    public void handleRowClick() {
        User u = userTableView.getSelectionModel().getSelectedItem();
        if (u != null) {
            id.setText(Integer.toString(u.getId()));
            username.setText(u.getUsername());
            password.setText(u.getPassword());
            firstname.setText(u.getFirstname());
            lastname.setText(u.getLastname());
            dayOfBirth.setText(Integer.toString(u.getDayOfBirth()));
            monthOfBirth.setText(Integer.toString(u.getMonthOfBirth()));
            yearOfBirth.setText(Integer.toString(u.getYearOfBirth()));
            recoveryCode.setText(u.getRecoveryCode());
            avatarLink.setText(u.getAvatarLink());
            LoadImage.loadAvatarImage(avatar, u.getAvatarLink());
            signUpDate.setText(u.getDayIn());
            isSave.setText(Integer.toString(u.getIsSave()));
        }
    }


    @FXML
    public void addMode() {
        modeDecision.setText("Add User");
        modeDecision.setOnAction(event -> {
            try {
                add();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    public void deleteMode() {
        modeDecision.setText("Delete User");
        modeDecision.setOnAction(event -> {
            try {
                delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    public void updateMode() {
        modeDecision.setText("Update User");
        modeDecision.setOnAction(event -> {
            try {
                update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
