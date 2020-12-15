package com.xu.tt.test.other.小游戏.桌球;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BallGame_简版 extends JFrame {

	Image ball = Toolkit.getDefaultToolkit().getImage("images/ball.png");
	Image desk = Toolkit.getDefaultToolkit().getImage("images/desk.png");

	double x = 100; // 小球的横坐标
	double y = 100; // 小球的纵坐标
	boolean right = true; // 方向

	/**
	 * @tips: 画窗口
	 */
	public void paint(Graphics g) {
		System.out.println("画窗口");
		g.drawImage(desk, 0, 0, null);
		g.drawImage(ball, (int) x, (int) y, null);
		if (right) {
			x = x + 10;
		} else {
			x = x - 10;
		}
		if (x > 856 - 40 - 30) { // 856是窗口宽度，40是桌子边框的宽度，30是小球的直径
			right = false;
		}
		if (x < 40) { // 40是桌子边框的宽度
			right = true;
		}
	}

	/**
	 * @tips: 窗口加载
	 */
	void launchFrame() {
		setSize(856, 500);
		setLocation(50, 50);
		setVisible(true);
		// 重画窗口，每秒画25次
		while (true) {
			repaint();
			try {
				Thread.sleep(40); // 40ms，1秒=1000毫秒，大约一秒画20次窗口
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("start...");
		BallGame_简版 g = new BallGame_简版();
		g.launchFrame();
	}

}
