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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
