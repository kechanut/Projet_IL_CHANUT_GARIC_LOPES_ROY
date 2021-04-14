package portailEV3.runnable;

import lejos.hardware.Sound;

public class SoundRunnable implements Runnable {
	
	private volatile boolean isRunning = false;
	
	@Override
    public void run() {
		Sound.setVolume(1);
		while(true) {
			if(this.isRunning) {
				try {
					playTune();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }

    public void stopThread() {
        this.isRunning = false;
    }
    
    public void resumeThread() {
        this.isRunning = true;
    }
    
    public void playTune() throws InterruptedException
    {
    	int f = 349;
    	int a = 440;
    	int cH = 523;

    	if(this.isRunning)
    		Sound.playTone(a, 500); 
    	if(this.isRunning)
    		Sound.playTone(a, 500);     
    	if(this.isRunning)
    		Sound.playTone(a, 500); 
    	if(this.isRunning)
    		Sound.playTone(f, 350); 
    	if(this.isRunning)
    		Sound.playTone(cH, 150);

    	if(this.isRunning)
    		Sound.playTone(a, 500);
    	if(this.isRunning)
    		Sound.playTone(f, 350);
    	if(this.isRunning)
    		Sound.playTone(cH, 150);
    	if(this.isRunning)
    		Sound.playTone(a, 1000);
        //first bit
    }

}