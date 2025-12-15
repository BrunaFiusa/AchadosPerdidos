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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Random;

public class CadAdm extends Application {

    private static Stage cadastroStage = null;
    private static Stage usuariosStage = null;

    private HBox statsBar;
    private ObservableList<Item> allItems;

    @Override
    public void start(Stage primaryStage) {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Cabeçalho Admin
        HBox adminHeader = createAdminHeader();

        // Barra de pesquisa e filtros
        HBox searchBar = createSearchBar();

        // Barra de estatísticas
        statsBar = createStatsBar();

        VBox topContainer = new VBox(10, adminHeader, searchBar, statsBar);
        topContainer.setPadding(new Insets(15));
        root.setTop(topContainer);
        root.setLeft(sideBar);

        // ====================== ÁREA CENTRAL COM TABELA PAGINADA ======================
        VBox centerArea = new VBox(20);
        centerArea.setPadding(new Insets(20, 20, 0, 20)); // sem padding inferior para não dar espaço extra

        // Tabela paginada
        PaginatedTableView<Item> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);

        TableView<Item> tableView = paginatedTable.getTableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        configureTableColumns(tableView);

        // Dados de exemplo
        allItems = generateSampleData();
        FilteredList<Item> filteredItems = new FilteredList<>(allItems, p -> true);
        paginatedTable.setItems(allItems); // inicial

        // Captura os controles de filtro
        TextField searchField = (TextField) searchBar.getChildren().get(0);
        ChoiceBox<String> filterType = (ChoiceBox<String>) searchBar.getChildren().get(1);
        ChoiceBox<String> filterStatus = (ChoiceBox<String>) searchBar.getChildren().get(2);

        // Aplicar filtros
        Runnable applyFilters = () -> {
            filteredItems.setPredicate(item -> {
                String query = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
                String type = filterType.getValue();
                String status = filterStatus.getValue();

                boolean matchesSearch = query.isEmpty() ||
                        item.getDescription().toLowerCase().contains(query) ||
                        item.getLocation().toLowerCase().contains(query) ||
                        item.getDate().contains(query) ||
                        item.getContact().toLowerCase().contains(query);

                boolean matchesType = "Todos".equals(type) || item.getType().equals(type);
                boolean matchesStatus = "Todos".equals(status) || item.getStatus().equals(status);

                return matchesSearch && matchesType && matchesStatus;
            });
            paginatedTable.setItems(filteredItems);
            updateStats();
        };

