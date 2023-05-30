package pl.wsei.mobilne.reminderapp;

import static org.mockito.Mockito.verify;

import android.widget.EditText;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EventsActivityFormTest {

    @Mock
    private EditText mEventNameEditText;
    @Mock
    private EditText mEventDescriptionEditText;
    @Mock
    private EditText mEventDateEditText;
    @Mock
    private EditText mEventTimeEditText;
    @Mock
    private Spinner mEventTypeSpinner;
    @Mock
    private Spinner mEventRepeatSinner;

    private EventsActivity yourClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        yourClass = new EventsActivity();
    }

    @Test
    public void testClearFields() {
        // Act
        yourClass.clearFields();

        // Assert
        verify(mEventNameEditText).setText("");
        verify(mEventDescriptionEditText).setText("");
        verify(mEventDateEditText).setText("");
        verify(mEventTimeEditText).setText("");
        verify(mEventTypeSpinner).setSelection(0);
        verify(mEventRepeatSinner).setSelection(0);
    }
}