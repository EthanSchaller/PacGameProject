import java.awt.Rectangle;

//character dimensions, position, and image file
public class Sprite {
	//define data
	protected int x, y;
	protected int width, height;
	protected String filename, filename2;
	protected Rectangle rect;
	
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public String getFilename() {return filename;}
	public Rectangle getRectangle() {return this.rect;}
	
	
	public void setY(int y) {
		this.y = y;
		this.rect.setLocation(this.x, this.y);
	}
	public void setX(int x) {
		this.x = x;
		this.rect.setLocation(this.x, this.y);
	}
	public void setWidth(int width) {
		this.width = width;
		this.rect.setLocation(this.x, this.y);
	}
	public void setHeight(int height) {
		this.height = height;
		this.rect.setLocation(this.x, this.y);
	}
	public void setFilename(String filename) {
		this.filename = filename;
		this.rect.setLocation(this.x, this.y);
	}
	
	public Sprite() {
		super();
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.filename = "";
		this.rect = new Rectangle(this.x, this.y, this.width, this.height);
	}

	public Sprite(int x, int y, int width, int height, String filename) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.filename = filename;
		this.rect = new Rectangle(this.x, this.y, this.width, this.height);
	}
	
	public Sprite(int width, int height, String filename) {
		super();
		this.x = 0;
		this.y = 0;
		this.width = width;
		this.height = height;
		this.filename = filename;
		this.rect = new Rectangle(this.x, this.y, this.width, this.height);
	}
}
