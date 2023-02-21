package fr.epsi.b32223g1;

import fr.epsi.b32223g1.bo.Fournisseur;
import fr.epsi.b32223g1.bo.Article;
import fr.epsi.b32223g1.dal.FournisseurDAO;
import fr.epsi.b32223g1.dal.ArticleDAO;
import fr.epsi.b32223g1.dal.jdbc.ArticleJDBCDAO;
import fr.epsi.b32223g1.dal.jdbc.FournisseurJDBCDAO;

import java.util.List;

import fr.epsi.b32223g1.bo.Article;
import fr.epsi.b32223g1.bo.Fournisseur;

public class TestJdbcArticles {

    public static void main(String[] args) throws Exception {
        FournisseurJDBCDAO fournisseurDao = new FournisseurJDBCDAO();
        ArticleJDBCDAO articleDao = new ArticleJDBCDAO();

        // Insert "La Maison de la Peinture" supplier
        Fournisseur fournisseur = new Fournisseur("La Maison de la Peinture");
        fournisseurDao.insert(fournisseur);

        // Insert 4 articles
        Article article1 = new Article("Peinture blanche 1L", 12.5, fournisseur);
        Article article2 = new Article("Peinture rouge mate 1L", 15.5, fournisseur);
        Article article3 = new Article("Peinture noire laquée 1L", 17.8, fournisseur);
        Article article4 = new Article("Peinture bleue mate 1L", 15.5, fournisseur);
        articleDao.insert(article1);
        articleDao.insert(article2);
        articleDao.insert(article3);
        articleDao.insert(article4);

        // Decrease price of all matte paints by 25%
        articleDao.updatePriceByType("mate", 0.75);

        // Display list of all articles
        List<Article> articles = articleDao.extraire();
        for (Article a : articles) {
            System.out.println(a);
        }

        // Display average price of all articles
        double avgPrice = articleDao.averagePrice();
        System.out.println("Average price: " + avgPrice);

        // Delete all articles with "Peinture" in the name
        articleDao.deleteByName("Peinture");
    }
}

