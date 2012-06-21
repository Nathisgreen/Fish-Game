package com.me.mygdxgame;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;

import com.me.mygdxgame.World;
import com.me.mygdxgame.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.InputProcessor;



public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	private int width, height;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.7f, 0.85f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.update(delta);
		renderer.render();
		
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		
		this.width = width;
		this.height = height;
		
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world,true);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {	
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Keys.LEFT)
		{
			controller.leftPressed();
		}
		
		if (keycode == Keys.RIGHT)
		{
			controller.rightPressed();
		}
		
		if (keycode == Keys.Z)
		{
			controller.jumpPressed();
		}
		
		if (keycode == Keys.X)
		{
			controller.firePressed();
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
		{
			controller.leftReleased();
		}
		
		if (keycode == Keys.RIGHT)
		{
			controller.rightReleased();
		}
		
		if (keycode == Keys.Z)
		{
			controller.jumpReleased();
		}
		
		if (keycode == Keys.X)
		{
			controller.fireReleased();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		world.getSelector().setTouched(true);
		world.getSelector().setFirstCords(x, y);
		world.getSelector().setSecondCords(x, y);
		world.setFingerPos(x,y);
		//System.out.println("Touched x " + x);
		//System.out.println("Touched y " + y);
		
		if (!Gdx.app.getType().equals(ApplicationType.Android))
		{
			return false;
		}
		
		if (x < width /2 && y > height / 2)
		{
			controller.leftPressed();
		}
		
		if (x > width /2 && y > height /2 )
		{
			controller.rightPressed();
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		world.checkSelected();
		world.getSelector().setTouched(false);
		world.getSelector().setFirstCords(0, 0);
		world.getSelector().setSecondCords(0, 0);
		if (!Gdx.app.getType().equals(ApplicationType.Android))
		{
			return false;
		}
		
		if (x < width /2 && y > height / 2)
		{
			controller.leftReleased();
		}
		
		if (x > width /2 && y > height /2 )
		{
			controller.rightReleased();
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		world.setFingerPos(x,y);
		if (x - world.getSelector().getX1() < -15 || x - world.getSelector().getX1() > 15 )
		{
			world.getSelector().setSecondCords(x, world.getSelector().getY1());
		}
		
		if (y - world.getSelector().getY1() < -15 || y - world.getSelector().getY1() > 15 )
		{
			world.getSelector().setSecondCords(world.getSelector().getX1(),y);
		}
		//world.getSelector().setSecondCords(x, y);
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		//world.setFingerPos(x,y);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
