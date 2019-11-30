package com.freshman4000;

import com.freshman4000.models.Person;
import org.hibernate.Session;

import java.util.*;

import com.freshman4000.models.Event;
import com.freshman4000.utilities.HibUtil;

public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        if (args[0].equals("store")) {
            mgr.createAndStoreEvent("My Event", new Date());
        } else if (args[0].equals("list")) {
            List events = mgr.listEvents();
            for (int i = 0; i < events.size(); i++) {
                Event theEvent = (Event) events.get(i);
                System.out.println(
                        "Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate()
                );
            }
        } else if (args[0].equals("addpersontoevent")) {
            Long eventId = mgr.createAndStoreEvent("My Event", new Date());
            Long personId = mgr.createAndStorePerson(25, "Bar");
            mgr.addPersonToEvent(personId, eventId);
            System.out.println("Added person " + personId + " to event " + eventId);
        }

        HibUtil.getSessionFactory().close();
    }

    private Long createAndStoreEvent(String title, Date theDate) {
        Session session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);

        session.getTransaction().commit();
        return theEvent.getId();
    }
    private Long createAndStorePerson(int age, String firstName) {
        Session session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = new Person();
        aPerson.setAge(age);
        aPerson.setFirstname(firstName);
        session.save(aPerson);

        session.getTransaction().commit();
        return aPerson.getId();
    }
    private List<Event> listEvents() {
        Session session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Event> result = (List<Event>)session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }
    private void addPersonToEvent(Long personId, Long eventId) {
        Session session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);
        aPerson.getEvents().add(anEvent);

        session.getTransaction().commit();
    }
    private void addEmailToPerson(Long personId, String emailAddress) {
        Session session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        // adding to the emailAddress collection might trigger a lazy load of the collection
        aPerson.getEmailAddresses().add(emailAddress);

        session.getTransaction().commit();
    }
}