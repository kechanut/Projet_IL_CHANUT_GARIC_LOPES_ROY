package portailEV3.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogEV3 {
	
	private static Logger logger = Logger.getLogger("MyLog");  
	private static FileHandler fh = null;
	
	private static void init() {
		try {
			fh = new FileHandler("lejos.log");
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addLog(String log) {
		if (fh == null) init();
		logger.log(Level.INFO, log);
	}
	
	public static void addError(String log) {
		if (fh == null) init();
		logger.log(Level.SEVERE, log);
	}

}
