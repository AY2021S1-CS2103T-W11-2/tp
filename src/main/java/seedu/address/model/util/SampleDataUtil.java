package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSamplePersons() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Telegram("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Telegram("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new Telegram("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new Telegram("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new Telegram("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Telegram("92624417"), new Email("royb@example.com"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSamplePersons()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
