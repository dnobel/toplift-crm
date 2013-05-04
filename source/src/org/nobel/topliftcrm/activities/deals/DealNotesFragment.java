package org.nobel.topliftcrm.activities.deals;

import java.util.List;

import org.nobel.highriseapi.entities.Note;
import org.nobel.highriseapi.resources.NoteResource.NoteKind;
import org.nobel.topliftcrm.activities.notes.AbstractNotesFragment;


public class DealNotesFragment extends AbstractNotesFragment {

	@Override
	protected void createDealNote(int entityId, Note note) {
		getNoteResource().createForEntity(NoteKind.DEAL_NOTES, entityId, note);
	}

	@Override
	protected List<Note> getNotes(int entityId) {
		return getNoteResource().getAll(NoteKind.DEAL_NOTES, entityId);
	}

}
