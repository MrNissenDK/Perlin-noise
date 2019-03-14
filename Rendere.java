package com.mrnissen.perlinnoise;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

public class Rendere extends Canvas implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2572659413885932464L;
	private boolean running = false;
	private Thread t;
	private JFrame frame;

	Random r = new Random(55555855);
	int octal = 1;
	
	HashMap<Float, Color> terrian = new HashMap<>();
	
	private int w = 500, h = 500,
			fps, zoom = 1;
	
	BufferedImage map;
	public static void main(String[] args) {
		Rendere Ren = new Rendere();
		Ren.start();
	}
	public void start() {
		map = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		setSize(w,h);
		addMap();
		crateMap();
		t = new Thread(this, "map rendere");
		running = true;
		t.start();
	}
	
	private void addMap() {
		terrian.put(10f, new Color(30,20,140));
		terrian.put(20f, new Color(45,25,150));
		terrian.put(30f, new Color(50,30,160));
		terrian.put(40f, new Color(86,125,70));
		terrian.put(64f, new Color(250,250,250));
	}
	
	private void init() {
		frame = new JFrame("Perlin Noise - map rendere");
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createBufferStrategy(3);
	}
	
	private void crateMap() {
		for(int y = 0; y < w; y++) {
			for(int x = 0; x < h; x++) {
				float point;
				if(zoom > 0)
					point = r.getPerlin(x*zoom, y*zoom, 4, octal);
				else
					point = r.getPerlin(x/Mathf.abs(zoom), y/Mathf.abs(zoom), 4, octal);
				Color color = Color.getHSBColor(point, 1f, 1f);
				
//				for(float i : terrian.keySet()) {
//					if(i >= point) {
//						color = terrian.get(i);
//						break;
//					}
//				}
//				if(color != null)
				map.setRGB(x, y, color.getRGB());
			}
		}
		System.out.println("map fhinish");
	}
	
	private void render(float time) {
		BufferStrategy bs = this.getBufferStrategy();
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		
		g2d.drawImage(map, 0, 0, frame.getWidth(), frame.getHeight(), null);
		
		g2d.dispose();
		bs.show();
	}
	private void tich(float time) {
	}
	
	@Override
	public void run() {
		long last = System.nanoTime();
		init();
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				System.out.println();
				switch (e.getWheelRotation()) {
				case -1:
					octal+=1;
					break;
				case 1:
					octal-=1;
					break;
				default:
					break;
				}
				System.out.println(octal);
				crateMap();
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			boolean create = true;
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				switch (e.getButton()) {
				case 1:
					zoom--;
					if(zoom == 0)
						zoom--;
					break;
				case 2:
					r = new Random(System.nanoTime());
					break;
				case 3:
					zoom++;
					if(zoom == 0)
						zoom++;
					break;

				default:
				    create = false;
					break;
				}
				System.out.println(zoom);
				if(create)
					crateMap();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		while(running) {
			long now = System.nanoTime();
			fps = Mathf.round(100000000f/(now-last));
			float time = (now-last)/100000000f;
			tich(time);
			render(time);
			last = now;
		}
	}
	
	
	
}
