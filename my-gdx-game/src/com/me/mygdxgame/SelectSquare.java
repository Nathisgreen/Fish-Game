package com.me.mygdxgame;

public class SelectSquare {
	
	private float x = 2;
	private float y = 5;
	private float x1 = 4;
	private float y1 = 1;
	
	private boolean isTouched = false;
	
	public SelectSquare()
	{
		
	}
	
	public void setTouched(Boolean aBool)
	{
		isTouched = aBool;
	}
	
	public boolean getTouched()
	{
		return isTouched;
	}
	
	public void setFirstCords(float aX, float aY)
	{
		x = aX;
		y = aY;
	}
	
	public void setSecondCords(float aX, float aY)
	{
		x1 = aX;
		y1 = aY;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getX1()
	{
		return x1;
	}
	
	public float getY1()
	{
		return y1;
	}

}
