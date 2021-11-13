public class Walls extends Sprite {
	private Boolean Vis;
	
	public Walls(String slct) {
		super(100, 100, "MainFolder/PacWall.png");
		
		switch(slct) {
			case "vrtTall": this.setHeight(1000);
						    this.setWidth(25);
						    this.Vis = true;
					        this.dupe = false;
					        break;
					        
			case "vrtShrt": this.setHeight(600);
			 				this.setWidth(25);
			 				this.Vis = true;
			 				this.dupe = false;
			 				break;
			 				
			case "vrtTiny": this.setHeight(374);
							this.setWidth(25);
							this.Vis = true;
							this.dupe = false;
							break;
				
			case "hrzWide": this.setHeight(25);
			 				this.setWidth(1000);
			 				this.Vis = true;
			 				this.dupe = false;
			 				break;
			 				
			case "hrzNrrw": this.setHeight(25);
			 				this.setWidth(650);
			 				this.Vis = true;
			 				this.dupe = false;
			 				break;
			 				
			case "hrzTiny": this.setHeight(25);
							this.setWidth(400);
							this.Vis = true;
							this.dupe = false;
							break;	
							
			case "mddlBrk": this.setHeight(250);
							this.setWidth(400);
							this.Vis = true;
							this.dupe = false;
							break;
			 				
			default: this.setHeight(0);
					 this.setWidth(0);
					 this.Vis = true;
				     this.dupe = false;
				     break;
		}
		
		this.rect.setLocation(this.getX(), this.getY());
		this.rect.setSize(this.getWidth(), this.getHeight());
		
	}
	
	public Boolean getVisible() {return Vis;}
	public void setVisible(Boolean temp) {this.Vis = temp;}
	
	public void hide() {
		this.Vis = false;
	}
	
	public void show() {
		this.Vis = true;
	}
}
