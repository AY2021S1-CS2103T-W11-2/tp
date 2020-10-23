package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserLogin;
import seedu.address.model.UserLogin;

/**
 * Represents a storage for {@link seedu.address.model.UserPrefs}.
 */
public interface UserLoginStorage {

    /**
     * Returns the file path of the UserLogin data file.
     */
    Path getUserLoginFilePath();

    /**
     * Returns UserLogin data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @return A UserLogin Optional describing the login formation read.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserLogin> readUserLogin() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.ReadOnlyUserPrefs} to the storage.
     * @param userLogin cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserLogin(ReadOnlyUserLogin userLogin) throws IOException;

}
