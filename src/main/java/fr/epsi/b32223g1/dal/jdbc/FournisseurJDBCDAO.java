package fr.epsi.b32223g1.dal.jdbc;

import fr.epsi.b32223g1.bo.Fournisseur;
import fr.epsi.b32223g1.dal.FournisseurDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FournisseurJDBCDAO implements FournisseurDAO {
	
	private static final String FIND_ALL_QUERY = "SELECT * FROM fournisseur";
	private static final String INSERT_QUERY = "INSERT INTO fournisseur (NOM) VALUES ('%s')";
	private static final String UPDATE_QUERY = "UPDATE fournisseur SET NOM = ? WHERE NOM = ?";
	private static final String DELETE_QUERY = "DELETE FROM fournisseur WHERE NOM = ?";
	
	private static final String DB_URL;
	private static final String DB_USER;
	private static final String DB_PWD;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle( "db" );
		DB_URL = bundle.getString( "db.url" );
		DB_USER = bundle.getString( "db.user" );
		DB_PWD = bundle.getString( "db.password" );
	}
	
	@Override
	public List<Fournisseur> extraire() throws SQLException {

		List<Fournisseur> list = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			 PreparedStatement st = connection.prepareStatement(FIND_ALL_QUERY)) {

			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("ID");
					String nom = rs.getString("NOM");
					Fournisseur fournisseur = new Fournisseur(id, nom);
					list.add(fournisseur);
				}
			}
		}
		return list;
	}

	@Override
	public void insert(Fournisseur fournisseur) throws SQLException {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			 PreparedStatement st = connection.prepareStatement(INSERT_QUERY)) {
			st.setString(1, fournisseur.getName());
			st.executeUpdate();
		}
	}

	@Override
	public int update(String ancienNom, String nouveauNom) throws SQLException {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			 PreparedStatement st = connection.prepareStatement(UPDATE_QUERY)) {
			st.setString(1, nouveauNom);
			st.setString(2, ancienNom);
			return st.executeUpdate();
		}
	}

	@Override
	public boolean delete(Fournisseur fournisseur) throws SQLException {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			 PreparedStatement st = connection.prepareStatement(DELETE_QUERY)) {
			st.setString(1, fournisseur.getName());
			return st.executeUpdate() > 0;
		}
	}
}
