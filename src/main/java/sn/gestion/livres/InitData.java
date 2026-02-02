package sn.gestion.livres;

import sn.gestion.livres.dao.TypeLivreDAO;
import sn.gestion.livres.entity.TypeLivre;

public class InitData {
    
    public static void main(String[] args) {
        TypeLivreDAO typeLivreDAO = new TypeLivreDAO();
        
        if (typeLivreDAO.findAll().isEmpty()) {
            System.out.println("Initialisation des types de livres...");
            
            typeLivreDAO.save(new TypeLivre("Roman"));
            typeLivreDAO.save(new TypeLivre("Science-Fiction"));
            typeLivreDAO.save(new TypeLivre("Policier"));
            typeLivreDAO.save(new TypeLivre("Biographie"));
            typeLivreDAO.save(new TypeLivre("Essai"));
            typeLivreDAO.save(new TypeLivre("Poésie"));
            typeLivreDAO.save(new TypeLivre("Théâtre"));
            typeLivreDAO.save(new TypeLivre("BD/Comics"));
            
            System.out.println("Types de livres initialisés avec succès!");
        } else {
            System.out.println("Les types de livres existent déjà.");
        }
    }
}
