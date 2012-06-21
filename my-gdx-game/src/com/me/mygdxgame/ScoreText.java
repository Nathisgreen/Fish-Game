package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class ScoreText {
	
	private int score;
	private Vector2 pos = new Vector2();
	private float alpha = 1f;
	
	public ScoreText(int aScore, Vector2 aPos)
	{
		score = aScore;
		pos = aPos;
	}
	
	public void update (float delta)
	{
		pos.y += 1 * delta;
		if (alpha > 0.1)
		{
			alpha -= 1 * delta;
		}
		else
		{
			alpha = 0;
		}
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public Vector2 getPos()
	{
		return pos;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setup(int aScore, Vector2 aPos)
	{
		alpha = 1;
		score = aScore;
		pos = aPos;
	}
}
