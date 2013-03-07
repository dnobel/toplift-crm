package org.nobel.highriseandroid.activities.cases;

import java.util.List;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseandroid.activities.notes.AbstractNotesFragment;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseandroid.util.ImageUtil;
import org.nobel.highriseapi.entities.Case;
import org.nobel.highriseapi.entities.Party;
import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.resources.CaseResource;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

public class CasePartiesFragment extends SherlockListFragment {

    private final class PartyListAdapter extends EntityListAdapter<Party> {
        private PartyListAdapter(Context context, List<Party> entities) {
            super(context, entities);
        }

        @Override
        public int getListItemLayoutId() {
            return R.layout.party_list_item;
        }

        @Override
        public void renderListItem(View listItem, Party party) {
            ImageView partyIconImageView = (ImageView) listItem.findViewById(R.id.partyIcon);
            ImageUtil.setImage(getActivity(), partyIconImageView, party);

            TextView partyNameTextView = (TextView) listItem.findViewById(R.id.partyName);
            if (party instanceof Person) {
                partyNameTextView.setText(((Person) party).getFullName());
            }
            else {
                partyNameTextView.setText(party.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListAdapter(new PartyListAdapter(getActivity(), getCase().getParties()));
        return inflater.inflate(R.layout.entity_list, null);
    }

    protected Case getCase() {
        // TODO: put loading into a task. If it is not cached, it tries to load
        // the case from remote, which results in a NetworkOnMainThreadException
        int caseId = getArguments().getInt(AbstractNotesFragment.ENTITY_ID);
        return HighriseApiService.getInstance(getActivity()).getResource(CaseResource.class).get(caseId);
    }

}
