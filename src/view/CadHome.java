package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.components.TopBar;
import view.components.SideBar;
import view.CadFormulario;

public class CadHome extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane tela = new BorderPane();

        HBox searchBar = new HBox(10);
        searchBar.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar...");
        searchField.setMaxWidth(300);
        final Stage[] cadastroStage = {null};
        Button registerButton = new Button("Cadastrar");

        registerButton.setOnAction(e -> {
            if (cadastroStage[0] == null) {
                cadastroStage[0] = new Stage();
                CadCadastro cadastro = new CadCadastro();
                try {
                    cadastro.start(cadastroStage[0]);
                    cadastroStage[0].setOnCloseRequest(event -> cadastroStage[0] = null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    cadastroStage[0] = null;
                }
            } else {
                cadastroStage[0].requestFocus();
            }
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchBar.getChildren().addAll(searchField, spacer, registerButton);

        VBox topContainer = new VBox();
        topContainer.getChildren().add(topBar);
        tela.setTop(topContainer);
        tela.setLeft(sideBar);

        VBox contentArea = new VBox(10);
        contentArea.setPadding(new Insets(20));
        contentArea.getChildren().add(searchBar);


        Scene scene = new Scene(tela, 800, 600);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Home");
        stage.show();
    }
}
