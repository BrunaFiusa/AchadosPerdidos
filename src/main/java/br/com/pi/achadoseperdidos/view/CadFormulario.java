package br.com.pi.achadoseperdidos.view;

import br.com.pi.achadoseperdidos.view.components.PaginatedTableView;
import br.com.pi.achadoseperdidos.view.components.SideBar;
import br.com.pi.achadoseperdidos.view.components.TopBar;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    // Classe para representar um item do formulário (mesma estrutura das outras telas)
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
    }

    @Override
    public void start(Stage stage) throws Exception {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        root.setTop(topBar);
        root.setLeft(sideBar);

        VBox centerArea = new VBox(20);
        centerArea.setPadding(new Insets(20));

        // === Barra de pesquisa com 3 campos relevantes ===
        TextField campoDescricao = new TextField();
        campoDescricao.setPromptText("Descrição do item...");
        campoDescricao.setPrefWidth(350);

        TextField campoLocal = new TextField();
        campoLocal.setPromptText("Local encontrado/perdido...");
        campoLocal.setPrefWidth(300);

        TextField campoContato = new TextField();
        campoContato.setPromptText("Contato ou e-mail...");
        campoContato.setPrefWidth(300);

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setStyle("-fx-background-color: #A67C52; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14; -fx-border-radius: 5;");

        HBox searchBox = new HBox(15, campoDescricao, campoLocal, campoContato, btnPesquisar);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        VBox searchContainer = new VBox(searchBox);
        searchContainer.setStyle("-fx-background-color: #C4AF95; -fx-padding: 15; -fx-border-radius: 8; -fx-background-radius: 8;");

        centerArea.getChildren().add(searchContainer);

        // === Gráfico de Pizza - Distribuição por Status ===
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Pendentes", 55),
                new PieChart.Data("Devolvidos", 30),
                new PieChart.Data("Descartados", 15)
        );

        PieChart pieChart = new PieChart(pieData);
        pieChart.setTitle("Status dos Itens Registrados");
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(15);
        pieChart.setLegendVisible(true);

        VBox chartBox = new VBox(pieChart);
        chartBox.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15, 0, 0, 8);");
        chartBox.setMaxHeight(380);

        centerArea.getChildren().add(chartBox);

        // === Tabela Paginada - Relatório de Itens do Formulário ===
        PaginatedTableView<FormItem> paginatedTable = new PaginatedTableView<>();
        paginatedTable.setPageSize(50);

        TableView<FormItem> tableView = paginatedTable.getTableView();

        // Colunas organizadas e com tamanhos adequados
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
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Dados realistas (150 itens)
        ObservableList<FormItem> dadosFormulario = FXCollections.observableArrayList();
        Random rand = new Random();
        String[] tipos = {"Achado", "Perdido"};
        String[] status = {"Pendente", "Devolvido", "Descartado"};
        String[] locais = {"Recepção", "Sala de Aula", "Biblioteca", "Refeitório", "Estacionamento", "Ginásio"};

        for (int i = 1; i <= 150; i++) {
            String tipo = tipos[rand.nextInt(2)];
            dadosFormulario.add(new FormItem(
                    String.format("%03d", i),
                    tipo,
                    tipo + " - " + (tipo.equals("Achado") ? "Carteira, chaves, celular" : "Mochila, livro, óculos") + " encontrado(a) por aluno",
                    locais[rand.nextInt(locais.length)],
                    LocalDate.now().minusDays(rand.nextInt(90)).toString(),
                    status[rand.nextInt(3)],
                    "usuario" + i + "@aluno.edu.br"
            ));
        }

        paginatedTable.setItems(dadosFormulario);

        centerArea.getChildren().add(paginatedTable);
        VBox.setVgrow(paginatedTable, Priority.ALWAYS);

        root.setCenter(centerArea);

        Scene scene = new Scene(root, 1300, 800);
        stage.setScene(scene);
        stage.setTitle("Relatório de Formulários - Achados e Perdidos");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}