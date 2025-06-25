    package models;

public class FillInBlank extends Questions{
    private String answer;


    public FillInBlank(int id, String questionText, String answer) {
        super(id, questionText);
        this.answer = answer;
    }

    public String getCorrectAnswer() { return answer; }
}
