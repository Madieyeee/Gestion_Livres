module sn.gestion.livres {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    
    opens sn.gestion.livres to javafx.fxml;
    opens sn.gestion.livres.controller to javafx.fxml;
    opens sn.gestion.livres.entity to org.hibernate.orm.core, javafx.base;
    
    exports sn.gestion.livres;
    exports sn.gestion.livres.controller;
}
