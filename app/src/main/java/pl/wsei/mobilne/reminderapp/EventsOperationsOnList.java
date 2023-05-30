package pl.wsei.mobilne.reminderapp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EventsOperationsOnList {

    private List<Event> passedEvents = new ArrayList<>();

    public boolean addToEvent() {
        Event testEvent = new Event("Event", "xxx", "2023-05-31", new Time(10, 20, 10), "Birthday", "no");
        passedEvents.add(testEvent);

        return testEvent.getName().equals("Event");
    }

    public boolean removeEvent(Event event) {
        boolean removed = passedEvents.remove(event);
        return removed;
    }

    public boolean editEvent(Event event, String newName, String newDescription) {
        int index = passedEvents.indexOf(event);
        if (index != -1) {
            Event updatedEvent = passedEvents.get(index);
            updatedEvent.getName();
            updatedEvent.getDescription();
            return true;
        }
        return false;
    }

    public List<Event> getPassedEvents() {
        return passedEvents;
    }
}
