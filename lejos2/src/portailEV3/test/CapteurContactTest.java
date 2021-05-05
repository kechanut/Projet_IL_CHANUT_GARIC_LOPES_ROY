package portailEV3.test;

import static org.junit.Assert.*;

import org.junit.Before;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.port.Port;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import portailEV3.hardware.CapteurContact;

public class CapteurContactTest {

	@Mock
	private CapteurContact capteur;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
    @Test
    public void isPressed() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        
        EV3TouchSensor touchSensor = Mockito.mock(EV3TouchSensor.class);
        Mockito.when(capteur.getSensor(Mockito.any(Port.class))).thenReturn(touchSensor);
        Mockito.when(capteur.getEtat()).thenReturn((float) 1);
        Mockito.when(capteur.contact()).thenCallRealMethod();
        assertTrue(capteur.contact());
        
        Mockito.when(capteur.getEtat()).thenReturn((float) 0);
        assertFalse(capteur.contact());
    }

}
