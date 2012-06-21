package com.me.mygdxgame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CrabBullet {

	private Vector2 position;
	
	private Rectangle bounds;
	
	private Vector2 velocity = new Vector2();
	
	private boolean active = true;
	
	public CrabBullet(Vector2 pos)
	{
		position = pos;
		velocity.y = 4;
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
	
	public void update(float delta)
	{
		position.y += velocity.y *delta;
		
		if (position.y > 7)
		{
			active = false;
		}
	}
	
	public void setup(Vector2 pos)
	{
		position = pos;
		active = true;
	}
	
}
