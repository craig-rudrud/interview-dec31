/*
A simple program to generate greeting messages for hotel guests.
@author Craig Rudrud
@date December 31, 2018
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class GreetingMessage {
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> companies = readJSON(GreetingMessage.class.getResourceAsStream("Companies.json"), "company");
        ArrayList<String> guests = readJSON(GreetingMessage.class.getResourceAsStream("Guests.json"), "firstName");
        ArrayList<Integer> roomNumbers = readJSON(GreetingMessage.class.getResourceAsStream("Guests.json"), "reservation", "roomNumber");
        ArrayList<String> greetings = readJSON(GreetingMessage.class.getResourceAsStream("Messages.json"), "greeting");

        Scanner input = new Scanner(System.in);

        chooser(companies, "company");
        int choice = input.nextInt();
        String company = companies.get(choice-1);

        chooser(guests, "guest");
        choice = input.nextInt();
        String firstName = guests.get(choice-1);
        int roomNumber = roomNumbers.get(choice-1);

        System.out.print("\nUse the standard greeting or a custom greeting? ");
        Scanner input2 = new Scanner(System.in);
        String choice2 = input2.nextLine();
        if(choice2.toLowerCase().contains("standard")) {
            printMessage(greetings, firstName, company, roomNumber);
        } else if(choice2.toLowerCase().contains("custom")) {
            System.out.print("Enter a custom message including \'greeting\', \'firstName\', \'company\', and \'roomNumber\': ");
            String message = input2.nextLine();
            printMessage(greetings, firstName, company, roomNumber, message);
        }
    }

    /*
    Lets the user make a choice.
    @param list an ArrayList containing different choices
    @param type what the user will be choosing. For example, the type might be the company/hotel or the guest.
     */
    private static void chooser(ArrayList<String> list, String type) {
        System.out.println("\nChoose a " + type + ": ");
        for(int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
        System.out.print("\nEnter a choice: ");
    }

    /*
    Gets a greeting based on the local time of day.
    @param greetings an ArrayList of greetings chosen from the Messages.json file.
     */
    private static String getGreeting(ArrayList<String> greetings) {
        int hour = LocalTime.now().getHour();
        if(5 <= hour && hour < 12) {
            return greetings.get(0); // Good morning
        } else if (12 <= hour && hour < 18) {
            return greetings.get(1); // Good afternoon
        } else {
            return greetings.get(2); // Good evening
        }
    }

    /*
    Prints a standard message.
    @param greetings an ArrayList of greetings chosen from the Messages.json file.
    @param firstName the first name of the guest
    @param company the name of the hotel
    @param roomNumber the room number of the guest's reservation
     */
    private static void printMessage(ArrayList<String> greetings, String firstName, String company, int roomNumber) {
        System.out.println('\n' + getGreeting(greetings) + " " + firstName + ", and welcome to " + company + "! Room "
                + roomNumber + " is now ready for you. Enjoy your stay, and let us know if you need anything.");
    }

    /*
    Prints a custom message.
    @param greetings an ArrayList of greetings chosen from the Messages.json file
    @param firstName the first name of the guest
    @param company the name of the hotel
    @param roomNumber the room number of the guest's reservation
    @param message the custom message to use. It provides a framework for building the message using specific keywords
    provided within it
     */
    private static void printMessage(ArrayList<String> greetings, String firstName, String company, int roomNumber, String message) {
        StringBuilder sb = new StringBuilder();
        String[] keywords = message.split(" ");
        for(int i = 0; i < keywords.length; i++) {
            if(keywords[i].contains("greeting")) {
                keywords[i] = keywords[i].replace("greeting", getGreeting(greetings));
            } else if(keywords[i].contains("firstName")) {
                keywords[i] = keywords[i].replace("firstName", firstName);
            } else if(keywords[i].contains("company")) {
                keywords[i] = keywords[i].replace("company", company);
            } else if(keywords[i].contains("roomNumber")) {
                keywords[i] = keywords[i].replace("roomNumber", String.valueOf(roomNumber));
            }
            sb.append(keywords[i]).append(" ");
        }
        System.out.println('\n' + sb.toString());
    }

    /*
    Reads a JSON file and returns an ArrayList of type String.
    @param jsonFile the JSON file to parse for data
    @param key the key to use to look for associated values
    @throws IOException if there is an issue reading from the InputStream
    @throws ParseException if there is an issue parsing the JSON file
    @return list an ArrayList containing each value associated with the given key
     */
    private static ArrayList<String> readJSON(InputStream jsonFile, String key) throws IOException, ParseException {
        ArrayList<String> list = new ArrayList<>();
        JSONArray array = (JSONArray) new JSONParser().parse(new InputStreamReader(jsonFile));
        for (Object o : array) {
            JSONObject object = (JSONObject) o;
            list.add(object.get(key).toString());
        }
        return list;
    }

    /*
    Reads a JSON file and returns an ArrayList of type Integer.
    @param jsonFile the JSON file to parse for data
    @param key the key to use to look for associated values
    @param subKey the sub-key embedded within the key. For example, the key might be "reservation" and the sub-key might
    be "roomNumber" which falls under "reservation"
    @throws IOException if there is an issue reading from the InputStream
    @throws ParseException if there is an issue parsing the JSON file
    @return list an ArrayList containing each value associated with the given key
     */
    private static ArrayList<Integer> readJSON(InputStream jsonFile, String key, String subKey) throws IOException, ParseException {
        ArrayList<Integer> list = new ArrayList<>();
        JSONArray array = (JSONArray) new JSONParser().parse(new InputStreamReader(jsonFile));
        for (Object o : array) {
            JSONObject object = (JSONObject) ((JSONObject) o).get(key);
            list.add(Integer.valueOf(object.get(subKey).toString()));
        }
        return list;
    }
}
