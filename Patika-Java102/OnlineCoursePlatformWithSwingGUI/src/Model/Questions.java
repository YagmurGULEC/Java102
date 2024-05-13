package Model;

public class Questions {
    private int id;
    private String question;
    private String answer;
    private int score;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return question;
    }

}
