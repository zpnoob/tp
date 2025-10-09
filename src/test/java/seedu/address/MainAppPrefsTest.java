package seedu.address;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.commons.exceptions.DataLoadingException;

public class MainAppPrefsTest {

    // Normal case: prefs file exists and is read successfully
    @Test
    public void initPrefs_readsPrefs_success() {
        UserPrefs expected = new UserPrefs();
        MainApp app = new MainApp();
        UserPrefsStorage storage = new UserPrefsStorage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Path.of("prefs.json");
            }
            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                return Optional.of(expected);
            }
            @Override
            public void saveUserPrefs(seedu.address.model.ReadOnlyUserPrefs prefs) throws IOException {
                // no-op
            }
        };
        UserPrefs result = app.initPrefs(storage);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    // Case: prefs file missing, should create new
    @Test
    public void initPrefs_readsPrefs_missingFile_createsNew() {
        MainApp app = new MainApp();
        UserPrefsStorage storage = new UserPrefsStorage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Path.of("prefs.json");
            }
            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                return Optional.empty();
            }
            @Override
            public void saveUserPrefs(seedu.address.model.ReadOnlyUserPrefs prefs) throws IOException {
                // no-op
            }
        };
        UserPrefs result = app.initPrefs(storage);
        assertNotNull(result);
    }

    // Case: exception when reading, should use default
    @Test
    public void initPrefs_readsPrefs_exception_returnsDefault() {
        MainApp app = new MainApp();
        UserPrefsStorage storage = new UserPrefsStorage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Path.of("prefs.json");
            }
            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                throw new DataLoadingException(new Exception("fail"));
            }
            @Override
            public void saveUserPrefs(seedu.address.model.ReadOnlyUserPrefs prefs) throws IOException {
                // nil op
            }
        };
        UserPrefs result = app.initPrefs(storage);
        assertNotNull(result);
    }

    // Case: exception when saving, should log warning but still return prefs
    @Test
    public void initPrefs_savePrefs_exception_logsWarning() {
        MainApp app = new MainApp();
        UserPrefsStorage storage = new UserPrefsStorage() {
            @Override
            public Path getUserPrefsFilePath() {
                return Path.of("prefs.json");
            }
            @Override
            public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
                return Optional.of(new UserPrefs());
            }
            @Override
            public void saveUserPrefs(seedu.address.model.ReadOnlyUserPrefs prefs) throws IOException {
                throw new IOException("fail");
            }
        };
        UserPrefs result = app.initPrefs(storage);
        assertNotNull(result);
    }
}
