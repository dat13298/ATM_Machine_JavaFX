module com.example.atm_machine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.atm_machine to javafx.fxml;
    exports com.atm_machine;
    exports com.atm_machine.Entity;
    opens com.atm_machine.Entity to javafx.fxml;
    exports com.atm_machine.Service;
    exports com.atm_machine.Connectivity;
}