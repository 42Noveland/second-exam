package com.example.mediaplayer;

public abstract class CallBackListener {
    public abstract void onFinish(String out);
    public abstract void onError(Exception ex);
}
