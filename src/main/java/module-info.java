module com.example.atm_machine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.atm_machine to javafx.fxml;
    exports com.example.atm_machine;
    exports com.example.atm_machine.Entity;
    opens com.example.atm_machine.Entity to javafx.fxml;
    exports com.example.atm_machine.Service;
    exports com.example.atm_machine.Connectivity;
}