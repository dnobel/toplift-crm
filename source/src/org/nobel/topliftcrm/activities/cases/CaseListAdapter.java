package org.nobel.topliftcrm.activities.cases;

import java.util.List;

import org.nobel.highriseapi.entities.Case;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;

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
