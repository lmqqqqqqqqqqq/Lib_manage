package com.example.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    @FXML private TableColumn<User, String> reasonOfBan;
    @FXML private TableColumn<User, Date> dayOfBan;
    @FXML private TableColumn<User, Integer> coin;

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
    @FXML private TextArea banReason;
    @FXML private DatePicker banDate;
    @FXML private TextField coinText;

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
        reasonOfBan.setCellValueFactory(new PropertyValueFactory<User, String>("banReason"));
        dayOfBan.setCellValueFactory(new PropertyValueFactory<User, Date>("isBan"));
        coin.setCellValueFactory(new PropertyValueFactory<User, Integer>("coin"));


        errorMessage.setVisible(false);
        userTableView.setItems(users);
        loadUsers();
        reset();
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
            Date sqlBanDate = res.getDate("isBan");
            Integer coinA = res.getInt("coin");
            LocalDate banDate = null;
            if(sqlBanDate != null) {
                banDate = sqlBanDate.toLocalDate();
            }
            String reason = res.getString("banReason");
            User newUser = new User(idusers, firstName, lastName, userName, passWord, dayBirth, monthBirth,
                    yearBirth, recoverCode, avatar, signUpDate, isSave, banDate, reason, coinA);
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
                || banDate.getValue() == null || banDate.getValue().isBefore(LocalDate.now());
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
        banReason.setText("");
        banDate.setValue(null);
        coinText.setText("");
        userTableView.getSelectionModel().clearSelection();
        loadUsers();
    }

    private void banUser(Connection conn) {
        try (PreparedStatement stm = conn.prepareStatement("update users set isBan = ?, banReason = ? where idusers = ?")) {
            stm.setDate(1, Date.valueOf(banDate.getValue()));
            stm.setString(2, banReason.getText());
            stm.setString(3, id.getText());
            stm.executeUpdate();
            System.out.println("Ban user successfully from the database!");
            errorMessage.setVisible(true);
            errorMessage.setText("Ban user successfully from the database!");
            errorMessage.setStyle("-fx-text-fill: green;");
        } catch (SQLException e) {
            System.out.println("Ban user failed!");
            errorMessage.setVisible(true);
            errorMessage.setText("Ban user failed!");
            errorMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    private void unbanUser(Connection conn) {
        try (PreparedStatement stm = conn.prepareStatement("update users set isBan = null, banReason = null where idusers = ?")) {
            stm.setString(1, id.getText());
            stm.executeUpdate();
            System.out.println("Unban user successfully from the database!");
            errorMessage.setVisible(true);
            errorMessage.setText("Unban user successfully from the database!");
            errorMessage.setStyle("-fx-text-fill: green;");
        } catch (SQLException e) {
            System.out.println("Unban user failed!");
            errorMessage.setVisible(true);
            errorMessage.setText("Unban user failed!");
            errorMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }

    @FXML
    private void banOnAction() throws Exception {
        if(checkempty()) {
            errorMessage.setVisible(true);
            errorMessage.setText("Please choose your User and complete the user's banning section.");
            errorMessage.setStyle("-fx-text-fill: red;");
            return;
        }
        StringBuilder error = new StringBuilder();
        if (id.getText().isEmpty()) {
            System.out.println("ID is empty!");
            error.append("ID is empty!");
            errorMessage.setText(error.toString());
            errorMessage.setVisible(true);
        } else {
            banUser(Connect.connect());
            reset();
        }
    }

    @FXML
    private void unBanOnAction() throws Exception {
        if(banDate.getValue() == null) {
            errorMessage.setVisible(true);
            errorMessage.setText("User is not being banned");
            errorMessage.setStyle("-fx-text-fill: red;");
        } else {
            unbanUser(Connect.connect());
            reset();
        }
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
            banDate.setValue(u.getIsBan());
            banReason.setText(u.getBanReason());
            coinText.setText(Integer.toString(u.getCoin()));
        }
    }
}
