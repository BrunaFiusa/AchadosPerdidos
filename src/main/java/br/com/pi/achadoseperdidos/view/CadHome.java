    package br.com.pi.achadoseperdidos.view;

    import br.com.pi.achadoseperdidos.view.components.PaginatedTableView;
    import br.com.pi.achadoseperdidos.view.components.SideBar;
    import br.com.pi.achadoseperdidos.view.components.TopBar;
    import javafx.application.Application;
    import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.property.StringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.collections.transformation.FilteredList;
    import javafx.geometry.Insets;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;
    import javafx.stage.Stage;

    import java.time.LocalDate;
    import java.util.Random;

    public class CadHome extends Application {
        private static Stage cadastroStage = null;

        public static class Item {
            private final StringProperty id = new SimpleStringProperty();
            private final StringProperty description = new SimpleStringProperty();
            private final StringProperty location = new SimpleStringProperty();
            private final StringProperty date = new SimpleStringProperty();
            private final StringProperty status = new SimpleStringProperty();
            private final StringProperty contact = new SimpleStringProperty();

            public Item(String id, String description, String location, String date, String status, String contact) {
                this.id.set(id);
                this.description.set(description);
                this.location.set(location);
                this.date.set(date);
                this.status.set(status);
                this.contact.set(contact);
            }

            public String getId() { return id.get(); }
            public StringProperty idProperty() { return id; }

            public String getDescription() { return description.get(); }
            public StringProperty descriptionProperty() { return description; }

            public String getLocation() { return location.get(); }
            public StringProperty locationProperty() { return location; }

            public String getDate() { return date.get(); }
            public StringProperty dateProperty() { return date; }

            public String getStatus() { return status.get(); }
            public StringProperty statusProperty() { return status; }
            public void setStatus(String newStatus) { status.set(newStatus); }

            public String getContact() { return contact.get(); }
            public StringProperty contactProperty() { return contact; }
        }

        @Override
        public void start(Stage stage) throws Exception {
            TopBar topBar = new TopBar();
            SideBar sideBar = new SideBar();

            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #F5F5F5;");


            VBox topContainer = new VBox(topBar);
            root.setTop(topContainer);


            root.setLeft(sideBar);


            VBox centerArea = new VBox();
            centerArea.setPadding(new Insets(20));
            centerArea.setSpacing(20);


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

            centerArea.getChildren().add(searchBar);


            PaginatedTableView<Item> paginatedTable = new PaginatedTableView<>();
            paginatedTable.setPageSize(50);

            TableView<Item> tableView = paginatedTable.getTableView();

            // Configuração das colunas
            TableColumn<Item, String> colId = new TableColumn<>("ID");
            colId.setPrefWidth(100);
            colId.setCellValueFactory(data -> data.getValue().idProperty());

            TableColumn<Item, String> colDescription = new TableColumn<>("Descrição");
            colDescription.setPrefWidth(300);
            colDescription.setCellValueFactory(data -> data.getValue().descriptionProperty());

            TableColumn<Item, String> colLocation = new TableColumn<>("Local");
            colLocation.setPrefWidth(200);
            colLocation.setCellValueFactory(data -> data.getValue().locationProperty());

            TableColumn<Item, String> colDate = new TableColumn<>("Data");
            colDate.setPrefWidth(150);
            colDate.setCellValueFactory(data -> data.getValue().dateProperty());

            TableColumn<Item, String> colStatus = new TableColumn<>("Status");
            colStatus.setPrefWidth(150);
            colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

            TableColumn<Item, String> colContact = new TableColumn<>("Contato");
            colContact.setPrefWidth(200);
            colContact.setCellValueFactory(data -> data.getValue().contactProperty());


            TableColumn<Item, Void> colActions = new TableColumn<>("Ações");
            colActions.setPrefWidth(150);
            colActions.setCellFactory(param -> new TableCell<>() {
                private final ChoiceBox<String> statusChoiceBox = new ChoiceBox<>(
                        FXCollections.observableArrayList("Pendente", "Devolvido", "Descartado"));

                {
                    statusChoiceBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52;");
                    statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
                        if (newVal != null && getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                            Item item = getTableView().getItems().get(getIndex());
                            item.setStatus(newVal);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getIndex() < 0) {
                        setGraphic(null);
                    } else {
                        Item currentItem = getTableView().getItems().get(getIndex());
                        statusChoiceBox.setValue(currentItem.getStatus());
                        setGraphic(statusChoiceBox);
                    }
                }
            });

            tableView.getColumns().addAll(colId, colDescription, colLocation, colDate, colStatus, colContact, colActions);

            // Dados de exemplo
            ObservableList<Item> allItems = FXCollections.observableArrayList();
            Random rand = new Random();
            String[] statuses = {"Pendente", "Devolvido", "Descartado"};

            for (int i = 1; i <= 150; i++) {
                allItems.add(new Item(
                        String.format("%03d", i),
                        "Item " + i + " - Exemplo de descrição do achado ou perdido",
                        "Local " + (rand.nextInt(10) + 1),
                        LocalDate.now().minusDays(rand.nextInt(90)).toString(),
                        statuses[rand.nextInt(3)],
                        "contato" + i + "@exemplo.com"
                ));
            }

            FilteredList<Item> filteredItems = new FilteredList<>(allItems, p -> true);
            paginatedTable.setItems(allItems); // Carrega inicialmente todos


            searchField.textProperty().addListener((obs, oldValue, newValue) -> {
                filteredItems.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) return true;
                    String filter = newValue.toLowerCase();
                    return item.getDescription().toLowerCase().contains(filter) ||
                            item.getLocation().toLowerCase().contains(filter) ||
                            item.getDate().contains(filter) ||
                            item.getStatus().toLowerCase().contains(filter) ||
                            item.getContact().toLowerCase().contains(filter);
                });
                paginatedTable.setItems(filteredItems);
            });


            centerArea.getChildren().add(paginatedTable);
            VBox.setVgrow(paginatedTable, Priority.ALWAYS); // Faz ocupar todo o espaço restante

            // Coloca a área central no BorderPane
            root.setCenter(centerArea);

            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Sistema de Achados e Perdidos");
            stage.setMaximized(true);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }