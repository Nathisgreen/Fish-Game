package com.me.mygdxgame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall {
	
	private Vector2 position;
	private Rectangle bounds = new Rectangle();
	
	private boolean active = true;
	
	public Wall (Vector2 pos)
	{
		position = pos;
		bounds.width = 1;
		bounds.height = 1;
	}
	
	public void update(float delta)
	{
		position.y -= 3 * delta;
		
		if (position.y < -1)
		{
			active = false;
		}
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public void setActive(boolean aActive)
	{
		active = aActive;
	}
	
	public void setup(Vector2 pos)
	{
		active = true;
		position = pos;
	}
	
}
