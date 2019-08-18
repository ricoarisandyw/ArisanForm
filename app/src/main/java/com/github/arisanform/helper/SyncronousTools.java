package com.github.arisanform.helper;


import android.os.Handler;
import android.util.Log;

public class SyncronousTools {
    boolean done = false;
    int counter = 0;

    public void waiting(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!done) waiting();
                counter++;
                Log.d("__Syncronous",counter+"s...");
            }
        }, 1000);
    }

    public void done(){
        this.done = true;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
