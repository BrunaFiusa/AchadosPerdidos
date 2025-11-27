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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Esquecisenha extends Application {

    @Override
    public void start(Stage stage) {
        // Configurações da janela
        stage.setTitle("Recuperar Senha de Administrador - Shopping Iguatemi Sorocaba");

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

        // Painel de recuperação de senha à esquerda
        VBox recoverPanel = new VBox(20);
        recoverPanel.setPadding(new Insets(60, 30, 60, 60));
        recoverPanel.setAlignment(Pos.TOP_CENTER);
        recoverPanel.setPrefWidth(450);
        recoverPanel.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Label de título
        Label recoverLabel = new Label("Recuperar Senha de Administrador");
        recoverLabel.setFont(Font.font("Arial", 28));
        recoverLabel.setTextFill(Color.rgb(196, 175, 149));

        // Instrução
        Label instructionLabel = new Label("Digite a senha de administrador para redefinir sua senha:");
        instructionLabel.setFont(Font.font("Arial", 16));
        instructionLabel.setTextFill(Color.GRAY);
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(350);

        // Campo de senha de administrador
        Label adminPasswordLabel = new Label("Senha de Administrador:");
        adminPasswordLabel.setFont(Font.font("Arial", 16));
        PasswordField adminPasswordField = new PasswordField();
        adminPasswordField.setPromptText("Digite a senha de administrador");
        adminPasswordField.setMaxWidth(300);
        adminPasswordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 14;"
        );

        // Botão de enviar
        Button sendButton = new Button("Enviar");
        sendButton.setPrefSize(180, 50);
        sendButton.setStyle(
                "-fx-background-color: #c4af95;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );
        sendButton.setOnMouseEntered(e -> sendButton.setStyle(
                "-fx-background-color: #b39d83;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);"
        ));
        sendButton.setOnMouseExited(e -> sendButton.setStyle(
                "-fx-background-color: #c4af95;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        ));
        sendButton.setOnAction(event -> {
            String adminPassword = adminPasswordField.getText();
            if (adminPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha o campo de senha!");
                alert.showAndWait();
            } else {
                String correctAdminPassword = "admin123";
                if (adminPassword.equals(correctAdminPassword)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Senha de administrador válida! Você pode agora redefinir sua senha.");
                    alert.showAndWait();
                    adminPasswordField.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Senha de administrador inválida!");
                    alert.showAndWait();
                }
            }
        });

        // Adicionando ao painel de recuperação
        recoverPanel.getChildren().addAll(recoverLabel, instructionLabel, adminPasswordLabel, adminPasswordField, sendButton);

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
        centerPanel.getChildren().addAll(recoverPanel, imagePanel);
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
