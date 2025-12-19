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
import java.util.Random;

public class CadAdm extends Application {

    private static Stage cadastroStage = null;
    private static Stage usuariosStage = null;

    private ObservableList<Item> allItems;
    private FilteredList<Item> filteredItems;

    private TextField searchField;
    private ChoiceBox<String> filterType;
    private ChoiceBox<String> filterStatus;
    private HBox statsBar;

    private Pane contentContainer;
    private Node dashboardView;
    private Node requestsView;
    private Node returnsView;
    private Node reportsView;

    @Override
    public void start(Stage primaryStage) {
        TopBar topBar = new TopBar(this::handleNavigation);
        SideBar sideBar = new SideBar();

        BorderPane root = new BorderPane();
        root.setTop(new VBox(topBar));
        root.setLeft(sideBar);
        root.setStyle("-fx-background-color: #F5F5F5;");

        contentContainer = new StackPane();
        VBox.setVgrow(contentContainer, Priority.ALWAYS);
        root.setCenter(contentContainer);

        allItems = generateSampleData();
        filteredItems = new FilteredList<>(allItems, p -> true);

        dashboardView = createDashboardView();
        requestsView = createRequestsView();
        returnsView = createReturnsView();
        reportsView = createReportsView();

        handleNavigation("Objetos");

        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Administrador - Sistema de Achados e Perdidos");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void handleNavigation(String section) {
        Node view = switch (section) {
            case "Objetos" -> dashboardView;
            case "Solicitações" -> requestsView;
            case "Devoluções" -> returnsView;
            case "Relatórios" -> reportsView;
            default -> dashboardView;
        };

        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(view);
        if (view instanceof Region region) {
            VBox.setVgrow(region, Priority.ALWAYS);
        }
    }

    private Node createDashboardView() {
        VBox dashboard = new VBox(15);
        dashboard.setPadding(new Insets(20));

        HBox searchBar = createSearchBar();
        dashboard.getChildren().add(searchBar);

        statsBar = createStatsBar();
        dashboard.getChildren().add(statsBar);

        PaginatedTableView<Item> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);
        TableView<Item> tableView = paginatedTable.getTableView();
        configureTableColumns(tableView);
        paginatedTable.setItems(filteredItems);

