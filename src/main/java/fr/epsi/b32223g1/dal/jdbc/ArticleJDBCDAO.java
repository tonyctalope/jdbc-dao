package fr.epsi.b32223g1.dal.jdbc;

import fr.epsi.b32223g1.bo.Article;
import fr.epsi.b32223g1.bo.Fournisseur;
import fr.epsi.b32223g1.dal.ArticleDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArticleJDBCDAO implements ArticleDAO {

    private static final String FIND_ALL_QUERY = "SELECT * FROM article";
    private static final String INSERT_QUERY = "INSERT INTO article (nom, prix, fournisseur_id) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE articles SET nom = ?, prix = ?, fournisseur = ? WHERE nom = ?";
    private static final String DELETE_QUERY = "DELETE FROM articles WHERE nom = ?";
    private static final String DELETE_BY_NAME_QUERY = "DELETE FROM article WHERE nom LIKE ?";
    private static final String AVG_PRICE_QUERY = "SELECT AVG(prix) FROM article";
    private static final String UPDATE_PRICE_BY_TYPE_QUERY = "UPDATE article SET prix = prix * ? WHERE type = ?";


    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PWD;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        DB_URL = bundle.getString("db.url");
        DB_USER = bundle.getString("db.user");
        DB_PWD = bundle.getString("db.password");
    }

    @Override
    public List<Article> extraire() throws SQLException {
        List<Article> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(FIND_ALL_QUERY)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("nom");
                    double prix = rs.getDouble("prix");
                    int fournisseurId = rs.getInt("fournisseur_id");
                    Article article = new Article(nom, prix, new Fournisseur(fournisseurId));
                    list.add(article);
                }
            }
        }
        return list;
    }

    @Override
    public void insert(Article article) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(INSERT_QUERY)) {
            st.setString(1, article.getNom());
            st.setDouble(2, article.getPrix());
            st.setInt(4, article.getFournisseur().getId());
            st.executeUpdate();
        }
    }

    @Override
    public int update(String ancienNom, Article nouveauArticle) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(UPDATE_QUERY)) {
            st.setString(1, nouveauArticle.getNom());
            st.setDouble(2, nouveauArticle.getPrix());
            st.setString(4, nouveauArticle.getFournisseur().getName());
            st.setString(5, ancienNom);
            return st.executeUpdate();
        }
    }

    @Override
    public boolean delete(Article article) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(DELETE_QUERY)) {
            st.setString(1, article.getNom());
            return st.executeUpdate() > 0;
        }
    }

    public void deleteByName(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(DELETE_BY_NAME_QUERY)) {
            st.setString(1, name);
            st.executeUpdate();
        }
    }

    public double averagePrice() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(AVG_PRICE_QUERY);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }

    public void updatePriceByType(String type, double ratio) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
             PreparedStatement st = connection.prepareStatement(UPDATE_PRICE_BY_TYPE_QUERY)) {
            st.setDouble(1, ratio);
            st.setString(2, type);
            st.executeUpdate();
        }
    }

}
