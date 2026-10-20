import org.junit.Test;
import static org.junit.Assert.*;
public class MessageTest {

    private static final String TEST_ID = "0012345678";
    private static final String MSG1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String MSG2 = "Hi Keegan, did you receive the payment?";

    // Message length: success
    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message(TEST_ID, 0, "+27718693002", MSG1);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    // Message length: failure (260 characters is 10 over the limit)
    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(260);
        Message m = new Message(TEST_ID, 0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                m.checkMessageLength());
    }

    // Recipient: success
    @Test
    public void testRecipientSuccess() {
        Message m = new Message(TEST_ID, 0, "+27718693002", MSG1);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    // Recipient: failure (no international code)
    @Test
    public void testRecipientFailure() {
        Message m = new Message(TEST_ID, 1, "08575975889", MSG2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                m.checkRecipientCell());
    }

    // Hash: test case 1
    @Test
    public void testHashMessage1() {
        Message m = new Message(TEST_ID, 0, "+27718693002", MSG1);
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    // Hash: remaining messages tested in a loop, as the paper requires
    @Test
    public void testHashLoop() {
        String[] texts = {MSG1, MSG2};
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};
        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(TEST_ID, i, "+27718693002", texts[i]);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    // Message ID is created
    @Test
    public void testMessageIDCreated() {
        Message m = new Message(1, "+27718693002", MSG1);
        assertEquals("Message ID generated: " + m.getMessageID(), m.messageIDResult());
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageID().length());
    }

    // MessageSent: all three options
    @Test
    public void testSentMessageOptions() {
        Message m = new Message(TEST_ID, 0, "+27718693002", MSG1);
        assertEquals("Message successfully sent.", m.SentMessage(1));
        assertEquals("Press 0 to delete the message.", m.SentMessage(2));
        assertEquals("Message successfully stored.", m.SentMessage(3));
    }

    // Total messages sent goes up by one when a message is sent
    @Test
    public void testReturnTotalMessages() {
        int before = Message.returnTotalMessages();
        new Message(TEST_ID, 0, "+27718693002", MSG1).addToSent();
        assertEquals(before + 1, Message.returnTotalMessages());
    }
}