        Runnable applyFilters = this::applyFilters;
        searchField.textProperty().addListener((o, ov, nv) -> applyFilters.run());
        filterType.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> applyFilters.run());
        filterStatus.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> applyFilters.run());

        dashboard.getChildren().add(paginatedTable);
        VBox.setVgrow(paginatedTable, Priority.ALWAYS);

        return dashboard;
    }

    private HBox createSearchBar() {
        HBox bar = new HBox(10);
        bar.setPadding(new Insets(15));
        bar.setStyle("-fx-background-color: #C4AF95; -fx-border-radius: 5; -fx-background-radius: 5;");
        bar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Pesquisar por descrição, local, data ou contato...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #333333; -fx-prompt-text-fill: #8B7A68; -fx-border-color: #A67C52; -fx-border-radius: 5;");

        filterType = new ChoiceBox<>(FXCollections.observableArrayList("Todos", "Achado", "Perdido"));
        filterType.setValue("Todos");
        filterType.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 5;");

        filterStatus = new ChoiceBox<>(FXCollections.observableArrayList("Todos", "Pendente", "Devolvido", "Descartado"));
        filterStatus.setValue("Todos");
        filterStatus.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 5;");

        Button registerButton = new Button("Cadastrar Item");
        registerButton.setStyle("-fx-background-color: #A67C52; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5;");
        registerButton.setOnAction(e -> openCadastro());

        Button manageUsersBtn = new Button("Gerenciar Usuários");
        manageUsersBtn.setStyle("-fx-background-color: #8B5A2B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5;");
        manageUsersBtn.setOnAction(e -> openUserManagement());

        Button logsBtn = new Button("Ver Logs");
        logsBtn.setStyle("-fx-background-color: #6B4423; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5;");
        logsBtn.setOnAction(e -> showLogs());

        bar.getChildren().addAll(searchField, filterType, filterStatus, registerButton, manageUsersBtn, logsBtn);
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

        TableColumn<Item, Void> colActions = new TableColumn<>("Ações");
        colActions.setPrefWidth(200);
        colActions.setCellFactory(param -> new TableCell<>() {
            private final ChoiceBox<String> statusBox = new ChoiceBox<>(FXCollections.observableArrayList("Pendente", "Devolvido", "Descartado"));
            private final Button deleteBtn = new Button("Excluir");

            {
                statusBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #A67C52; -fx-border-radius: 3;");
                deleteBtn.setStyle("-fx-background-color: #C62828; -fx-text-fill: white; -fx-font-size: 11; -fx-padding: 5 10; -fx-border-radius: 3;");

                statusBox.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
                    if (val != null && getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                        Item item = getTableView().getItems().get(getIndex());
                        item.setStatus(val);
                        updateStats();
                    }
                });

                deleteBtn.setOnAction(e -> {
                    if (getIndex() < 0 || getIndex() >= getTableView().getItems().size()) return;
                    Item item = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir o item " + item.getId() + "?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.YES) {
                            allItems.remove(item);
                            applyFilters();
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

    private void applyFilters() {
        String query = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String type = filterType.getValue();
        String status = filterStatus.getValue();

        filteredItems.setPredicate(item -> {
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
        int total = filteredItems.size();
        int pendentes = (int) filteredItems.stream().filter(i -> "Pendente".equals(i.getStatus())).count();
        int devolvidos = (int) filteredItems.stream().filter(i -> "Devolvido".equals(i.getStatus())).count();
        int descartados = (int) filteredItems.stream().filter(i -> "Descartado".equals(i.getStatus())).count();

        ((Label) statsBar.getChildren().get(0)).setText("Total: " + total);
        ((Label) statsBar.getChildren().get(1)).setText("Pendentes: " + pendentes);
        ((Label) statsBar.getChildren().get(2)).setText("Devolvidos: " + devolvidos);
        ((Label) statsBar.getChildren().get(3)).setText("Descartados: " + descartados);
    }

    private ObservableList<Item> generateSampleData() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] types = {"Achado", "Perdido"};
        String[] statuses = {"Pendente", "Devolvido", "Descartado"};
        String[] locais = {"Recepção", "Sala 101", "Biblioteca", "Refeitório", "Estacionamento", "Ginásio", "Laboratório"};

        for (int i = 1; i <= 150; i++) {
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

    private Node createRequestsView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));

        Label title = new Label("Solicitações de Devolução (Admin)");
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

        TableColumn<Solicitacao, Void> colActions = new TableColumn<>("Ações");
        colActions.setPrefWidth(200);
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button approveBtn = new Button("Aprovar");
            private final Button rejectBtn = new Button("Rejeitar");

            {
                approveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 11; -fx-padding: 5 10;");
                rejectBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 11; -fx-padding: 5 10;");

                approveBtn.setOnAction(e -> {
                    if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                        Solicitacao s = getTableView().getItems().get(getIndex());
                        s.status.set("Aprovada");
                    }
                });

                rejectBtn.setOnAction(e -> {
                    if (getIndex() >= 0 && getIndex() < getTableView().getItems().size()) {
                        Solicitacao s = getTableView().getItems().get(getIndex());
                        s.status.set("Rejeitada");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(8, approveBtn, rejectBtn));
                }
            }
        });

        table.getColumns().addAll(colIdItem, colDesc, colNome, colEmail, colData, colStatus, colActions);
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

        ObservableList<MonthlySummary> monthlyData = FXCollections.observableArrayList();
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        int[] registrados = {45, 52, 61, 58, 67, 55, 48, 42, 70, 65, 59, 72};
        int[] devolvidos = {32, 38, 45, 40, 48, 35, 30, 28, 50, 48, 42, 55};
        int[] pendentes = {8, 10, 12, 14, 15, 16, 14, 10, 16, 14, 13, 14};
        int[] descartados = {5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 4, 3};

        for (int i = 0; i < meses.length; i++) {
            monthlyData.add(new MonthlySummary(meses[i], registrados[i], devolvidos[i], pendentes[i], descartados[i]));
        }

        PaginatedTableView<MonthlySummary> paginatedMonthly = new PaginatedTableView<>();
        paginatedMonthly.setPageSize(10);
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
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F5F5;");

        Label title = new Label("Gerenciamento de Usuários");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #5D4037;");

        ObservableList<User> users = FXCollections.observableArrayList(
                new User("001", "admin@exemplo.com", "Administrador", "Ativo"),
                new User("002", "joao@exemplo.com", "Usuário", "Ativo"),
                new User("003", "maria@exemplo.com", "Usuário", "Inativo"),
                new User("004", "pedro@exemplo.com", "Usuário", "Ativo")
        );

        PaginatedTableView<User> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(20);
        TableView<User> userTable = paginatedTable.getTableView();

        TableColumn<User, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<User, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<User, String> colRole = new TableColumn<>("Função");
        colRole.setCellValueFactory(data -> data.getValue().roleProperty());

        TableColumn<User, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        userTable.getColumns().addAll(colId, colEmail, colRole, colStatus);
        paginatedTable.setItems(users);

        Button closeBtn = new Button("Fechar");
        closeBtn.setStyle("-fx-background-color: #6B4423; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16; -fx-border-radius: 5;");
        closeBtn.setOnAction(e -> usuariosStage.close());

        VBox.setVgrow(paginatedTable, Priority.ALWAYS);
        root.getChildren().addAll(title, paginatedTable, closeBtn);

        Scene scene = new Scene(root, 800, 600);
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
                "19/12/2025 15:45 - Admin alterou status do item 045 para 'Devolvido'\n" +
                        "19/12/2025 14:20 - Usuário cadastrou novo item achado\n" +
                        "19/12/2025 13:10 - Item 023 excluído pelo administrador\n" +
                        "19/12/2025 10:30 - Login administrativo realizado"
        );
        alert.getDialogPane().setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13;");
        alert.showAndWait();
    }

    // ==================== CLASSES INTERNAS ====================

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