package repository;

import java.util.Vector;

import model.Model;
import singleton.Connection;

public interface Repository {
	
	public abstract Vector<Vector<Object>> find(int columnType, String columnName, Boolean conditionType, boolean isJoined, Connection conn);
	public abstract Model findOne(String id, Connection conn);
	public abstract void insert(Model model, Connection conn);
	
}
