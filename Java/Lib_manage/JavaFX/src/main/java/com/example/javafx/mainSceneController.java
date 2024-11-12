package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class mainSceneController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField searchPaneInMain;
    @FXML
    private Button searchButtonInMain;
    @FXML
    private AnchorPane yourBookPane;
    @FXML
    private AnchorPane advancedSearchPane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label idLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private ComboBox<String> dayCombo;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private Button changeAvatarButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label invalidBirthDateLabel;
    @FXML
    private Label firstnameLabel;
    @FXML
    private ImageView infoAvatar;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private TextField codeField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label newPasswordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label codeLabel;
    @FXML
    private AnchorPane information;
    @FXML
    private AnchorPane security;
    @FXML
    private Label name;
    @FXML
    private Label dayLabel;
    @FXML
    private ImageView introAvatar;
    @FXML
    private AnchorPane intro;
    @FXML
    private AnchorPane header;
    @FXML
    private Label successfulLabel;
    @FXML
    private Label failedLabel;
    @FXML
    private Label successfulLabel1;
    @FXML
    private Label failedLabel1;
    @FXML
    private AnchorPane profile;
    @FXML
    private ImageView mainSceneAvatar;
    @FXML
    private Label welcomeText;
    @FXML
    private Button infoButton;
    @FXML
    private Button securityButton;
    @FXML
    private AnchorPane manager;
    @FXML
    private AnchorPane managerBar;
    /**
     * when init show mainPane first.
     */
    @FXML
    public void initialize() {
        welcomeText.setText("Welcome user " + user.getLastname() + " " + user.getFirstname() + "! It's been " + numberOfDay() + " since the first time!" );
        loadImage(mainSceneAvatar, user.getAvatarLink());
        showPane(mainPane);
        dayLabel.setText(numberOfDay());
        avatarPath = user.getAvatarLink();
        successfulLabel.setVisible(false);
        failedLabel.setVisible(false);
        loadImage(infoAvatar, user.getAvatarLink());
        loadImage(introAvatar, user.getAvatarLink());
        name.setText(user.getUsername());
        usernameTextField.setText(user.getUsername());
        idLabel.setText(Integer.toString(user.getId()));
        firstnameTextField.setText(user.getFirstname());
        lastnameTextField.setText(user.getLastname());

        ObservableList<String> months = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        monthCombo.setItems(months);

        ObservableList<String> days = FXCollections.observableArrayList();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        dayCombo.setItems(days);


        ObservableList<String> years = FXCollections.observableArrayList();
        for (int i = 1900; i <= 2024; i++) {
            years.add(Integer.toString(i));
        }
        yearCombo.setItems(years);

        dayCombo.setValue(Integer.toString(user.getDayOfBirth()));
        monthCombo.setValue(Integer.toString(user.getMonthOfBirth()));
        yearCombo.setValue(Integer.toString(user.getYearOfBirth()));
    }


    public void yourBookOnClick() {
        showPane(yourBookPane);
    }
    public void mainPaneOnClick() {
        showPane(mainPane);
    }
    public void advancedSearchPaneOnClick() {
        showPane(advancedSearchPane);
    }
    public void profilePaneOnClick() {
        showPane(profile);
        intro.setVisible(true);
        intro.setDisable(false);
        header.setVisible(false);
    }
    public void managerOnAction() {
        manager.setVisible(true);
        manager.setDisable(false);
        Animation.translateAnimation(managerBar);
    }

    public void outSideManagerClick() {
        manager.setVisible(false);
        manager.setDisable(true);
    }


    private void showPane(AnchorPane paneToShow) {
        // Ẩn tất cả các `Pane`
        manager.setVisible(false);
        profile.setVisible(false);
        mainPane.setVisible(false);
        yourBookPane.setVisible(false);
        advancedSearchPane.setVisible(false);
        manager.setDisable(true);
        profile.setDisable(true);
        mainPane.setDisable(true);
        yourBookPane.setDisable(true);
        advancedSearchPane.setDisable(true);

        // Chỉ hiển thị `Pane` được chọn
        paneToShow.setVisible(true);
        paneToShow.setDisable(false);

        // Ẩn hoặc hiển thị các phần tử bổ sung trong `mainPane`
        boolean showtoppane = (paneToShow == mainPane || paneToShow == yourBookPane);
        searchPaneInMain.setVisible(showtoppane);
        searchButtonInMain.setVisible(showtoppane);
    }

    User user = LoginController.user;
    /**
     * connect with database.
     */
    DatabaseConnect databaseConnect = new DatabaseConnect();


    public void updateButtonOnAction() {
        checkValidInformation();
    }

    public void changeOnAction() {
        openImageChooser();
    }

    public void checkValidInformation() {
        if (validateFields()) {
            invalidBirthDateLabel.setText("Valid Birth Date");
            invalidBirthDateLabel.setStyle("-fx-text-fill: #4CAF50");
        }



        if ((isValidUsername ||usernameLabel.getText().equals("Nothing changed") || usernameLabel.getText().isEmpty())
                && (isValidFirstname || firstnameLabel.getText().isEmpty())
                && (invalidBirthDateLabel.getText().equals("Valid Birth Date") || invalidBirthDateLabel.getText().isEmpty()) ) {
            successfulLabel.setVisible(true);
            Animation.fadeAnimation(successfulLabel);
            updateUser();
            loadImage(introAvatar, user.getAvatarLink());
            loadImage(mainSceneAvatar, user.getAvatarLink());
            user.setAvatarLink(avatarPath);
        } else {
            failedLabel.setVisible(true);
            Animation.fadeAnimation(failedLabel);
        }
    }

    /**
     * load image to imageview
     * @param avatarImage is .
     * @param link is.
     */
    public void loadImage(ImageView avatarImage, String link) {
        if(!link.equals("/com/example/javafx/user.jpg")) {
            Image image = new Image(link);
            avatarImage.setImage(image);
            System.out.println(avatarImage.getFitWidth() + " " + avatarImage.getFitHeight());
        } else {
            Image defaultImage = new Image(getClass().getResource("/com/example/javafx/user.jpg").toExternalForm());
            avatarImage.setImage(defaultImage);
            System.out.println(avatarImage.getFitWidth() + " " + avatarImage.getFitHeight());
        }
        double radius = Math.min(avatarImage.getFitWidth(), avatarImage.getFitHeight()) / 2;
        Circle circle = new Circle(avatarImage.getFitWidth() / 2, avatarImage.getFitHeight() / 2, radius);
        avatarImage.setPreserveRatio(false);
        avatarImage.setStyle("-fx-border-width: 5px; -fx-border-color: #000000; -fx-border-style: solid;");
        avatarImage.setClip(circle);
    }


    /**
     * using a list to wrap the boolean to take the value of checkUsername from Released.
     */
    public boolean[] checkUsername = {false};
    public boolean isValidUsername;

    public void usernameReleased() {
        Released.userNameReleased(usernameTextField, usernameLabel, checkUsername);
        isValidUsername = checkUsername[0];
        if(usernameTextField.getText().equals(user.getUsername())) {
            usernameLabel.setText("Nothing changed");
            usernameLabel.setStyle("-fx-text-fill: red");
        } else if (UsernameExisted()) {
            usernameLabel.setText("Username already exists");
            usernameLabel.setStyle("-fx-text-fill: red");
            usernameTextField.setStyle("-fx-border-color: red");
            isValidUsername = false;
        }
    }

    /**
     * using a list to wrap the boolean to take the value of checkFirstname from Released.
     */
    public boolean[] checkFirstname = {false};
    public boolean isValidFirstname;

    public void nameReleased() {
        Released.firstnameReleased(firstnameTextField, firstnameLabel, checkFirstname);
        isValidFirstname = checkFirstname[0];
    }

    /**
     * check If new username is existed or not.
     *
     * @return true if it's existed.
     */
    public boolean UsernameExisted() {
        String UsernameInput = usernameTextField.getText();
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UsernameInput);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * export data into database.
     */
    public void updateUser() {
        String username = usernameTextField.getText();
        String firstNameInput = firstnameTextField.getText();
        String lastNameInput = lastnameTextField.getText();
        String day = dayCombo.getValue();
        String month = monthCombo.getValue();
        String year = yearCombo.getValue();
        String avatarLink = avatarPath;
        int id = user.getId();


        String query = "update users set username = ?, first_name = ?, last_name = ?, dayOfBirth = ?, monthOfBirth = ?, yearOfBirth = ?, avatar = ?   where idusers = ?";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, firstNameInput);
            preparedStatement.setString(3, lastNameInput);
            preparedStatement.setInt(4, Integer.parseInt(day));
            preparedStatement.setInt(5, Integer.parseInt(month));
            preparedStatement.setInt(6, Integer.parseInt(year));
            preparedStatement.setString(7, avatarLink);
            preparedStatement.setInt(8, id);


            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * check if the date is valid.
     *
     * @return true if it's valid.
     */
    public boolean checkBirth(int year, int month, int day) {
        if (year > LocalDate.now().getYear()) {
            return false;
        } else if (year == LocalDate.now().getYear()) {
            if (month > LocalDate.now().getMonthValue()) {
                return false;
            } else if (month == LocalDate.now().getMonthValue()) {
                return day <= LocalDate.now().getDayOfMonth();
            }
        } else if (year < LocalDate.now().getYear()) {
            return true;
        }
        return true;
    }

    /**
     * check if the user fill by themselves is valid or not.
     *
     * @return true if it's.
     */
    public boolean validateFields() {
        String dayInput = dayCombo.getValue();
        String monthInput = monthCombo.getValue();
        String yearInput = yearCombo.getValue();

        if (monthInput == null || monthInput.isBlank() || dayInput == null || dayInput.isBlank() || yearInput == null || yearInput.isBlank()) {
            invalidBirthDateLabel.setText("Please finish your birth date.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        int month;
        try {
            month = Integer.parseInt(monthInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Month must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        int day;
        try {
            day = Integer.parseInt(dayInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Day of birth must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        int year;
        try {
            year = Integer.parseInt(yearInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Year must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(monthInput) < 1 || Integer.parseInt(monthInput) > 12) {
            invalidBirthDateLabel.setText("Month must be a number between 1 and 12.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(dayInput) < 1 && Integer.parseInt(monthInput) > 31) {
            invalidBirthDateLabel.setText("please finish your date of birth.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(yearInput) < 1900 || Integer.parseInt(yearInput) > 2024) {
            invalidBirthDateLabel.setText("please fill in your real year of birth.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(monthInput) == 2) {
            if (Integer.parseInt(yearInput) % 4 == 0 && Integer.parseInt(yearInput) % 100 != 0 || Integer.parseInt(yearInput) % 400 == 0) {
                if (Integer.parseInt(dayInput) > 29) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
            } else {
                if (Integer.parseInt(dayInput) > 28) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
            }
        } else if (Integer.parseInt(monthInput) == 4 || Integer.parseInt(monthInput) == 6 || Integer.parseInt(monthInput) == 9 || Integer.parseInt(monthInput) == 11) {
            if (Integer.parseInt(dayInput) > 30) {
                invalidBirthDateLabel.setText("please finish your birth date.");
                invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                return false;
            }
        }

        return checkBirth(year, month, day);
    }

    private String avatarPath = "";
    public void openImageChooser() {
        // Mở hộp thoại chọn file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your avatar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(changeAvatarButton.getScene().getWindow());

        if (selectedFile != null) {
            avatarPath = selectedFile.toURI().toString();
            loadImage(infoAvatar, avatarPath);
            user.setAvatarLink(avatarPath);
        } else {
            loadImage(infoAvatar, user.getAvatarLink());
            avatarPath = user.getAvatarLink();
        }
    }











    public void changePassOnAction() {
        checkValidSecurity();
    }

    public void checkValidSecurity() {
        if(passCheck()) {
            if ((newPasswordLabel.getText().equals("Medium Password") || newPasswordLabel.getText().equals("Strong Password"))
                    && confirmPasswordLabel.getText().equals("Valid Password")
                    && codeLabel.getText().equals("Valid Code")) {
                successfulLabel1.setVisible(true);
                Animation.fadeAnimation(successfulLabel1);
                updateSecurity();
            } else {
                failedLabel1.setVisible(true);
                Animation.fadeAnimation(failedLabel1);
            }
        } else {
            failedLabel1.setVisible(true);
            Animation.fadeAnimation(failedLabel1);
        }
    }

    public void passReleased() {
        if(passCheck()) {
            passwordLabel.setText("Valid Password");
            passwordLabel.setStyle("-fx-text-fill: green");
        } else {
            passwordLabel.setText("Invalid Password");
            passwordLabel.setStyle("-fx-text-fill: red");
        }
    }

    /**
     * using a list to wrap the boolean to take the value of checkPassword from Released.
     */
    public boolean[] checkPassword = {false};
    public boolean isValidPassword;

    public void passwordReleased() {
        Released.passwordReleased(newPasswordField, newPasswordLabel, checkPassword);
        isValidPassword = checkPassword[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkConfirm from Released.
     */
    public boolean[] checkConfirm = {false};
    public boolean isValidConfirm;

    public void confirmReleased() {
        Released.confirmReleased(confirmPasswordField, newPasswordField, newPasswordLabel, confirmPasswordLabel, checkConfirm);
        isValidConfirm = checkConfirm[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkCode from Released.
     */
    public boolean[] checkCode = {false};
    public boolean isValidCode;

    public void recoveryReleased() {
        Released.recoveryReleased(codeField, codeLabel, checkCode);
        isValidCode = checkCode[0];
    }

    public boolean passCheck() {
        String password = passwordField.getText();
        int id = user.getId();
        String query = "select * from users where password = ? and idusers = ?";
        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void updateSecurity() {
        String password = newPasswordField.getText();
        String code = codeField.getText();
        int id = user.getId();

        String query = "update users set password = ?, recoveryCode = ? where idusers = ?";
        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, code);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showProfilePane(AnchorPane paneShowed) {
        security.setVisible(false);
        information.setVisible(false);
        header.setVisible(false);
        intro.setVisible(false);
        security.setDisable(true);
        information.setDisable(true);
        header.setDisable(true);
        intro.setDisable(true);

        paneShowed.setVisible(true);
        paneShowed.setDisable(false);
    }

    public void informationOnAction() {
        showProfilePane(information);
        header.setVisible(true);
        header.setDisable(false);
        securityButton.setStyle(null);
        infoButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-text-fill: #43fb00; ");
    }

    public void securityOnAction() {
        showProfilePane(security);
        header.setVisible(true);
        header.setDisable(false);
        infoButton.setStyle(null);
        securityButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-text-fill: #43fb00; ");
    }

    public void setting() {
        showProfilePane(information);
        header.setVisible(true);
        header.setDisable(false);
        infoButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-text-fill: #43fb00; ");
    }

    public void logout() {
        Stage stage = (Stage) name.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    public void backToProfileOnAction() {
        showProfilePane(intro);
    }



    public String numberOfDay() {
        LocalDate date = LocalDate.now();
        LocalDate signUpDate = LocalDate.parse(user.getDayIn());
        long days = ChronoUnit.DAYS.between(signUpDate, date);
        return days + " days";
    }

    @FXML
    private TextField Title;
    @FXML
    private TextField Author;
    @FXML
    private TextField Subject;
    @FXML
    private TextField Publisher;
    @FXML
    private TextField ISBN;
    @FXML
    private TextField Year;
    @FXML
    private MenuButton Language;
    @FXML
    private MenuButton SortBy;

    @FXML
    public void onSearchClick() throws Exception {
        String title = Title.getText();
        String author = Author.getText();
        String subject = Subject.getText();
        String publisher = Publisher.getText();
        String isbn = ISBN.getText();
        String year = Year.getText();
        String language = Language.getText();
        String sortBy = SortBy.getText();
        StringBuilder Q = new StringBuilder("SELECT * FROM books WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            Q.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }
        if (author != null && !author.isEmpty()) {
            Q.append(" AND author LIKE ?");
            params.add("%" + author + "%");
        }
        if (subject != null && !subject.isEmpty()) {
            Q.append(" AND subject LIKE ?");
            params.add("%" + subject + "%");
        }
        if (publisher != null && !publisher.isEmpty()) {
            Q.append(" AND publisher LIKE ?");
            params.add("%" + publisher + "%");
        }
        if (isbn != null && !isbn.isEmpty()) {
            Q.append(" AND isbn = ?");
            params.add(isbn);
        }
//        if (language != null && !language.isEmpty()) {
//            Q.append(" AND language = ?");
//            params.add(language);
//        }
        if (year != null && !year.isEmpty()) {
            Q.append(" AND YEAR(created_date) = ?");
            params.add(year);
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            if(sortBy.equals("Newest first")) {
                Q.append(" ORDER BY created_date DESC");
            } else {
                Q.append(" ORDER BY created_date ASC");
            }
        }
        AdvancedSearch Search = new AdvancedSearch();
        List<Books> res = Search.search(Q.toString(), params, databaseConnect.connect());
        if (res.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Books b : res) {
                System.out.println(b.toString());
            }
        }
    }
}





