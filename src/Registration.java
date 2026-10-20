
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Student
 */
class Login {

    private String username;
    private String password;
    private String cellphoneNumber;

    public Login(String username, String password, String cellphoneNumber) {
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
    }

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapital && hasNumber && hasSpecialCharacter;
    }

    public boolean checkCellPhoneNumber(String cellphoneNumber) {
        return cellphoneNumber.matches("^\\+27\\d{10}$");
    }

    public String registerUser() {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellphoneNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain the international country code.";
        }

        return "User successfully registered.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome, it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

public class Registration {

    public static void main(String[] args) {
        

        Scanner hope = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String firstName = hope.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = hope.nextLine();

        System.out.print("Enter your username: ");
        String username = hope.nextLine();

        System.out.print("Enter your password: ");
        String password = hope.nextLine();

        System.out.print("Enter your cellphone number: ");
        String cellphoneNumber = hope.nextLine();

        Login login = new Login(username, password, cellphoneNumber);

        String registrationMessage = login.registerUser();
        System.out.println(registrationMessage);

        if (registrationMessage.equals("User successfully registered.")) {

            System.out.println("\n--- LOGIN ---");                 System.out.println("Welcome to QuickChat.");

                System.out.print("How many messages do you want to enter? ");
                int numMessages = Integer.parseInt(hope.nextLine());
                int messagesEntered = 0;
                int option = 0;

                while (option != 3) {
                    System.out.println("\n1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    option = Integer.parseInt(hope.nextLine());

                    if (option == 1) {
                        if (messagesEntered >= numMessages) {
                            System.out.println("You have already entered all " + numMessages + " messages.");
                        } else {
                            for (int i = messagesEntered; i < numMessages; i++) {

                                System.out.print("Enter recipient cell number: ");
                                String recipient = hope.nextLine();
                                Message check = new Message(i, recipient, "x");
                                while (!check.checkRecipientCell().equals("Cell phone number successfully captured.")) {
                                    System.out.println(check.checkRecipientCell());
                                    System.out.print("Enter recipient cell number: ");
                                    recipient = hope.nextLine();
                                    check = new Message(i, recipient, "x");
                                }

                                System.out.print("Enter your message: ");
                                String text = hope.nextLine();
                                while (text.length() > 250) {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                    System.out.print("Enter your message: ");
                                    text = hope.nextLine();
                                }
                                System.out.println("Message sent");

                                Message msg = new Message(i, recipient, text);

                                System.out.println("1) Send Message  2) Disregard Message  3) Store Message to send later");
                                int choice = Integer.parseInt(hope.nextLine());
                                System.out.println(msg.SentMessage(choice));

                                if (choice == 1) {
                                    msg.addToSent();
                                    System.out.println(msg.getDetails());
                                } else if (choice == 3) {
                                    msg.storeMessage();
                                }
                            }
                            messagesEntered = numMessages;
                            System.out.println("Total messages sent: " + Message.returnTotalMessages());
                        }
                    } else if (option == 2) {
                        System.out.println("Coming Soon.");
                    } else if (option != 3) {
                        System.out.println("Invalid option.");
                    }
                }

            System.out.print("Enter your username: ");
            String loginUsername = hope.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = hope.nextLine();

            if (login.loginUser(loginUsername, loginPassword)) {

                System.out.println(
                        "Welcome " + firstName + " " + lastName
                        + ", it is great to see you again."
                );

            } else {

                System.out.println(
                        "Username or password incorrect, please try again."
                );
                                System.out.println(
                        "Welcome " + firstName + " " + lastName
                        + ", it is great to see you again."
                );
            }
        }
    }
}
