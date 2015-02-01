package com.hiwky.wayofhifuki;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import android.graphics.Paint;
import android.graphics.Color;

import com.hiwky.framework.Game;
import com.hiwky.framework.Graphics;
import com.hiwky.framework.Image;
import com.hiwky.framework.Screen;
import com.hiwky.framework.Input.TouchEvent;

public class GameScreen extends Screen {
	enum GameState{
		Ready, Running, Paused, GameOver
	}
	
	GameState state=GameState.Ready;
	
	
	int livesLeft=1;
	Paint paint, paint2;
	
	private static Background bg1,bg2;
	private static Samurai hifuki;
	public static Heliboy hb,hb2;
	private Image currentSprite, character,heliboy,
	heliboy2, heliboy3, heliboy4, heliboy5;
	private Animation hanim;
	private ArrayList tilearray=new ArrayList();

	public GameScreen(Game game) {
		super(game);
		
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		hifuki = new Samurai();
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		character=Assets.character;
		currentSprite=character;
		
		heliboy = Assets.heliboy;
		heliboy2 = Assets.heliboy2;
		heliboy3 = Assets.heliboy3;
		heliboy4 = Assets.heliboy4;
		heliboy5 = Assets.heliboy5;
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		loadMap();
		
		paint=new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		paint2= new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
	}
	
	private void loadMap() {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		Scanner scanner = new Scanner(WayOfHifukiGame.map);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line==null){
				break;
			}
			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}
			}
		}

	}
	
	@Override
	public void update(float deltaTime){
		List<TouchEvent> touchEvents=game.getInput().getTouchEvents();
		if (state==GameState.Ready)
			updateReady(touchEvents);
		if(state==GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if(state==GameState.Paused)
			updatePaused(touchEvents);
		if(state==GameState.GameOver)
			updateGameOver(touchEvents);
	}
	
	private void updateReady(List<TouchEvent> touchEvents){
		
		if(touchEvents.size()>0)
			state=GameState.Running;
	}
	
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime){
		int len=touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event=touchEvents.get(i);
			
			if (event.type==TouchEvent.TOUCH_DOWN){
				if (inBounds(event, 0, 285, 65, 65)) {
					hifuki.jump();
					currentSprite=character;
					hifuki.setDucked(false);
				}

				else if (inBounds(event, 0, 350, 65, 65)) {

					if (hifuki.isDucked() == false && hifuki.isJumped() == false
							&& hifuki.isReadyToSlash()) {
						hifuki.slash();
					}
				}

				else if (inBounds(event, 0, 415, 65, 65)
						&& hifuki.isJumped() == false) {
					currentSprite = Assets.characterDown;
					hifuki.setDucked(true);
					hifuki.setSpeedX(0);

				}

				if (event.x > 400) {
					// Move right.
					hifuki.moveRight();
					hifuki.setMovingRight(true);

				}

			}
			
			if(event.type==TouchEvent.TOUCH_UP){
				if (inBounds(event, 0, 415, 65, 65)) {
					currentSprite=character;
					hifuki.setDucked(false);

				}

				if (inBounds(event, 0, 0, 35, 35)) {
					pause();

				}

				if (event.x > 400) {
					// Move right.
					hifuki.stopRight();
				}
			}
		}
		if(livesLeft==0){
			state=GameState.GameOver;
		}
		
		hifuki.update();
		if(hifuki.isJumped()){
			currentSprite=Assets.characterJump;
		}else if(hifuki.isJumped()==false && hifuki.isDucked()==false){
			currentSprite=character;
		}
		ArrayList projectiles = hifuki.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				projectiles.remove(i);
			}
		}
		
		updateTiles();
		hb.update();
		hb2.update();
		bg1.update();
		bg2.update();
		animate();
		
		if(hifuki.getCenterY()>500){
			state=GameState.GameOver;
		}
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	
	private void updatePaused(List<TouchEvent> touchEvents){
		int len=touchEvents.size();
		for(int i =0;i<len;i++){
			TouchEvent event=touchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
		
	}
	
	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 800, 480)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}
	
	private void updateTiles(){
		for(int i=0;i<tilearray.size();i++){
			Tile t=(Tile)tilearray.get(i);
			t.update();
		}
	}
	
	@Override
	public void paint(float deltaTime){
		Graphics g=game.getGraphics();
		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background,  bg2.getBgX(), bg2.getBgY());
		paintTiles(g);
		
		ArrayList projectiles=hifuki.getProjectiles();
		for(int i=0;i<projectiles.size();i++){
			Projectile p=(Projectile)projectiles.get(i);
			g.drawRect(p.getX(), p.getY(), 5, 30, Color.WHITE);
		}
		g.drawImage(currentSprite, hifuki.getCenterX() - 43,
				hifuki.getCenterY() - 63);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48);
		//draw ui
		
		//draw collision boxes
		//
//		g.drawRect(Samurai.rect.left, Samurai.rect.top,
//				 Samurai.rect.width(), Samurai.rect.height(), Color.GREEN);
//		g.drawRect(Samurai.rect2.left, Samurai.rect2.top,
//				 Samurai.rect2.width(), Samurai.rect2.height(), Color.RED);
//		//
		if(state==GameState.Ready)
			drawReadyUI();
		if(state==GameState.Running)
			drawRunningUI();
		if(state==GameState.Paused)
			drawPausedUI();
		if(state==GameState.GameOver)
			drawGameOverUI();
	}
	
	private void paintTiles(Graphics g){
		for(int i=0;i<tilearray.size();i++){
			Tile t=(Tile)tilearray.get(i);
			if(t.type!=0){
				g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
			}
		}
	}
	
	public void animate(){
		hanim.update(50);
	}
	
	private void nullify(){
		paint=null;
		bg1=null;
		bg2=null;
		hifuki=null;
		hb=null;
		hb2=null;
		currentSprite=null;
		character=null;
		heliboy=null;
		heliboy2=null;
		heliboy3=null;
		heliboy4=null;
		heliboy5=null;
		hanim=null;
		System.gc();
	}
	
	private void drawReadyUI(){
		Graphics g=game.getGraphics();
		
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to start", 400, 240, paint);
	}
	
	private void drawRunningUI(){
		Graphics g=game.getGraphics();
		g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
		g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
		g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
		g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);
	}
	
	private void drawPausedUI(){
		Graphics g=game.getGraphics();
		//darken screen
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);
	}
	private void drawGameOverUI(){
		Graphics g=game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER", 640, 300, paint);
	}
	
	@Override
	public void pause(){
		if(state==GameState.Running)
			state=GameState.Paused;
	}
	
	@Override
	public void resume(){
		if(state==GameState.Paused)
			state=GameState.Running;
		
	}
	
	@Override
	public void dispose(){
		
	}
	
	@Override
	public void backButton(){
		pause();
	}
	
	private void goToMenu(){
		game.setScreen(new MainMenuScreen(game));
	}
	
	public static Background getBg1(){
		return bg1;
	}
	
	public static Background getBg2(){
		return bg2;
	}
	
	public static Samurai getSamurai(){
		return hifuki;
	}

}
