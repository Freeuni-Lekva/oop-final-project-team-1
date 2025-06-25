    package models;

    import java.util.ArrayList;
    import java.util.List;

    public class MultipleChoice extends Questions{
    private final ArrayList<Option> optionsList;

    public MultipleChoice(int id, String questionText,ArrayList<Option> options) {
        super(id, questionText);
        this.optionsList = options;
    }

    public ArrayList<Option> getOptions() { return optionsList; }
}
