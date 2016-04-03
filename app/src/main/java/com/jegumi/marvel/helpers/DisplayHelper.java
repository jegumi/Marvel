package com.jegumi.marvel.helpers;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class DisplayHelper {

    public static int getWidthScreen(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
