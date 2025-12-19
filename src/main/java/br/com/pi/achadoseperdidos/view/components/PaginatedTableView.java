package br.com.pi.achadoseperdidos.view.components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class PaginatedTableView<T> extends VBox {

    private final TableView<T> tableView;
    private final ObservableList<T> fullData = FXCollections.observableArrayList();
    private final ObservableList<T> currentPageData = FXCollections.observableArrayList();

    private final SimpleIntegerProperty pageSize = new SimpleIntegerProperty(50);
    private final SimpleIntegerProperty currentPage = new SimpleIntegerProperty(0);

    private final Label pageInfoLabel = new Label();
    private final HBox paginationControls = new HBox(8);
    private final int maxPageButtons = 10;

    private final ScrollPane tableScrollPane = new ScrollPane();

    public PaginatedTableView() {
        // Cria a tabela (assumindo que você tem uma CustomTableView ou usa TableView normal)
        tableView = new TableView<>(); // Se você tem CustomTableView, troque aqui
        tableView.setItems(currentPageData);

        // === CORREÇÕES PARA REMOVER VERMELHO E ESTILOS INDESEJADOS ===
        tableView.setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                // Remove qualquer classe de erro que possa estar causando vermelho
                getStyleClass().removeAll("error", "table-row-cell:error");
                // Fundo limpo em linhas vazias
                if (empty || item == null) {
                    setStyle("");
                } else {
                    setStyle("");
                }
            }
        });

        // Cores de seleção mais suaves e no seu tema
        tableView.setStyle(
                "-fx-selection-bar: #C4AF95; " +
                        "-fx-selection-bar-non-focused: #E0D6CB; " +
                        "-fx-focus-color: #A67C52; " +
                        "-fx-faint-focus-color: #A67C5222; " +
                        "-fx-background: #FFFFFF;"
        );

        // Remove possíveis estilos herdados problemáticos
        tableView.getStyleClass().removeIf(c -> c.contains("error"));

        // Configura o ScrollPane
        tableScrollPane.setContent(tableView);
        tableScrollPane.setFitToWidth(true);
        tableScrollPane.setFitToHeight(true);
        tableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tableScrollPane.setStyle("-fx-background: #FFFFFF; -fx-border-color: transparent;");

        VBox.setVgrow(tableScrollPane, Priority.ALWAYS);

        // Barra de paginação
        paginationControls.setAlignment(Pos.CENTER);
        paginationControls.setPadding(new Insets(10));
        paginationControls.setStyle("-fx-background-color: #F9F5F0; " +
                "-fx-border-color: #C4AF95; " +
                "-fx-border-width: 1 0 0 0;");

        this.getChildren().addAll(tableScrollPane, paginationControls);
        this.setSpacing(0);

        // Listeners
        currentPage.addListener((obs, old, newVal) -> loadCurrentPage());
        pageSize.addListener((obs, old, newVal) -> {
            currentPage.set(0);
            loadCurrentPage();
        });

        loadCurrentPage();
    }

    public void setItems(List<T> items) {
        fullData.setAll(items);
        currentPage.set(0);
        loadCurrentPage();
    }

    private void loadCurrentPage() {
        int fromIndex = currentPage.get() * pageSize.get();
        int toIndex = Math.min(fromIndex + pageSize.get(), fullData.size());

        if (fromIndex > fullData.size() && !fullData.isEmpty()) {
            currentPage.set(0);
            fromIndex = 0;
            toIndex = Math.min(pageSize.get(), fullData.size());
        }

        currentPageData.setAll(fullData.subList(fromIndex, toIndex));
        updatePageInfo();
        rebuildPaginationButtons();
    }

    private void updatePageInfo() {
        int totalPages = calculateTotalPages();
        int page = currentPage.get() + 1;
        int totalItems = fullData.size();

        if (totalItems == 0) {
            pageInfoLabel.setText("Nenhum item encontrado");
        } else {
            pageInfoLabel.setText(String.format("Página %d de %d | Total: %d itens", page, totalPages, totalItems));
        }
    }

    private int calculateTotalPages() {
        if (fullData.isEmpty()) return 1;
        return (int) Math.ceil((double) fullData.size() / pageSize.get());
    }

    private void rebuildPaginationButtons() {
        paginationControls.getChildren().clear();

        int totalPages = calculateTotalPages();
        int current = currentPage.get();

        Button first = new Button("« Primeira");
        first.setOnAction(e -> currentPage.set(0));
        first.setDisable(current == 0);

        Button prev = new Button("‹ Anterior");
        prev.setOnAction(e -> currentPage.set(currentPage.get() - 1));
        prev.setDisable(current == 0);

        paginationControls.getChildren().addAll(first, prev);

        int startPage, endPage;
        if (totalPages <= maxPageButtons) {
            startPage = 0;
            endPage = totalPages - 1;
        } else {
            int half = maxPageButtons / 2;
            if (current < half) {
                startPage = 0;
                endPage = maxPageButtons - 1;
            } else if (current >= totalPages - half) {
                startPage = totalPages - maxPageButtons;
                endPage = totalPages - 1;
            } else {
                startPage = current - half;
                endPage = current + half;
            }
        }

        if (startPage > 0) {
            addPageButton(0);
            if (startPage > 1) {
                Label ellipsis = new Label("...");
                ellipsis.setStyle("-fx-padding: 0 10 0 10;");
                paginationControls.getChildren().add(ellipsis);
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            addPageButton(i);
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                Label ellipsis = new Label("...");
                ellipsis.setStyle("-fx-padding: 0 10 0 10;");
                paginationControls.getChildren().add(ellipsis);
            }
            addPageButton(totalPages - 1);
        }

        Button next = new Button("Próxima ›");
        next.setOnAction(e -> currentPage.set(currentPage.get() + 1));
        next.setDisable(current >= totalPages - 1);

        Button last = new Button("Última »");
        last.setOnAction(e -> currentPage.set(totalPages - 1));
        last.setDisable(current >= totalPages - 1);

        paginationControls.getChildren().addAll(next, last, pageInfoLabel);
    }

    private void addPageButton(int pageIndex) {
        Button btn = new Button(String.valueOf(pageIndex + 1));
        if (pageIndex == currentPage.get()) {
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #A67C52; -fx-text-fill: white; -fx-background-radius: 5;");
        } else {
            btn.setStyle("-fx-background-color: #F0E6DC; -fx-background-radius: 5;");
        }
        btn.setOnAction(e -> currentPage.set(pageIndex));
        paginationControls.getChildren().add(btn);
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public void setPageSize(int size) {
        if (size > 0) pageSize.set(size);
    }

    public int getCurrentPageNumber() {
        return currentPage.get() + 1;
    }

    public int getTotalPages() {
        return calculateTotalPages();
    }
}