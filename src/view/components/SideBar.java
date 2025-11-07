package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Um componente de barra lateral de filtro otimizado para um sistema de achados e perdidos de shopping.
 * Usa botões de rádio para seleção exclusiva, com categorias organizadas, funcionalidade de limpar e
 * atualizações em tempo real em um painel rolável.
 */
public class SideBar extends ScrollPane {
    private static final double SIDEBAR_WIDTH = 220.0;
    private static final String PRIMARY_COLOR = "#C4AF95"; // Bege suave (RGB: 196, 175, 149)
    private static final String ACCENT_COLOR = "#B39F85";  // Bege mais escuro (RGB: 179, 159, 133)
    private static final String HOVER_COLOR = "#A69475";   // Bege escuro para hover (RGB: 166, 148, 117)
    private static final String TEXT_COLOR = "#333333";    // Texto escuro para contraste
    private static final String CATEGORY_STYLE = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + TEXT_COLOR + ";";
    private static final String RADIO_STYLE = "-fx-font-size: 12px; -fx-text-fill: " + TEXT_COLOR + ";";
    private static final String BUTTON_STYLE = "-fx-background-color: " + ACCENT_COLOR + "; " +
            "-fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-padding: 6 12 6 12; -fx-cursor: hand;" +
            "-fx-background-radius: 4px; -fx-border-radius: 4px;";

    private final VBox content;
    private final List<RadioButton> radioButtons;
    private final ToggleGroup toggleGroup;
    private Consumer<List<String>> selectionListener;

    /**
     * Constrói a barra lateral de filtro com botões de rádio agrupados.
     */
    public SideBar() {
        content = new VBox(12);
        radioButtons = new ArrayList<>();
        toggleGroup = new ToggleGroup();

        // Configura o painel de conteúdo
        content.setPrefWidth(SIDEBAR_WIDTH);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";");
        content.setAlignment(Pos.TOP_CENTER);

        // Configura o painel de rolagem
        setContent(content);
        setFitToWidth(true);
        setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-border-color: #A69475; -fx-border-width: 0 1 0 0;");
        setFocusTraversable(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        // Adiciona cabeçalho e grupos de opções
        addHeader();
        createRadioGroups();
    }

    /**
     * Adiciona o cabeçalho com título e botão de limpar.
     */
    private void addHeader() {
        Label title = new Label("Filtros de Achados e Perdidos");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: " + TEXT_COLOR + ";");
        title.setPadding(new Insets(0, 0, 12, 0));

        Button clearButton = new Button("Limpar Filtro");
        clearButton.setStyle(BUTTON_STYLE);
        clearButton.setOnAction(e -> clearSelections());
        clearButton.setFocusTraversable(true);
        clearButton.setOnMouseEntered(e -> clearButton.setStyle(BUTTON_STYLE + "-fx-background-color: " + HOVER_COLOR + ";"));
        clearButton.setOnMouseExited(e -> clearButton.setStyle(BUTTON_STYLE));

        HBox buttonBox = new HBox(clearButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 12, 0));

        Separator separator = new Separator();
        separator.setPadding(new Insets(0, 0, 12, 0));

