package piano;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.Timer;

public class Piano extends JFrame implements KeyListener{
    private static int[] offset=new int[2];
    private static MidiChannel[]mc;
    private static boolean[] noteon=new boolean[128];
    private static boolean[] check=new boolean[4];
    private static int[] side=new int[128];
    private JLayeredPane keyPanel;
    public Piano() {
        try{
            keys = new HashMap<>();
            pianoKeys = new Key[128];
            registerKeys();
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Piano");
            Container content = getContentPane();
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            mc = synthesizer.getChannels();
            mc[0].programChange(0,54);
            addKeyListener(this);
            keyPanel = new JLayeredPane();
            keyPanel.setSize(1000, 600);
            pianoKeys[0].setLocation(0, 0);
            pianoKeys[0].setSize(50, 500);
            keyPanel.add(pianoKeys[60], 0, -1);
            keyPanel.add(pianoKeys[61], 1, -1);
            keyPanel.add(pianoKeys[62], 0, -1);
            keyPanel.add(pianoKeys[63], 1, -1);
            keyPanel.add(pianoKeys[64], 0, -1);
            content.add(keyPanel, BorderLayout.WEST);
            setLocation(0, 0);
            pack();
            setSize(1000, 600);
            setResizable(false);
        } catch(MidiUnavailableException ex) {
        }
    }
    public static int[] num=new int[128];
    public static void main(String[] args) {
    	if(args.length > 0) {
    		try {
    			File f = new File(args[0]);
    			Sequencer S = MidiSystem.getSequencer();
    			S.setSequence(new BufferedInputStream(new FileInputStream(f)));
    			S.start();
    		}catch(Exception e){}
    	}
        Piano P = new Piano();
        P.setVisible(true);
        int[][] im=ImperialMarch();
        //P.play(im, 250);
        int[][]
        P.free();
    }
    public void free(){
    	while(true){
        	boolean[] pres=new boolean[128];
        	for(int a=60;a<=72;a++){
    			if(noteon[a-60]){
    				pres[a+offset[side[a]]]=true;
    			}
    		}
        	for(int a=0;a<128;a++){
        		if(pres[a]){
        			mc[0].noteOn(a, 100);
        			if(!pianoKeys[a].isPressed()){
    					pianoKeys[a].press();
    				}
        		}
        		else{
        			mc[0].noteOff(a);
        			if(pianoKeys[a].isPressed()){
    					pianoKeys[a].depress();
    				}
        		}
        	}
        	keyPanel.revalidate();
        	psit(100);
        }
    }
    public static int[][] ImperialMarch(){
    	int[][] s1=new int[100][128];
    	for(int a=0;a<128;a++){
    		num[a]=0;
    	}
        add(s1, 67, 8, 7);
        add(s1, 67, 16, 7);
        add(s1, 67, 24, 7);
        add(s1, 63, 32, 5);
        add(s1, 70, 38, 2);
        add(s1, 67, 40, 7);
        add(s1, 63, 48, 5);
        add(s1, 70, 54, 2);
        add(s1, 67, 56, 15);
        add(s1, 74, 72, 7);
        add(s1, 74, 80, 7);
        add(s1, 74, 88, 7);
        add(s1, 75, 96, 5);
        add(s1, 70, 102, 2);
        add(s1, 66, 104, 7);
        add(s1, 63, 112, 5);
        add(s1, 70, 118, 2);
        add(s1, 67, 120, 15);
        add(s1, 79, 136, 7);
        add(s1, 67, 144, 5);
        add(s1, 67, 150, 2);
        add(s1, 79, 152, 7);
        add(s1, 78, 160, 5);
        add(s1, 77, 166, 2);
        add(s1, 76, 168, 2);
        add(s1, 75, 170, 2);
        add(s1, 76, 172, 4);
        add(s1, 68, 179, 4);
        add(s1, 73, 184, 7);
        add(s1, 72, 192, 5);
        add(s1, 71, 198, 2);
        add(s1, 70, 200, 2);
        add(s1, 69, 202, 2);
        add(s1, 70, 204, 4);
        add(s1, 63, 211, 4);
        add(s1, 66, 216, 7);
        add(s1, 63, 224, 5);
        add(s1, 66, 230, 2);
        add(s1, 70, 232, 7);
        add(s1, 67, 240, 5);
        add(s1, 70, 246, 2);
        add(s1, 74, 248, 15);
    	return s1;
    }
    private void registerKeys() {
    	for(int a=67;a<=72;a++) side[a]=1;
    	for(int a=0;a<128;a++){
    		if(a%12==0||a%12==2||a%12==4||a%12==5||a%12==7||a%12==9||a%12==11){
    			pianoKeys[a] = new Key(Keys.WHITE, a);
    		}
    		else{
    			pianoKeys[a] = new Key(Keys.BLACK, a);
    		}
    	}
        keys.put(KeyEvent.VK_A, 60); //C
        keys.put(KeyEvent.VK_W, 61); //C#
        keys.put(KeyEvent.VK_S, 62); //D
        keys.put(KeyEvent.VK_E, 63); //D#
        keys.put(KeyEvent.VK_D, 64); //E
        keys.put(KeyEvent.VK_F, 65); //F
        keys.put(KeyEvent.VK_T, 66); //F#
        keys.put(KeyEvent.VK_J, 67); //G
        keys.put(KeyEvent.VK_U, 68); //G#
        keys.put(KeyEvent.VK_K, 69); //A
        keys.put(KeyEvent.VK_I, 70); //A#
        keys.put(KeyEvent.VK_L, 71); //B
        keys.put(KeyEvent.VK_SEMICOLON, 72); //C
    }
    private HashMap<Integer, Integer>keys;
    private Synthesizer synthesizer ;
    Key[]pianoKeys;
    public void play(int[][] aa, int length){
    	boolean[] on=new boolean[128];
    	int[] cur=new int[128];
    	for(int time=1;time<=length;time++){
    		for(int a=0;a<128;a++){
    			if(aa[cur[a]][a]==time){
    				on[a]=!on[a];
    				cur[a]++;
    			}
    			if(on[a]){
    				if(!pianoKeys[a].isPressed())
    					pianoKeys[a].press();
    				mc[0].noteOn(a, 100);
    				keyPanel.revalidate();
    				if(!pianoKeys[a].isPressed()){
    					pianoKeys[a].press();
    				}
    			}
    			else{
    				if(pianoKeys[a].isPressed())
    					pianoKeys[a].depress();
    				mc[0].noteOff(a);
    				keyPanel.revalidate();
    				if(pianoKeys[a].isPressed()){
    					pianoKeys[a].depress();
    				}
    			}
    		}
			keyPanel.revalidate();
    		psit(100);
    	}
    }
    
