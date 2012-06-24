package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WallDestroyer {
	
	private final int RANDOMTIME = 40;
	private Vector2 position;
	private Rectangle bounds = new Rectangle();
	
	private boolean dir = false;
	
	private Random aRandom = new Random();
	
	private float changeTime;
	private float counter = 0;
	
	public WallDestroyer(Vector2 pos)
	{
		position = pos;
		bounds.width = 2;
		bounds.height = 1;
		changeTime = -1;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void update(float delta)
	{ 
		
		if (changeTime == -1)
		{
			changeTime = aRandom.nextInt(RANDOMTIME) + 100 * delta ;
		}
		
		if (counter < changeTime)
		{
			counter += 10 * delta;
		}
		else
		{
			counter = 0;
			dir = !dir;
			changeTime = aRandom.nextInt(RANDOMTIME) + 100 * delta ;
		}
		
		
		if (!dir)
		{
			if (position.x > 2)
			{
				position.x -= 1f * delta;
			}
		}
		else
		{
			if (position.x <6 )
			{
				position.x += 1f * delta;
			}
		}
	}
	
	public boolean checkWall(Wall aWall)
	{
		if ((aWall.getPosition().x  >= position.x && 
			aWall.getPosition().x <= position.x + bounds.width ||
			aWall.getPosition().x + aWall.getBounds().width >= position.x &&
			aWall.getPosition().x + aWall.getBounds().width <= position.x + bounds.width
				)  &&
			aWall.getPosition().y <= position.y &&
			aWall.getPosition().y+1 >= position.y - bounds.height)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
