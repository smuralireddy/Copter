
package game;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.media.*;

public class copter extends MIDlet {
GameDesign gd;
boolean pause = false;
Display display;
Player player;
 
    public copter() {
        gd = new GameDesign();
        display = Display.getDisplay(this);
        gd.start();
        display.setCurrent(gd);
        try {
	    player = Manager.createPlayer(getClass().getResourceAsStream("/game/res/theme.wav"),"audio/x-wav");
	    player.setLoopCount(-1);
	    player.start();
	    }
	catch(Exception e)
	    {
	    System.out.println("player"+e);
	    }
    }

    public void startApp() {
        if(pause){
            pause = false;
        gd.resume();}
        while(gd.hasEnded());
        if(!gd.hasEnded())
        {
           this.notifyDestroyed();
           try
           {
           	player.stop();
           }
           catch(Exception e){}
           this.destroyApp(true);
        }    
        
    }
    
    public void pauseApp() {
        try
        {
        	gd.pause();
        	pause = true;
        }
        catch(Exception e){}
    }
    
    public void destroyApp(boolean unconditional) {
        
    }
}
