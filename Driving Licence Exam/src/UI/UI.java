package UI;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Question;


public class UI extends Application implements EventHandler<ActionEvent> {

    private Controller Controller;
    private Question question;
    private Label timer;
    private Integer sec;
    private boolean status;
    private Button currentquestion;
    private Button answers;
    private Button score;
    private Button next;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button correctb;
    public UI() {

    }
    /**
     * functie de start a quizului
     * generat random cu id unic
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Controller = new Controller();
        sec = 1800;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Exam");
        stage.setScene(new Scene(root, 400, 350));
        stage.show();
        BorderPane format = new BorderPane();
        Scene scene = new Scene(format, 800, 600);

        startPane(stage, scene);

        question = Controller.getNextQuestion();
        timer = new Label();

        showbutton();

        buttonsformat();

        correctb = Controller.setCorrectButton(question, b1, b2, b3);

        b1.setOnAction(this);
        b2.setOnAction(this);
        b3.setOnAction(this);

        next.setOnAction(e -> {
            if (status) {
                Controller.setCorrectQuestion();
            } else {
                Controller.setFalseQuestion();
            }
            question = Controller.getNextQuestion();
            checkQuizStatus(stage);

        });

        GridPane gridPane = new GridPane();
        setPadding(stage, format, gridPane);

    }
    /**
     * functie de alocare a timpului
     * timpul porneste de la 30 min si se scurge in sen sinvers cu ajutorul functueu mangeTime
     * */
    private void manageTime(Stage primaryStage) {
        Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            sec--;
            int min = sec / 60;
            int lefttime = sec % 60;
            timer.setText("Time: " + min + ":" + lefttime);
            if (sec <= 0) {
                primaryStage.setScene(endlabel(primaryStage));
                primaryStage.show();
            }
        });
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        time.stop();
        time.play();
    }
    public void menu(String[] args) {
        launch(args);
    }

    /**
     * functie pentru repercusiunea alegerii unui buton
     * fiecare buton face altcv si are alta cauza finala
     * */
    @Override
    public void handle(ActionEvent actionEvent) {
        status = Controller.checkifcorrect(actionEvent, correctb);
    }
    private Scene endlabel(Stage primaryStage) {
        Label l2 = new Label("Sfarsit");
        Label l1 = new Label();
        l2.setMinSize(80, 80);
        l2.setStyle("-fx-font-size:40");
        l1.setMinSize(40, 80);
        l1.setStyle("-fx-font-size:40");
        Button restart = new Button("restart");
        restart.setMinSize(70, 70);
        restart.setStyle("-fx-font-size:50");
        restart.setOnAction(e -> {
            try {
                start(primaryStage);
                new Thread(() -> manageTime(primaryStage)).start();

                manageTime(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        if (Controller.getFalseAnswersNow() == 5 || (sec <= 0 && Controller.getCorrectAnswersNow() < 22))
            l1.setText("Picat");
        else
            l1.setText("Examen Trecut");
        Button finish = new Button("Exit");
        finish.setMinSize(30, 30);
        finish.setStyle("-fx-font-size:30");
        finish.setOnAction(e -> System.exit(0));
        VBox layout2 = new VBox(30);
        score.setMinSize(70, 70);
        score.setStyle("-fx-font-size:25");
        layout2.getChildren().addAll(l2, l1, restart, finish, score);
        layout2.setAlignment(Pos.CENTER);
        return new Scene(layout2, 800, 600);
    }
    private void showbutton() {
        currentquestion = new Button(/*question.getId() + ". " +*/ question.getQuestion());
        answers = new Button("1. " + question.getAnswers().get(0) +
                "\n2. " + question.getAnswers().get(1) +
                "\n3. " + question.getAnswers().get(2));
        b1 = new Button("1");
        b2 = new Button("2");
        b3 = new Button("3");
        next = new Button("Next");
        score = new Button("Correct: " + Controller.getCorrectAnswersNow() + "\nWrong: " + Controller.getFalseAnswersNow());
        correctb = new Button();
    }

    /**
     * componente estetice de grafica butoneleor
     * */
    private void buttonsformat() {
        answers.setStyle("-fx-font-size:20");
        b1.setMinSize(75, 75);
        b1.setStyle("-fx-font-size:30");
        b2.setMinSize(75, 75);
        b2.setStyle("-fx-font-size:30");
        b3.setMinSize(75, 75);
        b3.setStyle("-fx-font-size:30");
        currentquestion.setMinSize(60,60 );
        currentquestion.setStyle("-fx-font-size:20");
        next.setMinSize(75, 75);
        next.setStyle("-fx-font-size:40");
        timer.setMinSize(60, 60);
        timer.setStyle("-fx-font-size:30");
    }

    /**
     * grafica meniului principal sau de start
     * */
    private void startPane(Stage primaryStage, Scene scene) {
        Label label1 = new Label("Exam");
        label1.setMinSize(55, 55);
        label1.setStyle("-fx-font-size:25");
        Button start = new Button("Start");
        start.setMinSize(65, 65);
        start.setStyle("-fx-font-size:45");
        start.setOnAction(e -> {
            primaryStage.setScene(scene);
            new Thread(() -> manageTime(primaryStage)).start();
        });
        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(label1, start);
        layout1.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout1, 800, 600);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    private void setPadding(Stage primaryStage, BorderPane layout, GridPane gridPane) {
        HBox hbox = new HBox(50);
        hbox.setPadding(new Insets(50, 50, 50, 50));
        hbox.getChildren().addAll(b1, b2, b3, next, score);
        hbox.setAlignment(Pos.CENTER);

        HBox hbox1 = new HBox(20);
        hbox1.setPadding(new Insets(10, 10, 10, 10));
        hbox1.getChildren().addAll(timer);
        hbox1.setAlignment(Pos.CENTER);

        layout.setTop(hbox1);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(currentquestion, 1, 1);
        gridPane.add(answers, 1, 2);

        layout.setCenter(gridPane);
        layout.setBottom(hbox);
        primaryStage.setTitle("Chestionar");
    }
    /**
     *functie de check legata de statutul quizului
     * */
    private void checkQuizStatus(Stage primaryStage) {
        if (question == null || Controller.getFalseAnswersNow() == 5) {
            primaryStage.setScene(endlabel(primaryStage));
            primaryStage.show();
        }
        score.setText("Correct: " + Controller.getCorrectAnswersNow() + "\nWrong: " + Controller.getFalseAnswersNow());
        currentquestion.setText(/*question.getId() + ". "+*/  question.getQuestion());
        answers.setText("1. " + question.getAnswers().get(0) +
                "\n2. " + question.getAnswers().get(1) +
                "\n3. " + question.getAnswers().get(2));
        correctb = Controller.setCorrectButton(question, b1, b2, b3);
        status = false;
    }

}