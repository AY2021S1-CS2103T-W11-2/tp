package seedu.jarvis.testutil;

import seedu.jarvis.model.quest.Quest;

/**
 * A utility class to help with building Quest objects.
 */
public class QuestBuilder {

    public static final String DEFAULT_TITLE = "Runic Carpets";
    public static final String DEFAULT_DEADLINE = "Due: 26th August, 23:59";
    public static final boolean DEFAULT_ISGRADED = true;

    private String title;
    private String deadline;
    private boolean isGraded;

    /**
     * Creates a {@code QuestBuilder} with the default details.
     */
    public QuestBuilder() {
        title = DEFAULT_TITLE;
        deadline = DEFAULT_DEADLINE;
        isGraded = DEFAULT_ISGRADED;
    }

    /**
     * Initializes the QuestBuilder with the data of {@code questToCopy}.
     */
    public QuestBuilder(Quest questToCopy) {
        title = questToCopy.getTitle();
        deadline = questToCopy.getDeadline();
        isGraded = questToCopy.getIsGraded();
    }

    /**
     * Sets the {@code title} of the {@code Quest} that we are building.
     */
    public QuestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code Quest} that we are building.
     */
    public QuestBuilder withDeadline(String deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * Sets the {@code isGraded} of the {@code Quest} that we are building.
     */
    public QuestBuilder withIsGraded(boolean isGraded) {
        this.isGraded = isGraded;
        return this;
    }

    public Quest build() {
        return new Quest(title, deadline, isGraded);
    }

}


