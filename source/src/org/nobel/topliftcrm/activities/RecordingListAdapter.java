package org.nobel.topliftcrm.activities;

import java.util.Collections;
import java.util.List;

import org.nobel.highriseapi.entities.Recording;
import org.nobel.highriseapi.entities.Recording.SubjectType;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class RecordingListAdapter extends EntityListAdapter<Recording> {

    public RecordingListAdapter(Context context, List<Recording> entities) {
        super(context, entities);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.record_list_item;
    }

    @Override
    public void renderListItem(View view, Recording recording) {
        TextView subjecTypeTextView = (TextView) view.findViewById(R.id.subjectType);
        subjecTypeTextView.setText(getSubjectTypeText(recording.getSubjectType()));

        TextView subjecTextView = (TextView) view.findViewById(R.id.subject);
        subjecTextView.setText(recording.getSubjectName());

        TextView updatedAtTextView = (TextView) view.findViewById(R.id.updatedAt);
        updatedAtTextView.setText(recording.getUpdatedAt().toString());

        TextView bodyTextView = (TextView) view.findViewById(R.id.body);
        bodyTextView.setText(recording.getBody());
    }

    @Override
    protected void sort(List<Recording> entities) {
        Collections.sort(entities);
        Collections.reverse(entities);
    }

    private String getSubjectTypeText(SubjectType subjectType) {
        switch (subjectType) {
            case Deal:
                return "- Deal";
            case Kase:
                return "- Case";

            default:
                return "";
        }
    }

}
