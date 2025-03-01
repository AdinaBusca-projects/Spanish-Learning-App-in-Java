module org.example.aprendovar4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens org.example.aprendovar4 to javafx.fxml;
    exports org.example.aprendovar4;
}