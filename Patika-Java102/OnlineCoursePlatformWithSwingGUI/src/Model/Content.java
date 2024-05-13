package Model;

public class Content {

    private int id;
    private String title;
    private String description;
    private String link;
    private Quiz quiz;

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLink() {
        return link;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    @Override
    public String toString() {
        return title;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Content) {
            return ((Content) obj).getId() == this.id;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.id;
    }
}