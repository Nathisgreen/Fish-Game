package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class MultiplyerText {
	
	private int aAmount = 0;
	private Vector2 position;
	private float textSize = 1f;
	private float maxSize = 2.5f;
	private float alpha = 1f;
	
	private boolean active = true;
	
	private int type = 0;
	
	public MultiplyerText(int Amount, Vector2 aPosition, int aType, float aSize, float aMaxSize)
	{
		position = aPosition;
		aAmount = Amount;
		type = aType;
		textSize = aSize;
		maxSize = aMaxSize;
	}
	
	public int getAmount()
	{
		return aAmount;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public float getTextSize()
	{
		return textSize;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void update()
	{
		alpha -= 0.05;
		if (textSize < maxSize)
		{
			textSize += 0.03f;
		}
		else
		{
			active = false;
		}
	}	
	
	public void setup(int Amount, Vector2 aPosition, int aType, float aSize, float aMaxSize)
	{
		position = aPosition;
		aAmount = Amount;
		type = aType;
		textSize = aSize;
		maxSize = aMaxSize;
		active = true;
	}
}
