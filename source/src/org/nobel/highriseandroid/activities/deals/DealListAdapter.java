package org.nobel.highriseandroid.activities.deals;

import java.util.List;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseandroid.util.ImageUtil;
import org.nobel.highriseapi.entities.Deal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class DealListAdapter extends EntityListAdapter<Deal> {

    public DealListAdapter(Context context, List<Deal> entities) {
        super(context, entities);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.deal_list_item;
    }

    @Override
    public void renderListItem(View view, Deal deal) {
        setText(view, R.id.dealName, deal.getName());
        setText(view, R.id.dealCategory, deal.getCategory().getName());
        setText(view, R.id.dealParty, deal.getParty().getName());
        ImageUtil.setImage((Activity) context, (ImageView) view.findViewById(R.id.dealPartyImage), deal.getParty());
    }

}
