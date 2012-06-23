package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CrabAvoidController {

	private static Array<Wall> wallArray = new Array<Wall>();
	
	private float createTime = 3;
	private float time = 0;
	
	private static WallDestroyer theDestroyer;
	
	public CrabAvoidController()
	{
		theDestroyer = new WallDestroyer(new Vector2 (4 ,9));
	}
	
	public void update(float delta)
	{
		if (time < createTime)
		{
			time += 10 * delta;
		}
		else
		{
			time = 0;
			
			for (int i = 0; i < 10; i++)
			{
				createWall(new Vector2(1 * i, 9));
				//wallArray.add(new Wall(new Vector2(1 * i, 9)));
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
		
		theDestroyer.update(delta);
	}
	
	public static Array<Wall> getWallArray()
	{
		return wallArray;
	}
	
	public static WallDestroyer getWallDestroyer()
	{
		return theDestroyer;
	}
	
	/**
	 * Finds or creates a Wall
	 * @param pos	the position on the screen the text will be drawn
	 */
	private void createWall(Vector2 pos)
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
					break;
				}
			}
		}
		
		//if one wasn't found
		if (!hasCreated)
		{
			//System.out.println("Created new");
			//create a new one and add to array
			wallArray.add(new Wall(pos));
		}
	}

}
