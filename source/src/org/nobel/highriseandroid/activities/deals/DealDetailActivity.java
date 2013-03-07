package org.nobel.highriseandroid.activities.deals;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityDetailActivity;
import org.nobel.highriseandroid.activities.base.FragmentTabListener;
import org.nobel.highriseandroid.activities.notes.AbstractNotesFragment;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class DealDetailActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Bundle fragmentArguments = new Bundle();
        int dealId = DealDetailActivity.this.getIntent().getExtras().getInt(EntityDetailActivity.ENTITY_ID);
        fragmentArguments.putInt(AbstractNotesFragment.ENTITY_ID, dealId);

        Tab detailTab = actionBar.newTab();
        detailTab.setText("Details");
        detailTab.setTabListener(new FragmentTabListener<DealDetailFragment>(this, "", DealDetailFragment.class,
                fragmentArguments));
        actionBar.addTab(detailTab);

        Tab commentsTab = actionBar.newTab();
        commentsTab.setText("Notes");
        commentsTab.setTabListener(new FragmentTabListener<DealNotesFragment>(this, "", DealNotesFragment.class,
                fragmentArguments));
        actionBar.addTab(commentsTab);

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

    private void goBackToListActivity() {
        Intent intent = new Intent(this, DealListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
