package rover.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by rachelcabot on 24/10/2016.
 */
public class MessageParser {

    private static final HashMap<String,Class<? extends AMessage>> messageMap = new HashMap<String,Class<? extends AMessage>>(){{
        put("hello",HelloMessage.class);
    }};

    public static ArrayList<AMessage> parse(ArrayList<String> newMessages) {
        System.out.println("------------You've got mail "+newMessages.toString());
        ArrayList<AMessage> messageObjects = new ArrayList<>();
        for(String message : newMessages){
            String[] tokens = message.split(" ");
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
}
