package main;

import java.util.Scanner;
import java.util.Vector;

import model.Model;
import model.Team;
import model.User;
import repository.RepositoryFacade;
import repository.TeamRepository;
import repository.UserRepository;
import singleton.Connection;

public class Main {

	Scanner scan = new Scanner(System.in);
	
	Connection conn = Connection.getInstance();
	
	RepositoryFacade repositoryFacade = new RepositoryFacade();
	
	public void insertUser() {
		String name;
		String nim;
		String teamName;
		int idTeam = 0;
		System.out.print("add name: ");
		name = scan.nextLine();
		
		boolean unique = true;
		do {
			unique = true;
			System.out.print("add nim: ");
			nim = scan.nextLine();
			if(nim.length() != 10) {
				System.out.println("nim is not valid must be 10 character");
			}
			for (User user : conn.getUserList()) {
				if(nim.equals(user.getNim())) {
					unique = false;
					System.out.println("nim must be unique!");
				}
			}
		}while(nim.length() != 10 || !unique);
		
		boolean valid, full;
		do {
			valid = false;
			full = false;
			int count = 0;
			System.out.print("add team: ");
			teamName = scan.nextLine();
			for (Team team : conn.getTeamList()) {
				if(team.getName().equals(teamName)) {
					for (User user : conn.getUserList()) {
						if(user.getIdTeam() == team.getId()) {
							count++;
						}
					}
					if(count >= 3) {
						System.out.println("\nError: Team full\n");
						full = true;
						break;
					}else {
						idTeam = team.getId();
						valid = true;
						break;
					}
				}
			}
			if(!full && !valid) {
				System.out.println("\nTeam not found!\n");
			}
		}while(!valid);
		
		System.out.println("\nUser Created!\n");
		
		repositoryFacade.insert(nim, name, idTeam);
	}
	
	public void insertTeam() {
		String name;
		System.out.print("add name: ");
		name = scan.nextLine();
		
		System.out.println("\nTeam created!\n");
		
		repositoryFacade.insert(conn.getTeamList().size() + 1, name);
	}
	
	public void insertPage() {
		int choice = -1;
		do {
			System.out.println("Which table to insert\n1. User\n2. Team");
			choice = scan.nextInt();
			scan.nextLine();
		}while(choice < 1 || choice > 2);
		
		if(choice == 1) {
			insertUser();
		}else {
			insertTeam();
		}
	}
	
	public void showPage() {
		Vector<Vector<Object>> resultSet = new Vector<Vector<Object>>();
		String filterColumnName = null;
		String[] filterCondition = new String[2];
		Boolean join = null;
		String joinTableName = null;
		int repoType = 0;
		
		int choice = -1;
		do {
			System.out.println("Which table to show?\n1. User\n2. Team");
			choice = scan.nextInt();
			scan.nextLine();
		}while(choice < 1 || choice > 2);
		
		if(choice == 1) {
			repoType = 1;
		}else {
			repoType = 2;
		}
		
		choice = -1;
		
		do {
			System.out.println("Want to filter by condition?\n1. Yes\n2. No");
			choice = scan.nextInt();
			scan.nextLine();
		}while(choice < 1 || choice > 2);
		
		if(choice == 1) {
			System.out.println("add condition, separate by semicolon [columnName;condition;value]");
			String line[] = scan.nextLine().split(";");
			filterColumnName = line[0];
			filterCondition[0] = line[1];
			filterCondition[1] = line[2];
		}
		
		choice = -1;
		
		do {
			System.out.println("Want to join table?\n1. Yes\n2. No");
			choice = scan.nextInt();
			scan.nextLine();
		}while(choice < 1 || choice > 2);
		
		if(choice == 1) {
			join = true;
			System.out.print("insert table name: ");
			joinTableName = scan.nextLine();
		}
		
		resultSet = repositoryFacade.find(filterColumnName, filterCondition, join, joinTableName, repoType);
		
		if(resultSet == null || resultSet.size() < 2) {
			System.out.println("No data found...\nPress enter to continue...");
			scan.nextLine();
			return;
		}
		
		for (Vector<Object> r : resultSet) {
			int count = 0;
			for (Object o : r) {
				if(count++ == 0) {
					System.out.printf("| %-13s", o);
				}else {
					System.out.printf("| %-35s", o);
				}
			}
			System.out.println("|\n----------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.out.println("Press enter to continue...");
		scan.nextLine();
	}
	
	public void mainMenu() {
		int input = -1;
		do {
			conn.readTeamFile();
			conn.readUserFile();
			System.out.println("1. Insert Data\n2. Show\n3. Exit");
			
			input = scan.nextInt();
			scan.nextLine();
			
			switch(input) {
			case 1:
				insertPage();
				break;
			case 2:
				showPage();
				break;
			}
			
		}while(input != 3);
		
	}
	
	public Main() {
		mainMenu();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
