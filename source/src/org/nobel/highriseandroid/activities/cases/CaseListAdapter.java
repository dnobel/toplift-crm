package org.nobel.highriseandroid.activities.cases;

import java.util.List;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseapi.entities.Case;

import android.content.Context;
import android.view.View;

public class CaseListAdapter extends EntityListAdapter<Case> {

    public CaseListAdapter(Context context, List<Case> entities) {
        super(context, entities);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.case_list_item;
    }

    @Override
    public void renderListItem(View view, Case kase) {
        setText(view, R.id.caseName, kase.getName());
    }

}
