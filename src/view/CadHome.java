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
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.components.TopBar;
import view.components.SideBar;
import view.CadCadastro;

public class CadHome extends Application {
    private static Stage cadastroStage = null;

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

        // Create TableView and columns
        TableView<String> tableView = new TableView<>();
        tableView.setPrefHeight(900);
        tableView.setPrefWidth(1000);
        TableColumn<String, String> column = new TableColumn<>("Items");
        TableColumn<String, String> column2 = new TableColumn<>("algo");
        TableColumn<String, String> column3 = new TableColumn<>("algo");
        TableColumn<String, String> column4 = new TableColumn<>("algo");
        TableColumn<String, String> column5 = new TableColumn<>("algo");
        TableColumn<String, String> column6 = new TableColumn<>("algo");



        column2.setPrefWidth(300);
        column3.setPrefWidth(300);
        column4.setPrefWidth(300);
        column5.setPrefWidth(300);
        column6.setPrefWidth(300);
        column.setPrefWidth(100);
        column.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> data.getValue()));


        tableView.getColumns().add(column);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);

        // Add sample data
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            items.add("Content Item " + (i + 1));
        }
        tableView.setItems(items);

        contentArea.getChildren().add(tableView);

        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tela.setCenter(scrollPane);

        Scene scene = new Scene(tela, 800, 600);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Home");
        stage.show();
    }
}
