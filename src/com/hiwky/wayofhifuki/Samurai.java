package com.hiwky.wayofhifuki;

import android.graphics.Rect;
import java.util.ArrayList;

public class Samurai {
	final int MOVESPEED = 5;
	final int JUMPSPEED = -15;

	private int centerX = 100;
	private int centerY = 300;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	private boolean readyToSlash = true;

	private static Background bg1 = GameScreen.getBg1();
	private static Background bg2 = GameScreen.getBg2();

	private int speedX = 0;
	private int speedY = 0;
	public static Rect rect = new Rect(0, 0, 0, 0);
	public static Rect rect2 = new Rect(0, 0, 0, 0);
	public static Rect rect3 = new Rect(0, 0, 0, 0);
	public static Rect yellowRed = new Rect(0, 0, 0, 0);
	public static Rect footleft = new Rect(0, 0, 0, 0);
	public static Rect footright = new Rect(0, 0, 0, 0);

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update() {
		if (speedX < 0) {
			centerX += speedX;
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		} else if (speedX == 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		} else {
			if (centerX <= 200 && speedX > 0) {
				centerX += speedX;
			} else {
				if (speedX > 0 && centerX > 200) {
					bg1.setSpeedX(-MOVESPEED / 5);
					bg2.setSpeedX(-MOVESPEED / 5);
				}
			}
		}
		
		centerY+=speedY;

			speedY += 1;
//			if (speedY>3){
//				jumped=true;
//			}


		if (centerX + speedX <= 60) {
			centerX = 61;
		}

		rect.set(centerX - 25, centerY - 63, centerX+29, centerY);
		rect2.set(centerX - 33, centerY, centerX+22, centerY+63);
		rect3.set(centerX + 30, centerY - 19, centerX+43, centerY-7);
		yellowRed.set(centerX - 90, centerY - 110, centerX+90, centerY+70);
		footleft.set(centerX-45, centerY+13, centerX+5, centerY+28);
		footright.set(centerX, centerY+13, centerX+35, centerY+28);
	}

	public void moveLeft() {
		if (ducked == false) {
			speedX = -MOVESPEED;
		}
	}

	public void moveRight() {
		if (ducked == false) {
			speedX = MOVESPEED;
		}
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}

	public void stopRight() {
		setMovingRight(false);
		stop();

	}

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}
		if (isMovingRight() == false && isMovingLeft() == true) {
			speedX = -MOVESPEED;
		}
		if (isMovingRight() == true && isMovingLeft() == false) {
			speedX = MOVESPEED;
		}
	}

	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	public void slash() {
		if (readyToSlash) {
			Projectile p = new Projectile(centerX + 37, centerY - 13);
			projectiles.add(p);
		}
	}

	public ArrayList getProjectiles() {
		return projectiles;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public boolean isDucked() {
		return ducked;
	}

	public void setMovingLeft(boolean mov) {
		movingLeft = mov;
	}

	public void setMovingRight(boolean mov) {
		movingRight = mov;
	}

	public void setDucked(boolean mov) {
		ducked = mov;
	}

	public boolean isReadyToSlash() {
		return readyToSlash;
	}

	public void setReadyToSlash(boolean readyToSlash) {
		this.readyToSlash = readyToSlash;
	}

}

