package rover.messaging;

import rover.shared.practical.WorldPercept;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class HelloMessage extends AMessage {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {
        id = Integer.parseInt(originalMessageTokens[1]);
        System.out.println("Parsing id... "+id);
    }

    @Override
    public void modifyPercept(WorldPercept percept) {

    }
}
