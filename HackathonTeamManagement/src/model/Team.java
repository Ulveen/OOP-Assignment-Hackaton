package model;

public class Team extends Model{
	
	private int id;
	private String name;
	
	public Team(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
