package br.com.pi.achadoseperdidos.view;

import br.com.pi.achadoseperdidos.view.components.PaginatedTableView;
import br.com.pi.achadoseperdidos.view.components.SideBar;
import br.com.pi.achadoseperdidos.view.components.TopBar;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Random;

public class CadFormulario extends Application {

    private FilteredList<FormItem> filteredItems;
    private ObservableList<FormItem> allItems;

    private TextField campoDescricao;
    private TextField campoLocal;
    private TextField campoContato;

    private PieChart pieChart;

    @Override
    public void start(Stage stage) throws Exception {
        // TopBar com listener (não troca tela aqui, mas evita erro de construtor)
        TopBar topBar = new TopBar(section -> {
            // No relatório de formulários, os botões da TopBar não fazem navegação
            System.out.println("Botão da TopBar clicado (Formulários): " + section);
        });

        SideBar sideBar = new SideBar();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        root.setTop(topBar);
        root.setLeft(sideBar);

        VBox centerArea = new VBox(20);
        centerArea.setPadding(new Insets(20));

        // === Barra de pesquisa ===
        HBox searchBox = createSearchBar();
        VBox searchContainer = new VBox(searchBox);
        searchContainer.setStyle("-fx-background-color: #C4AF95; -fx-padding: 20; -fx-border-radius: 8; -fx-background-radius: 8;");
        centerArea.getChildren().add(searchContainer);

        // === Gráfico de Pizza ===
        pieChart = createPieChart();
        VBox chartBox = new VBox(pieChart);
        chartBox.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 8);");
        chartBox.setMaxHeight(400);
        chartBox.setMinHeight(350);
        centerArea.getChildren().add(chartBox);

