package worldwar;

import java.io.InputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.util.IOUtil;

public class Map {
	private static final int MAP_VERSION = 2;
    private static final String MAP_MAGIC = "MWWM";
    private static final String TAG_INFLUENCE = "TINF";
    private static final String TAG_REGION = "TREG";
    private static final String TAG_TILE = "TILE";
    private static final String TAG_FLAG = "FLAG";

    private int version;
    private int screenWidth;
    private int screenHeight;
    private int x, y, width, height;
    private int sideLen;
    private int sideXMappingLen;
    private int sideXMappingLen2;
    private int sideYMappingLen;
    private int sideYMappingLen2;
    private int regionW;
    private int regionH;
    private int tileWidth;
    private int tileHeight;
    private int rows, cols;
    private short[][] tiles;
    private short[][] tilesFlag;
    private Region[] regions;
    private Influence[] influences;
    
    protected Map() {}
    
    public Region[] getRegions()
    {
    	return regions;
    }
    
    public Influence[] getInfluences()
    {
    	return influences;
    }
    
    public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getRegionW() {
		return regionW;
	}

	public int getRegionH() {
		return regionH;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void readMapHead(InputStream is) /*throws IOException*/
    {
        String magic;
		try {
			magic = new String(IOUtil.readBytes(is, 4));
			 if (!MAP_MAGIC.equals(magic))
		        {
		        	throw new RuntimeException("地图格式错误");
		        }
		        this.version = IOUtil.readInt(is);
		        if (version > MAP_VERSION)
		        {
		        	throw new RuntimeException("地图版本不支持");
		        }
		        this.screenWidth = IOUtil.readInt(is);
		        this.screenHeight = IOUtil.readInt(is);
		        this.x = IOUtil.readInt(is)+35;
		        this.y = IOUtil.readInt(is)+20;
		        this.width = IOUtil.readInt(is);
		        this.height = IOUtil.readInt(is);
		        this.sideLen = IOUtil.readInt(is);
		        this.sideXMappingLen = IOUtil.readInt(is);
		        this.sideXMappingLen2 = sideXMappingLen*2;
		        this.sideYMappingLen = IOUtil.readInt(is);
		        this.sideYMappingLen2 = sideYMappingLen*2;
		        this.tileWidth = sideXMappingLen2;
		        this.tileHeight = sideYMappingLen2+sideLen;
		        this.regionW = IOUtil.readInt(is);
		        this.regionH = IOUtil.readInt(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
       
    }

    private void readInfluences(InputStream is) /*throws IOException*/
    {
    	String tag;
		try {
			tag = new String(IOUtil.readBytes(is, 4));
			if (!TAG_INFLUENCE.equals(tag))
	    	{
	    		throw new RuntimeException("势力标识错误");
	    	}
	        int count = IOUtil.readInt(is);
	        Influence[] influences = new Influence[count];
	        for (int i = 0; i < count; ++i)
	        {
	            Influence inf = new Influence();
	            inf.setId((short)i);
	            inf.setName(IOUtil.readString(is));
	            inf.setColor(IOUtil.readInt(is));
	            influences[i] = inf;
	        }
	        this.influences = influences;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void readRegions(InputStream is)/* throws IOException*/
    {
    	String tag;
		try {
			tag = new String(IOUtil.readBytes(is, 4));
			if (!TAG_REGION.equals(tag))
	    	{
	    		throw new RuntimeException("区块标识错误");
	    	}
	        int count = IOUtil.readInt(is);
	        Region.setBorderColor(IOUtil.readInt(is));
	        Region[] regions = new Region[count];
	        for (int i = 0; i < count; ++i)
	        {
	        	Region region = new Region();
	        	region.setId((short)i);
	            region.setName(IOUtil.readString(is));
	            region.setSoldiers(IOUtil.readShort(is));
	            region.setInfluenceId(IOUtil.readShort(is));

	            region.setUpRegionId(IOUtil.readShort(is));
	            region.setLeftRegionId(IOUtil.readShort(is));
	            region.setDownRegionId(IOUtil.readShort(is));
	            region.setRightRegionId(IOUtil.readShort(is));
	            region.setCenterTileRow(IOUtil.readShort(is));
	            region.setCenterTileCol(IOUtil.readShort(is));
	            
	            if (version >= 2) {
	            	region.setCenterTileX(IOUtil.readShort(is));
	                region.setCenterTileY(IOUtil.readShort(is));
	            	region.setAdjacentRegions(IOUtil.readShortArray(is));
	            }
	            regions[i] = region;
	        }
	        this.regions = regions;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void readTiles(InputStream is) /*throws IOException*/
    {
    	String tag;
		try {
			tag = new String(IOUtil.readBytes(is, 4));
			if (!TAG_TILE.equals(tag))
	    	{
	    		throw new RuntimeException("贴片标识错误");
	    	}
	        int rows = IOUtil.readInt(is);
	        int cols = IOUtil.readInt(is);
	        
	        short[][] tiles = new short[rows][];
	        for (int r = 0; r < rows; ++r)
	        {
	            tiles[r] = new short[cols];
	            for (int c = 0; c < cols; ++c)
	            {
	                tiles[r][c] = IOUtil.readShort(is);
	            }
	        }
	        this.rows = rows;
	        this.cols = cols;
	        this.tiles = tiles;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void readTilesFlag(InputStream is) /*throws IOException*/
    {
    	String tag;
		try {
			tag = new String(IOUtil.readBytes(is, 4));
			if (!TAG_FLAG.equals(tag))
	    	{
	    		throw new RuntimeException("贴片标志标识错误");
	    	}
	        int rows = IOUtil.readInt(is);
	        int cols = IOUtil.readInt(is);
	        
	        short[][] tilesFlag = new short[rows][];
	        for (int r = 0; r < rows; ++r)
	        {
	        	tilesFlag[r] = new short[cols];
	            for (int c = 0; c < cols; ++c)
	            {
	            	tilesFlag[r][c] = IOUtil.readShort(is);
	            }
	        }
	        this.tilesFlag = tilesFlag;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void deserialize(InputStream is) throws IOException
    {
    	readMapHead(is);
    	readInfluences(is);
    	readRegions(is);
    	readTiles(is);
    	readTilesFlag(is);
    }
    
    public static Map loadMap(String path)
    {
    	InputStream is = null;
    	try {
    		is = Map.class.getResourceAsStream(path);
    		Map map = new Map();
			map.deserialize(is);
			return map;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    protected void fillHexagon(Graphics g, int x, int y)
    {
        g.fillTriangle(x, y + sideYMappingLen, 
        		x + sideXMappingLen2, y + sideYMappingLen, 
        		x + sideXMappingLen, y);
        g.fillTriangle(x, y + sideYMappingLen + sideLen, 
        		x + sideXMappingLen,  y + sideYMappingLen2 + sideLen, 
        		x + sideXMappingLen2, y + sideYMappingLen + sideLen);
        g.fillRect(x, y + sideYMappingLen, sideXMappingLen2, sideLen);

    }

    protected void drawHexagon(Graphics g, int x, int y)
    {
        /*上面两条斜边*/
        g.drawLine(x, y + sideYMappingLen, x + sideXMappingLen, y);
        g.drawLine(x + sideXMappingLen, y, x + sideXMappingLen2, y + sideYMappingLen);

        /*下面两条斜边*/
        g.drawLine(x, y + sideYMappingLen + sideLen, x + sideXMappingLen, y + sideYMappingLen2 + sideLen);
        g.drawLine(x + sideXMappingLen, y + sideYMappingLen2 + sideLen, x + sideXMappingLen2, y + sideYMappingLen + sideLen);

        /*中间两条竖线*/
        g.drawLine(x, y + sideYMappingLen, x, y + sideYMappingLen + sideLen);
        g.drawLine(x + sideXMappingLen2, y + sideYMappingLen, x + sideXMappingLen2, y + sideYMappingLen + sideLen);
    }
    
    protected void drawRegionBorder(Graphics g, int x, int y, int drawFlag)
    {
        //左上角的斜线
        if ((drawFlag&1) != 0)
        {
            g.drawLine(x, y + sideYMappingLen, x + sideXMappingLen, y);
        }
        //右上角的斜线
        if ((drawFlag&(1<<5)) != 0)
        {
            g.drawLine(x + sideXMappingLen, y, x + sideXMappingLen2, y + sideYMappingLen);
        }
        //左下角的斜线
        if ((drawFlag&(1<<2)) != 0)
        {
            g.drawLine(x, y + sideYMappingLen + sideLen, x + sideXMappingLen, y + sideYMappingLen2 + sideLen);
        }
        //右下角的斜线
        if ((drawFlag&(1<<3)) != 0)
        {
            g.drawLine(x + sideXMappingLen, y + sideYMappingLen2 + sideLen, x + sideXMappingLen2, y + sideYMappingLen + sideLen);
        }
        //左边的竖线
        if ((drawFlag&(1<<1)) != 0)
        {
            g.drawLine(x, y + sideYMappingLen, x, y + sideYMappingLen + sideLen);
        }
        //右边的竖线
        if ((drawFlag&(1<<4)) != 0)
        {
            g.drawLine(x + sideXMappingLen2, y + sideYMappingLen, x + sideXMappingLen2, y + sideYMappingLen + sideLen);
        }
    }

    public void drawRegionBorder(Graphics g, int color)
    {
    	g.setColor(color);
        int sx = x, sy = y;
        for (int r = 0; r < rows; ++r)
        {
            sx = x;
            if (r % 2 != 0)
            {
                sx += sideXMappingLen;
            }
            for (int c = 0; c < cols; ++c)
            {
                if (tiles[r][c] >= 0)
                {
                    drawRegionBorder(g, sx, sy, tilesFlag[r][c]);
                }
                sx += (sideXMappingLen+sideXMappingLen);
            }
            sy += (sideYMappingLen + sideLen);
        }
    }
    
    protected Influence getInfluence(int r, int c)
    {
    	return influences[regions[tiles[r][c]].getInfluenceId()];
    }
    
    protected Influence getInfluence(int id)
    {
    	return influences[id];
    }
    
    protected Influence getInfluence(Region region)
    {
    	return influences[region.getInfluenceId()];
    }
    
    protected Region getRegion(int r, int c)
    {
    	return regions[tiles[r][c]];
    }
    
    protected Region getRegion(int id)
    {
    	return regions[id];
    }
    
    public Region getFirstRegion(Influence influence) {
    	int influenceId = influence.getId();
    	for (int i = 0; i < regions.length; ++i) {
    		if (regions[i].getInfluenceId() == influenceId) {
    			return regions[i];
    		}
    	}
    	return null;
    }
    
    public void drawRegion(Graphics g, Region region, int color)
    {
    	int regionId = region.getId();
    	int sx = x, sy = y;
        for (int r = 0; r < rows; ++r)
        {
            sx = x;
            if (r % 2 != 0)
            {
                sx += sideXMappingLen;
            }
            for (int c = 0; c < cols; ++c)
            {
            	if (tiles[r][c] >= 0 && tiles[r][c] == regionId)
            	{
            		g.setColor(color);
	            	fillHexagon(g, sx, sy);
	            	g.setColor(Region.getBorderColor());
	                drawRegionBorder(g, sx, sy, tilesFlag[r][c]);
            	}
                sx += (sideXMappingLen + sideXMappingLen);
            }
            sy += (sideYMappingLen + sideLen);
        }
    }
    
    public void drawInfluence(Graphics g, Influence inf, int color)
    {
    	int sx = x, sy = y;
        for (int r = 0; r < rows; ++r)
        {
            sx = x;
            if (r % 2 != 0)
            {
                sx += sideXMappingLen;
            }
            for (int c = 0; c < cols; ++c)
            {
            	if (tiles[r][c] >= 0 && getInfluence(r, c) == inf)
            	{
            		g.setColor(color);
	            	fillHexagon(g, sx, sy);
	            	g.setColor(Region.getBorderColor());
	                drawRegionBorder(g, sx, sy, tilesFlag[r][c]);
            	}
                sx += (sideXMappingLen + sideXMappingLen);
            }
            sy += (sideYMappingLen + sideLen);
        }
    }
    
    public void drawMap(Graphics g)
    {
        int sx = x, sy = y;
        for (int r = 0; r < rows; ++r)
        {
            sx = x;
            if (r % 2 != 0)
            {
                sx += sideXMappingLen;
            }
            for (int c = 0; c < cols; ++c)
            {
            	if (tiles[r][c] >= 0)
            	{
            		g.setColor(getInfluence(r, c).getColor());
	            	fillHexagon(g, sx, sy);
	            	g.setColor(Region.getBorderColor());
	                drawRegionBorder(g, sx, sy, tilesFlag[r][c]);
            	}
                sx += (sideXMappingLen + sideXMappingLen);
            }
            sy += (sideYMappingLen + sideLen);
        }
    }
    
    public boolean isConnexcity(Region region1, Region region2) {
    	if (region1 == null || region2 == null || region1 == region2) {
    		return false;
    	}
    	short[] adjacentRegions = region1.getAdjacentRegions();
    	short region2Id = region2.getId();
    	for (int i = 0; i < adjacentRegions.length; ++i) {
    		if (region2Id == adjacentRegions[i]) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public Region[] getAdjacentRegions(Region region) {
		short[] ids = region.getAdjacentRegions();
		Region[] regions = new Region[ids.length];
		for(int i=0;i<ids.length;i++){
			regions[i]=getRegion(ids[i]);
		}
		return regions;
	}
    
    public Region moveLeft(Region region)
    {
    	if (region.getLeftRegionId() >= 0)
    	{
    		return regions[region.getLeftRegionId()];
    	}
    	return region;
    }
    
    public Region moveRight(Region region)
    {
    	if (region.getRightRegionId() >= 0)
    	{
    		return regions[region.getRightRegionId()];
    	}
    	return region;
    }
    
    public Region moveUp(Region region)
    {
    	if (region.getUpRegionId() >= 0)
    	{
    		return regions[region.getUpRegionId()];
    	}
    	return region;
    }
    
    public Region moveDown(Region region)
    {
    	if (region.getDownRegionId() >= 0)
    	{
    		return regions[region.getDownRegionId()];
    	}
    	return region;
    }
}
