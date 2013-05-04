package org.nobel.topliftcrm.activities.base;

import java.util.List;

import org.nobel.highriseapi.entities.base.Entity;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.HomeActivity;
import org.nobel.topliftcrm.util.ProgressVisualizationUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class EntityListActivity<T extends Entity> extends SherlockListActivity {

    class LoadEntityListTask extends AsyncTask<Void, Void, List<T>> {

        private final boolean manualRefresh;

        public LoadEntityListTask(boolean manualRefresh) {
            this.manualRefresh = manualRefresh;
        }

        @Override
        protected List<T> doInBackground(Void... args) {
            final List<T> entities = loadEntityList();
            return entities;
        }

        @Override
        protected void onPostExecute(List<T> entities) {
            setListAdapter(createListAdapter(entities));
            ProgressVisualizationUtil.hideProgressbar(EntityListActivity.this);
            ProgressVisualizationUtil.stopRotate(refreshItem, EntityListActivity.this);
        }

        @Override
        protected void onPreExecute() {
            if (manualRefresh) {
                ProgressVisualizationUtil.rotateRefreshItem(refreshItem, EntityListActivity.this);
            }
            else {
                ProgressVisualizationUtil.showProgressbar(EntityListActivity.this);
            }
        }

    }

    private MenuItem refreshItem;

    @SuppressWarnings("unchecked")
    @Override
    public EntityListAdapter<T> getListAdapter() {
        return (EntityListAdapter<T>) super.getListAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.list, menu);
        refreshItem = menu.findItem(R.id.refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                clearEntityList();
                loadData(true);
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void clearEntityList();

    protected abstract EntityListAdapter<T> createListAdapter(List<T> entities);

    protected abstract Class<? extends Activity> getEntityDetailActivity();

    protected void loadData(boolean manualRefresh) {
        new LoadEntityListTask(manualRefresh).execute();
    }

    protected abstract List<T> loadEntityList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.entity_list);

        ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        loadData(false);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Entity entity = getListAdapter().getItem(position);
        Intent intent = new Intent(this, getEntityDetailActivity());
        intent.putExtra(EntityDetailActivity.ENTITY_ID, entity.getId());
        startActivity(intent);
    }
}
