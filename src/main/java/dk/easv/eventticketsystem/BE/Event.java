package dk.easv.eventticketsystem.BE;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private String description;
    private String location;
    private int ticketsAvailable;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;
    private boolean isDeleted;
    private String assignedCoordinators = "";
    private double price;

    public Event(int id, String name, String description, String location, int ticketsAvailable, LocalDate startDate, LocalDate endDate, String startTime, String endTime, boolean isDeleted, double price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.ticketsAvailable = ticketsAvailable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDeleted = isDeleted;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getIsDeleted(){return isDeleted;}

    public void setIsDeleted(boolean isDeleted){this.isDeleted = isDeleted;}

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public String getAssignedCoordinators() {
        return assignedCoordinators;
    }

    public void setAssignedCoordinators(String assignedCoordinators) {
        this.assignedCoordinators = assignedCoordinators;
    }

    public boolean isActive(){
        return !isDeleted && endDate != null && !endDate.isBefore(LocalDate.now());
    }
    public boolean isArchived() {
        return !isDeleted && endDate != null && endDate.isBefore(LocalDate.now());
    }
}
