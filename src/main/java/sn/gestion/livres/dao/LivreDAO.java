package sn.gestion.livres.dao;

import jakarta.persistence.EntityManager;
import sn.gestion.livres.entity.Livre;
import sn.gestion.livres.util.JPAUtil;

import java.util.List;

public class LivreDAO {
    
    private EntityManager em;
    
    public LivreDAO() {
        this.em = JPAUtil.getEntityManager();
    }
    
    public void save(Livre livre) {
        try {
            em.getTransaction().begin();
            em.persist(livre);
            em.getTransaction().commit();
            System.out.println("Livre ajouté avec succès");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de l'ajout: " + e.getMessage());
        }
    }
    
    public List<Livre> findAll() {
        try {
            em.clear();
            return em.createQuery("SELECT l FROM Livre l ORDER BY l.titre", Livre.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération: " + e.getMessage());
            return List.of();
        }
    }
    
    public Livre findById(Long id) {
        try {
            return em.find(Livre.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
            return null;
        }
    }
    
    public void update(Livre livre) {
        try {
            em.getTransaction().begin();
            em.merge(livre);
            em.getTransaction().commit();
            System.out.println("Livre modifié avec succès");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de la modification: " + e.getMessage());
        }
    }
    
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Livre livre = em.find(Livre.class, id);
            if (livre != null) {
                em.remove(livre);
                em.getTransaction().commit();
                System.out.println("Livre supprimé avec succès");
            } else {
                System.out.println("Livre non trouvé");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }
    
    public List<Livre> searchByAll(String mot) {
        try {
            em.clear();
            String jpql = "SELECT l FROM Livre l WHERE " +
                         "LOWER(l.titre) LIKE LOWER(:mot) OR " +
                         "LOWER(l.auteur) LIKE LOWER(:mot) OR " +
                         "LOWER(l.typeLivre.nom) LIKE LOWER(:mot)";
            return em.createQuery(jpql, Livre.class)
                    .setParameter("mot", "%" + mot + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
            return List.of();
        }
    }
}
