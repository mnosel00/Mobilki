package pl.wsei.mobilne.reminderapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.List;

public class EventsListOperation {

    private EventsOperationsOnList addEventToList;

    @Before
    public void setup() {
        addEventToList = new EventsOperationsOnList();
    }

    @Test
    public void testAddToEvent() {
        // Act
        boolean result = addEventToList.addToEvent();

        // Assert
        assertTrue(result);

        List<Event> passedEvents = addEventToList.getPassedEvents();
        assertEquals(1, passedEvents.size());

        Event addedEvent = passedEvents.get(0);
        assertEquals("Event", addedEvent.getName());
        assertEquals("xxx", addedEvent.getDescription());
        assertEquals("2023-05-31", addedEvent.getDate());
        assertEquals(new Time(10, 20, 10), addedEvent.getTime());
        assertEquals("Birthday", addedEvent.getType());
        assertEquals("no", addedEvent.getEventRepeat());
    }

    @Test
    public void testRemoveEvent() {
        // Arrange
        Event testEvent = new Event("Event", "xxx", "2023-05-31", new Time(10, 20, 10), "Birthday", "no");
        addEventToList.getPassedEvents().add(testEvent);

        // Act
        boolean result = addEventToList.removeEvent(testEvent);

        // Assert
        assertTrue(result);
        assertTrue(addEventToList.getPassedEvents().isEmpty());
    }

    @Test
    public void testEditEvent() {
        // Arrange
        Event testEvent = new Event("Event", "xxx", "2023-05-31", new Time(10, 20, 10), "Birthday", "no");
        addEventToList.getPassedEvents().add(testEvent);

        // Act
        boolean result = addEventToList.editEvent(testEvent, "New Name", "New Description");

        // Assert
        assertTrue(result);

        Event editedEvent = addEventToList.getPassedEvents().get(0);
        assertEquals("New Name", editedEvent.getName());
        assertEquals("New Description", editedEvent.getDescription());
    }

    @Test
    public void testEditEvent_NotFound() {
        // Arrange
        Event testEvent = new Event("Event", "xxx", "2023-05-31", new Time(10, 20, 10), "Birthday", "no");

        // Act
        boolean result = addEventToList.editEvent(testEvent, "New Name", "New Description");

        // Assert
        assertFalse(result);
    }

}
