package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SeaWeed {
	
	private Texture seaWeedTex;
	private TextureRegion[] seaWeedReg;
	private Animation seaWeedAni;
	private TextureRegion currentFrame;
	float stateTime = 0f;
	
	private Vector2 pos = new Vector2();
	
	private Random aRandom = new Random();
	
	
	private float alpha;
	
	public SeaWeed()
	{
	
		seaWeedTex = new Texture(Gdx.files.internal("images/sprSeaWeed.png"));
		TextureRegion[][] tmp = TextureRegion.split(seaWeedTex, seaWeedTex.getWidth()/ 8, 
				seaWeedTex.getHeight()/ 1);
		
		seaWeedReg = new TextureRegion[1 *8];
		int index = 0;
		for (int i = 0; i < 8; i++)
		{
			seaWeedReg[index++] = tmp[0][i];
		}
		seaWeedAni = new Animation(0.4f,seaWeedReg);
		
		pos.x = aRandom.nextFloat() * 10;
		pos.y = (aRandom.nextFloat() /2) + 0.2f;
		
		alpha = aRandom.nextFloat();
		
		stateTime = aRandom.nextInt(7);
		
	}
	
	public Vector2 getPos()
	{
		return pos;
	}
	
	public TextureRegion getCurrentFrame()
	{
		return currentFrame;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public void render()
	{
		stateTime += Gdx.graphics.getDeltaTime(); 
		currentFrame = seaWeedAni.getKeyFrame(stateTime, true);
	}

}