        searchField.textProperty().addListener((obs, old, novo) -> applyFilters.run());
        filterType.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> applyFilters.run());
        filterStatus.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> applyFilters.run());

        // Botão cadastrar do searchBar
        Button registerButton = (Button) searchBar.getChildren().get(3);
        registerButton.setOnAction(e -> openCadastro());

        // Adiciona a tabela paginada ao centro
        centerArea.getChildren().add(paginatedTable);
        VBox.setVgrow(paginatedTable, Priority.ALWAYS);

        root.setCenter(centerArea);

        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Administrador - Sistema de Achados e Perdidos");
        primaryStage.setMaximized(true);
        primaryStage.show();

        updateStats();
    }

    private void configureTableColumns(TableView<Item> tableView) {
        TableColumn<Item, String> colId = new TableColumn<>("ID");
        colId.setPrefWidth(80);
        colId.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<Item, String> colType = new TableColumn<>("Tipo");
        colType.setPrefWidth(100);
        colType.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Item, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setPrefWidth(300);
        colDesc.setCellValueFactory(data -> data.getValue().descriptionProperty());

        TableColumn<Item, String> colLoc = new TableColumn<>("Local");
        colLoc.setPrefWidth(150);
        colLoc.setCellValueFactory(data -> data.getValue().locationProperty());

        TableColumn<Item, String> colDate = new TableColumn<>("Data");
        colDate.setPrefWidth(120);
        colDate.setCellValueFactory(data -> data.getValue().dateProperty());

        TableColumn<Item, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(120);
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        TableColumn<Item, String> colContact = new TableColumn<>("Contato");
        colContact.setPrefWidth(200);
        colContact.setCellValueFactory(data -> data.getValue().contactProperty());

        // Coluna Ações (ChoiceBox + Excluir)
        TableColumn<Item, Void> colActions = new TableColumn<>("Ações");
        colActions.setPrefWidth(200);
        colActions.setCellFactory(param -> new TableCell<>() {
            private final ChoiceBox<String> statusBox = new ChoiceBox<>(
                    FXCollections.observableArrayList("Pendente", "Devolvido", "Descartado"));
            private final Button deleteBtn = new Button("Excluir");

            {
                statusBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 3;");
                deleteBtn.setStyle("-fx-background-color: #C62828; -fx-text-fill: white; -fx-font-size: 11; -fx-padding: 5 10; -fx-border-radius: 3;");

                statusBox.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
                    if (val != null && getIndex() >= 0) {
                        Item item = getTableView().getItems().get(getIndex());
                        item.setStatus(val);
                        updateStats();
                    }
                });

                deleteBtn.setOnAction(e -> {
                    if (getIndex() < 0) return;
                    Item item = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Tem certeza que deseja excluir o item " + item.getId() + "?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.YES) {
                            allItems.remove(item);
                            updateStats();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Item current = getTableView().getItems().get(getIndex());
                    statusBox.setValue(current.getStatus());
                    setGraphic(new HBox(8, statusBox, deleteBtn));
                }
            }
        });

        tableView.getColumns().addAll(colId, colType, colDesc, colLoc, colDate, colStatus, colContact, colActions);
    }

    private HBox createAdminHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #A67C52; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label title = new Label("Painel do Administrador");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");

        Button manageUsersBtn = new Button("Gerenciar Usuários");
        styleButton(manageUsersBtn, "#8B5A2B");
        manageUsersBtn.setOnAction(e -> openUserManagement());

        Button logsBtn = new Button("Ver Logs");
        styleButton(logsBtn, "#6B4423");
        logsBtn.setOnAction(e -> showLogs());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(title, spacer, manageUsersBtn, logsBtn);
        return header;
    }

    private HBox createSearchBar() {
        HBox bar = new HBox(10);
        bar.setPadding(new Insets(15));
        bar.setStyle("-fx-background-color: #C4AF95; -fx-border-radius: 5; -fx-background-radius: 5;");

        TextField searchField = new TextField();
        searchField.setPromptText("Pesquisar por descrição, local, data ou contato...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #333333; -fx-prompt-text-fill: #8B7A68; -fx-border-color: #A67C52; -fx-border-radius: 5;");

        ChoiceBox<String> filterType = new ChoiceBox<>(FXCollections.observableArrayList("Todos", "Achado", "Perdido"));
        filterType.setValue("Todos");
        styleChoiceBox(filterType);

        ChoiceBox<String> filterStatus = new ChoiceBox<>(FXCollections.observableArrayList("Todos", "Pendente", "Devolvido", "Descartado"));
        filterStatus.setValue("Todos");
        styleChoiceBox(filterStatus);

        Button registerButton = new Button("Cadastrar Item");
        styleButton(registerButton, "#A67C52");

        bar.getChildren().addAll(searchField, filterType, filterStatus, registerButton);
        return bar;
    }

    private HBox createStatsBar() {
        HBox bar = new HBox(20);
        bar.setPadding(new Insets(10));
        bar.setStyle("-fx-background-color: #E8D5C4; -fx-border-radius: 5; -fx-background-radius: 5;");
        bar.setAlignment(Pos.CENTER_LEFT);

        Label total = new Label("Total: 0");
        Label pendentes = new Label("Pendentes: 0");
        Label devolvidos = new Label("Devolvidos: 0");
        Label descartados = new Label("Descartados: 0");

        Label[] labels = {total, pendentes, devolvidos, descartados};
        for (Label lbl : labels) {
            lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #5D4037; -fx-background-color: #FFFFFF; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        }

        bar.getChildren().addAll(labels);
        return bar;
    }

    private void styleButton(Button btn, String color) {
        btn.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;",
                color
        ));
    }

    private void styleChoiceBox(ChoiceBox<?> cb) {
        cb.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 5;");
    }

    private ObservableList<Item> generateSampleData() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] types = {"Achado", "Perdido"};
        String[] statuses = {"Pendente", "Devolvido", "Descartado"};
        String[] locais = {"Recepção", "Sala 101", "Biblioteca", "Refeitório", "Estacionamento", "Ginásio", "Laboratório"};

        for (int i = 1; i <= 150; i++) { // 150 para testar paginação
            String type = types[rand.nextInt(types.length)];
            items.add(new Item(
                    String.format("%03d", i),
                    type + " - Item " + i + " (ex: carteira, chaves, livro, celular)",
                    locais[rand.nextInt(locais.length)],
                    LocalDate.now().minusDays(rand.nextInt(90)).toString(),
                    statuses[rand.nextInt(statuses.length)],
                    "usuario" + i + "@exemplo.com",
                    type
            ));
        }
        return items;
    }

    private void updateStats() {
        int total = allItems.size();
        int pendentes = (int) allItems.stream().filter(i -> "Pendente".equals(i.getStatus())).count();
        int devolvidos = (int) allItems.stream().filter(i -> "Devolvido".equals(i.getStatus())).count();
        int descartados = (int) allItems.stream().filter(i -> "Descartado".equals(i.getStatus())).count();

        ((Label) statsBar.getChildren().get(0)).setText("Total: " + total);
        ((Label) statsBar.getChildren().get(1)).setText("Pendentes: " + pendentes);
        ((Label) statsBar.getChildren().get(2)).setText("Devolvidos: " + devolvidos);
        ((Label) statsBar.getChildren().get(3)).setText("Descartados: " + descartados);
    }

    private void openCadastro() {
        if (cadastroStage == null || !cadastroStage.isShowing()) {
            cadastroStage = new Stage();
            CadCadastro cadastro = new CadCadastro();
            try {
                cadastro.start(cadastroStage);
                cadastroStage.setOnCloseRequest(e -> cadastroStage = null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            cadastroStage.requestFocus();
        }
    }

    private void openUserManagement() {
        if (usuariosStage != null && usuariosStage.isShowing()) {
            usuariosStage.requestFocus();
            return;
        }

        usuariosStage = new Stage();
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("Gerenciamento de Usuários");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #5D4037;");

        TableView<User> userTable = new TableView<>();
        userTable.setItems(FXCollections.observableArrayList(
                new User("001", "admin@exemplo.com", "Administrador", "Ativo"),
                new User("002", "joao@exemplo.com", "Usuário", "Ativo"),
                new User("003", "maria@exemplo.com", "Usuário", "Inativo"),
                new User("004", "pedro@exemplo.com", "Usuário", "Ativo")
        ));

        TableColumn<User, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<User, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<User, String> colRole = new TableColumn<>("Função");
        colRole.setCellValueFactory(data -> data.getValue().roleProperty());

        TableColumn<User, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        userTable.getColumns().addAll(colId, colEmail, colRole, colStatus);

        Button closeBtn = new Button("Fechar");
        styleButton(closeBtn, "#6B4423");
        closeBtn.setOnAction(e -> usuariosStage.close());

        root.getChildren().addAll(title, userTable, closeBtn);
        Scene scene = new Scene(root, 700, 500);
        usuariosStage.setScene(scene);
        usuariosStage.setTitle("Gerenciar Usuários");
        usuariosStage.show();
        usuariosStage.setOnCloseRequest(e -> usuariosStage = null);
    }

    private void showLogs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logs do Sistema");
        alert.setHeaderText("Últimas Ações Registradas");
        alert.setContentText(
                "14/12/2025 15:45 - Admin alterou status do item 045 para 'Devolvido'\n" +
                        "14/12/2025 14:20 - Usuário cadastrou novo item achado\n" +
                        "14/12/2025 13:10 - Item 023 excluído pelo administrador\n" +
                        "14/12/2025 10:30 - Login administrativo realizado"
        );
        alert.getDialogPane().setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13;");
        alert.showAndWait();
    }

    // === CLASSES INTERNAS ===
    public static class Item {
        private final StringProperty id = new SimpleStringProperty();
        private final StringProperty description = new SimpleStringProperty();
        private final StringProperty location = new SimpleStringProperty();
        private final StringProperty date = new SimpleStringProperty();
        private final StringProperty status = new SimpleStringProperty();
        private final StringProperty contact = new SimpleStringProperty();
        private final StringProperty type = new SimpleStringProperty();

        public Item(String id, String description, String location, String date, String status, String contact, String type) {
            this.id.set(id);
            this.description.set(description);
            this.location.set(location);
            this.date.set(date);
            this.status.set(status);
            this.contact.set(contact);
            this.type.set(type);
        }

        public String getId() { return id.get(); }
        public String getDescription() { return description.get(); }
        public String getLocation() { return location.get(); }
        public String getDate() { return date.get(); }
        public String getStatus() { return status.get(); }
        public String getContact() { return contact.get(); }
        public String getType() { return type.get(); }

        public StringProperty idProperty() { return id; }
        public StringProperty descriptionProperty() { return description; }
        public StringProperty locationProperty() { return location; }
        public StringProperty dateProperty() { return date; }
        public StringProperty statusProperty() { return status; }
        public StringProperty contactProperty() { return contact; }
        public StringProperty typeProperty() { return type; }

        public void setStatus(String s) { status.set(s); }
    }

    public static class User {
        private final StringProperty id = new SimpleStringProperty();
        private final StringProperty email = new SimpleStringProperty();
        private final StringProperty role = new SimpleStringProperty();
        private final StringProperty status = new SimpleStringProperty();

        public User(String id, String email, String role, String status) {
            this.id.set(id);
            this.email.set(email);
            this.role.set(role);
            this.status.set(status);
        }

        public StringProperty idProperty() { return id; }
        public StringProperty emailProperty() { return email; }
        public StringProperty roleProperty() { return role; }
        public StringProperty statusProperty() { return status; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}