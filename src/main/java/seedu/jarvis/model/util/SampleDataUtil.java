package seedu.jarvis.model.util;

import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.ReadOnlyAddressBook;
import seedu.jarvis.model.mission.Mission;
import seedu.jarvis.model.quest.Quest;
import seedu.jarvis.model.student.Email;
import seedu.jarvis.model.student.Name;
import seedu.jarvis.model.student.Student;
import seedu.jarvis.model.student.Telegram;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Telegram("alexyeoh"), new Email("alexyeoh@example.com")),
            new Student(new Name("Bernice Yu"), new Telegram("berniceyu132"), new Email("berniceyu@example.com")),
            new Student(new Name("Charlotte Oliveiro"), new Telegram("coliverro"),
                    new Email("charlotte@example.com")),
            new Student(new Name("David Li"), new Telegram("davidli"), new Email("lidavid@example.com")),
            new Student(new Name("Irfan Ibrahim"), new Telegram("irfan"), new Email("irfan@example.com")),
            new Student(new Name("Roy Balakrishnan"), new Telegram("royb"), new Email("royb@example.com"))
        };
    }

    public static Mission[] getSampleMissions() {
        return new Mission[] {
            new Mission("Fractal Dimensions", "Due: 26th August, 23:59", false),
            new Mission("Musical Notes", "Due: 12th October, 23:59", true),
            new Mission("Reuse Pairs", "Due: 18th October, 23:59", true),
            new Mission("Streams", "Due: 20th October, 23:59", false),
            new Mission("Stream Anomaly", "Deadline is over", false)
        };
    }

    public static Quest[] getSampleQuests() {
        return new Quest[] {
            new Quest("Runic Carpets", "Due: 26th August, 23:59", false),
            new Quest("Colorful Carpets", "Due: 3rd October, 23:59", true),
            new Quest("Functional Expressionism", "Due: 10th October, 23:59", true),
            new Quest("Cardioid Arrest", "Due: 20th October, 23:59", false),
            new Quest("Curvaceous Wizardry", "Deadline is over", false)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }

        for (Mission sampleMission: getSampleMissions()) {
            sampleAb.addMission(sampleMission);
        }

        for (Quest sampleQuest : getSampleQuests()) {
            sampleAb.addQuest(sampleQuest);
        }
        return sampleAb;
    }
}
