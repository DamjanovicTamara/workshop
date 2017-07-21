package com.teamtreehouse.pomodoro.model;

public class Attempt {
    private String mMessage;
    private int mRemainingSeconds;
    private AttemptKind mKind;

    public Attempt (AttemptKind kind,String message) {
        mKind=kind;
        mMessage=message;
        mRemainingSeconds=kind.getTotalSeconds();
    }

    public String getmMessage() {
        return mMessage;
    }

    public int getmRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getmKind() {
        return mKind;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void tick() {
        mRemainingSeconds--;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "mMessage='" + mMessage + '\'' +
                ", mRemainingSeconds=" + mRemainingSeconds +
                ", mKind=" + mKind +
                '}';
    }

    public void save() {
        System.out.printf("Saving %s %n",this);
    }
}
