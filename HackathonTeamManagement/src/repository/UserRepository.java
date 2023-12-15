package repository;

import java.util.Vector;

import model.Model;
import model.Team;
import model.User;
import singleton.Connection;

public class UserRepository implements Repository{

	@Override
	public Vector<Vector<Object>> find(int columnType, String columnValue, Boolean conditionType, boolean isJoined, Connection conn) {
		
		Vector<Object> header = new Vector<Object>();
		Vector<Vector<Object>> resultSet = new Vector<Vector<Object>>();
		
		header.add("NIM");
		header.add("NAME");
		header.add("IDTEAM");
		resultSet.add(header);
		if(columnType == 0) {
			for (User u : conn.getUserList()) {
				Vector<Object> row = new Vector<Object>();
				row.add(u.getNim());
				row.add(u.getName());
				row.add(u.getIdTeam());
				resultSet.add(row);
			}
		}else if(columnType == 1) {
			for (User u : conn.getUserList()) {
				if(!(u.getNim().equals(columnValue) ^ conditionType)) {
					Vector<Object> row = new Vector<Object>();
					row.add(u.getNim());
					row.add(u.getName());
					row.add(u.getIdTeam());
					resultSet.add(row);
				}
			}
		}else if(columnType == 2) {
			for (User u : conn.getUserList()) {
				if(!(u.getName().equalsIgnoreCase(columnValue) ^ conditionType)) {
					Vector<Object> row = new Vector<Object>();
					row.add(u.getNim());
					row.add(u.getName());
					row.add(u.getIdTeam());
					resultSet.add(row);
				}
			}
		}else {
			for (User u : conn.getUserList()) {
				if(!(u.getIdTeam() == Integer.parseInt(columnValue) ^ conditionType)) {
					Vector<Object> row = new Vector<Object>();
					row.add(u.getNim());
					row.add(u.getName());
					row.add(u.getIdTeam());
					resultSet.add(row);
				}
			}
		}
		
		if(isJoined) {
			header.add("TEAMNAME");
			for (Vector<Object> r : resultSet) {
				if(r.get(2) instanceof Integer) {
					for (Team t : conn.getTeamList()) {
						if(t.getId() == (Integer)r.get(2)) {
							r.add(t.getName());
						}
					}
				}
			}
		}
		
		return resultSet;
	}

	@Override
	public Model findOne(String id, Connection conn) {
		for (User u : conn.getUserList()) {
			if(u.getNim().equals(id)) {
				return u;
			}
		}
		return null;
		
	}

	@Override
	public void insert(Model model, Connection conn) {
		User user = (User) model;
		conn.writeUserFile(user.getNim(), user.getName(), user.getIdTeam());
	}

}
