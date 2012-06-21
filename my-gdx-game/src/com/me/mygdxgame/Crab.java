package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Crab {

	private Vector2 position;
	private Rectangle bounds = new Rectangle();

	public Crab(Vector2 pos)
	{
		position = pos;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void update(float delta)
	{
		position.x = position.x + (Gdx.input.getAccelerometerY()) * delta ;
	}
	
}
