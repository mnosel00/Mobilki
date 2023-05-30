package pl.wsei.mobilne.reminderapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

public class EventTest {
    private Event event;

    @Before
    public void setUp() {
        event = new Event("TestName", "TestDescription", "2025-01-01 00:00:00", "TestType", "TestRepeat");
    }

    @Test
    public void testName() {
        assertEquals("TestName", event.getName());
    }

    @Test
    public void testDescription() {
        assertEquals("TestDescription", event.getDescription());
    }

    @Test
    public void testDate() {
        assertEquals("2025-01-01 00:00:00", event.getDate());
    }

    @Test
    public void testType() {
        assertEquals("TestType", event.getType());
    }

    @Test
    public void testEventRepeat() {
        assertEquals("TestRepeat", event.getEventRepeat());
    }

    @Test
    public void testIsOn() {
        assertTrue(event.isOn());
    }
}