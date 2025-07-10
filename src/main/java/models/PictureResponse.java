    package models;

public class PictureResponse extends Questions{
    private String answer;
    private String image;


    public PictureResponse(int id, String questionText, String image, String  answer) {
        super(id, questionText);
        this.answer = answer;
        this.image = image;
    }
    @Override
    public String getCorrectAnswer() {
        return answer;
    }
    public String getImage() { return image; }
}
