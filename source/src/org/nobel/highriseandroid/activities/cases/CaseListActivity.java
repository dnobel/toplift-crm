package org.nobel.highriseandroid.activities.cases;

import java.util.List;

import org.nobel.highriseandroid.activities.base.EntityListActivity;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseapi.entities.Case;
import org.nobel.highriseapi.resources.CaseResource;

import android.app.Activity;

public class CaseListActivity extends EntityListActivity<Case> {

    @Override
    protected void clearEntityList() {
        HighriseApiService.getInstance(this).getResource(CaseResource.class).clear();
    }

    @Override
    protected EntityListAdapter<Case> createListAdapter(List<Case> entities) {
        return new CaseListAdapter(this, entities);
    }

    @Override
    protected Class<? extends Activity> getEntityDetailActivity() {
        return CaseDetailActivity.class;
    }

    @Override
    protected List<Case> loadEntityList() {
        return HighriseApiService.getInstance(this).getResource(CaseResource.class).getAll();
    }

}
