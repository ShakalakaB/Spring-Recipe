package aldora.spring.recipe.converters;

import aldora.spring.recipe.commands.NotesCommand;
import aldora.spring.recipe.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {
    public final static String ID = "1";
    public final static String DESCRIPTION = "DE";

    NotesToNotesCommand notesToNotesCommand;

    Notes notes;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
        notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(DESCRIPTION);
    }

    @Test
    void convert() {
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);
        assertEquals(ID, notesCommand.getId());
    }

    @Test
    void testNullObject() {
        NotesCommand notesCommand = notesToNotesCommand.convert(null);
        assertNull(notesCommand);
    }

    @Test
    void testEmptyObject() {
        NotesCommand notesCommand = notesToNotesCommand.convert(new Notes());
        assertNotNull(notesCommand);
    }
}