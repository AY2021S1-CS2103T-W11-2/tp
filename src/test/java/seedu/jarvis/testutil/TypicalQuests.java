package seedu.jarvis.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.quest.Quest;

/**
 * A utility class containing a list of {@code Quest} objects to be used in tests.
 */
public class TypicalQuests {

    public static final Quest RUNIC_CARPETS = new QuestBuilder().withTitle("Runic Carpets")
            .withDeadline("Due: 26th August, 23:59").withIsGraded(false).build();
    public static final Quest COLORFUL_CARPETS = new QuestBuilder().withTitle("Colorful Carpets")
            .withDeadline("Due: 3rd October, 23:59").withIsGraded(true).build();
    public static final Quest FUNCTIONAL_EXPRESSIONISM = new QuestBuilder().withTitle("Functional Expressionism")
            .withDeadline("Due: 10th October, 23:59").withIsGraded(true).build();
    public static final Quest CARDIOID_ARREST = new QuestBuilder().withTitle("Cardioid Arrest")
            .withDeadline("Due: 20th October, 23:59").withIsGraded(false).build();
    public static final Quest CURVACEOUS_WIZARDRY = new QuestBuilder().withTitle("Curvaceous Wizardry")
            .withDeadline("Deadline is over").withIsGraded(false).build();

    private TypicalQuests() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical missions.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Quest quest : getTypicalQuests()) {
            ab.addQuest(quest);
        }
        return ab;
    }

    public static List<Quest> getTypicalQuests() {
        return new ArrayList<>(Arrays.asList(RUNIC_CARPETS, COLORFUL_CARPETS, FUNCTIONAL_EXPRESSIONISM,
                CARDIOID_ARREST, CURVACEOUS_WIZARDRY));
    }

}
