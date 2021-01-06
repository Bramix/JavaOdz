package bramix.design;

import javafx.scene.control.Alert;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message {

    private final String title;
    private final String detail;
    private final String moreDetail;


    public void show() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(detail);
        alert.setContentText(moreDetail);
        alert.show();
    }
}
