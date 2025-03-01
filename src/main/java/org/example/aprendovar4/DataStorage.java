package org.example.aprendovar4;

import java.sql.*;
import java.util.HashMap;

public class DataStorage {
    private static final String DB_URL = "jdbc:sqlite:user_data.db";

    public DataStorage() {
        // Initialize the database (create table if it doesn't exist)
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                "name TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "totalScore INTEGER NOT NULL DEFAULT 0)";

        String createOtherTableSQL = "CREATE TABLE IF NOT EXISTS TopicProgress (" +
                "userName TEXT NOT NULL, " +
                "topicName TEXT NOT NULL, " +
                "level INTEGER NOT NULL, " +
                "score INTEGER NOT NULL, " +
                "attempts INTEGER NOT NULL, " +
                "firstAttempt BOOLEAN NOT NULL, " +
                "FOREIGN KEY (userName) REFERENCES Users (name)" +
                "UNIQUE (userName, topicName)" +
                ")";


        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            stmt.execute(createOtherTableSQL);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }


    // Check if a user exists in the database
    public boolean userExists(String username) {
        String querySQL = "SELECT COUNT(*) AS count FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("count") > 0;
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    // Get a user by username from the database
    public User getUser(String username) {
        String selectUserSQL = "SELECT * FROM Users WHERE name = ?";
        String selectTopicsSQL = "SELECT * FROM TopicProgress WHERE userName = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement userStmt = conn.prepareStatement(selectUserSQL);
             PreparedStatement topicStmt = conn.prepareStatement(selectTopicsSQL)) {

            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                String name = userRs.getString("name");
                String password = userRs.getString("password");
                int totalScore = userRs.getInt("totalScore");
                user = new User(name, password);
                user.setTotalScore(totalScore);

                topicStmt.setString(1, username);
                ResultSet topicRs = topicStmt.executeQuery();

                while (topicRs.next()) {
                    String topicName = topicRs.getString("topicName");
                    int level = topicRs.getInt("level");
                    int score = topicRs.getInt("score");
                    int attempts = topicRs.getInt("attempts");
                    boolean firstAttempt = topicRs.getBoolean("firstAttempt");

                    UserProgress progress = new UserProgress(level, score, attempts);
                    progress.setFirstAttempt(firstAttempt);
                    user.addProgress(topicName, progress);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }



    // Create a new user in the database
    public void createUser(String username, String password) {
        if (userExists(username)) {
            System.err.println("User already exists.");
            return;
        }
        User newUser = new User(username, password);
        newUser.setPassword(password);// see later if it works properly
        String insertUserSQL = "INSERT INTO Users (name, password, totalScore) VALUES  (?, ? , 0)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("User " + username + " created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    public void addTopicProgress(User username, String topicName) {
        // Check if progress for this topic already exists
        String checkProgressSQL = "SELECT COUNT(*) FROM TopicProgress WHERE userName = ? AND topicName = ?";
        String insertProgressSQL = "INSERT INTO TopicProgress (userName, topicName, level, score, attempts, firstAttempt) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Check if progress already exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkProgressSQL)) {
                checkStmt.setString(1, username.getName());
                checkStmt.setString(2, topicName);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Progress for topic '" + topicName + "' already exists for user " + username + ".");
                    return;
                }
            }

            // Insert new topic progress
            try (PreparedStatement insertStmt = conn.prepareStatement(insertProgressSQL)) {
                insertStmt.setString(1, username.getName());
                insertStmt.setString(2, topicName);
                insertStmt.setInt(3, username.getProgress(topicName).getLevel());
                insertStmt.setInt(4, username.getProgress(topicName).getScore());
                insertStmt.setInt(5, username.getProgress(topicName).getAttempts());
                insertStmt.setBoolean(6, username.getProgress(topicName).isFirstAttempt());
                insertStmt.executeUpdate();
                System.out.println("Progress for topic '" + topicName + "' added for user " + username + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error adding topic progress: " + e.getMessage());
        }
    }


    public void updateUserTotalScore(User username){
        String updateUserSQL = "UPDATE Users set totalScore = ? where name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateUserSQL)) {
            pstmt.setInt(1, username.getTotalScore());
            pstmt.setString(2, username.getName());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User " + username + " updated successfully.");
            } else {
                System.out.println("No user found with the name: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public void updateTopicProgress(User username, String topic) {
        String updateTopicSQL = "UPDATE TopicProgress SET score = ?, attempts = ?, firstAttempt = ?, level = ? WHERE userName = ? AND topicName = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateTopicSQL)) {
            pstmt.setInt(1, username.getProgress(topic).getScore());
            pstmt.setInt(2, username.getProgress(topic).getAttempts());
            pstmt.setBoolean(3, username.getProgress(topic).isFirstAttempt());
            pstmt.setInt(4, username.getProgress(topic).getLevel());
            pstmt.setString(5, username.getName());
            pstmt.setString(6, topic);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Topic progress updated successfully for " + topic);
            } else {
                System.out.println("No topic progress found for " + topic + " and user " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error updating topic progress: " + e.getMessage());
        }
    }



}
