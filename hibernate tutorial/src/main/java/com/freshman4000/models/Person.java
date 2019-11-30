package com.freshman4000.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class Person {

    private Long id;
    private int age;
    private String firstname;
    private String lastname;
    private Set<Event> events = new HashSet<>();
    private Set<String> emailAddresses = new HashSet<>();

    public Person() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(Set<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
    // Accessor methods for all properties, private setter for 'id'

}
