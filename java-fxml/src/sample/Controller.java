package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    Label lbl_count;

    int _count = 0;

    @FXML
    public void initialize() {
        _count = 0;
        lbl_count.setText(Integer.toString(this._count));
    }

    @FXML
    public void onBtnUp(ActionEvent evt) {
        this._count += 1;
        lbl_count.setText(Integer.toString(this._count));
    }

    @FXML
    public void onBtnDown(ActionEvent evt) {
        this._count -= 1;
        lbl_count.setText(Integer.toString(this._count));
    }
}
