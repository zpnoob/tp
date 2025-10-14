package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class MainAppPrefsTest {
    @TempDir
    Path tempDir;

    // ---------- Reflection helpers to reach private methods in MainApp ----------
    private Model invokeInitModelManager(MainApp app, Storage storage, ReadOnlyUserPrefs prefs) throws Exception {
        Method m = MainApp.class.getDeclaredMethod("initModelManager", Storage.class, ReadOnlyUserPrefs.class);
        m.setAccessible(true);
        return (Model) m.invoke(app, storage, prefs);
    }

    private void invokeInitLogging(MainApp app, Config cfg) throws Exception {
        Method m = MainApp.class.getDeclaredMethod("initLogging", Config.class);
        m.setAccessible(true);
        m.invoke(app, cfg);
    }

    // ---------- Case 1: address book present & valid -> load from existing file (NOT sample) ----------
    @Test
    public void initModelManager_validExistingFile_loadsExisting() throws Exception {
        Path abPath = tempDir.resolve("addressbook.json");
        Path prefsPath = tempDir.resolve("prefs.json");

        // Create a valid, empty AddressBook file
        AddressBookStorage abStorage = new JsonAddressBookStorage(abPath);
        abStorage.saveAddressBook(new AddressBook());

        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookFilePath(abPath);

        UserPrefsStorage prefsStorage = new JsonUserPrefsStorage(prefsPath);
        prefsStorage.saveUserPrefs(prefs);

        Storage storage = new StorageManager(abStorage, prefsStorage);
        MainApp app = new MainApp();

        Model model = invokeInitModelManager(app, storage, prefs);

        // If it loaded the existing file, it should be empty (not sample).
        assertTrue(model.getAddressBook().getPersonList().isEmpty(),
                "Expected to load existing empty AddressBook, not sample data.");
    }

    // ---------- Case 2: file missing -> use SampleDataUtil via Optional.orElseGet ----------
    @Test
    public void initModelManager_missingFile_usesSampleData() throws Exception {
        Path abPath = tempDir.resolve("missing-addressbook.json"); // do NOT create
        Path prefsPath = tempDir.resolve("prefs.json");

        AddressBookStorage abStorage = new JsonAddressBookStorage(abPath);
        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookFilePath(abPath);

        UserPrefsStorage prefsStorage = new JsonUserPrefsStorage(prefsPath);
        prefsStorage.saveUserPrefs(prefs);

        Storage storage = new StorageManager(abStorage, prefsStorage);
        MainApp app = new MainApp();

        Model model = invokeInitModelManager(app, storage, prefs);

        // Sample data should not be empty in typical AB3 setups.
        assertTrue(!model.getAddressBook().getPersonList().isEmpty(),
                "When file is missing, initModelManager should use SampleDataUtil to populate data.");
    }

    // ---------- Case 3: storage throws DataLoadingException -> start with empty AddressBook ----------
    @Test
    public void initModelManager_storageThrows_returnsEmptyAddressBook() throws Exception {
        Path fakeAbPath = tempDir.resolve("whatever.json");
        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookFilePath(fakeAbPath);

        Storage throwingStorage = new ThrowingStorage(fakeAbPath);
        MainApp app = new MainApp();

        Model model = invokeInitModelManager(app, throwingStorage, prefs);

        assertTrue(model.getAddressBook().getPersonList().isEmpty(),
                "When storage throws, MainApp should start with an empty AddressBook.");
    }

    // ---------- Helper Storage that always throws when reading the address book ----------
    private static class ThrowingStorage implements Storage {

        private final Path abPath;
        private final Path prefsPath;

        ThrowingStorage(Path addressBookPath) {
            this.abPath = addressBookPath;
            this.prefsPath = addressBookPath.getParent() == null
                    ? addressBookPath
                    : addressBookPath.getParent().resolve("prefs.json");
        }

        // ---- AddressBookStorage API ----
        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            throw new DataLoadingException(new IOException("boom: readAddressBook()"));
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            throw new DataLoadingException(new IOException("boom: readAddressBook(Path)"));
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            // not used in these tests
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            // not used in these tests
        }

        @Override
        public Path getAddressBookFilePath() {
            return abPath;
        }

        // ---- UserPrefsStorage API ----
        @Override
        public Optional<UserPrefs> readUserPrefs() {
            return Optional.of(new UserPrefs()); // simple default so initModelManager can proceed
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) {
            // not used
        }

        @Override
        public Path getUserPrefsFilePath() {
            return prefsPath;
        }
    }

    // Normal case: prefs file exists and is read successfully
    @Test
    public void initPrefsReadsPrefsSuccess() {
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
    public void initPrefsReadsPrefsMissingFileCreatesNew() {
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
    public void initPrefsReadsPrefsExceptionReturnsDefault() {
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
    public void initPrefsSavePrefsExceptionLogsWarning() {
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
