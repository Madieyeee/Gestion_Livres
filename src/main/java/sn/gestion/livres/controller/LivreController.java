package sn.gestion.livres.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sn.gestion.livres.dao.LivreDAO;
import sn.gestion.livres.dao.TypeLivreDAO;
import sn.gestion.livres.entity.Livre;
import sn.gestion.livres.entity.TypeLivre;

import java.net.URL;
import java.util.ResourceBundle;

public class LivreController implements Initializable {

    @FXML private TextField titreField;
    @FXML private TextField auteurField;
    @FXML private TextField anneeField;
    @FXML private TextField prixField;
    @FXML private ComboBox<TypeLivre> typeLivreCombo;
    @FXML private TextField rechercheField;
    
    @FXML private TableView<Livre> livreTable;
    @FXML private TableColumn<Livre, Long> idCol;
    @FXML private TableColumn<Livre, String> titreCol;
    @FXML private TableColumn<Livre, String> auteurCol;
    @FXML private TableColumn<Livre, Integer> anneeCol;
    @FXML private TableColumn<Livre, Double> prixCol;
    @FXML private TableColumn<Livre, String> typeCol;
    
    private LivreDAO livreDAO;
    private TypeLivreDAO typeLivreDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        livreDAO = new LivreDAO();
        typeLivreDAO = new TypeLivreDAO();
        
        configurerTableau();
        chargerTypesLivres();
        chargerLivres();
        
        livreTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormulaire(newSelection);
            }
        });
    }
    
    private void configurerTableau() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getTypeLivre().getNom()
            )
        );
    }
    
    private void chargerTypesLivres() {
        ObservableList<TypeLivre> types = FXCollections.observableArrayList(typeLivreDAO.findAll());
        typeLivreCombo.setItems(types);
    }
    
    private void chargerLivres() {
        ObservableList<Livre> livres = FXCollections.observableArrayList(livreDAO.findAll());
        livreTable.setItems(livres);
        livreTable.refresh();
    }
    
    private void remplirFormulaire(Livre livre) {
        titreField.setText(livre.getTitre());
        auteurField.setText(livre.getAuteur());
        anneeField.setText(livre.getAnnee() != null ? livre.getAnnee().toString() : "");
        prixField.setText(livre.getPrix() != null ? livre.getPrix().toString() : "");
        typeLivreCombo.setValue(livre.getTypeLivre());
    }
    
    @FXML
    private void handleAjouter() {
        if (!validerChamps()) {
            return;
        }
        
        try {
            Livre livre = new Livre();
            livre.setTitre(titreField.getText().trim());
            livre.setAuteur(auteurField.getText().trim());
            
            if (!anneeField.getText().trim().isEmpty()) {
                livre.setAnnee(Integer.parseInt(anneeField.getText().trim()));
            }
            
            if (!prixField.getText().trim().isEmpty()) {
                livre.setPrix(Double.parseDouble(prixField.getText().trim()));
            }
            
            livre.setTypeLivre(typeLivreCombo.getValue());
            
            livreDAO.save(livre);
            chargerLivres();
            viderFormulaire();
            
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre ajouté avec succès!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Année ou prix invalide!");
        }
    }
    
    @FXML
    private void handleModifier() {
        Livre livreSelectionne = livreTable.getSelectionModel().getSelectedItem();
        
        if (livreSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un livre à modifier!");
            return;
        }
        
        if (!validerChamps()) {
            return;
        }
        
        try {
            livreSelectionne.setTitre(titreField.getText().trim());
            livreSelectionne.setAuteur(auteurField.getText().trim());
            
            if (!anneeField.getText().trim().isEmpty()) {
                livreSelectionne.setAnnee(Integer.parseInt(anneeField.getText().trim()));
            } else {
                livreSelectionne.setAnnee(null);
            }
            
            if (!prixField.getText().trim().isEmpty()) {
                livreSelectionne.setPrix(Double.parseDouble(prixField.getText().trim()));
            } else {
                livreSelectionne.setPrix(null);
            }
            
            livreSelectionne.setTypeLivre(typeLivreCombo.getValue());
            
            livreDAO.update(livreSelectionne);
            chargerLivres();
            viderFormulaire();
            
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre modifié avec succès!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Année ou prix invalide!");
        }
    }
    
    @FXML
    private void handleSupprimer() {
        Livre livreSelectionne = livreTable.getSelectionModel().getSelectedItem();
        
        if (livreSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un livre à supprimer!");
            return;
        }
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer le livre");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce livre ?");
        
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            livreDAO.delete(livreSelectionne.getId());
            chargerLivres();
            viderFormulaire();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Livre supprimé avec succès!");
        }
    }
    
    @FXML
    private void handleVider() {
        viderFormulaire();
    }
    
    @FXML
    private void handleRechercher() {
        String mot = rechercheField.getText().trim();
        
        if (mot.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez saisir un mot à rechercher!");
            return;
        }
        
        ObservableList<Livre> resultats = FXCollections.observableArrayList(livreDAO.searchByAll(mot));
        livreTable.setItems(resultats);
        livreTable.refresh();
    }
    
    @FXML
    private void handleToutAfficher() {
        rechercheField.clear();
        chargerLivres();
    }
    
    private boolean validerChamps() {
        if (titreField.getText() == null || titreField.getText().trim().length() < 2) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le titre doit contenir au moins 2 caractères!");
            return false;
        }
        
        if (auteurField.getText() == null || auteurField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'auteur est obligatoire!");
            return false;
        }
        
        if (typeLivreCombo.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le type de livre est obligatoire!");
            return false;
        }
        
        if (!anneeField.getText().trim().isEmpty()) {
            try {
                int annee = Integer.parseInt(anneeField.getText().trim());
                if (annee < 1900 || annee > 2025) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'année doit être entre 1900 et 2025!");
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'année doit être un nombre valide!");
                return false;
            }
        }
        
        if (!prixField.getText().trim().isEmpty()) {
            try {
                double prix = Double.parseDouble(prixField.getText().trim());
                if (prix < 0) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être positif!");
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide!");
                return false;
            }
        }
        
        return true;
    }
    
    private void viderFormulaire() {
        titreField.clear();
        auteurField.clear();
        anneeField.clear();
        prixField.clear();
        typeLivreCombo.setValue(null);
        livreTable.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
