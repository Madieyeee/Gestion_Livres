package sn.gestion.livres.dao;

import jakarta.persistence.EntityManager;
import sn.gestion.livres.entity.TypeLivre;
import sn.gestion.livres.util.JPAUtil;

import java.util.List;

public class TypeLivreDAO {
    
    private EntityManager em;
    
    public TypeLivreDAO() {
        this.em = JPAUtil.getEntityManager();
    }
    
    public void save(TypeLivre typeLivre) {
        try {
            em.getTransaction().begin();
            em.persist(typeLivre);
            em.getTransaction().commit();
            System.out.println("Type de livre ajouté avec succès");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de l'ajout: " + e.getMessage());
        }
    }
    
    public List<TypeLivre> findAll() {
        try {
            em.clear();
            return em.createQuery("SELECT t FROM TypeLivre t ORDER BY t.nom", TypeLivre.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération: " + e.getMessage());
            return List.of();
        }
    }
    
    public TypeLivre findById(Long id) {
        try {
            return em.find(TypeLivre.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
            return null;
        }
    }
}
