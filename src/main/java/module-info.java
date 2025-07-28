module lab9.lab9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab9.lab9 to javafx.fxml;
    exports lab9.lab9;
}