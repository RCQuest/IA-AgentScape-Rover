package rover.messaging;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 24/10/2016.
 */
public class MessagePassingInterface {
    private static ArrayList<IMessageListener> listeners;

    static {
        listeners = new ArrayList<>();
    }

    public static void addListener(IMessageListener listener){
        listeners.add(listener);
    }

    public static void sendMessage(AMessage message){
        for(IMessageListener l: listeners){
            l.messageNotify(message);
        }
    }
}
