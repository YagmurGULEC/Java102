package Model;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private String name;
    private ArrayList<Questions> questions;
    public int getId() {
        return id;
    }
    void setId(int id) {
        this.id = id;
    }
    private String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    public ArrayList<Questions> getQuestions() {
        return questions;
    }
    public void addQuestions(Questions  question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        else
            questions.add(question);
    }

    public void removeQuestions(Questions question) {
        questions.remove(question);
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Quiz) {
            return ((Quiz) obj).getId() == this.id;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.id;
    }

}
