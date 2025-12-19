package br.com.pi.achadoseperdidos.view.components;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;

public class SideBar extends StackPane {

    private static final double SIDEBAR_WIDTH = 260.0;
    private static final double BUTTON_SIZE = 56.0;
    private static final String PRIMARY_COLOR = "#C4AF95";
    private static final String ACCENT_COLOR = "#B39F85";
    private static final String HOVER_COLOR = "#A69475";
    private static final String TEXT_COLOR = "#333333";
    private static final String ICON_COLOR = "#FFFFFF";

    private final VBox content;
    private final ScrollPane scrollPane;
    private final List<RadioButton> radioButtons = new ArrayList<>();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private Consumer<List<String>> selectionListener;
    private boolean isOpen = false;

    private final Button hamburgerButton;
    private final Region line1 = new Region();
    private final Region line2 = new Region();
    private final Region line3 = new Region();

    public SideBar() {
        content = new VBox(14);
        scrollPane = new ScrollPane(content);
        setupScrollPane();
        setupContent();
        buildFilterContent();

        hamburgerButton = createBeautifulHamburgerButton();
        updateButtonIcon(false);

        getChildren().addAll(scrollPane, hamburgerButton);
        StackPane.setAlignment(hamburgerButton, Pos.TOP_RIGHT);
        StackPane.setMargin(hamburgerButton, new Insets(24, 24, 0, 0));

        setPrefWidth(BUTTON_SIZE);
        setMinWidth(BUTTON_SIZE);
        setMaxWidth(BUTTON_SIZE);
        scrollPane.setOpacity(0.0);

        hamburgerButton.setOnAction(e -> toggle());
    }

