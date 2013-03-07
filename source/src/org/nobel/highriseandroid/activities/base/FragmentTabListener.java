package org.nobel.highriseandroid.activities.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class FragmentTabListener<T extends Fragment> implements TabListener {

    private final Activity activity;
    private Fragment fragment;
    private final Bundle fragmentArguments;
    private final Class<T> fragmentClass;
    private final String tag;

    public FragmentTabListener(Activity activity, String tag, Class<T> clz, Bundle fragmentArguments) {
        this.activity = activity;
        this.tag = tag;
        this.fragmentClass = clz;
        this.fragmentArguments = fragmentArguments;
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ignoredFt) {
        FragmentManager fragMgr = ((FragmentActivity) activity).getSupportFragmentManager();
        FragmentTransaction ft = fragMgr.beginTransaction();
        if (fragment == null) {
            fragment = Fragment.instantiate(activity, fragmentClass.getName(), fragmentArguments);
            fragment.setArguments(fragmentArguments);
            ft.replace(android.R.id.content, fragment, tag);
        }
        else {
            ft.attach(fragment);
        }

        ft.commit();
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ignoredFt) {
        FragmentManager fragMgr = ((FragmentActivity) activity).getSupportFragmentManager();
        FragmentTransaction ft = fragMgr.beginTransaction();

        if (fragment != null) {
            ft.detach(fragment);
        }

        ft.commit();
    }
}
