package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Game extends Application {

    Image clouds = new Image("clouds.png");
    Image bird = new Image("bird.png");
    Image game_over = new Image("game_over.png");
    Image column = new Image("column.png");

    int x_clouds1 = 0;
    int x_clouds2 = 1366;
    int y_clouds = 0;

    int column1_x = 800;
    int column2_x = 1144;
    int column3_x = 1488;
    int column1_y;
    int column2_y;
    int column3_y;

    int x_bird = 300;
    int y_bird = 50;

    float gravity = 0;
    boolean inGame = true;

    int score = 0;

    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        column1_y = (int) (Math.random() * 480 - 240);
        column2_y = (int) (Math.random() * 480 - 240);
        column3_y = (int) (Math.random() * 480 - 240);

        Group root = new Group();
        scene = new Scene(root,800, 600);

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(canvas);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Angry Bird");
        primaryStage.getIcons().add(bird);
        primaryStage.show();
        primaryStage.setResizable(false);

        AnimationTimer animationTimer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                if (inGame) {
                    gc.clearRect(0,0, scene.getWidth(), scene.getHeight());
                    check();
                    gravity += 0.3;
                    gc.drawImage(clouds, --x_clouds1, y_clouds);
                    gc.drawImage(clouds, --x_clouds2, y_clouds);
                    y_bird += gravity;
                    gc.drawImage(bird, x_bird, y_bird, 70, 70);
                    gc.drawImage(column, --column1_x, column1_y, 144, 240);
                    gc.drawImage(column, --column2_x, column2_y, 144, 240);
                    gc.drawImage(column, --column3_x, column3_y, 144, 240);
                    gc.drawImage(column, --column1_x, column1_y + 360, 144, 240);
                    gc.drawImage(column, --column2_x, column2_y + 360, 144, 240);
                    gc.drawImage(column, --column3_x, column3_y + 360, 144, 240);
                    gc.drawImage(column, column1_x, column1_y - 240, 144, 240);
                    gc.drawImage(column, column2_x, column2_y - 240, 144, 240);
                    gc.drawImage(column, column3_x, column3_y - 240, 144, 240);
                    gc.drawImage(column, column1_x, column1_y + 360 + 240, 144, 240);
                    gc.drawImage(column, column2_x, column2_y + 360 + 240, 144, 240);
                    gc.drawImage(column, column3_x, column3_y + 360 + 240, 144, 240);

                    Font font = Font.font("Arial", FontWeight.BOLD, 20);

                    gc.setFont(font);
                    gc.setFill(Color.GOLD);
                    gc.fillText("Your score: " + score, 20, 25);

                    if (column1_x == x_bird - 130 || column2_x == x_bird - 130 || column3_x == x_bird - 130) {
                        ++score;
                    }

                    if (column1_x <= -232) {
                        column1_x = 800;
                        column1_y = (int) (Math.random() * 480 - 240);
                    }
                    if (column2_x <= -232) {
                        column2_x = 800;
                        column2_y = (int) (Math.random() * 480 - 240);
                    }
                    if (column3_x <= -232) {
                        column3_x = 800;
                        column3_y = (int) (Math.random() * 480 - 240);
                    }

                    if (x_clouds1 <= -1366) {
                        x_clouds1 = 0;
                    }
                    if (x_clouds2 <= 0) {
                        x_clouds2 = 1366;
                    }
                } else {
                    gc.drawImage(game_over, scene.getWidth()/2 - 100, scene.getHeight()/2 - 100, 200, 200);
                    gc.fillText("Press R to restart", scene.getWidth()/2 - 85, scene.getHeight() - 100);
                }
            }
        };

        animationTimer.start();

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                gravity = 0;
                y_bird -= 40;
            }
            if (keyEvent.getCode() == KeyCode.R) {
                x_clouds1 = 0;
                x_clouds2 = 1366;

                column1_y = (int) (Math.random() * 480 - 240);
                column2_y = (int) (Math.random() * 480 - 240);
                column3_y = (int) (Math.random() * 480 - 240);

                score = 0;

                y_bird = 50;
                x_bird = 300;

                gravity = 0;
                column1_x = 800;
                column2_x = 1144;
                column3_x = 1488;
                inGame = true;
            }
        });
   }

    public void check() {
        if (y_bird >= 555 || y_bird <= -35) {
            inGame = false;
        }
        for (int i = 5; i < 116; i++) {
            if (((y_bird <= column1_y + 210 || y_bird >= column1_y + 325) && x_bird + 35 == column1_x + i) ||
                    ((y_bird <= column2_y + 210 || y_bird >= column2_y + 325) && x_bird + 35 == column2_x + i) ||
                    ((y_bird <= column3_y + 210 || y_bird >= column3_y + 325) && x_bird + 35== column3_x + i)) {
                inGame = false;
                break;
            }
        }
    }
}