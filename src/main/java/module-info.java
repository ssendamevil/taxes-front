module com.course.taxesfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.json;


    opens com.course.taxesfront to javafx.fxml;
    exports com.course.taxesfront;
    exports com.course.taxesfront.controllers;
    opens com.course.taxesfront.controllers to javafx.fxml;
    exports com.course.taxesfront.dtos;
    opens com.course.taxesfront.dtos to javafx.fxml;
}