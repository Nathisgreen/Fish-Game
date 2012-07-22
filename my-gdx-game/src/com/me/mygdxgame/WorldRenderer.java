package com.me.mygdxgame;

import java.util.logging.Level;

import com.me.mygdxgame.Block;
import com.me.mygdxgame.Bob;
import com.me.mygdxgame.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	
	private static WorldRenderer theRenderer;
	
	private World world;
	private OrthographicCamera cam;
	
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	//fish
	private Texture blockTexture;
	private Texture seaBed;
	//sea
	private TextureRegion seaBedRegion;
	private TextureRegion blockRegion;
	
	//clams
	//closed
	private Texture clamTexture;
	//bottom
	private Texture clamBottomTexture;
	//Top
	private Texture clamTopTexture;
	//pearl
	private Texture clamPearlTexture;
	
	//crab
	private Texture crabTexture;
	private Texture crabBulletTexture;
	
	//wall
	private Texture wallTexture;
	
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	static float ppuX; //pixels per unit on x axis
	static float ppuY; //pixels per unit on y axis
	
	private TextureAtlas atlas;
	private BitmapFont font;
	
	private SeaWeed[] weedArray;
	
	private FPSLogger aLogger = new FPSLogger();
	
	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
		ppuX = (float) width/ CAMERA_WIDTH;
		ppuY = (float) height/CAMERA_HEIGHT;
	}
	
	public WorldRenderer (World world, boolean debug)
	{
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH,CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
		
		weedArray = new SeaWeed[10];
		for (int i = 0; i < 10 ; i++)
		{
			weedArray[i] = new SeaWeed();
		}
		
		//atlas = new TextureAtlas("data/");
		font = new BitmapFont(Gdx.files.internal("data/ariel.fnt"), Gdx.files.internal("data/ariel.png"), false);
		//font.scale(1.5f);
	}
	
	private void loadTextures()
	{
		//load fish
		blockTexture = new Texture(Gdx.files.internal("images/fish.png"));
		blockRegion = new TextureRegion(blockTexture);
		//load seabed
		seaBed = new Texture(Gdx.files.internal("images/sandFloor.png"));
		seaBedRegion = new TextureRegion(seaBed);
		
		//load clams
		//Closed
		clamTexture = new Texture(Gdx.files.internal("images/sprClamClosed.png"));
		//bottom
		clamBottomTexture = new Texture(Gdx.files.internal("images/sprClamBottom.png"));
		//top
		clamTopTexture = new Texture(Gdx.files.internal("images/sprClamTop.png"));
		//pearl
		clamPearlTexture = new Texture(Gdx.files.internal("images/sprPearl.png"));
		
		//crab
		crabTexture = new Texture(Gdx.files.internal("images/sprCrab.png"));
		crabBulletTexture = new Texture(Gdx.files.internal("images/sprBullet.png"));
		
		//wall
		wallTexture = new Texture(Gdx.files.internal("images/sprWall.png"));
	}
	/**
	 * Render the world
	 */
	public void render()
	{	
		spriteBatch.begin();
		
		//draw back sea beds
		spriteBatch.setColor(0.6f,0.6f,0.6f,1f);
		spriteBatch.draw(seaBedRegion, 0, 1, 
				CAMERA_WIDTH * ppuX, 1.4f * ppuY);
		
		spriteBatch.setColor(0.8f,0.8f,0.8f,1f);
		spriteBatch.draw(seaBedRegion, 0, 1, 
				CAMERA_WIDTH * ppuX, 1.2f * ppuY);
		
		
		spriteBatch.setColor(1,1,1,1);
		
		//draw seaweed
		for (int i = 0; i < weedArray.length; i ++)
		{
			weedArray[i].render();
			
			spriteBatch.setColor(weedArray[i].getAlpha(), 1, weedArray[i].getAlpha(), 1);
			spriteBatch.draw(weedArray[i].getCurrentFrame(), weedArray[i].getPos().x * ppuX,
					weedArray[i].getPos().y * ppuY, 1.2f * ppuX, 1.2f * ppuY);
			
		}
	
		//draw front seabed
		spriteBatch.setColor(1,1,1,1);
		spriteBatch.draw(seaBedRegion, 0, 1, 
				CAMERA_WIDTH * ppuX, 0.8f * ppuY);
		

		//draw clams
		for (Clam aClam: world.getClamArray())
		{
			if (aClam.getClosed())
			{
			spriteBatch.draw(clamTexture, aClam.getPos().x * ppuX,
					aClam.getPos().y * ppuY,0.7f * ppuX,0.7f * ppuY);
			}
			else
			{
				spriteBatch.draw(clamBottomTexture, aClam.getPos().x * ppuX,
						aClam.getPos().y * ppuY,0.7f * ppuX,0.7f * ppuY);
				
				spriteBatch.draw(clamTopTexture, aClam.getPos().x * ppuX,
						aClam.getPos().y * ppuY,0.7f * ppuX,0.7f * ppuY);
				
				if (aClam.getPearl())
				{
					spriteBatch.draw(clamPearlTexture, aClam.getPos().x * ppuX,
							aClam.getPos().y * ppuY,0.7f * ppuX,0.7f * ppuY);
				}
			}
			
		}

		for (JellyFish aJelly: world.getJellyList())
		{	
			if (aJelly.getCurrentFrame() != null)
			{
				if (aJelly.getVelocity().x <= 0)
				{
							
					spriteBatch.draw(aJelly.getCurrentFrame(), (aJelly.getPosition().x - (0.7f/6 ) ) * ppuX, (aJelly.getPosition().y - (0.7f /6) )  * ppuY, 
							((0.7f ) * ppuX)/2, (0.7f * ppuY) /2,
							0.7f  * ppuX, 0.7f * ppuY, 1.0f, -1.0f, aJelly.getAngle() - 90 , false);	
							
				}
				else
				{
					spriteBatch.draw(aJelly.getCurrentFrame(), (aJelly.getPosition().x  -(0.7f /6) ) * ppuX, (aJelly.getPosition().y -(0.7f /6) )  * ppuY, 
							((0.7f ) * ppuX)/2, (0.7f * ppuY) /2,
							0.7f  * ppuX, 0.7f * ppuY, 1.0f , 1.0f , -aJelly.getAngle() - 90, false);
				}
			}

		}
		
		
		//Draw all the fishies
		drawBlocks();
		
		//Draw crab
		spriteBatch.setColor(1,1,1,1);
		if (world.getCrab().getActive())
		{
			spriteBatch.draw(crabTexture, (world.getCrab().getPosition().x ) * ppuX,
					(world.getCrab().getPosition().y) * ppuY ,0.7f * ppuX,0.7f * ppuY);
		}
		
		for (CrabBullet aBullet: world.getCrabBulletArray())
		{
			spriteBatch.draw(crabBulletTexture, (aBullet.getPosition().x ) * ppuX,
					(aBullet.getPosition().y) * ppuY ,0.2f * ppuX,0.2f * ppuY);
		}
		
		for (Wall aWall: CrabAvoidController.getWallArray())
		{
			if (aWall.getActive())
			{
				spriteBatch.draw(wallTexture, (aWall.getPosition().x ) * ppuX,
						(aWall.getPosition().y) * ppuY ,1 * ppuX,1 * ppuY);
			}
		}
		
		for (Pearl aPearl: CrabAvoidController.getPearlArray())
		{
			if (aPearl.getActive())
			{
				spriteBatch.draw(clamPearlTexture, (aPearl.getPosition().x ) * ppuX,
						(aPearl.getPosition().y) * ppuY ,0.7f * ppuX,0.7f * ppuY);
			}
		}
		
		spriteBatch.setColor(1,0,0,0.5f);
		spriteBatch.draw(wallTexture, (CrabAvoidController.getWallDestroyer().getPosition().x ) * ppuX,
				(CrabAvoidController.getWallDestroyer().getPosition().y) * ppuY ,2 * ppuX,1 * ppuY);
		spriteBatch.setColor(1,1,1,1);

		//draw texts
		for (MultiplyerText aText: world.getMultiplyerList())
		{
			if (aText.getActive())
			{
				float yy = WorldRenderer.getCameraHeight() * ppuY - aText.getPosition().y;
				font.setScale(aText.getTextSize());
				font.setColor(1, 0, 0, aText.getAlpha());
				
				if (aText.getType() == 0)
				{
					font.draw(spriteBatch,"X " + aText.getAmount(),(aText.getPosition().x / ppuX) * ppuX, yy );
				}
				else
				{
					font.draw(spriteBatch,"Level " + aText.getAmount(),(aText.getPosition().x / ppuX) * ppuX, yy );
				}
				
				font.setScale(1);
			}
		}
		
		for (ScoreText aText: world.getScoreArray())
		{
			font.setColor(0, 0, 0, aText.getAlpha());
			font.draw(spriteBatch,"+ " + aText.getScore(),(aText.getPos().x - ( 1/ppuX)) * ppuX ,aText.getPos().y * ppuY );
			font.draw(spriteBatch,"+ " + aText.getScore(),(aText.getPos().x + (1 / ppuY)) * ppuX ,aText.getPos().y * ppuY );
			font.draw(spriteBatch,"+ " + aText.getScore(),aText.getPos().x  * ppuX ,(aText.getPos().y -(1 / ppuY)) * ppuY );
			font.draw(spriteBatch,"+ " + aText.getScore(),aText.getPos().x  * ppuX ,(aText.getPos().y +(1 / ppuY)) * ppuY );
			font.setColor(0, 1, 0, aText.getAlpha());
			font.draw(spriteBatch,"+ " + aText.getScore(),aText.getPos().x * ppuX ,aText.getPos().y * ppuY );
		}
		
		//HUD
		font.setColor(1, 1, 1, 1);
		font.draw(spriteBatch,"Score: " + world.getScore(),10,20);
		
		
		font.draw(spriteBatch,"Life: " + world.getLife()  ,10,40);
		
		
		//debug draws
		/*
		for (Block aBlock: world.getBlocks())
		{
			float yyy = CAMERA_HEIGHT - (aBlock.getPosition().y );
			//font.draw(spriteBatch,"X:" + aBlock.getPosition().x * ppuX ,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY);
			//font.draw(spriteBatch,"Y:" + yyy * ppuY,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY -10);
			font.draw(spriteBatch,"X:" + aBlock.getVelocity().x ,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY);
			font.draw(spriteBatch,"Y:" + aBlock.getVelocity().y,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY -10);
		}
		
		for (JellyFish aBlock: world.getJellyList())
		{
			float yyy = CAMERA_HEIGHT - (aBlock.getPosition().y );
			font.draw(spriteBatch,"X:" + aBlock.getPosition().x * ppuX ,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY);
			font.draw(spriteBatch,"Y:" + yyy * ppuY,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY -10);
			//font.draw(spriteBatch,"X:" + aBlock.getVelocity().x ,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY);
			//font.draw(spriteBatch,"Y:" + aBlock.getVelocity().y,aBlock.getPosition().x * ppuX,aBlock.getPosition().y * ppuY -10);
		}
		
		font.draw(spriteBatch,"Block List #: " + world.getBlocks().size,10,450);
		font.draw(spriteBatch,"Selected List #: " + world.getNumberIn(),550,450);
		font.draw(spriteBatch,"Selected List Color: " + world.getColorIn(),550,430);
		
		SelectSquare selector = world.getSelector();
		if (selector.getTouched())
		{
			float yy = CAMERA_HEIGHT - (selector.getY() / ppuY) ;
			float yy1 = CAMERA_HEIGHT - (selector.getY1() / ppuY) ;
			font.draw(spriteBatch,"X: " +selector.getX()  ,selector.getX(),  yy * ppuY);
			font.draw(spriteBatch,"Y: " +selector.getY()  ,selector.getX(),  (yy - (10/ppuY)) * ppuY);
			
			font.draw(spriteBatch,"X1: " +selector.getX1()  ,selector.getX1(),  yy * ppuY);
			font.draw(spriteBatch,"Y: " +selector.getY()  ,selector.getX1(),  (yy - (10/ppuY)) * ppuY);
			
			font.draw(spriteBatch,"X: " +selector.getX()  ,selector.getX(),  yy1 * ppuY);
			font.draw(spriteBatch,"Y1: " +selector.getY1()  ,selector.getX(),  (yy1 - (10/ppuY)) * ppuY);
			
			font.draw(spriteBatch,"X1: " +selector.getX1()  ,selector.getX1(),  yy1 * ppuY);
			font.draw(spriteBatch,"Y1: " +selector.getY1()  ,selector.getX1(),  (yy1 - (10/ppuY)) * ppuY);
		}
		//*/
		
		spriteBatch.end();
		
		aLogger.log();
		//drawDebug();
		drawPowerUps();
		drawSelector();
		//Powerup Bar
		drawPowerupBar();

		if (world.getHurt())
		{
			debugRenderer.setProjectionMatrix(cam.combined);
			debugRenderer.setColor(1,0,0,1);
			debugRenderer.begin(ShapeType.FilledRectangle);
			
			debugRenderer.filledRect(0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY );
			debugRenderer.end();
			
		}		
	}
	
	public static float getCameraHeight()
	{
		return CAMERA_HEIGHT;
	}
	
	private void drawBlocks()
	{
		for (Block block: world.getBlocks())
		{	
			if(block.getColor() == 0)
			{
				spriteBatch.setColor(1, 0, 0, 1);
			}
			
			if(block.getColor() == 1)
			{
				spriteBatch.setColor(0, 1, 0, 1);
			}
			
			if(block.getColor() == 2)
			{
				spriteBatch.setColor(0, 0, 1, 1);
			}
			
			if(block.getColor() == 3)
			{
				spriteBatch.setColor(0.9f, 0.9f, 0f, 1);
			}
			
			if (block.getVelocity().x <= 0)
			{
				
			spriteBatch.draw(blockRegion, block.getPosition().x * ppuX, (block.getPosition().y - (5/ppuY))  * ppuY, 
					((0.5f *.5f) * ppuX)/2, (0.5f * ppuY) /2, (0.5f * .5f)  * ppuX, 0.5f * ppuY, 1.0f, 1.0f, block.getAngle() , true);
			}
			else
			{
			
			spriteBatch.draw(blockRegion, block.getPosition().x * ppuX, (block.getPosition().y - (5/ppuY)) * ppuY, 
					((0.5f *.5f) * ppuX)/2, (0.5f * ppuY) /2, (0.5f * .5f)  * ppuX, 0.5f * ppuY, 1.0f, -1.0f, -block.getAngle()  , false);
			
			}
			
		}
	}
	
	private void drawSelector()
	{
		SelectSquare selector = world.getSelector();
		if (selector.getTouched())
		{
			debugRenderer.setProjectionMatrix(cam.combined);
			debugRenderer.begin(ShapeType.Rectangle);
			debugRenderer.setColor(1, 0, 0, 1);
			float yy = CAMERA_HEIGHT - (selector.getY() / ppuY) ;
			debugRenderer.rect( selector.getX() / ppuX  , yy  ,((selector.getX1()/ppuX) - selector.getX()/ppuX),((selector.getY()/ppuY) - selector.getY1()/ppuY));
			debugRenderer.end();
		}
	}
	
	private void drawDebug()
	{
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		for (Block block : world.getBlocks())
		{
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			
			if(block.getColor() == 0)
			{
				debugRenderer.setColor(1, 0, 0, 1);
			}
			
			if(block.getColor() == 1)
			{
				debugRenderer.setColor(0, 1, 0, 1);
			}
			
			if(block.getColor() == 2)
			{
				debugRenderer.setColor(0, 0, 1, 1);
			}
			
			if(block.getColor() == 3)
			{
				debugRenderer.setColor(1, 0, 1, 1);
			}
			
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		
		for (JellyFish aJelly : world.getJellyList())
		{
			Rectangle rect = aJelly.getBounds();
			float x1 = aJelly.getPosition().x + rect.x;
			float y1 = aJelly.getPosition().y + rect.y;
			
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			
		}
		
		for (Clam aClam: world.getClamArray())
		{
			Rectangle rect = aClam.getBounds();
			float x1 = aClam.getPos().x + rect.x;
			float y1 = aClam.getPos().y + rect.y;
			
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			
		}
		
		Rectangle rectCrab = world.getCrab().getBounds();
		float x1 = world.getCrab().getPosition().x + rectCrab.x;
		float y1 = world.getCrab().getPosition().y + rectCrab.y;
		
		debugRenderer.rect(x1, y1, rectCrab.width, rectCrab.height);
		
		//System.out.println("FingBoxX: " + world.getFingerRect().x);
		//System.out.println("FingBoxY: " + world.getFingerRect().y);
		//System.out.println("FingBoxW: " + world.getFingerRect().width);
		debugRenderer.setColor(0, 0, 0, 1);
		debugRenderer.rect(world.getFingerRect().x / ppuX, CAMERA_HEIGHT - (world.getFingerRect().y / ppuY), world.getFingerRect().width / ppuX, world.getFingerRect().height / ppuY) ;
		
		debugRenderer.end();
		/*Bob bob = world.getBob();
		Rectangle rect = bob.getBounds();
		float x1 = bob.getPosition().x + rect.x;
		float y1 = bob.getPosition().y + rect.y;
		
		debugRenderer.setColor(0, 1, 0, 1);
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();	*/
	}
	
	private void drawPowerupBar()
	{
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		debugRenderer.setColor(80,80,80,1);
		debugRenderer.rect(600 / ppuX, 10 / ppuY, 200 / ppuX, 30 / ppuY);
		debugRenderer.setColor(1,1,0,1);
		debugRenderer.rect(599 / ppuX, 9 / ppuY, 202 / ppuX, 32 / ppuY);
		debugRenderer.setColor(0,1,0,1);
		debugRenderer.end();
		debugRenderer.begin(ShapeType.FilledRectangle);
		debugRenderer.filledRect(600 / ppuX, 11 / ppuY, (world.getPowerBar()) / ppuX, 29 / ppuY);
		debugRenderer.end();
	}
	
	private void drawPowerUps()
	{
		debugRenderer.begin(ShapeType.Rectangle);
		
		for (PowerUp aPowerup: world.getPowerupList())
		{
			if (aPowerup.getVisible())
			{
			Rectangle rect = aPowerup.getBounds();
			float x1 = aPowerup.getPosition().x + rect.x;
			float y1 = aPowerup.getPosition().y + rect.y;
			
			if(aPowerup.getColor() == 0)
			{
				debugRenderer.setColor(1, 0, 0, 1);
			}
			
			if(aPowerup.getColor() == 1)
			{
				debugRenderer.setColor(0, 1, 0, 1);
			}
			
			if(aPowerup.getColor() == 2)
			{
				debugRenderer.setColor(0, 0, 1, 1);
			}
			
			if(aPowerup.getColor() == 3)
			{
				debugRenderer.setColor(0.9f, 0.9f, 0f, 1);
			}
				debugRenderer.rect(x1, y1, rect.width, rect.height);
			}
		}
		
		debugRenderer.end();
		spriteBatch.begin();
		
		for (PowerUp aPowerup: world.getPowerupList())
		{
			if (aPowerup.getVisible())
			{
				font.draw(spriteBatch,aPowerup.getletter()  ,(aPowerup.getPosition().x + ((15)/ppuX)) *ppuX, ( aPowerup.getPosition().y + ((25)/ppuY)) * ppuY);
			}
		}
		spriteBatch.end();
	}

}
