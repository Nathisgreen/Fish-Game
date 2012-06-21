package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Crab {

	private Vector2 position;
	private Rectangle bounds = new Rectangle();
	
	private float yy;
	private float y1;
	

	public Crab(Vector2 pos)
	{
		position = pos;
		bounds.width = 0.7f;
		bounds.height = 0.6f;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void update(float delta)
	{
		
		if (position.x < 0 )
		{
			position.x = 1 / WorldRenderer.ppuX;
		}
		
		if (position.x > 9.3f)
		{
			position.x = 9.3f;
			
		}

		position.x = position.x + (Gdx.input.getAccelerometerY()) * delta ;

	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public boolean checkInside(Rectangle aBox) {
		
		yy = WorldRenderer.getCameraHeight() - getPosition().y;
		y1 = aBox.y;

		if ((aBox.x> position.x * WorldRenderer.ppuX && aBox.x < (position.x + getBounds().width) * WorldRenderer.ppuX)
				|| (aBox.x + aBox.width> position.x * WorldRenderer.ppuX && aBox.x + aBox.width < (position.x + getBounds().width) * WorldRenderer.ppuX))
		{

			if ((y1 > yy * WorldRenderer.ppuY && y1 < (yy + getBounds().height) * WorldRenderer.ppuY) ||
					(y1 + (aBox.height ) > yy * WorldRenderer.ppuY && y1 + (aBox.height )  < (yy + getBounds().height) * WorldRenderer.ppuY))
			{
				return true;
			}
		}
		
		return false;
	}
	
}
