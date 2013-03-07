package org.nobel.highriseandroid.util;

import org.nobel.highriseandroid.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.actionbarsherlock.view.MenuItem;

public class ProgressVisualizationUtil {

    public static ImageView imageView;

    public static void hideProgressbar(final Activity activity) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                View progressBar = activity.findViewById(R.id.progressBar);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public static void rotateRefreshItem(final MenuItem refreshItem, final Activity activity) {

        if (refreshItem == null) {
            return;
        }

        if (imageView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageView = (ImageView) inflater.inflate(R.layout.refresh_menuitem, null);
        }
        Animation rotation = AnimationUtils.loadAnimation(activity, R.drawable.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(rotation);
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                refreshItem.setActionView(imageView);
            }
        });
    }

    public static void showProgressbar(final Activity activity) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                View progressBar = activity.findViewById(R.id.progressBar);
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void stopRotate(final MenuItem refreshItem, final Activity activity) {
        if (refreshItem == null || refreshItem.getActionView() == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                View actionView = refreshItem.getActionView();

                actionView.clearAnimation();

                refreshItem.setActionView(null);
            }
        });
    }
}
