package br.com.pi.achadoseperdidos.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SideBar extends ScrollPane {
    private static final int CHECKBOXES_PER_GROUP = 6;
    private static final int TOTAL_CHECKBOXES = 40;
    private VBox content;

    public SideBar() {
        content = new VBox();
        content.setSpacing(5);
        content.setPrefWidth(150);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #f0f0f0;");

        this.setContent(content);
        this.setFitToWidth(true);
        this.setStyle("-fx-background-color: #f0f0f0;");

        createCheckBoxGroups();
    }

    private void createCheckBoxGroups() {
        for (int i = 1; i < TOTAL_CHECKBOXES; i++) {
            if (i % CHECKBOXES_PER_GROUP == 0) {
                javafx.scene.control.Label filterLabel = new javafx.scene.control.Label("categoria ");
                filterLabel.setPadding(new Insets(10, 0, 5, 0));
                filterLabel.setStyle("-fx-font-weight: bold");
                content.getChildren().add(filterLabel);
            }

            CheckBox checkBox = new CheckBox("Option " + (i + 1));
            content.getChildren().add(checkBox);

            if ((i + 1) % CHECKBOXES_PER_GROUP == 0 && i < TOTAL_CHECKBOXES - 1) {
                Separator separator = new Separator();
                separator.setPadding(new Insets(5, 0, 5, 0));
                content.getChildren().add(separator);
            }
        }
    }
}
