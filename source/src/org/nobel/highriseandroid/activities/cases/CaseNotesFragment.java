package org.nobel.highriseandroid.activities.cases;

import java.util.List;

import org.nobel.highriseandroid.activities.notes.AbstractNotesFragment;
import org.nobel.highriseapi.entities.Note;
import org.nobel.highriseapi.resources.NoteResource.NoteKind;


public class CaseNotesFragment extends AbstractNotesFragment {

	@Override
	protected void createDealNote(int entityId, Note note) {
		getNoteResource().createForEntity(NoteKind.CASE_NOTES, entityId, note);
	}

	@Override
	protected List<Note> getNotes(int entityId) {
		return getNoteResource().getAll(NoteKind.CASE_NOTES, entityId);
	}

}
