package models;

import java.sql.Timestamp;

public class QuizAttempt {
    private  Quiz quiz;
    private int score;


    public QuizAttempt(Quiz quiz, int score, Timestamp attemptTime) {
        this.quiz = quiz;
        this.score = score;

    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getScore() {
        return score;
    }


}

