package org.nobel.topliftcrm.activities.cases;

import java.util.List;

import org.nobel.highriseapi.entities.Note;
import org.nobel.highriseapi.resources.NoteResource.NoteKind;
import org.nobel.topliftcrm.activities.notes.AbstractNotesFragment;


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
