package seedu.jarvis.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.CommandTargetFeature;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.topic.Topic;
import seedu.jarvis.model.topic.Topics;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String SEMESTER_START_DATE = "10 08 2020";
    private static final String USER_GUIDE_URL = "https://ay2021s1-cs2103t-w11-2.github.io/tp/UserGuide.html";
    private static final String SOURCE_ACADEMY_URL = "https://sourceacademy.nus.edu.sg/login";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private MissionListPanel missionListPanel;
    private QuestListPanel questListPanel;
    private ConsultationListPanel consultationListPanel;
    private MasteryCheckListPanel masteryCheckListPanel;
    private TaskListPanel taskListPanel;

    @FXML
    private Tab studentTab;

    @FXML
    private Tab missionTab;

    @FXML
    private Tab questTab;

    @FXML
    private Tab consultationTab;

    @FXML
    private Tab masteryCheckTab;

    @FXML
    private Tab taskTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane studentListPanelPlaceholder;

    @FXML
    private StackPane missionListPanelPlaceholder;

    @FXML
    private StackPane consultationListPanelPlaceholder;

    @FXML
    private StackPane masteryCheckListPanelPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane questListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Label date;

    @FXML
    private Label greeting;

    @FXML
    private Label week;

    @FXML
    private Label topic;
    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        setDate();

        setGreeting();

        setWeekAndTopic();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        studentListPanelPlaceholder.getChildren().add(studentListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        String loginMessage = getLoginMessage();
        resultDisplay.setFeedbackToUser(loginMessage);

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        missionListPanel = new MissionListPanel(logic.getFilteredMissionList());
        missionListPanelPlaceholder.getChildren().add(missionListPanel.getRoot());

        questListPanel = new QuestListPanel(logic.getFilteredQuestList());
        questListPanelPlaceholder.getChildren().add(questListPanel.getRoot());

        consultationListPanel = new ConsultationListPanel(logic.getFilteredConsultationList());
        consultationListPanelPlaceholder.getChildren().add(consultationListPanel.getRoot());

        masteryCheckListPanel = new MasteryCheckListPanel(logic.getFilteredMasteryCheckList());
        masteryCheckListPanelPlaceholder.getChildren().add(masteryCheckListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the current date.
     */
    private void setDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String formattedDate = currentDate.format(formatter);
        date.setText(formattedDate);
    }

    /**
     * Sets the greeting.
     */
    private void setGreeting() {
        greeting.textProperty().bind(logic.getGreeting());
    }

    /**
     * Sets the week and topic
     */
    private void setWeekAndTopic() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate semesterStartDate = LocalDate.parse(SEMESTER_START_DATE, formatter);
        LocalDate currentDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(semesterStartDate, currentDate);

        int weekNumber = (int) daysBetween / 7;

        Topic topic = new Topics().getTopic(weekNumber);

        week.setText("Week " + weekNumber + ": ");

        if (topic.isEmpty()) {
            this.topic.setText("No topic");
        } else {
            this.topic.setText(topic.getOutline());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        try {
            logic.saveToStorage();
        } catch (CommandException e) {
            logger.info("Unable to save to file");
            resultDisplay.setFeedbackToUser("Unable to save to file");
        }

        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Opens Source Academy in the browser.
     */
    @FXML
    private void handleSourceAcademy() {
        try {
            Desktop.getDesktop().browse(URI.create(SOURCE_ACADEMY_URL));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Opens User Guide in the browser.
     */
    @FXML
    private void handleUserGuide() {
        try {
            Desktop.getDesktop()
                    .browse(URI.create(USER_GUIDE_URL));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public StudentListPanel getStudentListPanel() {
        return studentListPanel;
    }

    public MissionListPanel getMissionListPanel() {
        return missionListPanel;
    }

    private String getLoginMessage() {
        StringBuilder sb = new StringBuilder();

        if (!logic.hasUsername()) {
            sb.append("Please edit your username using `edit -l u/[USERNAME]` and restart to fetch missions/quests");
            sb.append("\n");
        } else if (!logic.hasPassword()) {
            sb.append("Please edit your password using `edit -l p/[PASSWORD]` and restart to fetch missions/quests");
        } else {
            sb.append("Welcome to Jarvis! enter 'help' to see the amazing things you can do with me.");
        }
        return sb.toString();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.jarvis.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            CommandTargetFeature commandTargetFeature = commandResult.getCommandTargetFeature();
            SingleSelectionModel<Tab> tabSelector = tabPane.getSelectionModel();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandTargetFeature == CommandTargetFeature.Students) {
                tabSelector.select(studentTab);
            } else if (commandTargetFeature == CommandTargetFeature.Missions) {
                tabSelector.select(missionTab);
            } else if (commandTargetFeature == CommandTargetFeature.Quest) {
                tabSelector.select(questTab);
            } else if (commandTargetFeature == CommandTargetFeature.Consultations) {
                tabSelector.select(consultationTab);
            } else if (commandTargetFeature == CommandTargetFeature.Tasks) {
                tabSelector.select(taskTab);
            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
