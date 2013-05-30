package org.nobel.topliftcrm.activities.notes;

import java.util.List;

import org.nobel.highriseapi.entities.Note;
import org.nobel.highriseapi.resources.NoteResource;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.data.HighriseApiService;
import org.nobel.topliftcrm.data.LoadDataTask;
import org.nobel.topliftcrm.util.ProgressVisualizationUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class AbstractNotesFragment extends SherlockListFragment implements OnClickListener {
    class CreateNoteTask extends LoadDataTask<Note, Void, Void> {

        private int entityId;

        private ProgressDialog progressDialog;

        public CreateNoteTask(Context context) {
            super(context);
        }

        @Override
        protected Void doLoad(Note... notes) {
            createDealNote(entityId, notes[0]);
            return null;
        }

        @Override
        protected void hideProgessbar() {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setListAdapter(new NoteListAdapter(getActivity(), getNotes(entityId)));
            getNoteBodyTextField().setText("");
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getNoteBodyTextField().getWindowToken(), 0);
        }

        @Override
        protected void onPreExecute() {
            entityId = getEntityId();
            super.onPreExecute();
        }

        @Override
        protected void showProgessbar() {
            progressDialog = ProgressDialog.show(AbstractNotesFragment.this.getActivity(), "Create Note",
                    "Creating note...");
        }

    }

    class LoadNotesTask extends LoadDataTask<Void, Void, List<Note>> {

        private final int entityId;
        private final boolean manualRefresh;

        public LoadNotesTask(Context context, boolean manualRefresh, int entityId) {
            super(context);
            this.manualRefresh = manualRefresh;
            this.entityId = entityId;
        }

        @Override
        protected List<Note> doLoad(Void... args) {
            final List<Note> notes = getNotes(entityId);
            return notes;
        }

        @Override
        protected void hideProgessbar() {
            ProgressVisualizationUtil.hideProgressbar(getActivity());
            ProgressVisualizationUtil.stopRotate(refreshItem, getActivity());
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            setListAdapter(new NoteListAdapter(getActivity(), notes));
        }

        @Override
        protected void showProgessbar() {
            if (manualRefresh) {
                ProgressVisualizationUtil.rotateRefreshItem(refreshItem, getActivity());
            }
            else {
                ProgressVisualizationUtil.showProgressbar(getActivity());
            }
        }

        private FragmentActivity getActivity() {
            return AbstractNotesFragment.this.getActivity();
        }

    }

    public static final String ENTITY_ID = "ENTITY_ID";

    private MenuItem refreshItem;

    public NoteResource getNoteResource() {
        return HighriseApiService.getInstance(getActivity()).getResource(NoteResource.class);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {

        EditText noteBodyTextField = getNoteBodyTextField();

        Note note = new Note();
        note.setBody(noteBodyTextField.getText().toString());

        new CreateNoteTask(getActivity()).execute(note);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.list, menu);
        refreshItem = menu.findItem(R.id.refresh);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list, null);
        TextView noteSendButton = (TextView) view.findViewById(R.id.noteSendButton);
        noteSendButton.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                getNoteResource().clear();
                loadNotes(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadNotes(false);
    }

    protected abstract void createDealNote(int entityId, Note note);

    protected abstract List<Note> getNotes(int entityId);

    private int getEntityId() {
        return getArguments().getInt(ENTITY_ID);
    }

    private EditText getNoteBodyTextField() {
        return (EditText) getView().findViewById(R.id.noteTextField);
    }

    private void loadNotes(boolean manualRefresh) {
        int entityId = getEntityId();
        new LoadNotesTask(getActivity(), manualRefresh, entityId).execute();
    }

}
