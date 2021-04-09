package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.Exam;
import model.Question;
import repository.Repository;
import utility.CSVReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    private Repository repository;
    private Exam exam;
    private List<Question> questions;


    /**
    * constructor pentru controllerul de la quiz
     */
    public Controller() {
        //introducerea datelor nu ar trebui sa fie aici
        List<List<String>> buff = CSVReader.readFile("src/Questions");
        int id=1;
        List<Question> QuestionList=new ArrayList<>();
        for(List<String> line: buff) {
            ArrayList<String> answers = new ArrayList<>();
            //line.get(0)=intrebare 1,2,3=raspunsuri 4=raspuns corect
            answers.add(line.get(1));
            answers.add(line.get(2));
            answers.add(line.get(3));
            Question q = new Question(id,line.get(0), answers, line.get(4));
            QuestionList.add(q);
            id++;
        }
        this.repository = new Repository(QuestionList);
        questions = this.repository.getQuestions();
        beginExam();
    }
    /**
     * functie pentru pornirea examenului care selecteaza random intrebarile pt noul quiz
     * se aleg 26 de intrebari aletorii cu ajutorul for ului
     * */
    public void beginExam() {
        Random rand = new Random();
        List<Question> questionscopy = this.repository.getQuestions();
        List<Question> quizQuestions = new ArrayList<>();

        for(int count=1;count<=26;count++){
            int questionnr = rand.nextInt(questionscopy.size() - 1);
            Question question = questionscopy.get(questionnr);
            question.setId(count);
            quizQuestions.add(question);
            questionscopy.remove(question);
        }
        this.exam = new Exam(0, quizQuestions, 0, 0);
    }

    /**
     * getter si setter pentru a returna
     * urmatoarea intrebare
     * a setat care este raspunsul corect
     * setter pt raspuns gresit/corect
     * getter pentru a afla daca raspunsul este gresit/corect
     * */
    public Question getNextQuestion() {
        return exam.getNext();
    }
    public void setCorrectQuestion() {
        int currentnr=exam.getCorrectAnswers();
        exam.setCorrectAnswers(currentnr + 1);
    }
    public void setFalseQuestion() {

        int currentnr=exam.getFalseAnswers();
        exam.setFalseAnswers(currentnr + 1);
    }
    public int getCorrectAnswersNow() {
        return exam.getCorrectAnswers();
    }
    public int getFalseAnswersNow() {
        return exam.getFalseAnswers();
    }
    public Exam getQuiz() {
        return exam;
    }
    /**
     * functie pentru a putea cumultudinea de butoane apasate corect/gresit
     * */
    public Button setCorrectButton(Question q, Button button1, Button button2, Button button3) {
        if (q.getCorrectA().equals(q.getAnswers().get(0)))
            return button1;
        else if (q.getCorrectA().equals(q.getAnswers().get(1)))
            return button2;
        else
            return button3;
       // @return butonul care face referire la raspunsul corect
    }
    public boolean checkifcorrect(ActionEvent event, Button correctone) {
        return event.getSource() == correctone;

    }
}
