package models;

public class Quiz implements Comparable<Quiz>{
    private String title;
    private String quizID;
    private int timesTaken;
    private String creator;

    public Quiz(String title, String quizID, int timesTaken, String creator)  {
        this.title = title;
        this.quizID = quizID;
        this.timesTaken = timesTaken;
        this.creator = creator;
    }
    public String getCreator() { return creator; }
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
