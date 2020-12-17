package com.xu.tt.test.other.小游戏.飞机;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class MyGameFrame extends Frame {

	// 将背景图片与飞机图片定义为成员变量
	Image bgImg = GameUtil.getImage("com/xu/tt/test/other/小游戏/飞机/images/bg.jpg");
	Image planeImg = GameUtil.getImage("com/xu/tt/test/other/小游戏/飞机/images/plane.png");

	Plane plane = new Plane(planeImg, 300, 300, 10);
	Explode bao; // 创建爆炸对象
	ArrayList<Shell> shellList = new ArrayList<Shell>();
	static int count = 0;
	// 将飞机的坐标设置为变量，初始值为（200,200）
	int planeX = 200;
	int planeY = 200;
	Date startTime = new Date(); // 游戏起始时刻
	Date endTime; // 游戏结束时刻

	/**
	 * LOOK 初始化窗口
	 */
	public void launchFrame() {
		// 在游戏窗口打印标题
		setTitle("尚学堂学员_程序猿作品");
		// 窗口默认不可见，设为可见
		setVisible(true);
		// 窗口大小：宽度500，高度500
		setSize(ConstantP.GAME_WIDTH, ConstantP.GAME_HEIGHT);
		// 窗口左上角顶点的坐标位置
		setLocation(300, 300);

		// 增加关闭窗口监听，这样用户点击右上角关闭图标，可以关闭游戏程序
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		new PaintThread().start(); // 启动重画窗口的线程
		addKeyListener(new KeyMonitor());// 给窗口增加键盘的监听

		// 初始化，生成一堆炮弹
		for (int i = 0; i < 50; i++) {
			Shell b = new Shell();
			shellList.add(b);
		}
	}

	/**
	 * LOOK paint方法作用是：画出整个窗口及内部内容。被系统自动调用。
	 */
	@Override
	public void paint(Graphics g) {
		// 画一些形状
		// super.paint(g); // 添加上这行代码, 表示再原有基础上重绘.
		// // 从坐标点(100,50)到(400,400)画出直线
		// g.drawLine(100, 50, 400, 400);
		// // 画出矩形。矩形左上角顶点坐标(100,50)，宽度300，高度300
		// g.drawRect(100, 50, 300, 300);
		// // 画出椭圆。椭圆外切矩形为：左上角顶点(100,50),宽度300，高度300
		// g.drawOval(100, 50, 300, 300);

		// 写死飞机位置
		// g.drawImage(planeImg, 200, 200, null);

		// 不再是写死的位置
		// g.drawImage(planeImg, planeX, planeY, null);
		// // 每次画完以后改变飞机的x坐标
		// planeX += 3;

		// 画出背景
		g.drawImage(bgImg, 0, 0, null);

		// 画出容器中所有的子弹
		for (int i = 0; i < shellList.size(); i++) {
			Shell b = shellList.get(i);
			b.draw(g);

			// 飞机和所有炮弹对象进行矩形检测
			boolean peng = b.getRect(true).intersects(plane.getRect(true));
			if (peng) {
				plane.live = false; // 飞机死掉,画面不显示
				System.out.println("peng" + LocalDateTime.now());
				endTime = new Date();
				if (bao == null) {
					bao = new Explode(plane.x, plane.y);
				}
				bao.draw(g);
			} else {
				// 画出飞机本身
				plane.drawMySelf(g);
			}
		}
		// 计时功能，给出提示
		if (!plane.live) {
			if (endTime == null) {
				endTime = new Date();
			}
			int period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
			printInfo(g, "时间：" + period + "秒", 50, 120, 260, Color.white);
		} else {
		}
	}

	/**
	 * LOOK 在窗口上打印信息
	 */
	public void printInfo(Graphics g, String str, int size, int x, int y, Color color) {
		Color c = g.getColor();
		g.setColor(color);
		Font f = new Font("宋体", Font.BOLD, size);
		g.setFont(f);
		g.drawString(str, x, y);
		g.setColor(c);
	}

	/**
	 * LOOK 定义一个重画窗口的线程类，是一个内部类
	 */
	class PaintThread extends Thread {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40); // 1s = 1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * LOOK 定义为内部类，可以方便的使用外部类的普通属性
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
	}

	private Image offScreenImage = null;

	/**
	 * 双缓冲技术解决闪烁问题
	 */
	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(ConstantP.GAME_WIDTH, ConstantP.GAME_HEIGHT); // 这是游戏窗口的宽度和高度
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * LOOK
	 */
	public static void main(String[] args) {
		MyGameFrame g = new MyGameFrame();
		g.launchFrame();
	}

}
