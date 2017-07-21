package com.teamtreehouse.pomodoro.model;

public enum AttemptKind {
    FOCUS(3,"Focus Time"),//25*60 sec
    BREAK(3,"Break Time");//5*60 sec

    private int mTotalSeconds;
    private String mDisplayName;
    AttemptKind(int totalSeconds,String displayName){
        mTotalSeconds=totalSeconds;
        mDisplayName=displayName;
    }
    public int getTotalSeconds(){
        return mTotalSeconds;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
