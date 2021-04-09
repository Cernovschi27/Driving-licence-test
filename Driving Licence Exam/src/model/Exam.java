package model;

import java.util.List;

/**
 * id face referire la id unic pt fiecare quiz nou generat
 * question e lista de intrebari de unde in mod selectiv alege aleatoriu 26 de intrebari
 * correct Answer
 * false Answer
 * acestea doua fac referire la un count
 * numara nr de raspunsuri corecte/gresite
 * */
public class Exam {
    private int id;
    private List<Question> questions;
    private int correctAnswers;
    private int falseAnswers;
    private int current;


    public Exam(int id, List<Question> questions, int correctAnswers, int falseAnswers) {
        this.id = id;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.falseAnswers = falseAnswers;
        this.current = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getFalseAnswers() {
        return falseAnswers;
    }

    public void setFalseAnswers(int falseAnswers) {
        this.falseAnswers = falseAnswers;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
    public Question getNext() {
        if (questions.size()<=current) return null;
        current++;
        return this.questions.get(this.current);
    }
}
