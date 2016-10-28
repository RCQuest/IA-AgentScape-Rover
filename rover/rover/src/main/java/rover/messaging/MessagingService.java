package rover.messaging;

import rover.APracticalRover;

/**
 * Created by rachelcabot on 27/10/2016.
 */
public class MessagingService {
    public static MessagingService instance;
    private APracticalRover rover;

    private MessagingService(){

    }

    public static synchronized MessagingService getInstance(){
        if(instance==null)
            instance = new MessagingService();
        return instance;
    }

    public void register(APracticalRover rover) throws Exception {
        if(getInstance().rover==null)
            getInstance().rover = rover;
        else
            throw new Exception("trying to register more than one rover :(");
    }

    public static void sendNewMessage(String message){
        APracticalRover r = getInstance().rover;
        if(r!=null)
            r.broadCastToTeam(message);
    }
}
