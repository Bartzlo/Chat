package client;

import common.InOutMessage;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by Bart on 26.09.2014.
 */
public class Controller {

    @FXML
    TextArea mainArea;
    @FXML
    TextField enterArea;

    Connect connect = new Connect();
    //InnerMessage innerMessage = new InnerMessage();

    public void initialize(){
        enterArea.setDisable(true);
        connect.start();
    }

    @FXML
    private void enterText(){
        OutMessage sendMes = new OutMessage(enterArea.getText());
        new Thread(sendMes).run();
        enterArea.clear();
    }

}

