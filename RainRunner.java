import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class RainRunner extends JPanel{

	List<Shape> drops;
	Block block;

	int v;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	public static final int DENSITY = 1;

	JFrame frame;

	RainRunner() {

		frame = new JFrame();
		frame.setSize( WIDTH, HEIGHT );
		frame.setTitle("RainRunner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new GridLayout());
		frame.add(this);
		this.setVisible(true);

		v=2;

		drops = new ArrayList<Shape>();
		block = new Block( 0, 0, 0 );
	}

	public static void main(String[] args) {
		RainRunner rr = new RainRunner();
		for ( double i = 6; i < 60; i+=3 ) {
			rr.reset( i/100 );

			int count = rr.run();
			//break;
			System.out.println( count + " " + i/100 );
		}

	}

	public void reset( double fraction ) {
		drops.clear();
		block.x = -60;
		block.v = v*fraction;
		block.count = 0;
	}

	public int run() {
		int c = 0;
		Random rand = new Random();
		int column = 0;
		while ( block.x + block.w < RainRunner.WIDTH ) {
			if ( c > HEIGHT/v ) {
				double step = block.v/v;
				for ( double d = 0; d < block.v; d += step ) {
					block.move( step );
					for ( int i = 0; i < drops.size(); i++ ) {
						if ( Shape.intersects( block, drops.get(i) )) {
							drops.remove( i );
						}
					}
				}
				try {Thread.sleep(1);} catch (InterruptedException e) {}
			}

			for ( int i = 0; i < drops.size(); i++ ) {
				drops.get(i).move( v );
				if ( drops.get(i).y < 0 ) {
					drops.remove( i );
				}
			}
			//every x counts, add a new drop
			if ( c % RainRunner.DENSITY == 0 ) {
				//*
				drops.add( new Drop( Math.abs(rand.nextInt(RainRunner.WIDTH)), Math.abs(RainRunner.HEIGHT), v ) );
				drops.add( new Drop( Math.abs(rand.nextInt(RainRunner.WIDTH)), Math.abs(RainRunner.HEIGHT), v ) );

				/*/
				drops.add( new Drop( column * 10, RainRunner.HEIGHT, v ) );
				column++;
				drops.add( new Drop( column * 10, RainRunner.HEIGHT, v ) );
				column++;
				if ( column * 10 > RainRunner.WIDTH ) {
					column = 0;
				}
				//*/
			}
			repaint();

			c++;
		}
		return block.count;
	}

	@Override
	public void paintComponent( Graphics g ) {
		//*
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.gray);
		g2d.fillRect( 0, 0, RainRunner.WIDTH, RainRunner.HEIGHT );
		block.draw(g2d);
		try {
			for ( Shape s : drops ) {
				s.draw( g2d );
			}
		} catch (Exception e) {}
		//*/
	}

}

class Shape {
	double x;
	double y;
	int w;
	int h;
	double v;//horizontal for block, vertical for drop

	int count;

	Shape( int x, int y, int w, int h, double v ) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.v = v;
		count = 0;
	}

	public void move( double vel ) {

	}

	/**
	 *
	 * @param s1 the block
	 * @param s2 a drop
	 * @return whether they've collided
	 */
	public static boolean intersects( Shape s1, Shape s2 ) {

		if ( ( s1.x + s1.w > s2.x ) && ( s2.x + s2.w > s1.x )
				&& ( s1.y + s1.h > s2.y ) && ( s2.y + s2.h > s1.y )  ) {
			s1.count++;
			return true;
		}
		return false;
	}

	public void draw( Graphics2D g2d ) {
		g2d.setColor( getCol() );
		g2d.fillRect( (int)x, (int)(RainRunner.HEIGHT - h - y), w, h);
	}

	public Color getCol() {
		return null;
	}
}

class Drop extends Shape {
	Drop( int x, int y, double v ) {
		super( x, y, 3, 6, v );
	}

	@Override
	public Color getCol() {
		return Color.blue;
	}

	@Override
	public void move( double vel ) {
		y -= vel;
	}
}


class Block extends Shape {
	Block( int x, int y, double d ) {
		super( x, y, 30, 180, d );
	}

	@Override
	public Color getCol() {
		return Color.magenta;
	}

	@Override
	public void move( double vel ) {
		x += vel;
	}

	@Override
	public void draw( Graphics2D g2d ) {
		super.draw( g2d );
		g2d.setColor(Color.black);
		g2d.drawString( count+" ", (int)(x+w/3), (int)(RainRunner.HEIGHT - (y + h/2 ) ) );
	}
}