module com.btm.btmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.btm.btmanager to javafx.fxml;
    exports com.btm.btmanager;
}