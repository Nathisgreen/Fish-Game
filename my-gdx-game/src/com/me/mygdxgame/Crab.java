package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Crab {

	private Vector2 position;
	private Rectangle bounds = new Rectangle();
	
	private float yy;
	private float y1;
	
	private float shootTime = 2;
	private float timer = 0;
	private boolean canShoot = true;

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
		if (!canShoot)
		{
			if (timer < shootTime )
			{
				timer += 10 *delta;
			}
			else
			{
				timer = 0;
				canShoot = true;
			}
		}
		
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
				if (canShoot)
				{
					canShoot = false;
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}
	
	public boolean checkWall(Wall aWall)
	{
		if ((position.x > aWall.getPosition().x && position.x < aWall.getPosition().x + aWall.getBounds().width)
				|| (position.x + bounds.width > aWall.getPosition().x && position.x + bounds.width < aWall.getPosition().x + aWall.getBounds().width))
			
		{
			
			if ((position.y > aWall.getPosition().y && position.y < aWall.getPosition().y + aWall.getBounds().height)
					|| (position.y + bounds.height > aWall.getPosition().y && position.y + bounds.height < aWall.getPosition().y + aWall.getBounds().height))

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
			return false;
		}
	}
	
	public boolean checkPearl(Pearl aPearl)
	{
		if ((position.x > aPearl.getPosition().x && position.x < aPearl.getPosition().x + aPearl.getBounds().width)
				|| (position.x + bounds.width > aPearl.getPosition().x && position.x + bounds.width < aPearl.getPosition().x + aPearl.getBounds().width))
			
		{
			
			if ((position.y > aPearl.getPosition().y && position.y < aPearl.getPosition().y + aPearl.getBounds().height)
					|| (position.y + bounds.height > aPearl.getPosition().y && position.y + bounds.height < aPearl.getPosition().y + aPearl.getBounds().height))

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
			return false;
		}
	}
	
}
