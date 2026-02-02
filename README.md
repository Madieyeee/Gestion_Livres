# Gestion de Livres - Application JavaFX

Application de gestion de bibliothèque avec CRUD complet, recherche multicritères et PostgreSQL.

## 📋 Prérequis

- JDK 17 ou supérieur
- Maven
- PostgreSQL 12 ou supérieur

## 🗄️ Configuration de la base de données

### 1. Créer la base de données PostgreSQL

Ouvrez **pgAdmin** ou **psql** et exécutez :

```sql
CREATE DATABASE gestion_livres;
```

### 2. Configurer les identifiants

Modifiez le fichier `src/main/resources/META-INF/persistence.xml` :

```xml
<property name="jakarta.persistence.jdbc.user" value="postgres"/>
<property name="jakarta.persistence.jdbc.password" value="VOTRE_MOT_DE_PASSE"/>
```

Remplacez `VOTRE_MOT_DE_PASSE` par votre mot de passe PostgreSQL.

## 🚀 Installation et lancement

### 1. Compiler le projet

```bash
mvn clean compile
```

### 2. Initialiser les types de livres (première fois uniquement)

```bash
mvn exec:java -Dexec.mainClass="sn.gestion.livres.InitData"
```

Cette commande va créer les types de livres par défaut (Roman, Science-Fiction, etc.).

### 3. Lancer l'application

```bash
mvn javafx:run
```

## 📚 Fonctionnalités

### CRUD Complet
- ✅ **Ajouter** un livre
- ✅ **Modifier** un livre (sélectionner dans le tableau)
- ✅ **Supprimer** un livre (avec confirmation)
- ✅ **Afficher** tous les livres

### Recherche
- ✅ Recherche multicritères (titre, auteur, type)
- ✅ Recherche avec LIKE (insensible à la casse)

### Validation
- ✅ Titre : minimum 2 caractères
- ✅ Auteur : obligatoire
- ✅ Année : entre 1900 et 2025
- ✅ Prix : minimum 0
- ✅ Type de livre : obligatoire

## 🏗️ Structure du projet

```
src/main/java/
├── sn.gestion.livres/
│   ├── entity/
│   │   ├── TypeLivre.java      (Entité JPA)
│   │   └── Livre.java          (Entité JPA avec relation ManyToOne)
│   ├── dao/
│   │   ├── TypeLivreDAO.java   (Accès données TypeLivre)
│   │   └── LivreDAO.java       (CRUD + recherche)
│   ├── controller/
│   │   └── LivreController.java (Logique JavaFX)
│   ├── util/
│   │   └── JPAUtil.java        (EntityManager)
│   ├── App.java                (Point d'entrée)
│   └── InitData.java           (Initialisation données)
└── module-info.java

src/main/resources/
├── META-INF/
│   └── persistence.xml         (Configuration JPA/Hibernate)
└── sn/gestion/livres/
    └── livre-view.fxml         (Interface graphique)
```

## 🎯 Utilisation

1. **Ajouter un livre** : Remplissez le formulaire et cliquez sur "Ajouter"
2. **Modifier un livre** : Cliquez sur une ligne du tableau, modifiez les champs, puis cliquez sur "Modifier"
3. **Supprimer un livre** : Sélectionnez une ligne et cliquez sur "Supprimer"
4. **Rechercher** : Tapez un mot dans le champ de recherche et cliquez sur "Rechercher"
5. **Vider le formulaire** : Cliquez sur "Vider"

## 🔧 Dépannage

### Erreur de connexion PostgreSQL
- Vérifiez que PostgreSQL est démarré
- Vérifiez le mot de passe dans `persistence.xml`
- Vérifiez que la base `gestion_livres` existe

### Erreur de module
- Vérifiez que vous utilisez JDK 17+
- Exécutez `mvn clean compile` pour recompiler

### Tableau vide au démarrage
- Exécutez d'abord `InitData` pour créer les types de livres
- Ajoutez ensuite des livres via l'interface

## 📝 Notes

- Les tables sont créées automatiquement par Hibernate (`hibernate.hbm2ddl.auto=update`)
- Les requêtes SQL sont affichées dans la console (`hibernate.show_sql=true`)
- La relation ManyToOne entre Livre et TypeLivre est gérée automatiquement
