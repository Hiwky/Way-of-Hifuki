package com.hiwky.wayofhifuki;

import android.graphics.Rect;

public class Enemy {
	private int power, speedX, centerX, centerY;
	public int health=5;
	private Background bg = GameScreen.getBg1();
	private Samurai hifuki=GameScreen.getSamurai();
	public Rect r = new Rect(0, 0, 0, 0);
	private int movementSpeed;

	public void update() {
		follow();
		centerX += speedX;
		speedX = bg.getSpeedX() * 5+movementSpeed;
		r.set(centerX - 25, centerY - 25, centerX+25, centerY+35);
		
		if(Rect.intersects(r, Samurai.yellowRed)){
			checkCollision();
		}
	}
	
	private void checkCollision(){
		if(Rect.intersects(r, Samurai.rect)||Rect.intersects(r, Samurai.rect2)||Rect.intersects(r,Samurai.rect3)){
			System.out.println("collision");
		}
	}
	
	public void follow(){
		if(centerX<-95||centerX>810){
			movementSpeed=0;
		} else if(Math.abs(hifuki.getCenterX()-centerX)<5){
			movementSpeed=0;
		} else {
			if(hifuki.getCenterX()>=centerX){
				movementSpeed=1;
			}else{
				movementSpeed=-1;
			}
		}
	}

	public void die() {

	}

	public void attack() {

	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}


	public void setPower(int power) {
		this.power = power;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
