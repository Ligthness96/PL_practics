package com.example.practic.models;

public class Event {
    private String id;

    private String date, organizer, place, participant, direction, name;
//    private String eventName,

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }
//    public String getEventName() {
//    return eventName;
//}

//    public void setEventName(String eventName) {
//        this.eventName = eventName;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getName(){return name;}

    public void setName(String name) {this.name = name;}

//    public Event(long id, String date, String organizer, String place, String participant, String direction) {
//        this.id = id;
//        this.date = date;
//        this.organizer = organizer;
//        this.place = place;
//        this.participant = participant;
//        this.direction = direction;
//    }
    public Event(String id, String date, String organizer, String place, String direction, String name) {
        this.id = id;
        this.date = date;
        this.organizer = organizer;
        this.place = place;
//        this.participant = participant;
        this.direction = direction;
        this.name = name;
    }

    public Event() {}

}