    private void setupScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: " + PRIMARY_COLOR + "; " +
                "-fx-background-color: " + PRIMARY_COLOR + "; " +
                "-fx-border-color: #A69475; -fx-border-width: 0 2 0 0;");
    }

    private void setupContent() {
        content.setPadding(new Insets(20, 20, 30, 20));
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";");
    }

    private Button createBeautifulHamburgerButton() {
        Button btn = new Button();
        btn.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        btn.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        btn.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);

        line1.setStyle("-fx-background-color: " + ICON_COLOR + ";");
        line2.setStyle("-fx-background-color: " + ICON_COLOR + ";");
        line3.setStyle("-fx-background-color: " + ICON_COLOR + ";");

        line1.setPrefSize(28, 4);
        line2.setPrefSize(28, 4);
        line3.setPrefSize(28, 4);

        VBox iconBox = new VBox(6, line1, line2, line3);
        iconBox.setAlignment(Pos.CENTER);
        btn.setGraphic(iconBox);

        btn.setStyle("-fx-background-color: " + ACCENT_COLOR + "; " +
                "-fx-background-radius: 16px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 16, 0.4, 6, 6);");

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + HOVER_COLOR + "; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0.5, 8, 8);"));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: " + ACCENT_COLOR + "; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 16, 0.4, 6, 6);"));

        return btn;
    }

    private void updateButtonIcon(boolean open) {
        if (open) {
            line1.setRotate(45);
            line2.setOpacity(0);
            line3.setRotate(-45);
            line1.setTranslateY(10);
            line3.setTranslateY(-10);
        } else {
            line1.setRotate(0);
            line2.setOpacity(1);
            line3.setRotate(0);
            line1.setTranslateY(0);
            line3.setTranslateY(0);
        }
    }

    private void buildFilterContent() {
        addHeader();

        Label generalLabel = new Label("Geral");
        generalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: " + TEXT_COLOR + ";");
        generalLabel.setPadding(new Insets(0, 0, 6, 0));

        RadioButton allRadio = createRadioButton("Todos", true);
        content.getChildren().addAll(generalLabel, allRadio, createSeparator(15));

        Map<String, List<String>> categories = new LinkedHashMap<>();
        categories.put("Eletrônicos", Arrays.asList("Celular", "Tablet", "Notebook", "Fone de Ouvido", "Carregador", "Câmera"));
        categories.put("Roupas", Arrays.asList("Camiseta", "Calça", "Jaqueta", "Sapato", "Boné", "Vestido"));
        categories.put("Acessórios", Arrays.asList("Bolsa", "Carteira", "Óculos", "Relógio", "Jóias", "Chaveiro"));
        categories.put("Documentos", Arrays.asList("RG", "CPF", "Passaporte", "Carteira de Motorista", "Cartão"));
        categories.put("Outros", Arrays.asList("Chaves", "Livro", "Guarda-chuva", "Brinquedo", "Garrafa"));

        for (Map.Entry<String, List<String>> entry : categories.entrySet()) {
            Label catLabel = new Label(entry.getKey());
            catLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: " + TEXT_COLOR + ";");
            catLabel.setPadding(new Insets(12, 0, 6, 0));
            content.getChildren().add(catLabel);

            for (String option : entry.getValue()) {
                content.getChildren().add(createRadioButton(option, false));
            }
            content.getChildren().add(createSeparator(15));
        }
    }

    private void addHeader() {
        Label title = new Label("Filtros de Achados");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: " + TEXT_COLOR + ";");

        Button clearButton = new Button("Limpar Filtro");
        clearButton.setStyle("-fx-background-color: " + ACCENT_COLOR + "; " +
                "-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 10 18; " +
                "-fx-background-radius: 8px; -fx-cursor: hand;");

        clearButton.setOnAction(e -> clearSelections());

        clearButton.setOnMouseEntered(e -> {
            String current = clearButton.getStyle();
            if (!current.contains(HOVER_COLOR)) {
                clearButton.setStyle(current.replace(ACCENT_COLOR, HOVER_COLOR));
            }
        });

        clearButton.setOnMouseExited(e -> {
            String current = clearButton.getStyle();
            if (!current.contains(ACCENT_COLOR)) {
                clearButton.setStyle(current.replace(HOVER_COLOR, ACCENT_COLOR));
            }
        });

        VBox headerBox = new VBox(12, title, clearButton);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(headerBox, createSeparator(20));
    }

    private Separator createSeparator(double paddingBottom) {
        Separator sep = new Separator();
        sep.setPadding(new Insets(0, 0, paddingBottom, 0));
        return sep;
    }

    private RadioButton createRadioButton(String text, boolean selected) {
        RadioButton rb = new RadioButton(text);
        rb.setToggleGroup(toggleGroup);
        rb.setSelected(selected);
        rb.setStyle("-fx-font-size: 13.5px; -fx-text-fill: " + TEXT_COLOR + ";");
        rb.setPadding(new Insets(6, 0, 6, 22));

        rb.setOnMouseEntered(e -> {
            String current = rb.getStyle();
            if (!current.contains("#D8C4A5")) {
                rb.setStyle(current + "-fx-background-color: #D8C4A5; -fx-background-radius: 8px;");
            }
        });

        rb.setOnMouseExited(e -> {
            String current = rb.getStyle();
            rb.setStyle(current.replace("-fx-background-color: #D8C4A5; -fx-background-radius: 8px;", ""));
        });

        rb.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (selectionListener != null && Boolean.TRUE.equals(newVal)) {
                selectionListener.accept(getSelectedOptions());
            }
        });

        radioButtons.add(rb);
        return rb;
    }

    public void setSelectionListener(Consumer<List<String>> listener) {
        this.selectionListener = listener;
        if (listener != null) {
            listener.accept(getSelectedOptions());
        }
    }

    public List<String> getSelectedOptions() {
        return radioButtons.stream()
                .filter(rb -> rb.isSelected() && !rb.getText().equals("Todos"))
                .map(RadioButton::getText)
                .findFirst()
                .map(List::of)
                .orElse(Collections.emptyList());
    }

    public void clearSelections() {
        radioButtons.stream()
                .filter(rb -> rb.getText().equals("Todos"))
                .findFirst()
                .ifPresent(rb -> rb.setSelected(true));

        if (selectionListener != null) {
            selectionListener.accept(getSelectedOptions());
        }
    }

    public void toggle() {
        setOpen(!isOpen);
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        updateButtonIcon(open);

        Timeline timeline = new Timeline();
        KeyValue kvPref = new KeyValue(prefWidthProperty(), open ? SIDEBAR_WIDTH : BUTTON_SIZE, Interpolator.EASE_BOTH);
        KeyValue kvMin = new KeyValue(minWidthProperty(), open ? SIDEBAR_WIDTH : BUTTON_SIZE);
        KeyValue kvMax = new KeyValue(maxWidthProperty(), open ? SIDEBAR_WIDTH : BUTTON_SIZE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), kvPref, kvMin, kvMax));
        timeline.play();

        FadeTransition fade = new FadeTransition(Duration.millis(300), scrollPane);
        fade.setFromValue(open ? 0.0 : 1.0);
        fade.setToValue(open ? 1.0 : 0.0);
        fade.play();
    }

    public boolean isOpen() {
        return isOpen;
    }
}