package engine.entity.JSONEntitiy;

public class JSONContentQuestion {
    private int id;
    private String title;
    private String text;
    private String[] options;

    public JSONContentQuestion(int id, String title, String text, String[] options) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }
}
