package main.java.ru.video.library.entity;

public class Feedback {
    private Integer id;
    private String message;
    private int assessment;

    public Feedback(Integer id, String message, int assessment) {
        this.id = id;
        this.message = message;
        this.assessment = assessment;
    }

    public Feedback() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return "Feedback{" +
               "id=" + id +
               ", message='" + message + '\'' +
               ", assessment=" + assessment +
               '}';
    }
}
