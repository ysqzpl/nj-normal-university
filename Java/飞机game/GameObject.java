package com.xu.tt.test.other.小游戏.飞机;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {

	Image img; // 该物体对应的图片对象
	double x, y; // 该物体的坐标
	int speed; // 该物体的运行速度
	int width, height; // 该物体所在矩形区域的宽度和高度

	/**
	 * 怎么样绘制本对象
	 */
	public void drawMySelf(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}

	public GameObject(Image img, double x, double y) {
		this.img = img;
		this.x = x;
		this.y = y;
		if (img != null) {
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
		}
	}

	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}

	public GameObject() {
	}

	/**
	 * 返回物体对应矩形区域，便于后续在碰撞检测中使用
	 */
	public Rectangle getRect(boolean flag) {
		if (flag)
			return new Rectangle((int) x, (int) y, width, height);
		else
			return new Rectangle((int) x, (int) y, 0, 0);
	}

}
