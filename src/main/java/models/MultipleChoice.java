    package models;

    import java.util.ArrayList;
    import java.util.List;

    public class MultipleChoice extends Questions{
    private final ArrayList<Option> options;


    public MultipleChoice(int id, String questionText,ArrayList<Option> options) {
        super(id, questionText);
        this.options = options;
    }

    public ArrayList<Option> getOptions() { return options; }
}
