package org.example.aprendovar4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    private User currentUser;
    private DataStorage dataStorage;
    private final TopicStorage topicStorage = new TopicStorage("topics.json");
    private final Map<String, Topic> topics = topicStorage.loadTopics();
    @Override
    public void start(Stage primaryStage) throws IOException {
        dataStorage = new DataStorage();

        // Layout for login screen
        BorderPane loginLayout = new BorderPane();//layout container, lets children get arranged in different regions(like top, center,left, etc.)
        VBox loginForm = new VBox(10);//layout container , arranges child nodes in vertical column
        loginForm.getStyleClass().add("vbox");
        Label loginLabel = new Label("Welcome! Please enter your username:");
        loginLabel.getStyleClass().add("label");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");
        usernameField.setPromptText("Enter username");
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");

        loginForm.getChildren().addAll(loginLabel, usernameField, loginButton);
        loginLayout.setCenter(loginForm);

        Scene loginScene = new Scene(loginLayout, 600, 500);
        loginScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Action for Login Button
        loginButton.setOnAction(e -> {//method to define an event handler for an action( here for clicking the button)
            String username = usernameField.getText();
            if (dataStorage.userExists(username)) {
                showPasswordScreen(primaryStage, username);//primaryStage = the first stage provided when the application starts
            } else {
                showCreateAccountScreen(primaryStage, username);
            }
        });

    }

    private void showPasswordScreen(Stage primaryStage, String username) {
        // Layout for password screen
        BorderPane passwordLayout = new BorderPane();
        VBox passwordForm = new VBox(10);
        passwordForm.getStyleClass().add("vbox");
        Label passwordLabel = new Label("User exists. Please enter your password:");
        passwordLabel.getStyleClass().add("label");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field");
        passwordField.setPromptText("Enter password");
        Button passwordButton = new Button("Login");
        passwordButton.getStyleClass().add("button");

        passwordForm.getChildren().addAll(passwordLabel, passwordField, passwordButton);
        passwordLayout.setCenter(passwordForm);

        Scene passwordScene = new Scene(passwordLayout, 600, 500);
        passwordScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Login Password");
        primaryStage.setScene(passwordScene);//here we switch the Scene

        // Action for Password Button
        passwordButton.setOnAction(e -> {
            String password = passwordField.getText();
            User user = dataStorage.getUser(username);
            if (user.getPassword().equals(password)) {
                currentUser = user;
                showMenu(primaryStage);
            } else {
                throw new WrongPasswordException("Incorrect password. Please try again.");
            }
        });
    }

    private void showCreateAccountScreen(Stage primaryStage, String username) {
        // Layout for creating a new account
        BorderPane createAccountLayout = new BorderPane();
        VBox createAccountForm = new VBox(10);
        createAccountForm.getStyleClass().add("vbox");
        Label createAccountLabel = new Label("User does not exist. Would you like to create an account?");
        createAccountLabel.getStyleClass().add("label");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter password for the user " + username);
        Button createAccountButton = new Button("Create Account Password");
        createAccountButton.getStyleClass().add("button");

        createAccountForm.getChildren().addAll(createAccountLabel, newPasswordField, createAccountButton);
        createAccountLayout.setCenter(createAccountForm);

        Scene createAccountScene = new Scene(createAccountLayout, 600, 500);
        createAccountScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Account Creation");
        primaryStage.setScene(createAccountScene);

        // Action for Create Account Button
        createAccountButton.setOnAction(e -> {
            String password = newPasswordField.getText();
            dataStorage.createUser(username, password);
            showAlert("Account created successfully! Please log in.");
            showPasswordScreen(primaryStage, username);
        });
    }

    private void showMenu(Stage primaryStage) {
        // Layout for the main menu screen
        BorderPane menuLayout = new BorderPane();
        VBox menuForm = new VBox(10);
        menuForm.getStyleClass().add("vbox");
        Label menuLabel = new Label("Welcome " + currentUser.getName() + "!");
        menuLabel.getStyleClass().add("label");
        Button viewProgressButton = new Button("View progress for all topics");
        viewProgressButton.getStyleClass().add("button");
        Button viewTotalScoreButton = new Button("View total score");
        viewTotalScoreButton.getStyleClass().add("button");
        Button selectTopicButton = new Button("View all topics and choose one to learn or take a quiz");
        selectTopicButton.getStyleClass().add("button");
        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button");

        menuForm.getChildren().addAll(menuLabel, viewProgressButton, viewTotalScoreButton, selectTopicButton, exitButton);
        menuLayout.setCenter(menuForm);

        Scene menuScene = new Scene(menuLayout, 600, 500);
        menuScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(menuScene);

        // Action for View Progress Button
        viewProgressButton.setOnAction(e -> showDisplayProgressScreen(primaryStage));

        // Action for View Total Score Button
        viewTotalScoreButton.setOnAction(e -> {
            currentUser.updateTotalScoreAndSet();
            showAlert("Total score: " + currentUser.getTotalScore());
        });

        // Action for Select Topic Button
        selectTopicButton.setOnAction(e -> showTopicSelectionScreen(primaryStage));

        // Action for Exit Button
        exitButton.setOnAction(e -> {
            primaryStage.close();
        });
    }

    private void showDisplayProgressScreen(Stage primaryStage){
        BorderPane topicLayout = new BorderPane();
        VBox topicForm = new VBox(10);
        topicForm.getStyleClass().add("vbox");
        Label userLabel = new Label("Progress for " + currentUser.getName() + " : ");
        userLabel.getStyleClass().add("label");
        Label totalScore = new Label ("Total Score: " + currentUser.getTotalScore());
        totalScore.getStyleClass().add("label");
        Label topicProgress = new Label ("If you want to see your progress for a certain topic you have to navigate to that topic's section!");
        topicProgress.getStyleClass().add("label");
        Button exitButton = new Button("Exit Progress Display");
        exitButton.getStyleClass().add("button");
        topicForm.getChildren().addAll(userLabel, totalScore, topicProgress,exitButton);
        topicLayout.setCenter(topicForm);

        Scene progressScene = new Scene(topicLayout, 600, 500);
        progressScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Overall Progress");
        primaryStage.setScene(progressScene);

        exitButton.setOnAction(e -> showMenu(primaryStage));


    }

    private void showTopicSelectionScreen(Stage primaryStage) {
        // Layout for selecting a topic
        BorderPane topicLayout = new BorderPane();
        VBox topicForm = new VBox(10);
        topicForm.getStyleClass().add("vbox");
        Label topicLabel = new Label("Select a topic:");
        topicLabel.getStyleClass().add("label");
        ComboBox<String> topicComboBox = new ComboBox<>();
        topicComboBox.getStyleClass().add("combo-box");
        Button selectTopicButton = new Button("Select Topic");
        selectTopicButton.getStyleClass().add("button");

        //Load topics and populate ComboBox
        topicComboBox.getItems().addAll(topics.keySet());

        topicForm.getChildren().addAll(topicLabel, topicComboBox, selectTopicButton);
        topicLayout.setCenter(topicForm);

        Scene topicScene = new Scene(topicLayout, 600, 500);
        topicScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle("Topic Selection");
        primaryStage.setScene(topicScene);

        // Action for Select Topic Button
        selectTopicButton.setOnAction(e -> {
            String selectedTopic = topicComboBox.getValue();
            if (selectedTopic != null && topics.containsKey(selectedTopic)) {
                Topic topic = topics.get(selectedTopic);
                showTopicSessionScreen(primaryStage, selectedTopic, topic);
            }else{
                showAlert("Please select a valid topic.");
            }
        });
    }

    private void showTopicSessionScreen(Stage primaryStage, String selectedTopic, Topic topic) {
        // Layout for the topic session screen
        BorderPane sessionLayout = new BorderPane();
        VBox sessionForm = new VBox(10);
        sessionForm.getStyleClass().add("vbox");
        Label sessionLabel = new Label("You have selected: " + selectedTopic);
        sessionLabel.getStyleClass().add("label");
        Button learnButton = new Button("Learn");
        learnButton.getStyleClass().add("button");
        Button quizButton = new Button("Take Quiz");
        quizButton.getStyleClass().add("button");
        Button progressButton = new Button("Show Progress");
        progressButton.getStyleClass().add("button");
        Button exitButton = new Button("Exit Topic");
        exitButton.getStyleClass().add("button");

        sessionForm.getChildren().addAll(sessionLabel, learnButton, quizButton, progressButton,exitButton);
        sessionLayout.setCenter(sessionForm);

        Scene sessionScene = new Scene(sessionLayout, 600, 500);
        sessionScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle( selectedTopic + " Session");
        primaryStage.setScene(sessionScene);

        // Action for Learn Button
        learnButton.setOnAction(e -> showLearningSession(primaryStage,selectedTopic,topic));

        // Action for Quiz Button
        quizButton.setOnAction(e -> showQuizSession(primaryStage,selectedTopic, topic));
        
        progressButton.setOnAction(e -> showTopicProgress(primaryStage,selectedTopic,topic));
        // Action for Exit Topic Button
        exitButton.setOnAction(e -> showMenu(primaryStage));
    }

    private void showLearningSession(Stage primaryStage,String selectedTopic,Topic topic){
        BorderPane sessionLayout = new BorderPane();
        VBox sessionForm = new VBox(10);
        sessionForm.getStyleClass().add("vbox");
        Label learnSession  = new Label("Learning Session for " + selectedTopic);
        learnSession.getStyleClass().add("label");
        TextArea contentArea = new TextArea(topic.getLearn().getContent());
        contentArea.getStyleClass().add("text-area");
        contentArea.setWrapText(true);
        contentArea.setEditable(false);
        contentArea.setPrefHeight(400);
        Button exitButton = new Button("Exit Learning Session");
        exitButton.getStyleClass().add("button");

        sessionForm.getChildren().addAll(learnSession, contentArea, exitButton);
        sessionLayout.setCenter(sessionForm);


        Scene sessionScene = new Scene(sessionLayout, 600, 500);
        sessionScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle(selectedTopic + " Learning Session");
        primaryStage.setScene(sessionScene);

        exitButton.setOnAction(e -> showTopicSessionScreen(primaryStage,selectedTopic,topic) );


    }

    private void showQuizSession(Stage primaryStage,String selectedTopic, Topic topic)
    {
        BorderPane sessionLayout = new BorderPane();
        VBox sessionForm = new VBox(10);
        sessionForm.getStyleClass().add("vbox");
        Label quizSession  = new Label("Quiz Session for " + selectedTopic);
        quizSession.getStyleClass().add("label");
        Label topicQuestion = new Label();
        topicQuestion.getStyleClass().add("label");
        TextField questionAnswer = new TextField();
        questionAnswer.getStyleClass().add("text-field");
        Button submitButton = new Button("Submit Answer");
        submitButton.getStyleClass().add("button");
        Button exitButton = new Button("Exit Quiz Session");
        exitButton.getStyleClass().add("button");
        Label feedbackLabel = new Label();
        feedbackLabel.getStyleClass().add("label");

        sessionForm.getChildren().addAll(quizSession,topicQuestion,questionAnswer,submitButton,feedbackLabel,exitButton);
        sessionLayout.setCenter(sessionForm);

        Scene sessionScene = new Scene(sessionLayout, 600, 500);
        sessionScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setTitle(selectedTopic + " Quiz Session");
        primaryStage.setScene(sessionScene);

        List<String> questions = topic.getQuiz().getQuestions();
        List<String> answers = topic.getQuiz().getAnswers();
        final int[] currentIndex = {0};
        final int[] score  = {0};



        if (!questions.isEmpty()) {
            topicQuestion.setText("Question " + (currentIndex[0] + 1) + ": " + questions.get(currentIndex[0]));
        }
        submitButton.setOnAction(e -> {
            String userAnswer = questionAnswer.getText();

            if (userAnswer.equalsIgnoreCase(answers.get(currentIndex[0]))){
                feedbackLabel.setText("Correct!");
                score[0] += 10;
            }else {
                feedbackLabel.setText("Oh no! The correct answer was " + answers.get(currentIndex[0]));
            }

            currentIndex[0] ++;
            if (currentIndex[0] < questions.size()){
                topicQuestion.setText("Question " + (currentIndex[0] + 1) + ": " + questions.get(currentIndex[0]));
                questionAnswer.clear();
            }else{
                feedbackLabel.setText("Quiz completed! Your final score is " + score[0]);
                submitButton.setDisable(true); // Disable the Submit button


                if (!currentUser.getTopicProgress().containsKey(selectedTopic)) {
                    UserProgress userprogress = new UserProgress(1, 0, 0);
                    userprogress.updateUserProgress(score[0]);
                    currentUser.addProgress(selectedTopic, userprogress);
                    dataStorage.addTopicProgress(currentUser,selectedTopic);
                }else {
                    currentUser.updateProgress(selectedTopic, score[0]);
                    dataStorage.updateTopicProgress(currentUser, selectedTopic);
                }

                currentUser.setTotalScore(currentUser.updateTotalScore());
                dataStorage.updateUserTotalScore(currentUser);

            }
        });

        exitButton.setOnAction(e -> showTopicSessionScreen(primaryStage,selectedTopic,topic) );



    }

    private void showTopicProgress(Stage primaryStage, String selectedTopic,Topic topic){
        BorderPane sessionLayout = new BorderPane();
        VBox sessionForm = new VBox(10);
        sessionForm.getStyleClass().add("vbox");
        if (currentUser.getTopicProgress() != null && !currentUser.getTopicProgress().containsKey(selectedTopic)){
            Label noTopicProgress = new Label("You have no progress for this topic yet. You have to take a quiz first!");
            noTopicProgress.getStyleClass().add("label");
            Button exitButton = new Button("Exit Topic Progress");
            exitButton.getStyleClass().add("button");

            sessionForm.getChildren().addAll(noTopicProgress, exitButton);
            sessionLayout.setCenter(sessionForm);

            Scene sessionScene = new Scene(sessionLayout, 600, 500);
            sessionScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            primaryStage.setTitle(selectedTopic + " Progress");
            primaryStage.setScene(sessionScene);

            exitButton.setOnAction(e -> showTopicSessionScreen(primaryStage,selectedTopic,topic));


        }else {
            Label sessionLabel = new Label("Progress for " + selectedTopic);
            sessionLabel.getStyleClass().add("label");
            Label topicLevel = new Label ("Level :" + currentUser.getProgress(selectedTopic).getLevel());
            topicLevel.getStyleClass().add("label");
            Label topicScore = new Label ("Score :" + currentUser.getProgress(selectedTopic).getScore());
            topicScore.getStyleClass().add("label");
            Label topicAttempts = new Label("Attempts :" + currentUser.getProgress(selectedTopic).getAttempts());
            topicAttempts.getStyleClass().add("label");
            Button exitButton = new Button("Exit Topic Progress");
            exitButton.getStyleClass().add("button");

            sessionForm.getChildren().addAll(sessionLabel, topicLevel, topicScore,topicAttempts, exitButton);
            sessionLayout.setCenter(sessionForm);

            Scene sessionScene = new Scene(sessionLayout, 600, 500);
            sessionScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            primaryStage.setScene(sessionScene);

            exitButton.setOnAction(e -> showTopicSessionScreen(primaryStage,selectedTopic,topic));
        }


    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    public static void main(String[] args) {
        launch();
    }
}