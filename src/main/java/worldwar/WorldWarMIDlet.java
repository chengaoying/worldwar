package worldwar;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class WorldWarMIDlet extends MIDlet {

	private static WorldWarMIDlet instance;

	public WorldWarMIDlet(){
		instance = this;
	}
	
	public static WorldWarMIDlet getInstance() {
		return instance;
	}

	protected void destroyApp(boolean unconditional)throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(WorldWarEngine.instance);
		new Thread(WorldWarEngine.instance).start();
	}

}
