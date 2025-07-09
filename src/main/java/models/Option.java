package models;

public class Option {
    private int id;
    private String optionText;
    private boolean isCorrect;

    public Option(int id, String optionText, boolean isCorrect) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public int getId() { return id; }
    public String getOptionText() { return optionText; }
    public boolean isCorrect() { return isCorrect; }

}
