import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Obstacle extends Entity
{
	public static final int SOLID = 4;
	public static final int BROKEN = 5;
	public static final int DESTROYED = 6;

	public static boolean[][][] broken;

	private Tile[][] obstacle;

	public Obstacle(int x, int y)
	{
		super(x, y, Entity.OBSTACLE);
		if (broken == null)
		{
			broken = new boolean[5][10][30];
			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					broken[0][i][j] = (int)(Math.random()*25) == 0;
				}
			}

			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					broken[1][i][j] = (int)(Math.random()*25) == 0;
				}
			}

			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					broken[2][i][j] = (int)(Math.random()*25) == 0;
				}
			}

			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					broken[3][i][j] = (int)(Math.random()*25) == 0;
				}
			}

			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					broken[4][i][j] = (int)(Math.random()*25) == 0;
				}
			}
		}
		obstacle = new Tile[][] {
			{new Corner(0, 0, Corner.TL, 0, 0), new Block(30, 0, 0, 1), new Corner(60, 0, Corner.TR, 0, 2)},
			{new Block(0, 10, 1, 0), new Block(30, 10, 1, 1), new Block(60, 10, 1, 2)},
			{new Block(0, 20, 2, 0), new Block(30, 20, 2, 1), new Block(60, 20, 2, 2)},
			{new Corner(0, 30, Corner.BL, 3, 0), null, new Corner(60, 30, Corner.BR, 3, 2)}
		};

		for (int i = 0; i < obstacle.length; i++)
		{
			for (int j = 0; j < obstacle[i].length; j++)
			{
				if (obstacle[i][j] != null)
				{
					if (i - 1 >= 0)
					{
						obstacle[i][j].addNeighbor(obstacle[i-1][j]);
					}
					if (j + 1 < obstacle[i].length)
					{
						obstacle[i][j].addNeighbor(obstacle[i][j+1]);
					}
					if (i + 1 < obstacle.length)
					{
						obstacle[i][j].addNeighbor(obstacle[i+1][j]);
					}
					if (j - 1 >= 0)
					{
						obstacle[i][j].addNeighbor(obstacle[i][j-1]);
					}
				}
			}
		}
	}

	/*
	public Tile hitBy(Bullet b, SpaceInvadersMain s)
	{
		if (b.isTouching(this))
		{
			Point contact = new Point((b.getX() * 2 + b.getWidth())/2 - getX(), b.getY() - getY());
			for (int i = 0; i < obstacle.length; i++)
			{
				for (int j = 0; j < obstacle[i].length; j++)
				{
					if (obstacle[i][j] != null)
					{
//						if (obstacle[i][j].contains(contact) && obstacle[i][j].getState() == SOLID)
//						{
//							obstacle[i][j].setState(BROKEN);
//							return obstacle[i][j];
//						}
//						if (i == 0 && j == 2 && obstacle[0][2].contains(contact, true))
//						{
//							System.out.println("BRUHHHHH");
//							System.out.println(((Corner)obstacle[0][2]).thingy(contact));
//						}
						if (obstacle[i][j] instanceof Corner)
						{
							Corner corner = (Corner)obstacle[i][j];
							Polygon triangle = corner.getTriangle();
							if (b.getDx() == -10)
							{
								if (corner.getType() == Corner.TL)
								{
									if (b.isInsideOf(corner))
									{
										if (corner.getX() + corner.getWidth() - b.getX() >= b.getWidth() / 2 || obstacle[i][j+1] == null)
										{
											obstacle[i][j].setState(BROKEN);
											return obstacle[i][j];
										}
										else if (obstacle[i][j+1] != null)
										{
											obstacle[i][j+1].setState(BROKEN);
											return obstacle[i][j+1];
										}
									}
								}
								else if (corner.getType() == Corner.TR)
								{
									if (b.isInsideOf(corner))
									{
										if (corner.getX() - b.getX() < b.getWidth() / 2 || obstacle[i][j-1] == null)
										{
											obstacle[i][j].setState(BROKEN);
											return obstacle[i][j];
										}
										else if (obstacle[i][j-1] != null)
										{
											obstacle[i][j-1].setState(BROKEN);
											return obstacle[i][j-1];
										}
									}
								}
								else if (corner.getType() == Corner.BR)
								{
									if (contact.x < corner.getX() + corner.getWidth())
									{
										double xDist = corner.getX() + corner.getWidth() - contact.x;
										double yDist = xDist/3;
										if (contact.y < corner.getY() + corner.getHeight() - yDist)
										{
											obstacle[i][j].setState(BROKEN);
											return obstacle[i][j];
										}
									}
									else
									{
										
									}
								}
								else
								{
									
								}
							}
							else
							{
								if (corner.getType() == Corner.BL || corner.getType() == Corner.BR)
								{
									if (b.isInsideOf(corner))
									{
										obstacle[i][j].setState(BROKEN);
										return obstacle[i][j];
									}
								}
							}
						}
						else
						{
							if (b.isInsideOf(obstacle[i][j]) && obstacle[i][j].getState() == SOLID)
							{
								obstacle[i][j].setState(BROKEN);
								return obstacle[i][j];
							}
						}
					}
				}
			}
		}
		return null;
	}
	*/
	
	public Tile[] hitBy(Bullet b)
	{
		if (b.isTouching(this))
		{
			for (int i = 0; i < obstacle.length; i++)
			{

				for (int j = 0; j < obstacle[i].length; j++)
				{
		
					if (obstacle[i][j] != null && obstacle[i][j].getState() == SOLID)
					{
						System.out.println("h213");
						Rectangle rec = new Rectangle(b.getX() - this.getX(), b.getY() - this.getY(), b.getWidth(), b.getHeight());
						Polygon poly = new Polygon(obstacle[i][j].getS().xpoints, obstacle[i][j].getS().ypoints, obstacle[i][j].getS().npoints);
						poly.translate(obstacle[i][j].getX(), obstacle[i][j].getY());
						System.out.println(rec.getBounds());
						System.out.println(b.getBounds());
						System.out.println(this.getBounds());
						System.out.println(obstacle[i][j].getS().getBounds());
						if(poly.intersects(rec))
						{
							System.out.println(i + " " + j);
							if(j + 1 < obstacle[i].length && obstacle[i][j+1] != null && obstacle[i][j+1].getS().intersects(rec))
							{
								System.out.println("h1");
								obstacle[i][j].setState(BROKEN);
								obstacle[i][j+1].setState(BROKEN);
								return new Tile[] {obstacle[i][j], obstacle[i][j+1]};
							}
							else
							{
								System.out.println("h2");
								obstacle[i][j].setState(BROKEN);
								return new Tile[] {obstacle[i][j]};
							}
						}
					}
				}
			}
		}
		return null;
	}

	public void test(Tile t)
	{
		System.out.println("HBIFJKNAIDHKJFKENFKS" + obstacle[0][2].getState());
		if (t != null)
		{
			System.out.println(1);
			for (int i = 0; i < t.getNeighbors().size(); i++)
			{
				System.out.println(2);
				if (t.getNeighbors().get(i) != null)
				{	
					System.out.println(3);
					if (t.getNeighbors().get(i).isGone(t))
					{
						System.out.println(4);
						obstacle[t.getNeighbors().get(i).getR()][t.getNeighbors().get(i).getC()].setState(DESTROYED);;
					}
				}
			}
			if (t.isGone())
			{
				System.out.println(5);
				obstacle[t.getR()][t.getC()].setState(DESTROYED);;
			}
		}
		System.out.println("*********************");
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.GREEN);
		for (int r = 0; r < obstacle.length; r++)
		{
			for (int c = 0; c < obstacle[r].length; c++)
			{
				if (obstacle[r][c] != null)
				{
					if (obstacle[r][c].getState() != DESTROYED)
					{
						if (obstacle[r][c].getState() == SOLID)
						{
							this.add(obstacle[r][c]);
						}
						else if (obstacle[r][c].getState() == BROKEN)
						{
							this.remove(obstacle[r][c]);
							repaint();
							if (obstacle[r][c].getBroken() == null)
							{
								int num = (int)(Math.random()*5);
								obstacle[r][c].setBroken(broken[num]);
							}
							for (int i = 0; i < obstacle[r][c].getBroken().length; i++)
							{
								for (int j = 0; j < obstacle[r][c].getBroken()[i].length; j++)
								{
									if (obstacle[r][c].getBroken()[i][j])
									{
										g2.fillRect((int)(obstacle[r][c].getX() + j), (int)(obstacle[r][c].getY() + i), 1, 1);
									}
								}
							}
						}
					}
					else
					{
						this.remove(obstacle[r][c]);
					}
				}
			}
		}
	}
}
