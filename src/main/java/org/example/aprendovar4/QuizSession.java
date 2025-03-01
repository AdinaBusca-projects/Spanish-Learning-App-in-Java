package org.example.aprendovar4;


import java.util.List;
import java.util.Scanner;

public class QuizSession implements Testable{
    private int score;
    private boolean completed;
    private String name;
    private User user;
    private List<String> questions;
    private List<String> answers;

    public QuizSession(){

    }

    public QuizSession(int score, boolean completed, String name, User user){
        this.score = score;
        this.completed = completed;
        this.name = name;
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public void startQuiz() {
        Scanner scanner = new Scanner((System.in));
        score = 0;

        //iterate through each question
        for(int i = 0; i < questions.size(); i++){
            //display question
            System.out.println("Question " + (i+1) + ": " + questions.get(i));

            //get user input for the answer
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();

            //check if good answer and update the score if correct
            if (userAnswer.equalsIgnoreCase(answers.get(i))){
                System.out.println("Correct!");
                score += 10;
            }else{
                System.out.println("Oh,no! This is not the right answer. You should have typed: " + answers.get(i));
            }
        }
        completeQuiz(score);

    }

    public void completeQuiz(int quizScore){
        this.score = quizScore;
        this.completed = true;
        if (user != null){
            //check for valid User object
            user.updateProgress(name,quizScore);
            System.out.println("Quiz completed for topic: " + name + " with score "+ quizScore);
        }
    }



}
