package singleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import model.Team;
import model.User;

public class Connection {
	private Vector<User> userList = new Vector<User>();
	private Vector<Team> teamList = new Vector<Team>();
	
	private static Connection connectionInstance = null;
	
	
	public static Connection getInstance() {
		if(connectionInstance == null) {
			connectionInstance = new Connection();
		}
		return connectionInstance;
	}
	
	private Connection() {
		
	}
	
	public void readUserFile() {
		userList.removeAllElements();
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("Database/user.csv"));
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] values = line.split(",");
				String nim = values[0];
				String name = values[1];
				int idTeam = Integer.parseInt(values[2]);
				userList.add(new User(nim, name, idTeam));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeUserFile(String nim, String name, int idTeam) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Database/user.csv", true));
			bw.write(nim + "," + name + "," + idTeam + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readTeamFile() {
		teamList.removeAllElements();
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("Database/teams.csv"));
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] values = line.split(",");
				int id = Integer.parseInt(values[0]);
				String name = values[1];
				teamList.add(new Team(id, name));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeTeamFile(int id, String name) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Database/teams.csv", true));
			bw.write(id + "," + name + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Vector<User> getUserList() {
		return userList;
	}

	public void setUserList(Vector<User> userList) {
		this.userList = userList;
	}

	public Vector<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(Vector<Team> teamList) {
		this.teamList = teamList;
	}

}