    public static void add(int[][] aa, int ind, int start, int len){
    	aa[num[ind]][ind]=start;
    	num[ind]++;
    	aa[num[ind]][ind]=start+len;
    	num[ind]++;
    }
    public static void psit(int x) {
        try {
            Thread.sleep(x);
        }
        catch (Exception e){
                
        }
    }
    @Override
    public void keyPressed(KeyEvent evt) {
        if(keys.containsKey(evt.getKeyCode())) {
        noteon[keys.get(evt.getKeyCode()) - 60]=true;
        }
        else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V){
                if(evt.getKeyCode()==KeyEvent.VK_C&&!check[0]){
                        check[0]=true;
                        if(offset[0]>=-24)offset[0]-=12;
                }
                else if(!check[1]){
                        check[1]=true;
                        if(offset[0]<=36)offset[0]+=12;
                }
        }
        else if(evt.getKeyCode()==KeyEvent.VK_N||evt.getKeyCode()==KeyEvent.VK_M){
                if(evt.getKeyCode()==KeyEvent.VK_N&&!check[2]){
                        check[2]=true;
                        if(offset[1]>=-24)offset[1]-=12;
                }
                else if(!check[3]){
                        check[3]=true;
                        if(offset[1]<=36)offset[1]+=12;
                }
        }
    }
    @Override
    public void keyReleased(KeyEvent evt) {
		if(keys.containsKey(evt.getKeyCode())) {
			noteon[keys.get(evt.getKeyCode()) - 60]=false;
		}
		else if(evt.getKeyCode()==KeyEvent.VK_C||evt.getKeyCode()==KeyEvent.VK_V||evt.getKeyCode()==KeyEvent.VK_N||evt.getKeyCode()==KeyEvent.VK_M){
			if(evt.getKeyCode()==KeyEvent.VK_C) check[0]=false;
			if(evt.getKeyCode()==KeyEvent.VK_V) check[1]=false;
			if(evt.getKeyCode()==KeyEvent.VK_N) check[2]=false;
			if(evt.getKeyCode()==KeyEvent.VK_M) check[3]=false;
		}
    }
	@Override
	public void keyTyped(KeyEvent evt) {
	}
}
