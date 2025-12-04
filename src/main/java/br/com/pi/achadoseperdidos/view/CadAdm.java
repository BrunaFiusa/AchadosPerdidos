package br.com.pi.achadoseperdidos.view;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Random;

public class CadAdm extends Application {

    private static Stage cadastroStage = null;
    private static Stage usuariosStage = null;

    private HBox statsBar; // Referência para atualizar estatísticas
    private ObservableList<Item> allItems; // Todos os itens (não filtrados)

    @Override
    public void start(Stage primaryStage) {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // === CABEÇALHO ADMIN ===
        HBox adminHeader = createAdminHeader();

        // === BARRA DE PESQUISA E FILTROS ===
        HBox searchBar = createSearchBar();

        // === ESTATÍSTICAS ===
        statsBar = createStatsBar();

        VBox topContainer = new VBox(10, adminHeader, searchBar, statsBar);
        topContainer.setPadding(new Insets(15));
        root.setTop(topContainer);
        root.setLeft(sideBar);

        // === TABELA ===
        TableView<Item> tableView = createItemTable();
        allItems = generateSampleData();
        FilteredList<Item> filteredItems = new FilteredList<>(allItems, p -> true);
        tableView.setItems(filteredItems);

        // Filtros
        TextField searchField = (TextField) searchBar.getChildren().get(0);
        ChoiceBox<String> filterType = (ChoiceBox<String>) searchBar.getChildren().get(1);
        ChoiceBox<String> filterStatus = (ChoiceBox<String>) searchBar.getChildren().get(2);

        searchField.textProperty().addListener((obs, old, novo) -> applyFiltersAndUpdate(filteredItems, searchField, filterType, filterStatus));
        filterType.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> applyFiltersAndUpdate(filteredItems, searchField, filterType, filterStatus));
        filterStatus.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> applyFiltersAndUpdate(filteredItems, searchField, filterType, filterStatus));

        // Conteúdo principal
        VBox content = new VBox(15, tableView);
        content.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: #F5F5F5;");
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Administrador - Sistema de Achados e Perdidos");
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Atualiza estatísticas iniciais
        updateStats();
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
        registerButton.setOnAction(e -> openCadastro());

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

    private TableView<Item> createItemTable() {
        TableView<Item> table = new TableView<>();
        table.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #C4AF95; -fx-table-cell-border-color: #E0E0E0;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Colunas
        TableColumn<Item, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> colType = new TableColumn<>("Tipo");
        colType.setCellValueFactory(cell -> cell.getValue().typeProperty());

        TableColumn<Item, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, String> colLoc = new TableColumn<>("Local");
        colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Item, String> colDate = new TableColumn<>("Data");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Item, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Item, String> colContact = new TableColumn<>("Contato");
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<Item, Void> colActions = new TableColumn<>("Ações");
        colActions.setCellFactory(param -> new TableCell<>() {
            private final ChoiceBox<String> statusBox = new ChoiceBox<>(FXCollections.observableArrayList("Pendente", "Devolvido", "Descartado"));
            private final Button deleteBtn = new Button("Excluir");

            {
                statusBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 3;");
                deleteBtn.setStyle("-fx-background-color: #C62828; -fx-text-fill: white; -fx-font-size: 10; -fx-padding: 4 8; -fx-border-radius: 3; -fx-background-radius: 3;");

                HBox box = new HBox(5, statusBox, deleteBtn);
                setGraphic(box);

                statusBox.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
                    if (val != null && getTableRow() != null) {
                        Item item = getTableView().getItems().get(getIndex());
                        item.setStatus(val);
                        updateStats();
                    }
                });

                deleteBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Excluir item " + item.getId() + "?", ButtonType.YES, ButtonType.NO);
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
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Item current = getTableView().getItems().get(getIndex());
                    statusBox.setValue(current.getStatus());
                    setGraphic(new HBox(5, statusBox, deleteBtn));
                }
            }
        });

        table.getColumns().addAll(colId, colType, colDesc, colLoc, colDate, colStatus, colContact, colActions);
        return table;
    }

    private ObservableList<Item> generateSampleData() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] types = {"Achado", "Perdido"};
        String[] statuses = {"Pendente", "Devolvido", "Descartado"};
        String[] locais = {"Recepção", "Sala 101", "Biblioteca", "Refeitório", "Estacionamento"};

        for (int i = 1; i <= 50; i++) {
            String type = types[rand.nextInt(types.length)];
            items.add(new Item(
                    String.format("%03d", i),
                    type + " - Item " + i + " (ex: carteira, chaves, livro)",
                    locais[rand.nextInt(locais.length)],
                    LocalDate.now().minusDays(rand.nextInt(60)).toString(),
                    statuses[rand.nextInt(statuses.length)],
                    "usuario" + i + "@exemplo.com",
                    type
            ));
        }
        return items;
    }

    private void applyFiltersAndUpdate(FilteredList<Item> filtered, TextField search, ChoiceBox<String> typeBox, ChoiceBox<String> statusBox) {
        filtered.setPredicate(item -> {
            String query = search.getText() == null ? "" : search.getText().toLowerCase();
            String type = typeBox.getValue();
            String status = statusBox.getValue();

            boolean matchesSearch = query.isEmpty() ||
                    item.getDescription().toLowerCase().contains(query) ||
                    item.getLocation().toLowerCase().contains(query) ||
                    item.getDate().contains(query) ||
                    item.getContact().toLowerCase().contains(query);

            boolean matchesType = "Todos".equals(type) || item.getType().equals(type);
            boolean matchesStatus = "Todos".equals(status) || item.getStatus().equals(status);

            return matchesSearch && matchesType && matchesStatus;
        });
        updateStats();
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
                cadastroStage = null;
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
                new User("003", "maria@exemplo.com", "Usuário", "Inativo")
        ));

        TableColumn<User, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> colRole = new TableColumn<>("Função");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        userTable.getColumns().addAll(colId, colEmail, colRole, colStatus);

        Button closeBtn = new Button("Fechar");
        styleButton(closeBtn, "#6B4423");
        closeBtn.setOnAction(e -> {
            usuariosStage.close();
            usuariosStage = null;
        });

        root.getChildren().addAll(title, userTable, closeBtn);
        Scene scene = new Scene(root, 700, 400);
        usuariosStage.setScene(scene);
        usuariosStage.setTitle("Gerenciar Usuários");
        usuariosStage.show();
        usuariosStage.setOnCloseRequest(e -> usuariosStage = null);
    }

    private void showLogs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logs do Sistema");
        alert.setHeaderText("Últimas Ações Registradas");
        alert.getDialogPane().setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13;");
        alert.setContentText(
                "28/10/2025 14:32 - Item 001 alterado para 'Devolvido'\n" +
                        "28/10/2025 13:15 - Usuário 'joao@exemplo.com' cadastrou item\n" +
                        "28/10/2025 12:01 - Item 012 excluído pelo admin\n" +
                        "28/10/2025 11:45 - Login administrativo"
        );
        alert.showAndWait();
    }

    // === CLASSES INTERNAS ===
    public static class Item {
        private final StringProperty id, description, location, date, status, contact, type;

        public Item(String id, String description, String location, String date, String status, String contact, String type) {
            this.id = new SimpleStringProperty(id);
            this.description = new SimpleStringProperty(description);
            this.location = new SimpleStringProperty(location);
            this.date = new SimpleStringProperty(date);
            this.status = new SimpleStringProperty(status);
            this.contact = new SimpleStringProperty(contact);
            this.type = new SimpleStringProperty(type);
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
        private final StringProperty id, email, role, status;

        public User(String id, String email, String role, String status) {
            this.id = new SimpleStringProperty(id);
            this.email = new SimpleStringProperty(email);
            this.role = new SimpleStringProperty(role);
            this.status = new SimpleStringProperty(status);
        }

        public String getId() { return id.get(); }
        public String getEmail() { return email.get(); }
        public String getRole() { return role.get(); }
        public String getStatus() { return status.get(); }

        public StringProperty idProperty() { return id; }
        public StringProperty emailProperty() { return email; }
        public StringProperty roleProperty() { return role; }
        public StringProperty statusProperty() { return status; }
    }

    // === MAIN ===
    public static void main(String[] args) {
        launch(args);
    }
}