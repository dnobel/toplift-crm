package org.nobel.highriseandroid.util;

import org.nobel.highriseapi.entities.base.EntityImage;

import android.graphics.drawable.Drawable;

public class AndroidEntityImage implements EntityImage {

    private Drawable drawable;

    public AndroidEntityImage(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
