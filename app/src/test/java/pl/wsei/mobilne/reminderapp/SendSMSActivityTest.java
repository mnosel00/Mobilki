package pl.wsei.mobilne.reminderapp;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class SendSMSActivityTest {
    @Test
    public void testMessageTextSetFromIntent() {
        String testMessage = "Test message";

        Intent intent = new Intent();
        intent.putExtra("dataToBePassed", testMessage);

        ActivityController<SendSMSActivity> controller = Robolectric.buildActivity(SendSMSActivity.class, intent);
        SendSMSActivity activity = controller.create().get();

        EditText messageText = activity.findViewById(R.id.edit_text_message);
        assertEquals(testMessage, messageText.getText().toString());
    }
}