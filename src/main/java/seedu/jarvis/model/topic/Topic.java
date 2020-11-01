package seedu.jarvis.model.topic;

public class Topic {
    public final String week;
    public final String outline;

    /**
     * Initializes an empty Topic object.
     */
    public Topic() {
        this.week = "";
        this.outline = "";
    }

    /**
     * Initializes a Topic object with the corresponding week and outline.
     * @param week week of Topic
     * @param outline outline of Topic
     */
    public Topic(String week, String outline) {
        this.week = week;
        this.outline = outline;
    }

    /**
     * Returns the week of the Topic object
     * @return a string representing the week
     */
    public String getWeek() {
        return week;
    }

    /**
     * Returns the outline of the Topic object
     * @return a string representing the outline
     */
    public String getOutline() {
        return outline;
    }

    /**
     * Checks whether the Topic object is empty
     * @return true if Topic is empty
     */
    public boolean isEmpty() {
        return this.week.isEmpty() && this.outline.isEmpty();
    }
}
