package org.nobel.topliftcrm.activities.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nobel.topliftcrm.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationListAdapter extends BaseAdapter {

    public enum NavigationItem {

        CASES(R.string.menu_cases, R.drawable.cases, 5),
        CONTACTS(R.string.menu_contacts, R.drawable.contacts, 3),
        DEALS(R.string.menu_deals, R.drawable.deals, 4),
        HOME(R.string.menu_home, R.drawable.home, 1),
        SETTINGS(R.string.menu_settings, R.drawable.settings, 6),
        TASKS(R.string.menu_tasks, R.drawable.tasks, 2);

        public static NavigationItem getByItemIndex(int itemIndex) {
            NavigationItem[] navigationItems = NavigationItem.values();
            for (NavigationItem navigationItem : navigationItems) {
                if (navigationItem.itemIndex == itemIndex) {
                    return navigationItem;
                }
            }
            return null;
        }

        public int icon;
        public int itemIndex;

        public int title;

        NavigationItem(int title, int icon, int itemIndex) {
            this.icon = icon;
            this.title = title;
            this.itemIndex = itemIndex;
        }

    }

    private static final List<NavigationItem> NAVIGATION_ITEMS = new ArrayList<NavigationListAdapter.NavigationItem>();

    static {
        List<NavigationItem> navigationItems = Arrays.asList(NavigationItem.values());
        Collections.sort(navigationItems, new Comparator<NavigationItem>() {

            @Override
            public int compare(NavigationItem lhs, NavigationItem rhs) {
                return Integer.valueOf(lhs.itemIndex).compareTo(Integer.valueOf(rhs.itemIndex));
            }
        });
        NAVIGATION_ITEMS.addAll(navigationItems);
    }

    protected final Context context;

    public NavigationListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return NAVIGATION_ITEMS.size();
    }

    @Override
    public NavigationItem getItem(int index) {
        return NAVIGATION_ITEMS.get(index);
    }

    @Override
    public long getItemId(int index) {
        return -1;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.navigation_list_item, parent, false);

        NavigationItem navigationItem = getItem(index);

        TextView textView = (TextView) view.findViewById(R.id.navigationItemTitle);
        textView.setText(navigationItem.title);

        Drawable drawable = context.getResources().getDrawable(navigationItem.icon);

        ImageView imageView = (ImageView) view.findViewById(R.id.navigationItemIcon);
        imageView.setImageDrawable(drawable);

        return view;
    }

}
