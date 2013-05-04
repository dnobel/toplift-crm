package org.nobel.topliftcrm.activities.notes;

import java.util.List;

import org.nobel.highriseapi.entities.Note;
import org.nobel.highriseapi.entities.User;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class NoteListAdapter extends EntityListAdapter<Note> {

    public NoteListAdapter(Context context, List<Note> entities) {
        super(context, entities);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.note_list_item;
    }

    @Override
    public void renderListItem(View view, Note note) {
        setText(view, R.id.noteBody, note.getBody());

        TextView updatedAtTextView = (TextView) view.findViewById(R.id.noteUpdatedAt);
        updatedAtTextView.append(note.getUpdatedAt().toString());

        User author = note.getAuthor();
        TextView noteAuthorTextView = (TextView) view.findViewById(R.id.noteAuthor);
        if (author != null) {
            noteAuthorTextView.setText(author.getName());
        }

    }

}
