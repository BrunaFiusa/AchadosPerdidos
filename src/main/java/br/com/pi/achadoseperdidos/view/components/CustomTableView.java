package br.com.pi.achadoseperdidos.view.components;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * TableView personalizada para o sistema Achados e Perdidos.
 * Apenas define tamanho, estilo e alguns melhoramentos visuais.
 * As colunas são adicionadas manualmente onde a tabela é usada.
 */
public class CustomTableView<T> extends TableView<T> {

    public CustomTableView() {
        // Tamanho preferido
        this.setPrefHeight(900);
        this.setPrefWidth(1000);

        // Estilo básico
        this.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: #C4AF95;" +
                        "-fx-table-cell-border-color: #E0E0E0;" +
                        "-fx-font-size: 14px;"
        );

        // Permite selecionar múltiplas linhas (útil para ações em lote no futuro)
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Linhas zebradas (alternando cores claras para melhor legibilidade)
        this.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("");
                } else {
                    // Linha par = cor mais clara, ímpar = branca
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #F9F9F9;");
                    } else {
                        setStyle("-fx-background-color: #FFFFFF;");
                    }
                }
            }
        });
    }
}