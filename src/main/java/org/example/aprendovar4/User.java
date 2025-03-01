package org.example.aprendovar4;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class User implements Comparable<User>{
    private String name;
    private String password;
    private int totalScore;
    @JsonProperty("topicProgress")
    private HashMap<String, UserProgress> topicProgress;

    public User(){

    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.totalScore = 0;
        this.topicProgress = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }


    //validate password security
    public void setPassword(String password) throws WeakPasswordException{
        if(!isValidPassword(password)){
            throw new WeakPasswordException("Password is too weak! Ensure your password has at least 8 characters, one number and one special character.");
        }
        this.password = password;//sets it only if strong enough
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*].*");
    }

    public HashMap<String, UserProgress> getTopicProgress() {
        return topicProgress;
    }

    public void setTopicProgress(HashMap<String, UserProgress> topicProgress) {
        this.topicProgress = topicProgress;
    }



    //get user's progress for a topic
    public UserProgress getProgress(String topicName){
        return topicProgress.get(topicName);
    }

    //add user topic progress
    public void addProgress(String topicName, UserProgress progress){
        topicProgress.put(topicName,progress);
    }

    //update progress for a topic
    public void updateProgress(String topicName, int quizScore){
        UserProgress progress = topicProgress.get(topicName);
        if (progress != null) {
            if (progress.isFirstAttempt()) {
                System.out.println("This is your first attempt to solve the quiz");
            }
        }else{
            progress = new UserProgress(1,0,0);
            topicProgress.put(topicName, progress);
            System.out.println("This is your first attempt to solve the quiz.");
        }
        progress.updateUserProgress(quizScore);
        System.out.println("Quiz completed.");
    }




    //get user's level for a specific topic
    public int getLevelForTopic(String topicName){
        if(topicProgress.containsKey(topicName)){
            return topicProgress.get(topicName).getLevel();
        }else{
            return 1; // the user has not started this topic yet
        }
    }

    public int updateTotalScore(){
        int totalScore = 0;
        for (String topic : topicProgress.keySet()) {

            totalScore += topicProgress.get(topic).getScore();
        }
        return totalScore;

    }

    public void updateTotalScoreAndSet(){
        int updateTotalScore = updateTotalScore();
        setTotalScore(updateTotalScore);
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(this.getTotalScore(),o.getTotalScore());
    }
}