        // === Tabela Paginada ===
        PaginatedTableView<FormItem> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);

        TableView<FormItem> tableView = paginatedTable.getTableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        configureTableColumns(tableView);

        // Dados
        allItems = generateSampleData();
        filteredItems = new FilteredList<>(allItems, p -> true);
        paginatedTable.setItems(filteredItems);

        // Aplica filtro ao digitar
        Runnable updateFilter = this::updateFilterAndChart;
        campoDescricao.textProperty().addListener((obs, old, novo) -> updateFilter.run());
        campoLocal.textProperty().addListener((obs, old, novo) -> updateFilter.run());
        campoContato.textProperty().addListener((obs, old, novo) -> updateFilter.run());

        // Botão Pesquisar também atualiza
        Button btnPesquisar = (Button) searchBox.getChildren().get(3);
        btnPesquisar.setOnAction(e -> updateFilter.run());

        centerArea.getChildren().add(paginatedTable);
        VBox.setVgrow(paginatedTable, Priority.ALWAYS);

        root.setCenter(centerArea);

        Scene scene = new Scene(root, 1300, 800);
        stage.setScene(scene);
        stage.setTitle("Relatório de Formulários - Achados e Perdidos");
        stage.setMaximized(true);
        stage.show();

        // Atualiza gráfico inicial
        updateChart();
    }

    private HBox createSearchBar() {
        campoDescricao = new TextField();
        campoDescricao.setPromptText("Descrição do item...");
        campoDescricao.setPrefWidth(350);
        styleTextField(campoDescricao);

        campoLocal = new TextField();
        campoLocal.setPromptText("Local encontrado/perdido...");
        campoLocal.setPrefWidth(300);
        styleTextField(campoLocal);

        campoContato = new TextField();
        campoContato.setPromptText("Contato ou e-mail...");
        campoContato.setPrefWidth(300);
        styleTextField(campoContato);

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setStyle("-fx-background-color: #A67C52; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14; -fx-border-radius: 5; -fx-background-radius: 5;");

        HBox box = new HBox(15, campoDescricao, campoLocal, campoContato, btnPesquisar);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private void styleTextField(TextField tf) {
        tf.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #333333; -fx-prompt-text-fill: #8B7A68; -fx-border-color: #A67C52; -fx-border-radius: 5; -fx-background-radius: 5;");
    }

    private PieChart createPieChart() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Pendentes", 0),
                new PieChart.Data("Devolvidos", 0),
                new PieChart.Data("Descartados", 0)
        );

        PieChart chart = new PieChart(pieData);
        chart.setTitle("Distribuição por Status");
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(20);
        chart.setLegendVisible(true);
        chart.setClockwise(true);
        chart.setStartAngle(90);

        return chart;
    }

    private void configureTableColumns(TableView<FormItem> tableView) {
        TableColumn<FormItem, String> colId = new TableColumn<>("ID");
        colId.setPrefWidth(80);
        colId.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<FormItem, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setPrefWidth(100);
        colTipo.setCellValueFactory(data -> data.getValue().tipoProperty());

        TableColumn<FormItem, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setPrefWidth(350);
        colDescricao.setCellValueFactory(data -> data.getValue().descricaoProperty());

        TableColumn<FormItem, String> colLocal = new TableColumn<>("Local");
        colLocal.setPrefWidth(180);
        colLocal.setCellValueFactory(data -> data.getValue().localProperty());

        TableColumn<FormItem, String> colData = new TableColumn<>("Data");
        colData.setPrefWidth(120);
        colData.setCellValueFactory(data -> data.getValue().dataProperty());

        TableColumn<FormItem, String> colStatus = new TableColumn<>("Status");
        colStatus.setPrefWidth(120);
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        TableColumn<FormItem, String> colContato = new TableColumn<>("Contato");
        colContato.setPrefWidth(220);
        colContato.setCellValueFactory(data -> data.getValue().contatoProperty());

        tableView.getColumns().addAll(colId, colTipo, colDescricao, colLocal, colData, colStatus, colContato);
    }

    private ObservableList<FormItem> generateSampleData() {
        ObservableList<FormItem> items = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] tipos = {"Achado", "Perdido"};
        String[] status = {"Pendente", "Devolvido", "Descartado"};
        String[] locais = {"Recepção", "Sala de Aula", "Biblioteca", "Refeitório", "Estacionamento", "Ginásio"};

        for (int i = 1; i <= 150; i++) {
            String tipo = tipos[rand.nextInt(tipos.length)];
            items.add(new FormItem(
                    String.format("%03d", i),
                    tipo,
                    tipo + " - " + getDescricaoExemplo(tipo, rand),
                    locais[rand.nextInt(locais.length)],
                    LocalDate.now().minusDays(rand.nextInt(90)).toString(),
                    status[rand.nextInt(status.length)],
                    "usuario" + i + "@aluno.edu.br"
            ));
        }
        return items;
    }

    private String getDescricaoExemplo(String tipo, Random rand) {
        String[] achados = {"Carteira com documentos", "Chaves de casa", "Celular Android", "Fone de ouvido", "Mochila preta", "Óculos de grau"};
        String[] perdidos = {"Notebook Dell", "Livro de matemática", "Jaqueta jeans", "Garrafa térmica", "Relógio de pulso", "Bolsa feminina"};
        String[] exemplos = tipo.equals("Achado") ? achados : perdidos;
        return exemplos[rand.nextInt(exemplos.length)];
    }

    private void updateFilterAndChart() {
        String desc = campoDescricao.getText() == null ? "" : campoDescricao.getText().toLowerCase();
        String loc = campoLocal.getText() == null ? "" : campoLocal.getText().toLowerCase();
        String cont = campoContato.getText() == null ? "" : campoContato.getText().toLowerCase();

        filteredItems.setPredicate(item -> {
            boolean matchDesc = desc.isEmpty() || item.getDescricao().toLowerCase().contains(desc);
            boolean matchLoc = loc.isEmpty() || item.getLocal().toLowerCase().contains(loc);
            boolean matchCont = cont.isEmpty() || item.getContato().toLowerCase().contains(cont);
            return matchDesc && matchLoc && matchCont;
        });

        updateChart();
    }

    private void updateChart() {
        int pendentes = 0, devolvidos = 0, descartados = 0;

        for (FormItem item : filteredItems) {
            switch (item.getStatus()) {
                case "Pendente" -> pendentes++;
                case "Devolvido" -> devolvidos++;
                case "Descartado" -> descartados++;
            }
        }

        pieChart.getData().get(0).setPieValue(pendentes);
        pieChart.getData().get(1).setPieValue(devolvidos);
        pieChart.getData().get(2).setPieValue(descartados);

        pieChart.getData().get(0).setName("Pendentes (" + pendentes + ")");
        pieChart.getData().get(1).setName("Devolvidos (" + devolvidos + ")");
        pieChart.getData().get(2).setName("Descartados (" + descartados + ")");
    }

    // ==================== CLASSE INTERNA ====================
    public static class FormItem {
        private final SimpleStringProperty id = new SimpleStringProperty();
        private final SimpleStringProperty tipo = new SimpleStringProperty();
        private final SimpleStringProperty descricao = new SimpleStringProperty();
        private final SimpleStringProperty local = new SimpleStringProperty();
        private final SimpleStringProperty data = new SimpleStringProperty();
        private final SimpleStringProperty status = new SimpleStringProperty();
        private final SimpleStringProperty contato = new SimpleStringProperty();

        public FormItem(String id, String tipo, String descricao, String local, String data, String status, String contato) {
            this.id.set(id);
            this.tipo.set(tipo);
            this.descricao.set(descricao);
            this.local.set(local);
            this.data.set(data);
            this.status.set(status);
            this.contato.set(contato);
        }

        public SimpleStringProperty idProperty() { return id; }
        public SimpleStringProperty tipoProperty() { return tipo; }
        public SimpleStringProperty descricaoProperty() { return descricao; }
        public SimpleStringProperty localProperty() { return local; }
        public SimpleStringProperty dataProperty() { return data; }
        public SimpleStringProperty statusProperty() { return status; }
        public SimpleStringProperty contatoProperty() { return contato; }

        public String getDescricao() { return descricao.get(); }
        public String getLocal() { return local.get(); }
        public String getContato() { return contato.get(); }
        public String getStatus() { return status.get(); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}