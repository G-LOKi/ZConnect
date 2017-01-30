package com.zconnect.login.zconnect;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;


public class CollapseAnimation extends TranslateAnimation implements TranslateAnimation.AnimationListener {

    int panelWidth;
    private LinearLayout slidingLayout;

    public CollapseAnimation(LinearLayout layout, int width, int fromXType, float fromXValue, int toXType,
                             float toXValue, int fromYType, float fromYValue, int toYType, float toYValue) {

        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);

        //Initialize
        slidingLayout = layout;
        panelWidth = width;
        setDuration(400);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);

        //Clear left and right margins
        LayoutParams params = (LayoutParams) slidingLayout.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        slidingLayout.setLayoutParams(params);
        slidingLayout.requestLayout();
        slidingLayout.startAnimation(this);

    }

    public void onAnimationEnd(Animation animation) {

    }

    public void onAnimationRepeat(Animation animation) {

    }

    public void onAnimationStart(Animation animation) {

    }

}