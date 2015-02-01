package com.hiwky.wayofhifuki;

import com.hiwky.framework.Image;
import android.graphics.Rect;

public class Tile {
	private int tileX,tileY,speedX;
	int type;
	public Image tileImage;
	private Rect r;
	
	private Background bg=GameScreen.getBg1();

	public Tile(int x, int y, int typeInt) {
		tileX=x*40;
		tileY=y*40;
		type=typeInt;
		
		r=new Rect();
		
		if(type==1){
			tileImage=Assets.tilegrassBot;	
		} else if (type==2){
			tileImage=Assets.tilegrassBot;
		} else if (type==5){
			tileImage=Assets.tiledirt;
		} else if (type==8){
			tileImage=Assets.tilegrassTop;
		} else if (type==4){
			tileImage=Assets.tilegrassLeft;
		} else if(type==6){
			tileImage=Assets.tilegrassRight;
		} else {
			type=0;
		}
	}
	
	public void update(){
//		if(type==1){
//			if(bg.getSpeedX()==0){
//				speedX=-1;
//			}else{
//				speedX=-2;
//			}
//		}else{
//			speedX=bg.getSpeedX()*5;
//		}
		speedX=bg.getSpeedX()*5;
		tileX+=speedX;
		r.set(tileX, tileY, tileX+40, tileY+40);
		if(Rect.intersects(r, Samurai.yellowRed)&&type!=0){
			checkVerticalCollision(Samurai.rect,Samurai.rect2);
			checkSideCollision(Samurai.rect, Samurai.rect3, Samurai.footleft, Samurai.footright);
		}
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}
	
	public void checkVerticalCollision(Rect rtop, Rect rbot){
		if(Rect.intersects(rtop, r)){
			
		}
		if(Rect.intersects(rbot, r)&&type==8){
			GameScreen.getSamurai().setJumped(false);
			GameScreen.getSamurai().setSpeedY(0);
			GameScreen.getSamurai().setCenterY(tileY-63);
			
		}
	}
	
	public void checkSideCollision(Rect top, Rect hand, Rect leftfoot, Rect rightfoot){
		if(type!=5&&type!=2&&type!=0){
			if(Rect.intersects(top, r)){
				GameScreen.getSamurai().setCenterX(tileX+65);
				GameScreen.getSamurai().setSpeedX(0);
			}else if (Rect.intersects(leftfoot,r)){
				GameScreen.getSamurai().setCenterX(tileX+85);
				GameScreen.getSamurai().setSpeedX(0);
			}
			
			if(Rect.intersects(hand, r)){
				GameScreen.getSamurai().setCenterX(tileX-43);
				GameScreen.getSamurai().setSpeedX(0);
			}else if (Rect.intersects(rightfoot, r)){
				GameScreen.getSamurai().setCenterX(tileX-35);
				GameScreen.getSamurai().setSpeedX(0);
			}
		}
	}

}

