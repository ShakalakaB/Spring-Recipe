package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.NotesCommand;
import aldora.spring.recipe.model.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    NotesCommandToNotes notesCommandToNotes;

    NotesCommand notesCommand;

    @Before
    public void setUp() {
        notesCommandToNotes =  new NotesCommandToNotes();

        notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(DESCRIPTION);
    }

    @Test
    public void convert() {
        Notes notes = notesCommandToNotes.convert(notesCommand);
        assertEquals(ID, notes.getId());
    }

    @Test
    public void testNullObject() {
        Notes notes = notesCommandToNotes.convert(null);
        assertNull(notes);
    }

    @Test
    public void testEmptyObject() {
        Notes notes = notesCommandToNotes.convert(new NotesCommand());
        assertNotNull(notes);
    }
}