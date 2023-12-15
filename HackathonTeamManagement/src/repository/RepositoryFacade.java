package repository;

import java.util.Vector;

import model.Team;
import model.User;
import singleton.Connection;

public class RepositoryFacade {
	Connection conn = Connection.getInstance();
	UserRepository userRepository = new UserRepository();
	TeamRepository teamRepository = new TeamRepository();
	
	public void insert(String nim, String name, int idTeam) {
		userRepository.insert(new User(nim, name, idTeam), conn);
	}
	
	public void insert(int id, String name) {
		teamRepository.insert(new Team(id, name), conn);
	}
	
	int validateColumnName(String name, int type) {
		if(type == 1) {
			if(name.equalsIgnoreCase("nim")) {
				return 1;
			}else if(name.equalsIgnoreCase("name")) {
				return 2;
			}else if(name.equalsIgnoreCase("idTeam")) {
				return 3;
			}else {
				System.out.printf("There are no column named '%s'...\n", name);
				System.out.println("Column name must be either 'nim', 'name', or 'idteam'");
			}
		}else {
			if(name.equalsIgnoreCase("id")) {
				return 1;
			}else if(name.equalsIgnoreCase("name")) {
				return 2;
			}else {
				System.out.printf("There are no column named '%s'...\n", name);
				System.out.println("Column name must be either 'id', or 'name'");
			}
		}
		return 0;
	}
	
	boolean validateJoinTableName(String name, int type) {
		if(type == 1) {
			if(name.equalsIgnoreCase("user")) {
				System.out.println("Can't join to the same table");
				return false;
			}else if(name.equalsIgnoreCase("team")){
				return true;
			}
		}else {
			if(name.equalsIgnoreCase("team")) {
				System.out.println("Can't join to the same table");
				return false;
			}else if(name.equalsIgnoreCase("user")){
				return true;
			}
		}
		System.out.println("Table name not found...\n must be either 'user' or 'team'");
		return false;
	}
	
	public Boolean validateConditionType(String condition) {
		if(condition.equals("=")) {
			return true;
		}else if(condition.equals("!=")) {
			return false;
		}else {
			return null;
		}
	}
	
	public Vector<Vector<Object>> find(String filterColumnName, String[] filterCondition, Boolean join, String joinTableName, int repoType) {
		int columnType = 0;
		boolean isJoined = false;
		Boolean conditionType = null;
		if(filterColumnName != null) {
			if((columnType = validateColumnName(filterColumnName, repoType)) == 0) {
				return null;
			}
			
			if((conditionType = validateConditionType(filterCondition[0])) == null) {
				System.out.println("Condition are not valid, make sure you type either '=' or '!=' for the conditions...");
				return null;
			}
		}
		if(join != null) {
			isJoined = validateJoinTableName(joinTableName, repoType);
		}
		if(repoType == 1) {
			return userRepository.find(columnType, filterCondition[1], conditionType, isJoined, conn);
		}else {
			return teamRepository.find(columnType, filterCondition[1], conditionType, isJoined, conn);
		}
	}
	
}
