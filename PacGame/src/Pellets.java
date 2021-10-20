public class Pellets extends Sprite{
	private Boolean Vis;
	private Pacman Pac;
	private Boolean got;
	
	public Pellets() {
		super(20, 20, "Pellet.png");
		this.Vis = true;
		this.dupe = false;
	}
	
	public Pellets(String temp) {
		super(20, 20, "pPellet.png");
		this.Vis = true;
		this.dupe = false;
	}
	
	public Boolean collected() {  
		if(this.rect.intersects(Pac.getRect())) {
			got = true;
			this.setVisible(false);
		} else {
			got = false;
		}
		
		return got;
	}
	
	public Boolean getVisible() {return Vis;}
	public void setVisible(Boolean temp) {this.Vis = temp;}
	public void setPac(Pacman temp) {this.Pac = temp;}
	
	public void hide() {
		this.Vis = false;
	}
	
	public void show() {
		this.Vis = true;
	}
}

	
