package portailEV3.test;

import static org.junit.Assert.*;

import org.junit.Before;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.port.Port;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import portailEV3.hardware.CapteurPresence;

public class CapteurPresenceTest {

	@Mock
	private CapteurPresence capteur;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
    @Test
    public void presenceTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        
    	EV3UltrasonicSensor sensor = Mockito.mock(EV3UltrasonicSensor.class);
        Mockito.when(capteur.getSensor(Mockito.any(Port.class))).thenReturn(sensor);
        Mockito.when(capteur.getEtat()).thenReturn((float) 0);
        Mockito.when(capteur.presence()).thenCallRealMethod();
        assertTrue(capteur.presence());
        Mockito.when(capteur.getEtat()).thenReturn((float) 1);
        assertFalse(capteur.presence());
    }

}
