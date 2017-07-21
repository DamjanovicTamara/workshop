package com.teamtreehouse.pomodoro.controllers;

import com.teamtreehouse.pomodoro.model.Attempt;
import com.teamtreehouse.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Home {
    private final AudioClip mApplause;
    @FXML
    private VBox container;
    @FXML
    private Label title;
    @FXML
    private TextArea message;

    private Attempt mCurrentAttempt;

    private StringProperty mTimerText;

    private Timeline mTimeline;

    public String getTimerText() {
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String mTimerText) {
        this.mTimerText.set(mTimerText);
    }


    public void setmTimerText(int remainingSeconds){
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d",minutes,seconds));

    }

    public Home(){
        mTimerText=new SimpleStringProperty();
        setmTimerText(0);
        mApplause= new AudioClip(getClass().getResource("/sounds/applause.mp3").toExternalForm());
    }



    private void prepareAttempt(AttemptKind kind){

        reset();
        mCurrentAttempt=new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setmTimerText(mCurrentAttempt.getmRemainingSeconds());
        //TODO This is creating multiple Timelines we need to fix this
        mTimeline= new Timeline();
        mTimeline.setCycleCount(kind.getTotalSeconds());//for 1 sec pre page
        //Sets up what happeens on each page- key Frame - property that return a list on timeline
        mTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),e->{
            mCurrentAttempt.tick();
            setmTimerText(mCurrentAttempt.getmRemainingSeconds());   //in labda is what is going to run on every key frame
        }));
        mTimeline.setOnFinished(e->{
            saveCurrentAttempt();
            mApplause.play();
            prepareAttempt(mCurrentAttempt.getmKind()==AttemptKind.FOCUS ? AttemptKind.BREAK : AttemptKind.FOCUS);
        });

    }

    private void saveCurrentAttempt() {
        mCurrentAttempt.setmMessage(message.getText());
        mCurrentAttempt.save();
    }

    private void reset() {
        clearAttemptStyles();
        if(mTimeline != null && mTimeline.getStatus()== Animation.Status.RUNNING){
            mTimeline.stop();
        }
    }

    private void addAttemptStyle(AttemptKind kind){
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

    private void clearAttemptStyles(){
        container.getStyleClass().remove("playing");
        for(AttemptKind kind:AttemptKind.values()){
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }



    public void playTimer(){
        container.getStyleClass().add("playing");
        mTimeline.play();
    }
    public void pauseTimer(){
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if(mCurrentAttempt == null) {
            handleRestart(actionEvent);
        }else{
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
