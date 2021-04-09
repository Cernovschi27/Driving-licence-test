package model;

import java.util.List;

public class Question {
    /**
     * id- id unic pt quiz
     * question- string redat cu o intrebare curenta
     * answ- posibilitatile de raspuns
     * correctA face referire la raspunsul corect dintre cele trei posibilitati de raspunsuri date
     * */
    private int id;
    private String question;
    private List<String> answers;
    private String correctA;

    public Question(int id, String question, List<String> answers, String correctA) {

        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctA = correctA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectA() {
        return correctA;
    }

    public void setCorrectA(String correctA) {
        this.correctA = correctA;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", correctA='" + correctA + '\'' +
                '}';
    }
}

