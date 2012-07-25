package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Clam {
	
	private final int RANDOMTIME = 4000;
	private Vector2 pos;
	private Rectangle bounds = new Rectangle();
	private boolean closed = true;
	private Random aRandom = new Random();
	
	private int count;
	private float openTime;
	private float closeTime = 4500;
	
	private boolean hasPearl = true;
	
	private int ID;
	
	private boolean simon = false;
	
	//if touch is being stored for simon
	private boolean collect = false;
	
	private SimonController aSimonController;
	
	private boolean canPress = true;
	
	public Clam ( Vector2 aPos, int aID, SimonController parentSimon)
	{
		aSimonController = parentSimon;
		ID = aID;
		pos = aPos;
		setBoundsWidth(0.65f);
		setBoundsHeight(0.55f);
		
		openTime = -1;

		if (aRandom.nextInt(2) == 1)
		{
			hasPearl = true;
		}
		else
		{
			hasPearl = false;
		}
	}
	
	public Vector2 getPos()
	{
		return pos;
	}

	public void setBoundsWidth( float size)
	{
		bounds.width = size;
	}
	
	public void setBoundsHeight( float size)
	{
		bounds.height = size;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void setPos(Vector2 aPos)
	{
		pos = aPos;
	}
	
	public boolean getClosed()
	{
		return closed;
	}
	
	public boolean getPearl()
	{
		return hasPearl;
	}
	
	public void setPearl(boolean aBool)
	{
		hasPearl = aBool;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setCollect(boolean aBool)
	{
		collect = aBool;
	}
	
	public void update (float delta)
	{
		if (openTime == -1){openTime = aRandom.nextInt(RANDOMTIME) + 150 * delta ;}
		
		if (!simon)
		{
			if (closed)
			{
				if ( count < openTime)
				{
					count += 100 * delta;
				}
				else
				{
					count = 0;
					closed = false;
				}
				
			}
			else
			{
				if (count < closeTime * delta)
				{
					count += 100 * delta;
				}
				else
				{
					count = 0;
					openTime = aRandom.nextInt(RANDOMTIME) + 150 * delta ;
	
					if (aRandom.nextInt(2) == 1)
					{
						hasPearl = true;
					}
					else
					{
						hasPearl = false;
					}
					
					closed = true;
				}
			}
		}
		else
		{
			//System.out.println("SIMON IN PROGRESS");
		}
	}
	
	public boolean checkInside(Rectangle aBox) {
		
		float yy = WorldRenderer.getCameraHeight() - pos.y;
		float y1 = aBox.y;

		if ((aBox.x> pos.x * WorldRenderer.ppuX && aBox.x < (pos.x + getBounds().width) * WorldRenderer.ppuX)
				|| (aBox.x + aBox.width> pos.x * WorldRenderer.ppuX && aBox.x + aBox.width < (pos.x + getBounds().width) * WorldRenderer.ppuX))
		{

			if ((y1 > yy * WorldRenderer.ppuY && y1 < (yy + getBounds().height/1.5f) * WorldRenderer.ppuY) ||
					(y1 + (aBox.height * 1.5f) > yy * WorldRenderer.ppuY && y1 + (aBox.height*1.5f )  < (yy + getBounds().height) * WorldRenderer.ppuY))
			{
				
				if (!simon)
				{
					if (!closed)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					if (canPress)
					{
						aSimonController.addToPressList(ID);
						canPress = false;
					}
				}
			}
		}
		
		return false;
	}
	
	public void setSimon(boolean aBool)
	{
		simon = aBool;
		
		if (aBool == true)
		{
			hasPearl = false;
		}
	}
	
	public void open()
	{
		closed = false;
	}
	
	public void close()
	{
		closed = true;
	}
	
	public void setCanPress()
	{
		canPress = true;
	}
	

}
