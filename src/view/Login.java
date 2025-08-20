package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage stage) {
        // Configurações da janela
        stage.setTitle("Login - Shopping Iguatemi Sorocaba");

        // Painel raiz
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        root.setEffect(new DropShadow(10, 0, 5, Color.rgb(0, 0, 0, 0.1)));

        // Cabeçalho superior
        Label titleLabel = new Label("Shopping Iguatemi Sorocaba");
        titleLabel.setFont(Font.font("Arial", 32));
        titleLabel.setTextFill(Color.WHITE);
        HBox titlePanel = new HBox(titleLabel);
        titlePanel.setAlignment(Pos.CENTER);
        titlePanel.setBackground(new Background(new BackgroundFill(
                Color.rgb(196, 175, 149),
                new CornerRadii(10),
                new Insets(0)
        )));
        titlePanel.setPadding(new Insets(20));
        titlePanel.setEffect(new DropShadow(5, 0, 3, Color.rgb(0, 0, 0, 0.2)));
        root.setTop(titlePanel);

        // Painel central: HBox para formulário à esquerda e imagem à direita
        HBox centerPanel = new HBox();
        centerPanel.setSpacing(30);
        centerPanel.setPadding(new Insets(30));
        centerPanel.setAlignment(Pos.CENTER);

        // Painel de login à esquerda
        VBox loginPanel = new VBox(20);
        loginPanel.setPadding(new Insets(60, 30, 60, 60));
        loginPanel.setAlignment(Pos.TOP_CENTER);
        loginPanel.setPrefWidth(450);
        loginPanel.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Label de boas-vindas
        Label welcomeLabel = new Label("Bem-vindo!");
        welcomeLabel.setFont(Font.font("Arial", 28));
        welcomeLabel.setTextFill(Color.rgb(196, 175, 149));

        // Campo de e-mail
        Label emailLabel = new Label("E-mail:");
        emailLabel.setFont(Font.font("Arial", 16));
        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu e-mail");
        emailField.setMaxWidth(300);
        emailField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 14;"
        );

        // Campo de senha
        Label passwordLabel = new Label("Senha:");
        passwordLabel.setFont(Font.font("Arial", 16));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Digite sua senha");
        passwordField.setMaxWidth(300);
        passwordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 14;"
        );

        // Botão de login
        Button loginButton = new Button("Entrar");
        loginButton.setPrefSize(180, 50);
        loginButton.setStyle(
                "-fx-background-color: #c4af95;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: #b39d83;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: #c4af95;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        ));
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha todos os campos!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Login bem-sucedido!");
                alert.showAndWait();
            }
        });

        // Adicionando ao painel de login
        loginPanel.getChildren().addAll(welcomeLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton);

        // Painel da imagem à direita
        StackPane imagePanel = new StackPane();
        imagePanel.setPrefSize(500, 450);
        imagePanel.setBackground(new Background(new BackgroundFill(
                Color.LIGHTGRAY,
                new CornerRadii(10),
                new Insets(0)
        )));
        try {
            Image image = new Image("img/iguatemi_esplanada.jpg", 500, 450, true, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500);
            imageView.setFitHeight(450);
            imageView.setPreserveRatio(false);
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
            imagePanel.getChildren().add(imageView);
        } catch (Exception e) {
            Label placeholder = new Label("Imagem do Shopping");
            placeholder.setFont(Font.font("Arial", 20));
            placeholder.setTextFill(Color.GRAY);
            imagePanel.getChildren().add(placeholder);
        }
        imagePanel.setAlignment(Pos.CENTER);

        // Adicionando ao painel central (formulário à esquerda, imagem à direita)
        centerPanel.getChildren().addAll(loginPanel, imagePanel);
        root.setCenter(centerPanel);

        // Cena
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}