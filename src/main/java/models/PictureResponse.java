    package models;

public class PictureResponse extends Questions{
    private String answer;
    private String image;


    public PictureResponse(int id, String questionText, String answer, String image) {
        super(id, questionText);
        this.answer = answer;
        this.image = image;
    }

    public String getCorrectAnswer() { return answer; }
    public String getImage() { return image; }
}
