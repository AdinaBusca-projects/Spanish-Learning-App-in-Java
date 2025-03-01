package org.example.aprendovar4;

public class Topic {
    private String name;
    private LearningSession learn;
    private QuizSession quiz;

    public Topic(){

    }
    public Topic(String name, LearningSession learn, QuizSession quiz){
        this.name = name;
        this.learn = learn;
        this.quiz = quiz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LearningSession getLearn() {
        return learn;
    }

    public void setLearn(LearningSession learn) {
        this.learn = learn;
    }

    public QuizSession getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizSession quiz) {
        this.quiz = quiz;
    }

}
