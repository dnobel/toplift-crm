package org.nobel.highriseandroid.activities.deals;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.notes.AbstractNotesFragment;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseandroid.util.ImageUtil;
import org.nobel.highriseapi.entities.Deal;
import org.nobel.highriseapi.entities.Deal.Status;
import org.nobel.highriseapi.resources.DealResource;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class DealDetailFragment extends SherlockFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deal_detail, container, false);

        // TODO: put loading into a task. If it is not cached, it tries to load
        // the deal from remote, which results in a NetworkOnMainThreadException
        Deal deal = HighriseApiService.getInstance(getActivity()).getResource(DealResource.class)
                .get(getArguments().getInt(AbstractNotesFragment.ENTITY_ID));

        setText(view, R.id.dealName, deal.getName());
        setText(view, R.id.dealCategory, deal.getCategory().getName());
        setText(view, R.id.dealParty, deal.getParty().getName());
        setText(view, R.id.dealPartyType, deal.getParty().getType());
        setText(view, R.id.dealStatus, deal.getStatus().name());
        setText(view, R.id.dealBackground, deal.getBackground());
        TextView dealStatusText = (TextView) view.findViewById(R.id.dealStatus);
        dealStatusText.setTextColor(getResources().getColor(getColorForStatus(deal.getStatus())));
        ImageUtil.setImage((Activity) this.getActivity(), (ImageView) view.findViewById(R.id.dealPartyImage),
                deal.getParty());

        return view;
    }

    protected void setText(View view, int textViewId, String text) {
        TextView textView = (TextView) view.findViewById(textViewId);
        textView.setText(text);
    }

    private int getColorForStatus(Status status) {
        switch (status) {
            case lost:
                return R.color.deal_status_lost;
            case pending:
                return R.color.deal_status_pending;
            case won:
                return R.color.deal_status_won;
            default:
                return R.color.deal_status_won;
        }
    }

}
