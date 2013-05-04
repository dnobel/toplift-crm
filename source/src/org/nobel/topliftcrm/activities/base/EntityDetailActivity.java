package org.nobel.topliftcrm.activities.base;

import org.nobel.highriseapi.entities.base.Entity;
import org.nobel.highriseapi.entities.base.EntityWithName;
import org.nobel.topliftcrm.util.ProgressVisualizationUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;


public abstract class EntityDetailActivity<T extends Entity> extends SherlockActivity {

	public static final String ENTITY_ID = "ENTITY_ID";

	class LoadEntityTask extends AsyncTask<Integer, Void, T> {

		@Override
		protected T doInBackground(Integer... ids) {
			final T entities = getEntity(ids[0]);
			return entities;
		}

		@Override
		protected void onPostExecute(T entity) {
			renderView(entity);
			setTitle(getActivityTitle(entity));
			ProgressVisualizationUtil.hideProgressbar(EntityDetailActivity.this);
		}

		@Override
		protected void onPreExecute() {

			ProgressVisualizationUtil.showProgressbar(EntityDetailActivity.this);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());

		int entityId = getIntent().getExtras().getInt(ENTITY_ID);
		new LoadEntityTask().execute(entityId);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	protected String getActivityTitle(T entity) {
		if (entity instanceof EntityWithName) {
			EntityWithName entityWithName = (EntityWithName) entity;
			return getTitle() + " " + entityWithName.getName();
		} else {
			return getTitle().toString();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			goBackToListActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected abstract T getEntity(Integer id);

	protected abstract int getLayoutId();

	protected abstract Class<? extends Activity> getListActivity();

	protected abstract void renderView(T entity);

	protected void setText(int textViewId, String text) {
		TextView textView = (TextView) this.findViewById(textViewId);
		textView.setText(text);
	}

	private void goBackToListActivity() {
		Intent intent = new Intent(this, getListActivity());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
