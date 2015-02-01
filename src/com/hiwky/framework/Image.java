package com.hiwky.framework;

import com.hiwky.framework.Graphics.ImageFormat;

public interface Image {
	public int getWidth();
	public int getHeight();
	public ImageFormat getFormat();
	public void dispose();

}
