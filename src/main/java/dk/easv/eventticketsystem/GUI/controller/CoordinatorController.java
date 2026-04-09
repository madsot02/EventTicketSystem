package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BLL.utils.UserSession;
import dk.easv.eventticketsystem.GUI.model.EventModel;
import dk.easv.eventticketsystem.GUI.utils.TableViewSwitcher;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CoordinatorController {

    public TableColumn<Event, String> colAssignedCoordinators;
    @FXML
    private TableView<Event> tblEvents;
    @FXML
    private TableColumn<Event, String> colName;
    @FXML
    private TableColumn<Event, Integer> colTicketsAvailable;
    @FXML
    private TableColumn<Event, String> colLocation;
    @FXML
    private TableColumn<Event, LocalDate> colStartDate;
    @FXML
    private TableColumn<Event, LocalDate> colEndDate;
    @FXML
    private TableColumn<Event, String> colStartTime;
    @FXML
    private TableColumn<Event, String> colEndTime;
    @FXML
    private TextArea txtAreaDescriptionMain;

    private EventModel eventModel;

    private TableViewSwitcher status;

    private FilteredList<Event> filteredEvents;

    public void initialize() throws Exception {
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    colTicketsAvailable.setCellValueFactory(new PropertyValueFactory<>("ticketsAvailable"));
    colAssignedCoordinators.setCellValueFactory(new PropertyValueFactory<>("assignedCoordinators"));



        try {
            eventModel = new EventModel();
            filteredEvents = new FilteredList<>(eventModel.getObservableEvents());
            filteredEvents.setPredicate(Event::isActive);

            tblEvents.setItems(filteredEvents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setModel(EventModel eventModel){
        this.eventModel = eventModel;
    }

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateEventView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Create Event");
        stage.setScene(scene);
        
        CreateEventController eventController = fxmlLoader.getController();
        eventController.setEventModel(eventModel);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleRemoveEvent(ActionEvent actionEvent) {
        Event selectedEvent = tblEvents.getSelectionModel().getSelectedItem();

        if(selectedEvent == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event Selected");
            alert.setHeaderText("Please select an event to delete");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Are you sure you want to delete this event");
        confirm.setContentText(selectedEvent.getName());
        if(confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK){
            return;
        }
        try{
            eventModel.deleteEvent(selectedEvent);

            //filteredEvents.setPredicate(filteredEvents.getPredicate());

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        UserSession session = UserSession.getInstance();
        session.clear();

        Stage coordianatorStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        coordianatorStage.close();
    }

    @FXML
    private void handleCreateTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEditEvent(ActionEvent actionEvent) throws IOException {
        Event selectedEvent = tblEvents.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateEventView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Edit Event");
        stage.setScene(scene);

        CreateEventController controller = loader.getController();
        controller.setEventModel(eventModel);
        controller.setEditingEvent(selectedEvent);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleActiveTableView(ActionEvent actionEvent) {
        filteredEvents.setPredicate(Event::isActive);
    }

    @FXML
    private void handleArchivedTableView(ActionEvent actionEvent) {
        filteredEvents.setPredicate(Event::isArchived);
    }

    @FXML
    private void handleDeletedTableView(ActionEvent actionEvent) {
        filteredEvents.setPredicate(Event::getIsDeleted);
    }
}