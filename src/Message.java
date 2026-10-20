import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    // Shared by all messages: holds the details of every message sent while the program runs
    private static ArrayList<String> sentMessages = new ArrayList<>();
        // Holds the JSON text of every message the user chose to store
    private static ArrayList<String> storedMessages = new ArrayList<>();
    // Constructor: the ID is generated automatically when a message is created
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }

     // Generates a random ten-digit number as text (leading zeros allowed)
    private String generateMessageID() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 10_000_000_000L);
        return String.format("%010d", number);
    }

    // Paper: ensures the message ID is not more than ten characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String getMessageID() {
        return messageID;
    }
   // Paper: recipient must have an international code and no more than ten digits after it
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }    // Hash = first two ID digits : message number : first word + last word, all caps
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }    // Paper: lets the user choose to send, disregard or store the message
    // choice: 1 = Send, 2 = Disregard, 3 = Store
    public String SentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        }
        return "Invalid option.";
    }
 
// Paper: message must not exceed 250 characters
    public String checkMessageLength() {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }
        // Paper order: Message ID, Message Hash, Recipient, Message
    public String getDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Call this when the user picks "Send Message"
    public void addToSent() {
        sentMessages.add(getDetails());
    }

    // Paper: returns all the messages sent while the program is running
    public static String printMessages() {
        StringBuilder all = new StringBuilder();
        for (String details : sentMessages) {
            all.append(details).append("\n\n");
        }
        return all.toString();
    }

    // Paper: returns the total number of messages sent
    public static int returnTotalMessages() {
        return sentMessages.size();
    }
        // Turns this message into one JSON object (quotes inside the text are escaped)
    private String toJson() {
        String safeText = messageText.replace("\\", "\\\\").replace("\"", "\\\"");
        return "  {\"messageID\": \"" + messageID + "\", "
                + "\"messageHash\": \"" + createMessageHash() + "\", "
                + "\"recipient\": \"" + recipient + "\", "
                + "\"message\": \"" + safeText + "\"}";
    }

    // Paper: stores the message in a JSON file for later
    public String storeMessage() {
        storedMessages.add(toJson());
        try (FileWriter writer = new FileWriter("storedMessages.json")) {
            writer.write("[\n" + String.join(",\n", storedMessages) + "\n]");
        } catch (IOException e) {
            return "Message could not be stored: " + e.getMessage();
        }
        return "Message successfully stored.";
    }    // Used by unit tests so the ID is known in advance
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    // Paper: test expects "Message ID generated: <Message ID>"
    public String messageIDResult() {
        return "Message ID generated: " + messageID;
    }
}