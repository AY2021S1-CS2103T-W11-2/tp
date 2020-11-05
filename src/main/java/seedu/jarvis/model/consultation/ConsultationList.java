package seedu.jarvis.model.consultation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.jarvis.model.task.Task;
import seedu.jarvis.model.task.exceptions.TaskNotFoundException;


public class ConsultationList {
    private final ObservableList<Consultation> internalList = FXCollections.observableArrayList();

    /**
     * Adds a consultation to the list.
     */
    public void add(Consultation toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent consultation as the given argument.
     */
    public boolean contains(Consultation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameConsultation);
    }

    public void setConsultations(List<Consultation> consultations) {
        requireNonNull(consultations);
        this.internalList.setAll(consultations);
    }

    public ObservableList<Consultation> asObservableList() {
        return internalList;
    }

    public boolean isConsultationInList(String name) {
        return internalList.stream().anyMatch(consultation -> consultation.getIdentifier().equals(name));
    }

    /**
     * Removes the equivalent consultation from the list.
     * The consultation must exist in the list.
     */
    public void remove(Consultation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConsultationNotFoundException();
        }
    }
}
