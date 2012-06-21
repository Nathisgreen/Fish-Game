package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Block {

	private int time = 0;
	private int counter = 0;
	private String letter;
	private int type = 0;
	private boolean destroy = false;
	private boolean visible = true;
	private boolean flicker = false;
	private int flickerCount = 0;
	
	public PowerUp(Vector2 pos, int aType, int aTime) {
		super(pos);
		type = aType;
		time = aTime;
		
		//freeze
		if (type == 0)
		{
			letter = "F";
		}
	}
	
	public void update(float delta)
	{
		super.update(delta);
		
		if (counter < time)
		{
			counter++;
		}
		else
		{
			destroy = true;
		}
		
		if (counter > (time * .8))
		{
			flicker = true;
		}
		
		if (flicker)
		{
			if (flickerCount < 7)
			{
				flickerCount ++;
			}
			else
			{
				visible = !visible;
				flickerCount = 0;
			}
		}
	}
	
	public boolean getDestroy()
	{
		return destroy;
	}
	
	public String getletter()
	{
		return letter;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public int getType()
	{
		return type;
	}

}
