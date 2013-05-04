package org.nobel.topliftcrm.activities.deals;

import java.util.List;

import org.nobel.highriseapi.entities.Deal;
import org.nobel.highriseapi.resources.DealResource;
import org.nobel.topliftcrm.activities.base.EntityListActivity;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.app.Activity;

public class DealListActivity extends EntityListActivity<Deal> {

    @Override
    protected void clearEntityList() {
        HighriseApiService.getInstance(this).getResource(DealResource.class).clear();
    }

    @Override
    protected EntityListAdapter<Deal> createListAdapter(List<Deal> entities) {
        return new DealListAdapter(this, entities);
    }

    @Override
    protected Class<? extends Activity> getEntityDetailActivity() {
        return DealDetailActivity.class;
    }

    @Override
    protected List<Deal> loadEntityList() {
        return HighriseApiService.getInstance(this).getResource(DealResource.class).getAll();
    }

}
