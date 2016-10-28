package rover.messaging;

import rover.shared.practical.RoverOffset;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rachelcabot on 24/10/2016.
 */
public class MessageParser {

    private static final String DELIMITER = " ";

    private static final HashMap<String,Class<? extends AMessage>> messageMap = new HashMap<String,Class<? extends AMessage>>(){{
        put("hello",HelloMessage.class);
        put("searching",SearchingMessage.class);
        put("searched",SearchedMessage.class);
        put("collecting",CollectingMessage.class);
        put("collected",CollectedMessage.class);
        put("found",FoundMessage.class);
    }};

    public static ArrayList<AMessage> parse(ArrayList<String> newMessages) {
        System.out.println("You've got mail "+newMessages.toString());
        ArrayList<AMessage> messageObjects = new ArrayList<>();
        for(String message : newMessages){
            String[] tokens = message.split(DELIMITER);
            try {
                AMessage messageObject = messageMap.get(tokens[0]).newInstance();
                messageObject.setMessageTokens(tokens);
                messageObjects.add(messageObject);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return messageObjects;
    }

    public static String generateFoundMessage(ArrayList<RoverOffset> foundItems){
        return generateMessage("found",foundItems);
    }

    public static String generateCollectedMessage(RoverOffset myPosition) {
        return generateMessage("collected",myPosition);
    }

    private static String generateMessage(String command, ArrayList<RoverOffset> items){
        String message = command;
        for (RoverOffset item : items) {
            message+=DELIMITER+item.toMessageString(DELIMITER);
        }
        return message;
    }

    private static String generateMessage(String command, RoverOffset item){
        String message = command;
        message+=DELIMITER+item.toMessageString(DELIMITER);
        return message;
    }
}
