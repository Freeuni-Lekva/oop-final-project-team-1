package models;

public class QuestionResponse extends Questions{
    private String answer;


    public QuestionResponse(int id, String questionText, String answer) {
        super(id, questionText);
        this.answer = answer;
    }
    @Override
    public String getCorrectAnswer() {

        return answer;
    }
}
