/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;
import javax.microedition.media.*;

/**
 * @author mahi
 */
public class copter extends MIDlet {
GameDesign gd;
boolean pause=false;
Display d;
//Player player;
 
    public copter(){
        gd=new GameDesign();
        d=Display.getDisplay(this);
        gd.start();
        d.setCurrent(gd);/*
        try {
	    player = Manager.createPlayer(getClass().getResourceAsStream("/game/res/theme.wav"),"audio/x-wav");
	    player.setLoopCount(-1);
	    player.start();
	    }
	catch(Exception e)
	    {
	    System.out.println("player"+e);
	    }*/



    }

    public void startApp() {
        if(pause){
            pause=false;
        gd.resume();}
        //System.out.println("back to copter");
        //boolean b=gd.hasEnded();
        while(gd.hasEnded());
        if(!gd.hasEnded())
        {
            System.out.println("ended");
           this.notifyDestroyed();
           /*try{
           player.stop();}
           catch(Exception e){}*/
           this.destroyApp(true);
        }    
        
    }
    
    public void pauseApp() {
        try{
        gd.pause();
        pause=true;}
        catch(Exception e){}
    }
    
    public void destroyApp(boolean unconditional) {
        //Display.getDisplay (this).setCurrent ((Displayable) null);
    }
}
