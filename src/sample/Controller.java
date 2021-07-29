package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class Controller {
    public TextArea MsgArea;
    public TextField MsgField;
    public Button SendMsg;

    public void btnSendMsg(ActionEvent actionEvent) {
        if (!MsgField.getText().isEmpty()) {
            MsgArea.appendText(MsgField.getText() + "\n");
            MsgField.clear();
            MsgField.requestFocus();
        }
    }

    public void js2controller(ObservableValue observable, String oldValue, String newValue) {
//            if (!MsgField.getText().isEmpty())
        if (!newValue.isEmpty()) {
//            System.out.println("enable button");
            SendMsg.setDisable(false);
        } else {
            SendMsg.setDisable(true);
//            System.out.println("disable button");
        }
    }

    public void btnClearChat(ActionEvent actionEvent) {
        System.out.println("Clear MsgArea");
        MsgArea.clear();
    }

    public void clickClose(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            Stage stage = (Stage) SendMsg.getScene().getWindow();
            stage.close();
        });
    }
}
