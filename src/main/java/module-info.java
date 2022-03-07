module com.example.chekingmail_interface {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.example.chekingmail_interface to javafx.fxml;
    exports com.example.chekingmail_interface;
}