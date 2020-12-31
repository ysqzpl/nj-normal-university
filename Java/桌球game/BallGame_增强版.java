package com.xu.tt.test.other.小游戏.桌球;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BallGame_增强版 extends JFrame {

	Image ball = Toolkit.getDefaultToolkit().getImage("images/ball.png");
	Image desk = Toolkit.getDefaultToolkit().getImage("images/desk.png");

	double x = 100, y = 100; // 桌球的坐标

	double degree = 3.14 / 3; // 弧度，即为：60度

	/**
	 * @tips: 画窗口
	 */
	public void paint(Graphics g) {
		System.out.println("画窗口");
		g.drawImage(desk, 0, 0, null);
		g.drawImage(ball, (int) x, (int) y, null);

		x = x + 10 * Math.cos(degree);
		y = y + 10 * Math.sin(degree);

		// 碰到上下边界
		if (y > 500 - 40 - 30 || y < 40 + 40) { // 500是窗口高度；40是桌子边框，30是球直径；最后一个40是标题栏的高度
			degree = -degree; // 沿X轴对称变化
		}

		// 碰到左右边界
		if (x < 0 + 40 || x > 856 - 40 - 30) {
			degree = 3.14 - degree; // 沿Y轴对称变化
		}
	}

	/**
	 * @tips: 加载窗口
	 */
	void launchFrame() {
		setSize(856, 500);
		setLocation(50, 50);
		setVisible(true); // 让窗口可见
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
		BallGame_增强版 g = new BallGame_增强版();
		g.launchFrame();
	}

}
