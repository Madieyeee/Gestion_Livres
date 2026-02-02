package sn.gestion.livres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "livre")
public class Livre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titre;
    
    @Column(nullable = false, length = 150)
    private String auteur;
    
    @Column
    private Integer annee;
    
    @Column
    private Double prix;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_livre_id", nullable = false)
    private TypeLivre typeLivre;

    public Livre() {
    }

    public Livre(String titre, String auteur, Integer annee, Double prix, TypeLivre typeLivre) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.prix = prix;
        this.typeLivre = typeLivre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public TypeLivre getTypeLivre() {
        return typeLivre;
    }

    public void setTypeLivre(TypeLivre typeLivre) {
        this.typeLivre = typeLivre;
    }
}
