package seedu.jarvis.model.quest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.TypicalQuests.COLORFUL_CARPETS;
import static seedu.jarvis.testutil.TypicalQuests.RUNIC_CARPETS;

import org.junit.jupiter.api.Test;

import seedu.jarvis.testutil.QuestBuilder;

class QuestTest {

    @Test
    void getTitle() {
        assertTrue(RUNIC_CARPETS.getTitle().equals("Runic Carpets"));
    }

    @Test
    void getDeadline() {
        assertTrue(RUNIC_CARPETS.getDeadline().equals("Due: 26th August, 23:59"));
    }

    @Test
    void testEquals() {
        // same values -> return true
        assertTrue(RUNIC_CARPETS.equals(RUNIC_CARPETS));

        // same object -> return true
        assertTrue(RUNIC_CARPETS.equals(RUNIC_CARPETS));

        // null -> returns false
        assertFalse(RUNIC_CARPETS.equals(null));

        // different type -> returns false
        assertFalse(RUNIC_CARPETS.equals(5));

        // different quest -> returns false
        assertFalse(RUNIC_CARPETS.equals(COLORFUL_CARPETS));

        // different deadline -> returns false
        Quest editedRunicCarpets = new QuestBuilder(RUNIC_CARPETS)
                .withDeadline("Due: 20th October, 23:59").build();
        assertFalse(editedRunicCarpets.equals(RUNIC_CARPETS));

        // different isGraded -> return false
        Quest editedColorfulCarpets = new QuestBuilder(COLORFUL_CARPETS).withIsGraded(false).build();
        assertFalse(editedColorfulCarpets.equals(COLORFUL_CARPETS));
    }

    @Test
    void testToString() {
        String questTestToString = RUNIC_CARPETS.getTitle() + " Deadline: " + RUNIC_CARPETS.getDeadline();
        System.out.println(RUNIC_CARPETS.toString().equals(questTestToString));
    }
}
