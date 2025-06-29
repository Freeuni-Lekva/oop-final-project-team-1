package models;

public abstract class Questions  {
    protected int id;
    protected String question;

    public Questions(int id, String question) {
        this.id = id;
        this.question = question;
    }

    public int getId() {return id;};
    public String getQuestion() {return question;};
}
