package br.com.pi.achadoseperdidos.view.components;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.function.Consumer;

/**
 * Sidebar colapsível premium com botão hamburger bonito e flutuante.
 */
public class SideBar extends StackPane {

    private static final double SIDEBAR_WIDTH = 260.0;
    private static final double BUTTON_SIZE = 56.0; // Um pouco maior para ficar mais premium
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

    public SideBar() {
        content = new VBox(14);
        scrollPane = new ScrollPane(content);

        setupScrollPane();
        setupContent();
        buildFilterContent();

        hamburgerButton = createBeautifulHamburgerButton();

        getChildren().addAll(scrollPane, hamburgerButton);

        StackPane.setAlignment(hamburgerButton, Pos.TOP_LEFT);
        StackPane.setMargin(hamburgerButton, new Insets(24, 0, 0, 24)); // Mais espaço para ficar elegante

        setOpen(false); // Inicia fechada

        hamburgerButton.setOnAction(e -> toggle());
    }

    private void setupScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: " + PRIMARY_COLOR + "; " +
                "-fx-background-color: " + PRIMARY_COLOR + "; " +
                "-fx-border-color: #A69475; -fx-border-width: 0 2 0 0;");
        scrollPane.setPrefWidth(SIDEBAR_WIDTH);
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

        // Estilo premium: sombra forte, bordas bem arredondadas
        btn.setStyle("-fx-background-color: " + ACCENT_COLOR + "; " +
                "-fx-background-radius: 16px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 16, 0.4, 6, 6);");

        // Ícone hamburger mais grosso e bonito
        String hamburgerSVG = """
            <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="%s" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round">
                <line x1="3" y1="6" x2="21" y2="6"/>
                <line x1="3" y1="12" x2="21" y2="12"/>
                <line x1="3" y1="18" x2="21" y2="18"/>
            </svg>
            """.formatted(ICON_COLOR.replace("#", "%23"));

        BackgroundImage bg = new BackgroundImage(
                new Image(new ByteArrayInputStream(hamburgerSVG.getBytes()), 28, 28, true, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        btn.setBackground(new Background(bg));

        // Hover com escala + cor
        btn.setOnMouseEntered(e -> {
            btn.setStyle(btn.getStyle() + "-fx-background-color: " + HOVER_COLOR + ";");
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), btn);
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.play();
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(btn.getStyle().replace("-fx-background-color: " + HOVER_COLOR + ";", "") +
                    "-fx-background-color: " + ACCENT_COLOR + ";");
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), btn);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });

        return btn;
    }

    // === O resto do código permanece igual (buildFilterContent, addHeader, etc.) ===
    // (Copie os métodos da versão anterior completa: buildFilterContent, addHeader,
    // createSeparator, createRadioButton, setSelectionListener, getSelectedOptions,
    // clearSelections)

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
        clearButton.setOnMouseEntered(e -> clearButton.setStyle(clearButton.getStyle() + "-fx-background-color: " + HOVER_COLOR + ";"));
        clearButton.setOnMouseExited(e -> clearButton.setStyle(clearButton.getStyle().replace("-fx-background-color: " + HOVER_COLOR + ";", "") +
                "-fx-background-color: " + ACCENT_COLOR + ";"));

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

        rb.setOnMouseEntered(e -> rb.setStyle(rb.getStyle() + "-fx-background-color: #D8C4A5; -fx-background-radius: 8px;"));
        rb.setOnMouseExited(e -> rb.setStyle(rb.getStyle().replace("-fx-background-color: #D8C4A5; -fx-background-radius: 8px;", "")));

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
        if (listener != null) listener.accept(getSelectedOptions());
    }

    public List<String> getSelectedOptions() {
        return radioButtons.stream()
                .filter(rb -> rb.isSelected() && !rb.getText().equals("Todos"))
                .map(RadioButton::getText)
                .findFirst()
                .map(List::of)
                .orElse(new ArrayList<>());
    }

    public void clearSelections() {
        radioButtons.stream()
                .filter(rb -> rb.getText().equals("Todos"))
                .findFirst()
                .ifPresent(rb -> rb.setSelected(true));

        if (selectionListener != null) selectionListener.accept(getSelectedOptions());
    }

    public void toggle() {
        setOpen(!isOpen);
    }

    public void setOpen(boolean open) {
        this.isOpen = open;

        TranslateTransition slide = new TranslateTransition(Duration.millis(400), scrollPane);
        slide.setToX(open ? 0 : -SIDEBAR_WIDTH);
        slide.play();

        FadeTransition fade = new FadeTransition(Duration.millis(300), hamburgerButton);
        fade.setToValue(open ? 0.0 : 1.0);
        fade.play();

        hamburgerButton.toFront();
    }

    public boolean isOpen() {
        return isOpen;
    }
}