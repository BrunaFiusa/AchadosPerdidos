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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CadHome extends Application {

    private static Stage cadastroStage = null;

    private FilteredList<Item> filteredItems;
    private ObservableList<Item> allItems;
    private String categoryFilter = "";
    private TextField searchField;

    private BorderPane root;
    private VBox fixedSearchArea;
    private Pane contentContainer;

    private Node objectsView;
    private Node requestsView;
    private Node returnsView;
    private Node reportsView;

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
        TopBar topBar = new TopBar(this::handleNavigation);
        SideBar sideBar = new SideBar();

        root = new BorderPane();
        root.setTop(new VBox(topBar));
        root.setLeft(sideBar);
        root.setStyle("-fx-background-color: #F5F5F5;");

        fixedSearchArea = new VBox(20);
        fixedSearchArea.setPadding(new Insets(20));

        contentContainer = new StackPane();
        VBox.setVgrow(contentContainer, Priority.ALWAYS);

        VBox center = new VBox(fixedSearchArea, contentContainer);
        VBox.setVgrow(center, Priority.ALWAYS);
        root.setCenter(center);

        createFakeData();
        objectsView = createObjectsView();
        requestsView = createRequestsView();
        returnsView = createReturnsView();
        reportsView = createReportsView();

        handleNavigation("Objetos");

        sideBar.setSelectionListener(selected -> {
            categoryFilter = selected.isEmpty() ? "" : selected.get(0).toLowerCase();
            updatePredicate();
        });

        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Sistema de Achados e Perdidos");
        stage.setMaximized(true);
        stage.show();
    }

    private void handleNavigation(String section) {
        Node view = null;
        boolean showSearch = false;

        switch (section) {
            case "Objetos" -> { view = objectsView; showSearch = true; }
            case "Solicitações" -> view = requestsView;
            case "Devoluções" -> view = returnsView;
            case "Relatórios" -> view = reportsView;
        }

        fixedSearchArea.getChildren().clear();
        if (showSearch) {
            fixedSearchArea.getChildren().add(createSearchBar());
        }

        contentContainer.getChildren().clear();
        if (view != null) {
            contentContainer.getChildren().add(view);
            if (view instanceof Region region) {
                VBox.setVgrow(region, Priority.ALWAYS);
            }
        }
    }

    private HBox createSearchBar() {
        HBox searchBar = new HBox(10);
        searchBar.setPadding(new Insets(10));
        searchBar.setStyle("-fx-background-color: #C4AF95; -fx-border-radius: 5; -fx-background-radius: 5;");
        searchBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Pesquisar por descrição, local ou data...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #333333; -fx-prompt-text-fill: #8B7A68; -fx-border-color: #A67C52; -fx-border-radius: 5;");
        searchField.textProperty().addListener((obs, old, novo) -> updatePredicate());

        Button registerButton = new Button("Cadastrar Item");
        registerButton.setStyle("-fx-background-color: #A67C52; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5; -fx-background-radius: 5;");
        registerButton.setOnAction(e -> openCadastroWindow());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchBar.getChildren().addAll(searchField, spacer, registerButton);
        return searchBar;
    }

    private void openCadastroWindow() {
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

    private Node createObjectsView() {
        PaginatedTableView<Item> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);
        TableView<Item> tableView = paginatedTable.getTableView();

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
                        getTableView().getItems().get(getIndex()).setStatus(newVal);
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
        paginatedTable.setItems(filteredItems);

        VBox container = new VBox(paginatedTable);
        container.setPadding(new Insets(0, 20, 20, 20));
        VBox.setVgrow(paginatedTable, Priority.ALWAYS);
        return container;
    }

    private Node createRequestsView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label title = new Label("Solicitações de Devolução");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #A67C52;");

        ObservableList<Solicitacao> solicitacoes = FXCollections.observableArrayList(
                new Solicitacao("045", "Celular Samsung", "João Silva", "joao@email.com", LocalDate.now().minusDays(2), "Pendente"),
                new Solicitacao("112", "Carteira com documentos", "Maria Oliveira", "maria@email.com", LocalDate.now().minusDays(1), "Pendente"),
                new Solicitacao("078", "Chaves de casa", "Pedro Santos", "pedro@email.com", LocalDate.now().minusDays(5), "Aprovada"),
                new Solicitacao("023", "Notebook Dell", "Ana Costa", "ana@email.com", LocalDate.now().minusDays(3), "Rejeitada")
        );

        PaginatedTableView<Solicitacao> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);
        TableView<Solicitacao> table = paginatedTable.getTableView();

        TableColumn<Solicitacao, String> colIdItem = new TableColumn<>("ID Item");
        colIdItem.setPrefWidth(100);
        colIdItem.setCellValueFactory(data -> data.getValue().idItemProperty());

        TableColumn<Solicitacao, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setPrefWidth(300);
        colDesc.setCellValueFactory(data -> data.getValue().descricaoProperty());

        TableColumn<Solicitacao, String> colNome = new TableColumn<>("Solicitante");
        colNome.setPrefWidth(200);
        colNome.setCellValueFactory(data -> data.getValue().nomeProperty());

        TableColumn<Solicitacao, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setPrefWidth(250);
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Solicitacao, String> colData = new TableColumn<>("Data");
        colData.setPrefWidth(150);
        colData.setCellValueFactory(data -> data.getValue().dataProperty());

        TableColumn<Solicitacao, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(150);
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        table.getColumns().addAll(colIdItem, colDesc, colNome, colEmail, colData, colStatus);
        paginatedTable.setItems(solicitacoes);

        VBox.setVgrow(paginatedTable, Priority.ALWAYS);
        container.getChildren().addAll(title, paginatedTable);

        return container;
    }

    private Node createReturnsView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label title = new Label("Histórico de Devoluções");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #A67C52;");

        ObservableList<Devolucao> devolucoes = FXCollections.observableArrayList(
                new Devolucao("045", "Celular Samsung", "João Silva", LocalDate.now().minusDays(5), "Admin"),
                new Devolucao("078", "Chaves de casa", "Pedro Santos", LocalDate.now().minusDays(10), "Admin"),
                new Devolucao("112", "Carteira", "Maria Oliveira", LocalDate.now().minusDays(15), "Usuário"),
                new Devolucao("023", "Notebook Dell", "Ana Costa", LocalDate.now().minusDays(20), "Admin")
        );

        PaginatedTableView<Devolucao> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);
        TableView<Devolucao> table = paginatedTable.getTableView();

        TableColumn<Devolucao, String> colId = new TableColumn<>("ID Item");
        colId.setPrefWidth(100);
        colId.setCellValueFactory(data -> data.getValue().idItemProperty());

        TableColumn<Devolucao, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setPrefWidth(300);
        colDesc.setCellValueFactory(data -> data.getValue().descricaoProperty());

        TableColumn<Devolucao, String> colDono = new TableColumn<>("Dono");
        colDono.setPrefWidth(200);
        colDono.setCellValueFactory(data -> data.getValue().donoProperty());

        TableColumn<Devolucao, String> colData = new TableColumn<>("Data Devolução");
        colData.setPrefWidth(150);
        colData.setCellValueFactory(data -> data.getValue().dataProperty());

        TableColumn<Devolucao, String> colResp = new TableColumn<>("Responsável");
        colResp.setPrefWidth(150);
        colResp.setCellValueFactory(data -> data.getValue().responsavelProperty());

        table.getColumns().addAll(colId, colDesc, colDono, colData, colResp);
        paginatedTable.setItems(devolucoes);

        VBox.setVgrow(paginatedTable, Priority.ALWAYS);
        container.getChildren().addAll(title, paginatedTable);

        return container;
    }

    private Node createReportsView() {
        VBox mainContainer = new VBox(30);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("Relatórios e Estatísticas do Sistema");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #A67C52;");

        HBox statsCards = new HBox(40);
        statsCards.setAlignment(Pos.CENTER);

        VBox cardTotal = createReportCard("Total de Itens Registrados", "428", "#4CAF50");
        VBox cardDevolvidos = createReportCard("Itens Devolvidos", "278 (65%)", "#2196F3");
        VBox cardPendentes = createReportCard("Itens Pendentes", "112 (26%)", "#FF9800");
        VBox cardDescartados = createReportCard("Itens Descartados", "38 (9%)", "#F44336");

        statsCards.getChildren().addAll(cardTotal, cardDevolvidos, cardPendentes, cardDescartados);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Devolvidos (65%)", 278),
                new PieChart.Data("Pendentes (26%)", 112),
                new PieChart.Data("Descartados (9%)", 38)
        );

        PieChart pieChart = new PieChart(pieData);
        pieChart.setTitle("Distribuição Atual dos Itens");
        pieChart.setLabelLineLength(30);
        pieChart.setLegendVisible(true);
        pieChart.setClockwise(true);
        pieChart.setStartAngle(90);
        pieChart.setPrefSize(500, 400);

        Label monthlyTitle = new Label("Resumo por Mês (2025)");
        monthlyTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #5D4037;");

        // Dados mensais (12 meses para 2025)
        ObservableList<MonthlySummary> monthlyData = FXCollections.observableArrayList();
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        int[] registrados = {45, 52, 61, 58, 67, 55, 48, 42, 70, 65, 59, 72};
        int[] devolvidos = {32, 38, 45, 40, 48, 35, 30, 28, 50, 48, 42, 55};
        int[] pendentes = {8, 10, 12, 14, 15, 16, 14, 10, 16, 14, 13, 14};
        int[] descartados = {5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 4, 3};

        for (int i = 0; i < meses.length; i++) {
            monthlyData.add(new MonthlySummary(meses[i], registrados[i], devolvidos[i], pendentes[i], descartados[i]));
        }

        PaginatedTableView<MonthlySummary> paginatedMonthly = new PaginatedTableView<>();
        paginatedMonthly.setPageSize(10); // Mostra a paginação com 12 itens
        TableView<MonthlySummary> monthlyTable = paginatedMonthly.getTableView();

        TableColumn<MonthlySummary, String> colMonth = new TableColumn<>("Mês");
        colMonth.setPrefWidth(150);
        colMonth.setCellValueFactory(data -> data.getValue().monthProperty());

        TableColumn<MonthlySummary, String> colRegistered = new TableColumn<>("Registrados");
        colRegistered.setPrefWidth(120);
        colRegistered.setCellValueFactory(data -> data.getValue().registeredProperty());

        TableColumn<MonthlySummary, String> colReturned = new TableColumn<>("Devolvidos");
        colReturned.setPrefWidth(120);
        colReturned.setCellValueFactory(data -> data.getValue().returnedProperty());

        TableColumn<MonthlySummary, String> colPending = new TableColumn<>("Pendentes");
        colPending.setPrefWidth(120);
        colPending.setCellValueFactory(data -> data.getValue().pendingProperty());

        TableColumn<MonthlySummary, String> colDiscarded = new TableColumn<>("Descartados");
        colDiscarded.setPrefWidth(120);
        colDiscarded.setCellValueFactory(data -> data.getValue().discardedProperty());

        monthlyTable.getColumns().addAll(colMonth, colRegistered, colReturned, colPending, colDiscarded);
        paginatedMonthly.setItems(monthlyData);

        VBox tableContainer = new VBox(10, monthlyTitle, paginatedMonthly);
        tableContainer.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        mainContainer.getChildren().addAll(title, statsCards, pieChart, tableContainer);

        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #F5F5F5;");

        return scroll;
    }

    private VBox createReportCard(String label, String value, String color) {
        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 38px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label lblLabel = new Label(label);
        lblLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");

        VBox card = new VBox(15, lblValue, lblLabel);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0.3, 0, 8);");

        return card;
    }

    private void createFakeData() {
        allItems = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] statuses = {"Pendente", "Devolvido", "Descartado"};
        List<String> subCategories = Arrays.asList("Celular", "Tablet", "Notebook", "Fone de Ouvido", "Carregador", "Câmera",
                "Camiseta", "Calça", "Jaqueta", "Sapato", "Boné", "Vestido", "Bolsa", "Carteira", "Óculos", "Relógio", "Jóias", "Chaveiro",
                "RG", "CPF", "Passaporte", "Carteira de Motorista", "Cartão", "Chaves", "Livro", "Guarda-chuva", "Brinquedo", "Garrafa");

        for (int i = 1; i <= 150; i++) {
            String subCat = subCategories.get(rand.nextInt(subCategories.size()));
            allItems.add(new Item(
                    String.format("%03d", i),
                    subCat + " - Exemplo de item achado/perdido",
                    "Local " + (rand.nextInt(15) + 1),
                    LocalDate.now().minusDays(rand.nextInt(90)).toString(),
                    statuses[rand.nextInt(3)],
                    "contato" + i + "@exemplo.com"
            ));
        }

        filteredItems = new FilteredList<>(allItems, p -> true);
    }

    private void updatePredicate() {
        if (searchField == null) return;
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase();

        filteredItems.setPredicate(item -> {
            if (!categoryFilter.isEmpty() && !item.getDescription().toLowerCase().contains(categoryFilter)) {
                return false;
            }
            if (searchText.isEmpty()) return true;

            return item.getDescription().toLowerCase().contains(searchText) ||
                    item.getLocation().toLowerCase().contains(searchText) ||
                    item.getDate().contains(searchText) ||
                    item.getStatus().toLowerCase().contains(searchText) ||
                    item.getContact().toLowerCase().contains(searchText);
        });
    }

    // Classes internas
    public static class Solicitacao {
        private final StringProperty idItem = new SimpleStringProperty();
        private final StringProperty descricao = new SimpleStringProperty();
        private final StringProperty nome = new SimpleStringProperty();
        private final StringProperty email = new SimpleStringProperty();
        private final StringProperty data = new SimpleStringProperty();
        private final StringProperty status = new SimpleStringProperty();

        public Solicitacao(String idItem, String descricao, String nome, String email, LocalDate data, String status) {
            this.idItem.set(idItem);
            this.descricao.set(descricao);
            this.nome.set(nome);
            this.email.set(email);
            this.data.set(data.toString());
            this.status.set(status);
        }

        public StringProperty idItemProperty() { return idItem; }
        public StringProperty descricaoProperty() { return descricao; }
        public StringProperty nomeProperty() { return nome; }
        public StringProperty emailProperty() { return email; }
        public StringProperty dataProperty() { return data; }
        public StringProperty statusProperty() { return status; }
    }

    public static class Devolucao {
        private final StringProperty idItem = new SimpleStringProperty();
        private final StringProperty descricao = new SimpleStringProperty();
        private final StringProperty dono = new SimpleStringProperty();
        private final StringProperty data = new SimpleStringProperty();
        private final StringProperty responsavel = new SimpleStringProperty();

        public Devolucao(String idItem, String descricao, String dono, LocalDate data, String responsavel) {
            this.idItem.set(idItem);
            this.descricao.set(descricao);
            this.dono.set(dono);
            this.data.set(data.toString());
            this.responsavel.set(responsavel);
        }

        public StringProperty idItemProperty() { return idItem; }
        public StringProperty descricaoProperty() { return descricao; }
        public StringProperty donoProperty() { return dono; }
        public StringProperty dataProperty() { return data; }
        public StringProperty responsavelProperty() { return responsavel; }
    }

    public static class MonthlySummary {
        private final StringProperty month = new SimpleStringProperty();
        private final StringProperty registered = new SimpleStringProperty();
        private final StringProperty returned = new SimpleStringProperty();
        private final StringProperty pending = new SimpleStringProperty();
        private final StringProperty discarded = new SimpleStringProperty();

        public MonthlySummary(String month, int registered, int returned, int pending, int discarded) {
            this.month.set(month);
            this.registered.set(String.valueOf(registered));
            this.returned.set(String.valueOf(returned));
            this.pending.set(String.valueOf(pending));
            this.discarded.set(String.valueOf(discarded));
        }

        public StringProperty monthProperty() { return month; }
        public StringProperty registeredProperty() { return registered; }
        public StringProperty returnedProperty() { return returned; }
        public StringProperty pendingProperty() { return pending; }
        public StringProperty discardedProperty() { return discarded; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}