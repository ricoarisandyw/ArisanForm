package com.github.arisan.helper;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.arisan.R;

public class AnimationTools {
    public interface OnAnimationDone{
        public void done();
    }

    public static Animation fly_in(Context context, final OnAnimationDone onDone){
        /*AnimationUtils.loadAnimation(context, R.anim.fly_in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.startAnimation(anim);
                onDone.done();
            }
        },position*100);*/
        return null;
    }
}
