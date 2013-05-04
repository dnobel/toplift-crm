package org.nobel.topliftcrm.activities.cases;

import java.util.List;

import org.nobel.highriseapi.entities.Case;
import org.nobel.highriseapi.resources.CaseResource;
import org.nobel.topliftcrm.activities.base.EntityListActivity;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.data.HighriseApiService;

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