        content.getChildren().addAll(title, buttonBox, separator);
    }

    /**
     * Cria grupos de botões de rádio com categorias relevantes para achados e perdidos.
     */
    private void createRadioGroups() {
        Map<String, List<String>> categoryOptions = new LinkedHashMap<>();
        categoryOptions.put("Eletrônicos", Arrays.asList("Celular", "Tablet", "Notebook", "Fone de Ouvido", "Carregador", "Câmera"));
        categoryOptions.put("Roupas", Arrays.asList("Camiseta", "Calça", "Jaqueta", "Sapato", "Boné", "Vestido"));
        categoryOptions.put("Acessórios", Arrays.asList("Bolsa", "Carteira", "Óculos", "Relógio", "Jóias", "Chaveiro"));
        categoryOptions.put("Documentos", Arrays.asList("RG", "CPF", "Passaporte", "Carteira de Motorista", "Cartão"));
        categoryOptions.put("Outros", Arrays.asList("Chaves", "Livro", "Guarda-chuva", "Brinquedo", "Garrafa"));

        // Adiciona opção "Todos" no topo
        Label generalLabel = new Label("Geral");
        generalLabel.setStyle(CATEGORY_STYLE);
        generalLabel.setPadding(new Insets(10, 0, 5, 8));

        RadioButton allRadio = createRadioButton("Todos", true);
        content.getChildren().addAll(generalLabel, allRadio);

        Separator initialSeparator = new Separator();
        initialSeparator.setPadding(new Insets(10, 0, 10, 0));
        content.getChildren().add(initialSeparator);

        int groupIndex = 0;
        int groupCount = categoryOptions.size();

        for (Map.Entry<String, List<String>> entry : categoryOptions.entrySet()) {
            Label categoryLabel = new Label(entry.getKey());
            categoryLabel.setStyle(CATEGORY_STYLE);
            categoryLabel.setPadding(new Insets(10, 0, 5, 8));
            content.getChildren().add(categoryLabel);

            for (String option : entry.getValue()) {
                RadioButton radioButton = createRadioButton(option, false);
                content.getChildren().add(radioButton);
            }

            if (groupIndex < groupCount - 1) {
                Separator separator = new Separator();
                separator.setPadding(new Insets(10, 0, 10, 0));
                content.getChildren().add(separator);
            }
            groupIndex++;
        }
    }

    /**
     * Cria um botão de rádio com configurações padrão.
     *
     * @param text     Texto do botão
     * @param selected Se deve estar selecionado por padrão
     * @return RadioButton configurado
     */
    private RadioButton createRadioButton(String text, boolean selected) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle(RADIO_STYLE);
        radioButton.setPadding(new Insets(4, 0, 4, 20));
        radioButton.setFocusTraversable(true);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setSelected(selected);

        radioButton.setOnMouseEntered(e -> radioButton.setStyle(RADIO_STYLE + "-fx-background-color: #D8C4A5; -fx-background-radius: 4px;"));
        radioButton.setOnMouseExited(e -> radioButton.setStyle(RADIO_STYLE));

        radioButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (selectionListener != null && newVal) {
                selectionListener.accept(getSelectedOptions());
            }
        });

        radioButtons.add(radioButton);
        return radioButton;
    }

    /**
     * Define um ouvinte para ser notificado quando as seleções mudarem.
     *
     * @param listener Consumer que recebe a lista de opções selecionadas (0 ou 1 item)
     */
    public void setSelectionListener(Consumer<List<String>> listener) {
        this.selectionListener = listener;
        if (listener != null) {
            listener.accept(getSelectedOptions());
        }
    }

    /**
     * Retorna a lista de opções selecionadas (máximo de 1 item, exceto "Todos").
     *
     * @return Lista com a opção selecionada ou vazia se "Todos" estiver selecionado
     */
    public List<String> getSelectedOptions() {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected() && !radioButton.getText().equals("Todos")) {
                return List.of(radioButton.getText());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Limpa a seleção e seleciona "Todos" por padrão.
     */
    public void clearSelections() {
        radioButtons.stream()
                .filter(radio -> radio.getText().equals("Todos"))
                .findFirst()
                .ifPresent(radio -> radio.setSelected(true));
        if (selectionListener != null) {
            selectionListener.accept(getSelectedOptions());
        }
    }

    /**
     * Define opções personalizadas para a barra lateral, substituindo os botões existentes.
     *
     * @param options Lista de nomes de opções a exibir
     */
    public void setOptions(List<String> options) {
        content.getChildren().clear();
        radioButtons.clear();
        toggleGroup.getToggles().clear();

        addHeader();

        Label generalLabel = new Label("Geral");
        generalLabel.setStyle(CATEGORY_STYLE);
        generalLabel.setPadding(new Insets(10, 0, 5, 8));
        RadioButton allRadio = createRadioButton("Todos", true);
        content.getChildren().addAll(generalLabel, allRadio);

        Separator initialSeparator = new Separator();
        initialSeparator.setPadding(new Insets(10, 0, 10, 0));
        content.getChildren().add(initialSeparator);

        final int OPTIONS_PER_GROUP = 6;
        int groupCount = (int) Math.ceil((double) options.size() / OPTIONS_PER_GROUP);
        for (int group = 0; group < groupCount; group++) {
            Label categoryLabel = new Label("Categoria " + (group + 1));
            categoryLabel.setStyle(CATEGORY_STYLE);
            categoryLabel.setPadding(new Insets(10, 0, 5, 8));
            content.getChildren().add(categoryLabel);

            int startIndex = group * OPTIONS_PER_GROUP;
            int endIndex = Math.min(startIndex + OPTIONS_PER_GROUP, options.size());

            for (int i = startIndex; i < endIndex; i++) {
                RadioButton radioButton = createRadioButton(options.get(i), false);
                content.getChildren().add(radioButton);
            }

            if (group < groupCount - 1) {
                Separator separator = new Separator();
                separator.setPadding(new Insets(10, 0, 10, 0));
                content.getChildren().add(separator);
            }
        }
    }
}