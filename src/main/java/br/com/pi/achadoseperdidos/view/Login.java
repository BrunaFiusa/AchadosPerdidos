package br.com.pi.achadoseperdidos.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {

    private boolean passwordVisible = false;

    @Override
    public void start(Stage stage) {
        // Window setup
        stage.setTitle("Login - Shopping Iguatemi Sorocaba");
        stage.setResizable(false); // Disable resizing and maximize button

        // Root pane with a modern gradient background
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(245, 245, 245), null, null
        )));

        // Header
        Label titleLabel = new Label("Shopping Iguatemi Sorocaba");
        titleLabel.setFont(Font.font("Arial", 34));
        titleLabel.setTextFill(Color.WHITE);
        HBox titlePanel = new HBox(titleLabel);
        titlePanel.setAlignment(Pos.CENTER);
        titlePanel.setBackground(new Background(new BackgroundFill(
                Color.rgb(180, 160, 135),
                new CornerRadii(0),
                new Insets(0)
        )));
        titlePanel.setPadding(new Insets(25));
        titlePanel.setEffect(new DropShadow(8, 0, 4, Color.rgb(0, 0, 0, 0.15)));
        root.setTop(titlePanel);

        // Center: Split layout with form on left and image on right
        HBox centerPanel = new HBox();
        centerPanel.setSpacing(0);
        centerPanel.setAlignment(Pos.CENTER);

        // Left: Login form panel
        VBox loginPanel = new VBox(25);
        loginPanel.setPadding(new Insets(40, 50, 40, 50));
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setPrefWidth(500); // Fixed width for form
        loginPanel.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 6);"
        );

        // Welcome label
        Label welcomeLabel = new Label("Bem-vindo!");
        welcomeLabel.setFont(Font.font("Arial", 30));
        welcomeLabel.setTextFill(Color.rgb(180, 160, 135));

        // Email field
        Label emailLabel = new Label("E-mail:");
        emailLabel.setFont(Font.font("Arial", 16));
        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu e-mail");
        emailField.setMaxWidth(320);
        emailField.setStyle(
                "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 15;" +
                        "-fx-border-width: 1.5;"
        );

        // Password field
        Label passwordLabel = new Label("Senha:");
        passwordLabel.setFont(Font.font("Arial", 16));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");
        passwordField.setMaxWidth(320);
        passwordField.setStyle(
                "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 12 40 12 12;" +
                        "-fx-font-size: 15;" +
                        "-fx-border-width: 1.5;"
        );

        TextField visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Digite sua senha");
        visiblePasswordField.setMaxWidth(320);
        visiblePasswordField.setStyle(
                "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 12 40 12 12;" +
                        "-fx-font-size: 15;" +
                        "-fx-border-width: 1.5;"
        );
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);

        ImageView eyeOpenIcon = new ImageView();
        ImageView eyeClosedIcon = new ImageView();
        try {
            eyeOpenIcon.setImage(new Image("img/OlhoOpen.png"));
            eyeClosedIcon.setImage(new Image("img/OlhoClose.png"));
        } catch (Exception e) {
            System.out.println("Eye icons not found, using text placeholders");
        }
        eyeOpenIcon.setFitWidth(20);
        eyeOpenIcon.setFitHeight(20);
        eyeOpenIcon.setPreserveRatio(true);
        eyeOpenIcon.setVisible(false);

        eyeClosedIcon.setFitWidth(20);
        eyeClosedIcon.setFitHeight(20);
        eyeClosedIcon.setPreserveRatio(true);

        StackPane eyeIconStack = new StackPane(eyeClosedIcon, eyeOpenIcon);
        eyeIconStack.setMaxSize(20, 20);
        eyeIconStack.setMinSize(20, 20);
        eyeIconStack.setPrefSize(20, 20);
        eyeIconStack.setStyle("-fx-cursor: hand;");

        StackPane toggleIconPane = new StackPane(eyeIconStack);
        toggleIconPane.setAlignment(Pos.CENTER_RIGHT);
        toggleIconPane.setPadding(new Insets(0, 10, 0, 0));
        toggleIconPane.setMaxWidth(320);
        toggleIconPane.setPickOnBounds(false);

        StackPane passwordFieldContainer = new StackPane();
        passwordFieldContainer.getChildren().addAll(passwordField, visiblePasswordField, toggleIconPane);
        passwordFieldContainer.setAlignment(Pos.CENTER_LEFT);
        passwordFieldContainer.setMaxWidth(320);

        eyeIconStack.setOnMouseClicked((MouseEvent event) -> {
            passwordVisible = !passwordVisible;
            if (passwordVisible) {
                visiblePasswordField.setText(passwordField.getText());
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                visiblePasswordField.setVisible(true);
                visiblePasswordField.setManaged(true);
                eyeClosedIcon.setVisible(false);
                eyeOpenIcon.setVisible(true);
            } else {
                passwordField.setText(visiblePasswordField.getText());
                visiblePasswordField.setVisible(false);
                visiblePasswordField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                eyeClosedIcon.setVisible(true);
                eyeOpenIcon.setVisible(false);
            }
        });

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (passwordField.isVisible()) {
                visiblePasswordField.setText(newVal);
            }
        });

        visiblePasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (visiblePasswordField.isVisible()) {
                passwordField.setText(newVal);
            }
        });

        // Login button
        Button loginButton = new Button("Entrar");
        loginButton.setPrefSize(200, 50);
        loginButton.setStyle(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        );
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: #a09077;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        ));
        loginButton.setOnAction(event -> {
            String email = emailField.getText().trim();
            String password = passwordVisible ? visiblePasswordField.getText().trim() : passwordField.getText().trim();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            if (email.isEmpty() || password.isEmpty()) {
                alert.setContentText("Por favor, preencha todos os campos!");
                alert.showAndWait();
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                alert.setContentText("Por favor, insira um e-mail válido!");
                alert.showAndWait();
            } else if (email.equals("admin@iguatemi.com") && password.equals("admin123")) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Login bem-sucedido!");
                alert.showAndWait();
                CadHome cadHomeApp = new CadHome();
                Stage newStage = new Stage();
                try {
                    cadHomeApp.start(newStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                stage.close();
            } else {
                alert.setContentText("E-mail ou senha incorretos!");
                alert.showAndWait();
            }
        });

        // Forgot Password button
        Button forgotPasswordButton = new Button("Esqueceu a senha?");
        forgotPasswordButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #b4a087;" +
                        "-fx-font-size: 14;" +
                        "-fx-underline: true;"
        );
        forgotPasswordButton.setOnMouseEntered(e -> forgotPasswordButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #a09077;" +
                        "-fx-font-size: 14;" +
                        "-fx-underline: true;"
        ));
        forgotPasswordButton.setOnMouseExited(e -> forgotPasswordButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #b4a087;" +
                        "-fx-font-size: 14;" +
                        "-fx-underline: true;"
        ));
        forgotPasswordButton.setOnAction(event -> {
            Esquecisenha forgotPasswordApp = new Esquecisenha();
            Stage newStage = new Stage();
            forgotPasswordApp.start(newStage);
            stage.close(); // Close the login stage
        });

        // Add elements to login panel
        loginPanel.getChildren().addAll(
                welcomeLabel,
                emailLabel, emailField,
                passwordLabel, passwordFieldContainer,
                loginButton,
                forgotPasswordButton
        );

        // Right: Image panel
        StackPane imagePanel = new StackPane();
        imagePanel.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Spacer to push image to fill Right Side
        try {
            Image image = new Image("img/iguatemi_esplanada.jpg", true);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(false);
            imageView.fitWidthProperty().bind(centerPanel.widthProperty().divide(2)); // Fill Right Half
            imageView.fitHeightProperty().bind(centerPanel.heightProperty());
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);");
            imagePanel.getChildren().add(imageView);
        } catch (Exception e) {
            Label placeholder = new Label("Imagem do Shopping");
            placeholder.setFont(Font.font("Arial", 20));
            placeholder.setTextFill(Color.GRAY);
            imagePanel.getChildren().add(placeholder);
        }

        // Add panels to Center
        centerPanel.getChildren().addAll(loginPanel, spacer, imagePanel);
        root.setCenter(centerPanel);

        // Scene setup
        Scene scene = new Scene(root, 1000, 600);
        scene.setFill(Color.rgb(245, 245, 245));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}