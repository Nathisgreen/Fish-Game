package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CrabAvoidController {

	private static Array<Wall> wallArray = new Array<Wall>();
	private static Array<Pearl> pearlArray = new Array<Pearl>();
	
	private float createTime = 3;
	private float time = 0;
	
	private static WallDestroyer theDestroyer;
	
	private Wall lastWall;
	
	private boolean starting = true;
	
	private Random aRandom = new Random();
	
	public boolean active = false;
	
	public CrabAvoidController()
	{
		theDestroyer = new WallDestroyer(new Vector2 (4 ,9)); //9
		createTime = aRandom.nextInt(1) + 0.5f;
	}
	
	public void update(float delta, int theLevel)
	{
		if (active)
		{
			if (starting)
			{
				for (int i = 0; i < 10; i++)
				{
					if (lastWall == null)
					{
						lastWall = createWall(new Vector2(1 * i, 9));
					}
					else
					{
						createWall(new Vector2(1 * i, 9));
					}
				}
				starting = false;
			}
			
	
			if (lastWall.getPosition().y < 7.9f + 10 *delta)
			{
				lastWall = null;
				
				for (int i = 0; i < 10; i++)
				{
					if (lastWall == null)
					{
						lastWall = createWall(new Vector2(1 * i, 9));
					}
					else
					{
						createWall(new Vector2(1 * i, 9));
					}
				}
			}
			
			
			for (Wall aWall: wallArray)
			{
	
				if (aWall.getActive())
				{	
					aWall.update(delta);
					
					if (theDestroyer.checkWall(aWall))
					{
						aWall.setActive(false);
					}
					
					
				}
				
			}
			
			//pearl creation timer
			if (time < createTime)
			{
				time += 1 * delta;
			}
			else
			{	
				createPearl(new Vector2(((theDestroyer.getPosition().x)  + aRandom.nextFloat() * 1.5f  ),
								theDestroyer.getPosition().y - theDestroyer.getBounds().height));
				
				time = 0;
				createTime = aRandom.nextFloat() + 0.1f;
			}
			
			for (Pearl aPearl: pearlArray)
			{
				aPearl.update(delta);
			}
			
			theDestroyer.update(delta);
		}
	}
	
	public static Array<Wall> getWallArray()
	{
		return wallArray;
	}
	
	public static WallDestroyer getWallDestroyer()
	{
		return theDestroyer;
	}
	
	public static Array<Pearl> getPearlArray()
	{
		return pearlArray;
	}
	
	/**
	 * Finds or creates a Wall
	 * @param pos	the position on the screen the text will be drawn
	 */
	private Wall createWall(Vector2 pos)
	{		
		//pooling system for Walls
		Boolean hasCreated = false;
		
		//first look for one that isnt being used 
		if (wallArray.size != 0)
		{
			for(Wall aWall: wallArray)
			{
				//if the alpha is 0 its done being used
				if (!aWall.getActive())
				{
					//System.out.println("Reused");
					//flag we found one to stop new one being made
					hasCreated = true;
					//reset up the text with new position
					aWall.setup(pos);
					//stop the loop as we got what we need
					return aWall;
				}
			}
		}
		
		//if one wasn't found
		if (!hasCreated)
		{
			Wall aWall = new Wall(pos);

			//create a new one and add to array
			wallArray.add(aWall);
			
			return aWall;
		}
		
		return null;
	}
	
	/**
	 * Finds or creates a Pearl
	 * @param pos	the position on the screen of the Pearl
	 */
	private void createPearl(Vector2 pos)
	{		
		//pooling system
		Boolean hasCreated = false;
		
		//first look for one that isnt being used 
		if (pearlArray.size != 0)
		{
			for(Pearl aPearl: pearlArray)
			{
				//if the alpha is 0 its done being used
				if (!aPearl.getActive())
				{
					//System.out.println("Reused");
					//flag we found one to stop new one being made
					hasCreated = true;
					//reset up the text with new position
					aPearl.setup(pos);
					//stop the loop as we got what we need
					break;
				}
			}
		}
		
		//if one wasn't found
		if (!hasCreated)
		{
			//create a new one and add to array
			pearlArray.add(new Pearl(pos));
			
		}
		
	}

	public void reset() 
	{
		wallArray.clear();
		pearlArray.clear();
		lastWall = null;
		starting = true;
		active = false;
	}
	
	public void begin()
	{
		active = true;
	}
	
	public void end()
	{
		active = false;
		reset();
	}
}
