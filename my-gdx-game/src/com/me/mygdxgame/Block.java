package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public float SIZE = 0.25f;
	
	public Vector2		position = new Vector2();
	private Vector2		velocity = new Vector2();
	private Vector2 	velocityNew = new Vector2();
	private Rectangle	bounds = new Rectangle();
	private int			iColor = 0;
	private Random aRandom = new Random();
	private float speed = 5;
	
	private float rotateSpeed = 0.05f;
	
	private int counter = 0;
	private int alarm0 = 0;
	
	public Block(Vector2 pos)
	{
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		
		//first velocity
		this.velocity.x = -1 + (aRandom.nextFloat()* 2);
		this.velocity.y = -1 + (aRandom.nextFloat()* 2);
		
		//second velocity
		this.velocityNew.x = -1 + (aRandom.nextFloat()* 2);
		this.velocityNew.y = -1 + (aRandom.nextFloat()* 2);
		
		iColor = aRandom.nextInt(4);
		alarm0 = aRandom.nextInt(120)+ 30;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	public Vector2 getPosition()
	{
		return position;
	}
	public int getColor()
	{
		return iColor;
	}
	public void setRotateSpeed(float aSpeed)
	{
		rotateSpeed = aSpeed;
	}
	public float getAngle()
	{
		if (velocity.x > 0)
		{
			return (float) Math.atan2(velocity.x, velocity.y) * 57;
		}
		else
		{
			return (float) Math.atan2(velocity.x, velocity.y) * -57;
		}
	}
	public Vector2 getVelocity()
	{
		return velocity;
	}
	public void setBounds(float size)
	{
		bounds.width = size;
		bounds.height = size;
	}
	
	public void update(float delta)
	{
		if (this.position.x > 10){this.position.x = -0.5f;}
		if (this.position.x < -1){this.position.x = 10;}
		if ( this.position.y > 7.5){ this.position.y = 0;}
		if ( this.position.y < -1){ this.position.y = 7.5f;}
		
		if (counter < alarm0)
		{
			counter++;
		}
		else
		{
			this.velocityNew.x =  -1 + (aRandom.nextFloat()* 2);
			this.velocityNew.y =  -1 + (aRandom.nextFloat()* 2);
			
			alarm0 = aRandom.nextInt(120)+ 30;
			counter = 0;
			
		}
		
		if (this.position.x > 9){velocityNew.x = -1f;}
		if (this.position.x < 0.8f){velocityNew.x = 1f;}
		if ( this.position.y > 6f){ velocityNew.y = -1f;}
		if ( this.position.y < 0.8f){ velocityNew.y = 1f;}
		
		if (velocity.x - velocityNew.x > 0.10 ||  velocity.x - velocityNew.x < -0.10)
		{
			if (velocity.x > velocityNew.x)
			{
				velocity.x -= rotateSpeed;
			}
			else
			{
				velocity.x += rotateSpeed;
			}
		}
		
		if (velocity.y - velocityNew.y > 0.10 ||  velocity.y - velocityNew.y < -0.10)
		{
			if (velocity.y > velocityNew.y)
			{
				velocity.y -= rotateSpeed;
			}
			else
			{
				velocity.y += rotateSpeed;
			}
		}
		
		position.add(velocity.tmp().mul(delta));

	}
	
	
	
	
}
