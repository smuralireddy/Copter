package game;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.IOException;
import java.util.*;

public class GameDesign extends GameCanvas implements Runnable,CommandListener{
    Image helimg,blockimg;
    boolean paused=false;
    int hx=20,hy=getHeight()/2;
    Thread t;
    int lives=3;
    boolean live=true,ex=false;
    long score=0;
    int NUM_OF_BLOCKS=5,VEL=4;
    Command exit,pause,resume;
    Sprite heli,block[]=new Sprite[10];
    public GameDesign(){
        super(false);
        exit=new Command("exit",Command.EXIT,0);
        pause=new Command("pause",Command.SCREEN,1);
        resume=new Command("resume",Command.SCREEN,1);
        
        this.addCommand(exit);
        this.addCommand(pause);
        this.setCommandListener(this);
    }
   public void commandAction(Command c,Displayable s){
    if(c==exit){
        ex=true;               
    }
    else if(c==pause){
        pause();
        this.removeCommand(pause);
        this.addCommand(resume);
    }
    else if(c==resume){
        resume();
        this.removeCommand(resume);
        this.addCommand(pause);
    }}
    public void run(){
        while(true){
            try{
               if(!ex){
                   if(paused){
                       Graphics g=getGraphics();
                       g.drawString("Game paused", getWidth()/2,getHeight()/2,Graphics.HCENTER|Graphics.TOP);
                       flushGraphics();    
                       t.wait();
                   }
                   
                   else if(lives>=0){
                       score++;
                       if(score%200==0)
                       {
                           score+=50;
                       }
                       if(score%200==0&&NUM_OF_BLOCKS<10)
                       {
                           this.createBlock(2);
                       }
                       Thread.sleep(10);
                       drawGraphics();
                   }             
                else{
                   lose();
                  // break;
                   }
                }
               else{
                    System.out.println("Exited");
                 //   t.join();
                    break;
                }
            
            }
            catch(Exception e){}
        }
        //System.exit(0);
     System.out.println("came out");
        
        
    }
    public void createBlock(int n)
    {
        System.out.print("create block called"+n);
        Random r=new Random();
        for(int i=NUM_OF_BLOCKS;n>0;i++,n--){
            System.out.println("1 block creating");
            block[i]= new Sprite(blockimg,30,10);
            block[i].defineReferencePixel(15,5);
            int k=r.nextInt(getHeight());
            block[i].setRefPixelPosition(getWidth()+i*10,i+20+k);
            System.out.println(k+"\n\n\n");
        }
        NUM_OF_BLOCKS+=n;
        System.out.print(NUM_OF_BLOCKS);
    }
    public void drawGraphics(){
        Graphics g=getGraphics();
        setbg(g);
        moveheli();
        moveblock(g);
        drawScore();
        heli.setRefPixelPosition(hx,hy);
        heli.paint(g);
        drawScore();
        flushGraphics();
    }
    public void drawScore(){
           Graphics g=getGraphics();
           if(lives>=0)
           g.drawString("lives remaining="+lives, 10,10,Graphics.LEFT|Graphics.TOP);
        g.drawString("Score"+score, getWidth()-10,20,Graphics.RIGHT|Graphics.TOP);
        //flushGraphics();
    
    }
    public void moveheli()
    {
        int key=getKeyStates();
        
        if((key & UP_PRESSED)!=0&&hy>=10){
            hy-=VEL;
        }
        else if((key & DOWN_PRESSED)!=0&&hy<=getHeight()-10)
                hy+=VEL;
        else if((key & RIGHT_PRESSED)!=0&&hx<=getWidth()-5)
                hx+=VEL;
        else if((key& LEFT_PRESSED)!=0&&hx>=5)
                hx-=VEL;
        
    }
    public void moveblock(Graphics g){
        int bx,by;
        Random r=new Random();
        for(int i=0;i<NUM_OF_BLOCKS;i++){
            if(heli.collidesWith(block[i],false)){
                bx=getWidth()+i*10;
                by=r.nextInt(getHeight());
                
                lives--;
                
            }
            
            else{
            bx=block[i].getRefPixelX();
            by=block[i].getRefPixelY();
            if(bx>-5){
                bx-=3;
                
            }
            else{
            bx=getWidth()+i*10;
            by=r.nextInt(getHeight());
            }
            block[i].setRefPixelPosition(bx,by);
            block[i].paint(g);
        }}
    }
    public void lose()
    {
        this.removeCommand(pause);
        this.removeCommand(resume);
        Graphics g=getGraphics();
        g.drawString("you lose. your score:"+score, getWidth()/2,getHeight()/2,Graphics.HCENTER|Graphics.TOP);
        flushGraphics();
        
    }
    public void setbg(Graphics g){
        g.setColor(204,255,153);
        g.fillRect(0, 0, getWidth(),getHeight());
    }
    public void start(){
        try{
            
            helimg= Image.createImage("/game/res/thirty.png");
            blockimg= Image.createImage("/game/res/block.png");
        }
        
        catch(Exception e){
            System.out.println("here it is\n\n\n\n\n\n\n\n\n\n "+e);
            
        }
        heli= new Sprite(helimg,30,30);
        heli.defineReferencePixel(15,15);
        heli.setRefPixelPosition(10,getHeight()/2);
        Random r=new Random();
        for(int i=0;i<NUM_OF_BLOCKS;i++){
            block[i]= new Sprite(blockimg,30,10);
            block[i].defineReferencePixel(15,5);
            int k=r.nextInt(getHeight());
            block[i].setRefPixelPosition(getWidth()+i*10,i+20+k);
            System.out.println(k+"\n\n\n");
        }
        
        t= new Thread(this);
        t.start();
        System.out.println("fininsdh");
        //System.exit(0);
    }
    void pause(){
        
        paused=true;
     }
    void resume(){
        paused=false;
    }
    boolean hasEnded()
    {
        return t.isAlive();
    }
    

    
}
