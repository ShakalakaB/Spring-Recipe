package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.NotesCommand;
import aldora.spring.recipe.model.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    NotesToNotesCommand notesToNotesCommand;

    Notes notes;

    @Before
    public void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
        notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(DESCRIPTION);
    }

    @Test
    public void convert() {
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);
        assertEquals(ID, notesCommand.getId());
    }

    @Test
    public void testNullObject() {
        NotesCommand notesCommand = notesToNotesCommand.convert(null);
        assertNull(notesCommand);
    }

    @Test
    public void testEmptyObject() {
        NotesCommand notesCommand = notesToNotesCommand.convert(new Notes());
        assertNotNull(notesCommand);
    }
}