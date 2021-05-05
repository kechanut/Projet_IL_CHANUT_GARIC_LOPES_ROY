package portailEV3.test;

import static org.junit.Assert.*;

import org.junit.Test;

import portailEV3.runnable.SoundRunnable;

public class SoundRunnableTest {

	SoundRunnable sound;
	
	@Test
	public void isRunningTest() {
		sound = new SoundRunnable();
		new Thread(sound).start();
		assertFalse(sound.getIsRunning());
		
		sound.resumeThread();
		assertTrue(sound.getIsRunning());
		
		sound.stopThread();
		assertFalse(sound.getIsRunning());
		
		Thread.currentThread().interrupt();
		
	}
	
	

}
