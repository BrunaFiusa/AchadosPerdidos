package br.com.pi.achadoseperdidos.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.function.Consumer;

public class TopBar extends HBox {

    private static final double HEIGHT = 70.0;
    private static final String PRIMARY_COLOR = "#C4AF95";
    private static final String ACCENT_COLOR = "#A67C52";
    private static final String HOVER_COLOR = "#B39F85";
    private static final String SELECTED_COLOR = "#A67C52";
    private static final String TEXT_COLOR = "#333333";
    private static final String TEXT_SELECTED_COLOR = "#FFFFFF";

    private final Consumer<String> navigationListener;
    private Button selectedButton = null;

    // Construtor com navegação (usado em CadHome)
    public TopBar(Consumer<String> navigationListener) {
        this.navigationListener = navigationListener;
        initialize();
    }

    // Construtor vazio (usado em CadAdm, CadFormulario, etc.)
    public TopBar() {
        this(null);
    }

    private void initialize() {
        setPrefHeight(HEIGHT);
        setMinHeight(HEIGHT);
        setStyle("-fx-background-color: " + PRIMARY_COLOR + "; " +
                "-fx-border-color: #919191; " +
                "-fx-border-width: 0 0 3 0; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);");

        setPadding(new Insets(0, 20, 0, 20));
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(20);

        createLogo();
        createNavigationButtons();
        createRightArea();
    }

    private void createLogo() {
        Label logoLabel = new Label("Achados & Perdidos");
        logoLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_COLOR + ";");
        getChildren().add(logoLabel);
    }

    private void createNavigationButtons() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);

        HBox navBox = new HBox(30);
        navBox.setAlignment(Pos.CENTER);

        Button objectsBtn = createNavButton("Objetos");
        Button requestsBtn = createNavButton("Solicitações");
        Button returnsBtn = createNavButton("Devoluções");
        Button reportsBtn = createNavButton("Relatórios");

        objectsBtn.setOnAction(e -> selectAndNavigate(objectsBtn, "Objetos"));
        requestsBtn.setOnAction(e -> selectAndNavigate(requestsBtn, "Solicitações"));
        returnsBtn.setOnAction(e -> selectAndNavigate(returnsBtn, "Devoluções"));
        reportsBtn.setOnAction(e -> selectAndNavigate(reportsBtn, "Relatórios"));

        // Deixa "Objetos" selecionado por padrão
        selectButton(objectsBtn);

        navBox.getChildren().addAll(objectsBtn, requestsBtn, returnsBtn, reportsBtn);
        getChildren().add(navBox);
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: " + TEXT_COLOR + "; " +
                "-fx-font-size: 15px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-cursor: hand;");

        btn.setOnMouseEntered(e -> {
            if (btn != selectedButton) {
                btn.setStyle(btn.getStyle()
                        .replace("transparent", HOVER_COLOR)
                        .replace(TEXT_COLOR, "#FFFFFF"));
            }
        });

        btn.setOnMouseExited(e -> {
            if (btn != selectedButton) {
                btn.setStyle(btn.getStyle()
                        .replace(HOVER_COLOR, "transparent")
                        .replace("#FFFFFF", TEXT_COLOR));
            }
        });

        return btn;
    }

    private void selectAndNavigate(Button button, String section) {
        selectButton(button);
        if (navigationListener != null) {
            navigationListener.accept(section);
        }
    }

    private void selectButton(Button button) {
        if (selectedButton != null) {
            selectedButton.setStyle(selectedButton.getStyle()
                    .replace(SELECTED_COLOR, "transparent")
                    .replace(TEXT_SELECTED_COLOR, TEXT_COLOR));
        }

        button.setStyle("-fx-background-color: " + SELECTED_COLOR + "; " +
                "-fx-text-fill: " + TEXT_SELECTED_COLOR + "; " +
                "-fx-font-size: 15px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;");

        selectedButton = button;
    }

    private void createRightArea() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);

        Label bellIcon = new Label("🔔");
        bellIcon.setStyle("-fx-font-size: 22px; -fx-text-fill: " + TEXT_COLOR + "; -fx-cursor: hand;");
        bellIcon.setOnMouseClicked(e -> showNotifications());

        Circle avatar = new Circle(18);
        avatar.setFill(Color.web(ACCENT_COLOR));
        avatar.setStroke(Color.web("#8B6F47"));
        avatar.setStrokeWidth(2);
        avatar.setStyle("-fx-cursor: hand;");

        Label userInitial = new Label("U");
        userInitial.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane avatarContainer = new StackPane(avatar, userInitial);
        avatarContainer.setAlignment(Pos.CENTER);

        // Clique no avatar abre o menu
        avatarContainer.setOnMouseClicked(e -> showUserMenu(avatarContainer));

        HBox rightArea = new HBox(15, bellIcon, avatarContainer);
        rightArea.setAlignment(Pos.CENTER);
        getChildren().add(rightArea);
    }

    private void showNotifications() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificações");
        alert.setHeaderText("Você tem novas notificações");
        alert.setContentText(
                "• Item 045 foi devolvido ao dono\n" +
                        "• Novo item cadastrado: Celular iPhone\n" +
                        "• Solicitação pendente (ID: 089)\n" +
                        "• 3 itens com status alterado hoje"
        );
        alert.getDialogPane().setStyle("-fx-font-size: 14px;");
        alert.showAndWait();
    }

    private void showUserMenu(Node anchor) {
        ContextMenu menu = new ContextMenu();

        MenuItem profile = new MenuItem("Meu Perfil");
        profile.setOnAction(e -> System.out.println("Abrir perfil"));

        MenuItem settings = new MenuItem("Configurações");
        settings.setOnAction(e -> System.out.println("Abrir configurações"));

        MenuItem logout = new MenuItem("Sair do Sistema");
        logout.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente sair do sistema?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Usuário saiu do sistema");
                }
            });
        });

        menu.getItems().addAll(profile, settings, new SeparatorMenuItem(), logout);


        menu.show(anchor, Side.BOTTOM, 0, 5);
    }
}