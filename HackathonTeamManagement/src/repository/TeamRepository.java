package repository;

import java.util.Vector;

import model.Model;
import model.Team;
import model.User;
import singleton.Connection;

public class TeamRepository implements Repository{

	@Override
	public Vector<Vector<Object>> find(int columnType, String columnValue, Boolean conditionType, boolean isJoined, Connection conn) {
		Vector<Object> header = new Vector<Object>();
		Vector<Vector<Object>> resultSet = new Vector<Vector<Object>>();
		
		header.add("ID");
		header.add("NAME");
		resultSet.add(header);
		if(columnType == 0) {
			for (Team t : conn.getTeamList()) {
				Vector<Object> row = new Vector<Object>();
				row.add(t.getId());
				row.add(t.getName());
				resultSet.add(row);
			}
		}else if(columnType == 1) {
			for (Team t : conn.getTeamList()) {
				if(!(t.getId() == Integer.parseInt(columnValue) ^ conditionType)) {
					Vector<Object> row = new Vector<Object>();
					row.add(t.getId());
					row.add(t.getName());
					resultSet.add(row);
				}
			}
		}else {
			for (Team t : conn.getTeamList()) {
				if(!(t.getName().equals(columnValue) ^ conditionType)) {
					Vector<Object> row = new Vector<Object>();
					row.add(t.getId());
					row.add(t.getName());
					resultSet.add(row);
				}
			}
		}
		
		if(isJoined) {
			Vector<Vector<Object>> newResultSet = new Vector<Vector<Object>>(); 
			header.add("NIM");
			header.add("USERNAME");
			newResultSet.add(header);
			for (Vector<Object> r : resultSet) {
				if(r.get(0) instanceof Integer) {
					for (User u : conn.getUserList()) {
						if(u.getIdTeam() == (Integer)r.get(0)) {
							Vector<Object> newRow = r;
							newRow.add(u.getNim());
							newRow.add(u.getName());
							newResultSet.add(newRow);
						}
					}
				}
			}
			return newResultSet;
		}
		
		return resultSet;
	}

	@Override
	public Model findOne(String id, Connection conn) {
		for (Team t : conn.getTeamList()) {
			if(t.getId() == Integer.parseInt(id)) {
				return t;
			}
		}
		return null;
	}

	@Override
	public void insert(Model model, Connection conn) {
		Team team = (Team) model;
		conn.writeTeamFile(team.getId(), team.getName());
	}

}
