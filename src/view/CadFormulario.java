package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import view.components.TopBar;
import view.components.SideBar;
import view.components.CustomTableView;

public class CadFormulario extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();

        BorderPane tela = new BorderPane();
        HBox centerContent = new HBox(20);
        centerContent.setPadding(new Insets(20));

        TextField searchField1 = new TextField();
        TextField searchField2 = new TextField();
        TextField searchField3 = new TextField();
        Button searchButton = new Button("Pesquisar");

        searchField1.setPromptText("Pesquisar campo 1...");
        searchField2.setPromptText("Pesquisar campo 2...");
        searchField3.setPromptText("Pesquisar campo 3...");

        VBox field1Container = new VBox(10);
        field1Container.getChildren().addAll(searchField1, searchButton);

        HBox fieldsContainer = new HBox(20);
        fieldsContainer.getChildren().addAll(field1Container, searchField2, searchField3);



        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            items.add("Content Item " + (i + 1));
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Item A", 30),
                new PieChart.Data("Item B", 25),
                new PieChart.Data("Item C", 45)
        );
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Distribution");
        pieChart.setPrefHeight(300);

        VBox pieChartContainer = new VBox(pieChart);
        pieChartContainer.setPrefWidth(400);

        CustomTableView<String> tableView = new CustomTableView<>();
        tableView.setItems(items);
        tableView.setPrefHeight(500);
        tableView.setPrefWidth(3000);
        VBox contentContainer = new VBox(20);
        contentContainer.getChildren().addAll(fieldsContainer, pieChart, tableView);

        centerContent.getChildren().add(contentContainer);

        tela.setTop(topBar);
        tela.setLeft(sideBar);
        tela.setCenter(centerContent);

        Scene scene = new Scene(tela, 800, 600);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Formulário");
        stage.show();
    }
}
