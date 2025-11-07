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
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import view.components.TopBar;
import view.components.SideBar;
import view.CadCadastro;

import java.time.LocalDate;
import java.util.Random;

public class CadHome extends Application {
    private static Stage cadastroStage = null;

    @Override
    public void start(Stage stage) throws Exception {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane tela = new BorderPane();
        tela.setStyle("-fx-background-color: #F5F5F5;");

        HBox searchBar = new HBox(10);
        searchBar.setPadding(new Insets(10));
        searchBar.setStyle("-fx-background-color: #C4AF95; -fx-border-radius: 5; -fx-background-radius: 5;");
        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar por descrição, local ou data...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #333333; -fx-prompt-text-fill: #8B7A68; -fx-border-color: #A67C52;");

        Button registerButton = new Button("Cadastrar Item");
        registerButton.setStyle("-fx-background-color: #A67C52; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        registerButton.setOnAction(e -> {
            if (cadastroStage == null) {
                cadastroStage = new Stage();
                CadCadastro cadastro = new CadCadastro();
                try {
                    cadastro.start(cadastroStage);
                    cadastroStage.setOnCloseRequest(event -> cadastroStage = null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    cadastroStage = null;
                }
            } else {
                cadastroStage.requestFocus();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        searchBar.getChildren().addAll(searchField, spacer, registerButton);

        VBox topContainer = new VBox();
        topContainer.getChildren().add(topBar);
        tela.setTop(topContainer);
        tela.setLeft(sideBar);

        VBox contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.getChildren().add(searchBar);

        // Define a model for Lost and Found items
        class Item {
            private final StringProperty id;
            private final StringProperty description;
            private final StringProperty location;
            private final StringProperty date;
            private final StringProperty status;
            private final StringProperty contact;

            public Item(String id, String description, String location, String date, String status, String contact) {
                this.id = new SimpleStringProperty(id);
                this.description = new SimpleStringProperty(description);
                this.location = new SimpleStringProperty(location);
                this.date = new SimpleStringProperty(date);
                this.status = new SimpleStringProperty(status);
                this.contact = new SimpleStringProperty(contact);
            }

            public String getId() { return id.get(); }
            public String getDescription() { return description.get(); }
            public String getLocation() { return location.get(); }
            public String getDate() { return date.get(); }
            public String getStatus() { return status.get(); }
            public StringProperty statusProperty() { return status; }
            public String getContact() { return contact.get(); }
            public void setStatus(String newStatus) { status.set(newStatus); }
        }

        // Create TableView with proper columns
        TableView<Item> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #C4AF95; -fx-table-cell-border-color: #E0E0E0;");
        tableView.setPrefHeight(900);
        tableView.setPrefWidth(1000);

        TableColumn<Item, String> colId = new TableColumn<>("ID");
        colId.setPrefWidth(100);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> colDescription = new TableColumn<>("Descrição");
        colDescription.setPrefWidth(300);
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, String> colLocation = new TableColumn<>("Local");
        colLocation.setPrefWidth(200);
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Item, String> colDate = new TableColumn<>("Data");
        colDate.setPrefWidth(150);
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Item, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(150);
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Item, String> colContact = new TableColumn<>("Contato");
        colContact.setPrefWidth(200);
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tableView.getColumns().addAll(colId, colDescription, colLocation, colDate, colStatus, colContact);

        // Generate sample data
        ObservableList<Item> items = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] statuses = {"Pendente", "Devolvido", "Descartado"};
        for (int i = 1; i <= 50; i++) {
            items.add(new Item(
                    String.format("%03d", i),
                    "Item " + i + " - Exemplo de descrição",
                    "Local " + (rand.nextInt(5) + 1),
                    LocalDate.now().minusDays(rand.nextInt(30)).toString(),
                    statuses[rand.nextInt(statuses.length)],
                    "contato" + i + "@exemplo.com"
            ));
        }

        // Filtered list for search functionality
        FilteredList<Item> filteredItems = new FilteredList<>(items, p -> true);
        tableView.setItems(filteredItems);

        // Add search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return item.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        item.getLocation().toLowerCase().contains(lowerCaseFilter) ||
                        item.getDate().toLowerCase().contains(lowerCaseFilter) ||
                        item.getStatus().toLowerCase().contains(lowerCaseFilter) ||
                        item.getContact().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Add status change dropdown column
        TableColumn<Item, Void> colActions = new TableColumn<>("Ações");
        colActions.setPrefWidth(150);
        colActions.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
            private final ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();

            {
                statusChoiceBox.getItems().addAll("Pendente", "Devolvido", "Descartado");
                statusChoiceBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-font-size: 12;");
                statusChoiceBox.setPrefWidth(120);

                statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                    if (newValue != null) {
                        Item item = getTableView().getItems().get(getIndex());
                        item.setStatus(newValue);
                        getTableView().refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Item currentItem = getTableView().getItems().get(getIndex());
                    statusChoiceBox.setValue(currentItem.getStatus());
                    setGraphic(statusChoiceBox);
                }
            }
        });
        tableView.getColumns().add(colActions);

        contentArea.getChildren().add(tableView);

        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: #F5F5F5;");
        tela.setCenter(scrollPane);

        Scene scene = new Scene(tela, 1200, 800);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Sistema de Achados e Perdidos");
        stage.show();
    }
}