package model;

public class User extends Model{
	
	private String nim;
	private String name;
	private int idTeam;
	
	public User(String nim, String name, int idTeam) {
		this.nim = nim;
		this.name = name;
		this.idTeam = idTeam;
	}
	
	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		
	}

	public String getNim() {
		return nim;
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}
}
