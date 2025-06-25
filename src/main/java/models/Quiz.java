package models;

public class Quiz implements Comparable<Quiz>{
    private String title;
    private String quizID;
    private int timesTaken;

    public Quiz(String title, String quizID, int timesTaken)  {
        this.title = title;
        this.quizID = quizID;
        this.timesTaken = timesTaken;
    }

    public String getTitle() {
        return title;
    }
    public String getQuizID() {
        return quizID;
    }

    public int getTimesTaken() {
        return timesTaken;
    }
    @Override
    public int compareTo(Quiz o) {
        return Integer.compare(o.timesTaken, this.timesTaken);
    }
}
