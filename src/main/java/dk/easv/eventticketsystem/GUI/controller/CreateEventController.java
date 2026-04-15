package dk.easv.eventticketsystem.GUI.controller;

//project imports
import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.UserSession;
import dk.easv.eventticketsystem.GUI.model.EventModel;
import dk.easv.eventticketsystem.GUI.model.UserModel;

//java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateEventController {
    @FXML private TextField txtEnterEventName;
    @FXML private TextField txtLocation;
    @FXML private TextArea txtAreaDescription;
    @FXML private Button editBtn;
    @FXML private ComboBox<String> cBoxTimeSelectEnd;
    @FXML private DatePicker txtEndDate;
    @FXML private ComboBox<String> cBoxTimeSelectStart;
    @FXML private DatePicker txtStartDate;
    @FXML private TextField txtTicketsAvailable;
    @FXML private ListView<User> listViewCoordinators;

    //instantiate
    private EventModel eventModel;
    private UserModel userModel;
    private Event editingEvent = null;
    private boolean editMode = false;
    private List<User> previouslyAssigned = new ArrayList<>();

    public void initialize() {
       //set time combobox
        initTime(cBoxTimeSelectEnd);
        initTime(cBoxTimeSelectStart);

        //display full name
        listViewCoordinators.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getFullName());
            }
        });

        //so you can choose more coordinators
        listViewCoordinators.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            userModel = new UserModel();
            // only show coordinators in the list
            for (User u : userModel.getObservableUsers()) {
                if (u.getRole() == Role.COORDINATOR) {
                    listViewCoordinators.getItems().add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        applyRolePermissions();
    }

    private void initTime(ComboBox<String> comboBox) {
        comboBox.getItems().addAll(
                "00.00","00.30","01.00","01.30","02.00","02.30","03.00","03.30","04.00","04.30","05.00","05.30",
                "06.00","06.30","07.00","07.30","08.00","08.30","09.00","09.30","10.00","10.30","11.00","11.30",
                "12.00","12.30","13.00","13.30","14.00","14.30","15.00","15.30","16.00","16.30","17.00","17.30",
                "18.00","18.30","19.00","19.30","20.00","20.30","21.00","21.30","22.00","22.30","23.00","23.30"
        );
        comboBox.setOnAction(e -> {
            String selected = comboBox.getSelectionModel().getSelectedItem();
            comboBox.setPromptText(selected);
        });
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setEditingEvent(Event event) {
        if (event != null) {
            this.editingEvent = event;
            this.editMode = true;

            txtEnterEventName.setText(event.getName());
            txtStartDate.setValue(event.getStartDate());
            txtEndDate.setValue(event.getEndDate());
            txtLocation.setText(event.getLocation());
            txtAreaDescription.setText(event.getDescription());
            txtTicketsAvailable.setText(String.valueOf(event.getTicketsAvailable()));
            cBoxTimeSelectStart.setValue(event.getStartTime());
            cBoxTimeSelectEnd.setValue(event.getEndTime());
            editBtn.setText("Edit Event");
            applyRolePermissions();


            try {
                previouslyAssigned = eventModel.getCoordinatorsForEvent(event.getId());
                for (User u : listViewCoordinators.getItems()) {
                    for (User assigned : previouslyAssigned) {
                        if (u.getUserId() == assigned.getUserId()) {
                            listViewCoordinators.getSelectionModel().select(u);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) {
        if (eventModel == null) {
            System.out.println("ERROR: eventModel is null");
            return;
        }

        String name = txtEnterEventName.getText().trim();
        LocalDate startDate = txtStartDate.getValue();
        LocalDate endDate = txtEndDate.getValue();
        String startTime = cBoxTimeSelectStart.getValue();
        String endTime = cBoxTimeSelectEnd.getValue();
        String location = txtLocation.getText().trim();
        String description = txtAreaDescription.getText().trim();
        String ticketsText = txtTicketsAvailable.getText().trim();

        if (name.isEmpty() || startDate == null || endDate == null || location.isEmpty() || startTime == null || endTime == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Please fill in all required fields");
            alert.showAndWait();
            return;
        }
        if (startDate.equals(endDate) && endTime.compareTo(startTime) <= 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Time");
            alert.setHeaderText("End time cannot be before or equal to start time on the same day");
            alert.showAndWait();
            return;
        }

        int ticketsAvailable;
        try {
            ticketsAvailable = Integer.parseInt(ticketsText);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number");
            return;
        }

        if (ticketsAvailable <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Value");
            alert.setHeaderText("Tickets available must be a positive number");
            alert.showAndWait();
            return;
        }
        if (endDate.isBefore(startDate)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("End date cannot be before start date");
            alert.showAndWait();
            return;
        }

        try {
            Event savedEvent;

            Role role = UserSession.getInstance().getCurrentUser().getRole();

            if (editMode && editingEvent != null) {

                if (role == Role.COORDINATOR) {
                    editingEvent.setName(name);
                    editingEvent.setStartDate(startDate);
                    editingEvent.setEndDate(endDate);
                    editingEvent.setStartTime(startTime);
                    editingEvent.setEndTime(endTime);
                    editingEvent.setLocation(location);
                    editingEvent.setDescription(description);
                    editingEvent.setTicketsAvailable(ticketsAvailable);

                    eventModel.updateEvent(editingEvent);
                }

                savedEvent = editingEvent;

            } else {
                Event newEvent = new Event(0, name, description, location, ticketsAvailable, startDate, endDate, startTime, endTime, false);
                savedEvent = eventModel.createEvent(newEvent);
            }

            List<User> selectedNow = new ArrayList<>(listViewCoordinators.getSelectionModel().getSelectedItems());

            for (User u : selectedNow) {
                boolean wasAssigned = previouslyAssigned.stream()
                        .anyMatch(p -> p.getUserId() == u.getUserId());
                if (!wasAssigned) {
                    eventModel.addCoordinatorToEvent(savedEvent.getId(), u.getUserId());
                }
            }

            for (User u : previouslyAssigned) {
                boolean stillSelected = selectedNow.stream()
                        .anyMatch(s -> s.getUserId() == u.getUserId());
                if (!stillSelected) {
                    eventModel.removeCoordinatorFromEvent(savedEvent.getId(), u.getUserId());
                }
            }

            List<User> updatedCoords = eventModel.getCoordinatorsForEvent(savedEvent.getId());
            String names = updatedCoords.stream()
                    .map(User::getFullName)
                    .collect(java.util.stream.Collectors.joining(", "));
            savedEvent.setAssignedCoordinators(names);

            eventModel.refreshCoordinators(savedEvent);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Saving Event");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    //admin and coordinator different permissions to edit events
    private void applyRolePermissions() {
        Role role = UserSession.getInstance().getCurrentUser().getRole();

        if (role == Role.ADMIN) {
            txtEnterEventName.setEditable(false);
            txtLocation.setEditable(false);
            txtAreaDescription.setEditable(false);
            txtStartDate.setDisable(true);
            txtEndDate.setDisable(true);
            cBoxTimeSelectStart.setDisable(true);
            cBoxTimeSelectEnd.setDisable(true);
            txtTicketsAvailable.setEditable(false);

            listViewCoordinators.setDisable(false);

            editBtn.setText("Assign Coordinators");
        }

        if (role == Role.COORDINATOR) {
            txtEnterEventName.setEditable(true);
            txtLocation.setEditable(true);
            txtAreaDescription.setEditable(true);
            txtStartDate.setDisable(false);
            txtEndDate.setDisable(false);
            cBoxTimeSelectStart.setDisable(false);
            cBoxTimeSelectEnd.setDisable(false);
            txtTicketsAvailable.setEditable(true);

            listViewCoordinators.setDisable(false);

            if (editMode) {
                editBtn.setText("Edit Event");
            } else {
                editBtn.setText("Create Event");
            }
        }
    }
}