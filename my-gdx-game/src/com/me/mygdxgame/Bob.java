package com.me.mygdxgame;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bob {
	
	public enum State{
		IDLE, WALKING, JUMPING, DYING
	}
	
	static final float SPEED = 4f;
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.5f;
	
	Vector2 	position = new Vector2();
	Vector2 	accelaration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State 		state = State.IDLE;
	boolean 	facingLeft = true;
	
	public Bob(Vector2 position)
	{
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public Vector2 getVelocity()
	{
		return this.velocity;
	}
	
	public Vector2 getAcceleration()
	{
		return this.accelaration;
	}
	
	public void setState(State newState)
	{
		this.state = newState;
	}

	public void update(float delta)
	{
		position.add(velocity.tmp().mul(delta));
	}
	
	public void setFacingLeft(boolean aBool)
	{
		this.facingLeft = aBool;
	}
}
