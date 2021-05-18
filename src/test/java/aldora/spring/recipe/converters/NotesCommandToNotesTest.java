package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.NotesCommand;
import aldora.spring.recipe.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {
    public final static Long ID = 1L;
    public final static String DESCRIPTION = "DE";

    NotesCommandToNotes notesCommandToNotes;

    NotesCommand notesCommand;

    @BeforeEach
    void setUp() {
        notesCommandToNotes =  new NotesCommandToNotes();

        notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(DESCRIPTION);
    }

    @Test
    void convert() {
        Notes notes = notesCommandToNotes.convert(notesCommand);
        assertEquals(ID, notes.getId());
    }

    @Test
    void testNullObject() {
        Notes notes = notesCommandToNotes.convert(null);
        assertNull(notes);
    }

    @Test
    void testEmptyObject() {
        Notes notes = notesCommandToNotes.convert(new NotesCommand());
        assertNotNull(notes);
    }
}