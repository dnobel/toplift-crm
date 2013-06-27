package org.nobel.topliftcrm.activities.base;

import java.util.List;

import org.nobel.highriseapi.entities.base.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class EntityListAdapter<T extends Entity> extends BaseAdapter {

    protected final Context context;
    protected List<T> entities;

    public EntityListAdapter(Context context, List<T> entities) {
        this.context = context;
        setEntities(entities);
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    public List<T> getEntities() {
        return entities;
    }

    @Override
    public T getItem(int pos) {
        return entities.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return -1;
    }

    public abstract int getListItemLayoutId();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entityListItemView = inflater.inflate(getListItemLayoutId(), parent, false);

        renderListItem(entityListItemView, getItem(position));

        return entityListItemView;
    }

    public abstract void renderListItem(View entityListItemView, T entity);

    public void setEntities(List<T> entities) {
        this.entities = entities;
        sort(this.entities);
    }

    protected void setText(View view, int textViewId, String text) {
        TextView textView = (TextView) view.findViewById(textViewId);
        textView.setText(text);
    }

    protected void sort(List<T> entities) {

    }
}
