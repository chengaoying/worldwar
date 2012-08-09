package worldwar;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.ui.VerticalListMenu;

public class StateTestMap {
	private static final byte STATE_SEL_MAP = 0;
	private static final byte STATE_SEL_INFLUENCE = 1;
	private static final byte STATE_SEL_REGION = 2;
	
	private WorldWarEngine engine;
	private int state;
	private VerticalListMenu mapMenu;
	private Map map;
	private Influence[] influences;
	private int influenceId;
	private Region region;
	
	private String[] mapPaths = {
		"/map/map1.wwm",	
		"/map/map2.wwm",
		"/map/map3.wwm",
		"/map/map4.wwm",
	};
	
	public StateTestMap(WorldWarEngine engine)
	{
		this.engine = engine;
		mapMenu = new VerticalListMenu();
		mapMenu.setItemNormalColor(0XFFFFFF);
		mapMenu.setItemHilightColor(0XFFFF00);
		mapMenu.setItemsText(mapPaths);
		short[][] menuCoordinate = new short[mapPaths.length][];
		int xstart = 300, ystart = 240;
		int yspace = 25;
		for (int i = 0; i < menuCoordinate.length; ++i)
		{
			menuCoordinate[i] = new short[2];
			menuCoordinate[i][0] = (short)xstart;
			menuCoordinate[i][1] = (short)(ystart+i*yspace);
		}
		mapMenu.setItemsCoordinate(menuCoordinate);
	}
	
	public void handle(KeyState key)
	{
		switch (state) {
		case STATE_SEL_MAP: 
			handleSelectMap(key);
			break;
		case STATE_SEL_INFLUENCE: 
			handleSelectInfluence(key);
			break;
		case STATE_SEL_REGION: 
			handleSelectRegion(key);
			break;
		default: break;
		}
	}
	
	private void handleSelectRegion(KeyState key) {
		if (key.containsAndRemove(KeyCode.UP)) {
			region = map.moveUp(region);
		}
		if (key.containsAndRemove(KeyCode.DOWN)) {
			region = map.moveDown(region);
		}
		if (key.containsAndRemove(KeyCode.LEFT)) {
			region = map.moveLeft(region);
		}
		if (key.containsAndRemove(KeyCode.RIGHT)) {
			region = map.moveRight(region);
		}
		if (key.containsAndRemove(KeyCode.NUM0)) {
			state = STATE_SEL_INFLUENCE;
		}
	}

	private void handleSelectInfluence(KeyState key) {
		if (key.containsAndRemove(KeyCode.LEFT)) {
			if (influenceId > 0) {
				influenceId--;
			}
			else {
				influenceId = influences.length-1;
			}
		}
		if (key.containsAndRemove(KeyCode.RIGHT)) {
			if (influenceId < influences.length-1) {
				influenceId++;
			}
			else {
				influenceId = 0;
			}
		}
		if (key.containsAndRemove(KeyCode.NUM0)) {
			map = null;
			state = STATE_SEL_MAP;
		}
		if (key.containsAndRemove(KeyCode.OK)) {
			region = map.getFirstRegion(influences[influenceId]);
			state = STATE_SEL_REGION;
		}
	}

	private void handleSelectMap(KeyState key) {
		
		if (key.containsAndRemove(KeyCode.UP))
		{
			mapMenu.prevItem();
		}
		if (key.containsAndRemove(KeyCode.DOWN))
		{
			mapMenu.nextItem();
		}
		if (key.containsAndRemove(KeyCode.OK))
		{
			try {
				map = Map.loadMap(mapPaths[mapMenu.getHilightIndex()]);
				influences = map.getInfluences();
				influenceId = 0;
				state = STATE_SEL_INFLUENCE;
			}
			catch (Exception e) {
				PopupText pt = UIResource.getInstance().buildDefaultPopupText();
				pt.setText("¼ÓÔØµØÍ¼Ê§°Ü£¬"+e.getMessage());
				pt.popup();
			}
		}
	}

	public void show(Graphics g)
	{
		switch (state) {
		case STATE_SEL_MAP: 
			showSelectMap(g);
			break;
		case STATE_SEL_INFLUENCE: 
			showSelectInfluence(g);
			break;
		case STATE_SEL_REGION: 
			showSelectRegion(g);
			break;
		default: break;
		}
	}

	private void showSelectRegion(Graphics g) {
		g.setColor(0XFFFFFF);
		g.fillRect(0, 0, engine.getScreenWidth(), engine.getScreenHeight());
		map.drawMap(g);
		map.drawRegion(g, region, 0XFF0000);
		short[] adjacentRegions = region.getAdjacentRegions();
		for (int i = 0; i < adjacentRegions.length; ++i) {
			map.drawRegion(g, map.getRegion(adjacentRegions[i]), 0XF010F0);
		}
	}

	private void showSelectInfluence(Graphics g) {
		g.setColor(0XFFFFFF);
		g.fillRect(0, 0, engine.getScreenWidth(), engine.getScreenHeight());
		map.drawMap(g);
		
		int xstart = 20, ystart = 20, sideLen = 30;
		int xspace = 20;
		
		for (int i = 0; i < influences.length; ++i)
		{
			int sx = xstart+i*(sideLen+xspace);
			g.setColor(influences[i].getColor());
			g.fillRect(sx, ystart, sideLen, sideLen);
			g.setColor(0);
			g.drawRect(sx, ystart, sideLen, sideLen);
			if (i == influenceId)
			{
				g.setColor(0XFFFF00);
				DrawUtil.drawRect(g, sx-3, ystart-3, sideLen+6, sideLen+6, 2);
			}
		}
		
		map.drawInfluence(g, influences[influenceId], 0XFF0000);
	}

	private void showSelectMap(Graphics g) {
		g.setColor(0);
		g.fillRect(0, 0, engine.getScreenWidth(), engine.getScreenHeight());
		mapMenu.show(g);
	}
}
