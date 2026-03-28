package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.GUI.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CreateEventController {

    @FXML
    private TextField txtEnterEventName;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextArea txtAreaDescription;
    @FXML
    private Button editBtn;
    @FXML
    private ComboBox<String> cBoxTimeSelectEnd;
    @FXML
    private DatePicker txtEndDate;
    @FXML
    private ComboBox<String> cBoxTimeSelectStart;
    @FXML
    private DatePicker txtStartDate;
    @FXML
    private TextField txtTicketsAvailable;

    private EventModel eventModel;
    private Event editingEvent = null;
    private boolean editMode = false;

    public void initialize() {

        initTime(cBoxTimeSelectEnd);

        initTime(cBoxTimeSelectStart);
    }

    private void initTime(ComboBox<String> cBoxTimeSelectEnd) {
        cBoxTimeSelectEnd.getItems().addAll(
                "00.00","00.30","01.00","01.30","02.00","02.30","03.00","03.30","04.00","04.30","05.00","05.30",
                "06.00","06.30","07.00","07.30","08.00","08.30","09.00","09.30","10.00","10.30","11.00","11.30",
                "12.00","12.30","13.00","13.30","14.00","14.30","15.00","15.30","16.00","16.30","17.00","17.30",
                "18.00","18.30","19.00","19.30","20.00","20.30","21.00","21.30","22.00","22.30","23.00","23.30"
        );

        cBoxTimeSelectEnd.setOnAction(e -> {
            String selectedEnd = cBoxTimeSelectEnd.getSelectionModel().getSelectedItem();
            cBoxTimeSelectEnd.setPromptText(selectedEnd);
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
        }
    }

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) {

        // ✅ safety check (helps debugging if something breaks again)
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

        try {
            if (editMode && editingEvent != null) {

                editingEvent.setName(name);
                editingEvent.setStartDate(startDate);
                editingEvent.setEndDate(endDate);
                editingEvent.setStartTime(startTime);
                editingEvent.setEndTime(endTime);
                editingEvent.setLocation(location);
                editingEvent.setDescription(description);
                editingEvent.setTicketsAvailable(ticketsAvailable);
                //editingEvent.setIsDeleted(false);

                eventModel.updateEvent(editingEvent);

            } else {
                Event newEvent = new Event(0, name, description, location, ticketsAvailable, startDate, endDate, startTime, endTime, false);
                eventModel.createEvent(newEvent);
            }

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
}