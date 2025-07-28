package lab9.lab9;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;

/**
 * ClickingGameFX runs a JavaFX game that challenges the user to click balls before they hit the edge of the screen
 * @author: Kevin Mah
 * As a bonus I had 3 balls in it instead of 1, and there is an animation of the balls turning red when clicked on
 * */
public class ClickingGameFX extends Application {
    //creation of necessary variables for later use
    int width = 400;
    int height = 400;
    int ballRadius = 25;
    Pane canvas = new Pane();
    BorderPane root = new BorderPane(canvas);
    Rectangle background = new Rectangle(0,0, width, height);
    HBox menu = new HBox(100);
    Button pause = new Button("Pause");
    boolean isPaused = false;
    Button reset = new Button("Reset");
    Ellipse ball1 = new Ellipse();
    Ellipse ball2 = new Ellipse();
    Ellipse ball3 = new Ellipse();
    Color b1;
    Color b2;
    Color b3;
    Ellipse [] ballArray = {ball1, ball2, ball3};
    Color[] colorArray = {b1,b2,b3};
    int hits = 0;
    int misses = 0;
    Text hitsAndMisses = new Text(20,20, ("Hits: "+ hits + " Misses: " + misses));
    Text gameOver = new Text (width/3, height/2, "Game Over!");
    Font overFont = new Font(20);
    double xVelocity = 2;
    private BallAnimation animation;
    int startTimer = 120;

    /**
     *resetCircles is a helper method that is used to reset the circle locations, their colors, and the color array
     * @param e,c: an ellipse, and a color
     * @return: nothing
     * */
    public void resetCircles (Ellipse e, Color c){
        c = Color.GREY;
        e.setFill(c);
        e.setCenterX(ballRadius);
        e.setCenterY(Math.random()*(width-ballRadius)+ballRadius);
    }

    /**
     * cleanUp is a helper method that will essentially reset all values to their originals and reset the game
     * @param: none
     * @return: nothing
     * */
    public void cleanup(){
        xVelocity = 2;
        startTimer = 120;
        hits = 0;
        misses = 0;
        gameOver.setVisible(false);
        hitsAndMisses.setText("Hits: "+ hits + " Misses: " + misses);
        for (int i = 0; i < ballArray.length; i++){
            resetCircles(ballArray[i], colorArray[i]);
            ballArray[i].setVisible(false);
        }
    }
    @Override
    public void start (Stage primaryStage){
        cleanup();
        background.setFill(Color.BLACK);


        restartPause r = new restartPause();
        reset.setOnAction(r);
        pause.setOnAction(r);

        menu.getChildren().addAll(pause,reset);
        menu.setAlignment(Pos.BASELINE_RIGHT);
        menu.setPadding(new Insets(10));
        menu.setSpacing(10);
        root.setBottom(menu);

        canvas.getChildren().add(background);

        for (int i = 0; i < ballArray.length; i++){
            colorArray[i] = Color.GREY;
            ballArray[i] = new Ellipse(ballRadius, Math.random()*(width-ballRadius)+ballRadius, ballRadius,ballRadius);
            ballArray[i].setFill(colorArray[i]);
            ballArray[i].setVisible(false);
            canvas.getChildren().add(ballArray[i]);
            ballArray[i].setOnMouseClicked(new CircleClickEventHandler());
        }

        hitsAndMisses.setFill(Color.WHITE);
        canvas.getChildren().add(hitsAndMisses);
        animation = new BallAnimation();
        animation.start();
        gameOver.setFill(Color.WHITE);
        gameOver.setTextAlignment(TextAlignment.CENTER);
        gameOver.setFont(overFont);
        gameOver.setVisible(false);
        canvas.getChildren().add(gameOver);
        Scene scene = new Scene (root);
        primaryStage.setTitle("Clicking Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * handle is a function that will allow event handlers to be assigned to buttons
     * @param e: an ActionEvent
     * @return: nothing
     * */
    public void handle (ActionEvent e){}

    /**
     * restartPause is a class with a method that can restart or pause the game
     * @author: Kevin Mah
     *  */
    private class restartPause implements EventHandler<ActionEvent>{
        /**
         * handle is the method that will restart or pause the game
         * @param e: an ActionEvent
         * @return: nothing
         * */
        @Override
        public void handle (ActionEvent e){
            if (e.getSource() == reset){
                animation.stop();
                cleanup();
                animation.start();
            }
            if (e.getSource() == pause){
                if (!isPaused){
                    animation.stop();
                    isPaused = true;
                } else {
                    animation.start();
                    isPaused = false;
                }
            }
        }
    }
    /**
     * CircleClickEventHandler is the class that handles mouse click interactions
     * @author: Kevin Mah
     * */
    private class CircleClickEventHandler implements EventHandler<MouseEvent>{
        /**
         * handle is the method that will alter the balls if hit and increase the hit counter if successful
         * @param e: an ActionEvent
         * @return: nothing
         * */
        @Override
        public void handle(MouseEvent e){
            for (int i = 0; i < ballArray.length; i++){
                if (ballArray[i].contains(e.getX(),e.getY()) && ballArray[i].getFill().equals(Color.GREY)){
                    colorArray[i] = Color.RED;
                    ballArray[i].setFill(colorArray[i]);
                    hits++;
                    hitsAndMisses.setText("Hits: "+ hits + " Misses: " + misses);
                    xVelocity += 0.25;
                }
            }
        }
    }
    /**
     * BallAnimation handles the animations of the balls on screen
     * @author: Kevin Mah
     * */
    private class BallAnimation extends AnimationTimer{
        /**
         * handle is the method that will animate the balls, reset them once they leave the screen, increase the miss counters, and cause a game over
         * @param now: the given timestamp
         * @return: nothing
         * */
        @Override
        public void handle (long now){

            if (startTimer > 0){
                startTimer-=1;
            } else {
                for (int i = 0; i < ballArray.length; i++){
                    ballArray[i].setVisible(true);
                    double x = ballArray[i].getCenterX();
                    double y = ballArray[i].getCenterY();
                    if (x > (width+ballRadius)){
                        if (ballArray[i].getFill().equals(Color.GREY)){
                            misses++;
                            hitsAndMisses.setText("Hits: "+ hits + " Misses: " + misses);
                        }
                        resetCircles(ballArray[i], colorArray[i]);
                        ballArray[i].setVisible(false);
                        startTimer = (int)(Math.random()*120);

                    } else {
                        x += xVelocity;
                        ballArray[i].setCenterX(x);
                        ballArray[i].setCenterY(y);
                    }
                }
            }
            if (misses >= 5){
                animation.stop();
                gameOver.setVisible(true);
            }
        }
    }
}
