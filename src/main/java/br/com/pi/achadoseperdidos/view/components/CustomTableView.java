package br.com.pi.achadoseperdidos.view.components;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;

public class CustomTableView<T> extends TableView<T> {

    public CustomTableView() {
        this.setPrefHeight(900);
        this.setPrefWidth(1000);
        configureDefaultColumns();
    }

    private void configureDefaultColumns() {
        TableColumn<T, String> column = new TableColumn<>("Items");
        TableColumn<T, String> column2 = new TableColumn<>("");
        TableColumn<T, String> column3 = new TableColumn<>("");
        TableColumn<T, String> column4 = new TableColumn<>("");
        TableColumn<T, String> column5 = new TableColumn<>("");
        TableColumn<T, String> column6 = new TableColumn<>("");

        column.setPrefWidth(100);
        column2.setPrefWidth(300);
        column3.setPrefWidth(300);
        column4.setPrefWidth(300);
        column5.setPrefWidth(300);
        column6.setPrefWidth(300);

        column.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> String.valueOf(data.getValue())));

        this.getColumns().addAll(column, column2, column3, column4, column5, column6);
    }
}

