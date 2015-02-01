package com.hiwky.wayofhifuki;

import com.hiwky.framework.Image;
import com.hiwky.framework.Sound;
import com.hiwky.framework.Music;

public class Assets {
	
	public static Image menu, splash, background, character, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
	public static Image tiledirt, tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, characterJump, characterDown;
	public static Image button;
	public static Sound click;
	public static Music theme;
	public static void load(WayOfHifukiGame wayofhifukiGame){
		theme=wayofhifukiGame.getAudio().createMusic("menutheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}
}
