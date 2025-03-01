package org.example.aprendovar4;

public class UserProgress {
    private int level;
    private int score;
    private int attempts;
    private boolean firstAttempt;

    public UserProgress(){

    }

    public UserProgress(int level, int score, int attempts){
        this.level = level;
        this.score = score;
        this.attempts = attempts;
        this.firstAttempt = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isFirstAttempt() {
        return firstAttempt;
    }

    public void setFirstAttempt(boolean firstAttempt) {
        this.firstAttempt = firstAttempt;
    }

    public void updateLevel(){
        if(score >= 90){
            level = 5; //max at the moment
        }else if (score >= 70){
            level = 4;
        }else if (score >= 50){
            level = 3;
        }else if (score >= 30){
            level = 2;
        }else{
            level = 1;//beginner level, default level
        }
    }

    public void updateUserProgress(int quizScore){
        this.score = quizScore;
        updateLevel();
        this.attempts++;
        this.firstAttempt = false;


    }


}
