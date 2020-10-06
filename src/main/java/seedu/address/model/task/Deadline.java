package seedu.address.model.task;

public class Deadline extends Task {

    private final String taskId;
    private final String description;
    private final String deadline;

    public Deadline(String description, String deadline) {
        this.taskId = generateTaskId();
        this.description = description;
        this.deadline = deadline;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    /**
     * Returns a unique task id.
     */
    public String generateTaskId() {
        int taskNum = Task.getTaskNum();
        Task.taskNumInc();
        return "E" + taskNum;
    }

    /**
     * Returns true if both tasks have the same task id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.task.Deadline)) {
            return false;
        }

        Deadline otherTask = (Deadline) other;
        return this.taskId.equals(otherTask.getTaskId());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(getTaskId())
                .append("] ")
                .append(getDescription())
                .append(" by ")
                .append(getDeadline());

        return builder.toString();
    }
}
