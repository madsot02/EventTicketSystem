package dk.easv.eventticketsystem.BE;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private String description;
    private String location;
    private int createdBy;
    private int ticketsAvailable;
    private LocalDate date;
    private int time;

    public Event(int id, String name, String description, String location, int createdBy, int ticketsAvailable, LocalDate date, int time){
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.createdBy = createdBy;
        this.ticketsAvailable = ticketsAvailable;
        this.date = date;
        this.time = time;
    }

    private int getId() {
        return id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    private int getCreatedBy() {
        return createdBy;
    }

    private void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    private int getTicketsAvailable() {
        return ticketsAvailable;
    }

    private void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    private LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    private int getTime() {
        return time;
    }

    private void setTime(int time) {
        this.time = time;
    }
}
