package rover.messaging;

import rover.APracticalRover;
import rover.Rover;

/**
 * Created by rachelcabot on 27/10/2016.
 */
public class MessagingSystem {
    public static MessagingSystem instance;
    private APracticalRover rover;

    private MessagingSystem(){

    }

    public static synchronized MessagingSystem getInstance(){
        if(instance==null)
            instance = new MessagingSystem();
        return instance;
    }

    public void register(APracticalRover rover) throws Exception {
        if(getInstance().rover==null)
            getInstance().rover = rover;
        else
            throw new Exception("trying to register more than one rover :(");
    }

    public static void sendNewMessage(String message){
        Rover r = getInstance().rover;
        if(r!=null)
            r.broadCastToTeam(message);
    }
}
