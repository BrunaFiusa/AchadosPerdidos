package view;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
        // Window setup
        stage.setTitle("Recuperar Senha de Administrador - Shopping Iguatemi Sorocaba");
        stage.setResizable(false); // Disable resizing and maximize button

        // Root pane with a modern gradient background
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(245, 245, 245), null, null
        )));

        // Header
        Label titleLabel = new Label("Shopping Iguatemi Sorocaba");
        titleLabel.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Arial", Math.min(stage.getWidth() * 0.03, 34)),
                stage.widthProperty()
        ));
        titleLabel.setTextFill(Color.WHITE);
        HBox titlePanel = new HBox(titleLabel);
        titlePanel.setAlignment(Pos.CENTER);
        titlePanel.setBackground(new Background(new BackgroundFill(
                Color.rgb(180, 160, 135),
                new CornerRadii(0),
                new Insets(0)
        )));
        titlePanel.paddingProperty().bind(Bindings.createObjectBinding(
                () -> new Insets(Math.min(stage.getHeight() * 0.04, 25)),
                stage.heightProperty()
        ));
        titlePanel.setEffect(new DropShadow(8, 0, 4, Color.rgb(0, 0, 0, 0.15)));
        root.setTop(titlePanel);

        // Center: Split layout with form on left and image on right
        HBox centerPanel = new HBox();
        centerPanel.setSpacing(0);
        centerPanel.setAlignment(Pos.CENTER);

        // Left: Recover password form panel
        VBox recoverPanel = new VBox();
        recoverPanel.spacingProperty().bind(Bindings.createDoubleBinding(
                () -> Math.min(stage.getHeight() * 0.03, 25),
                stage.heightProperty()
        ));
        recoverPanel.paddingProperty().bind(Bindings.createObjectBinding(
                () -> new Insets(Math.min(stage.getHeight() * 0.05, 40),
                        Math.min(stage.getWidth() * 0.05, 50),
                        Math.min(stage.getHeight() * 0.05, 40),
                        Math.min(stage.getWidth() * 0.05, 50)),
                stage.widthProperty(), stage.heightProperty()
        ));
        recoverPanel.setAlignment(Pos.CENTER);
        recoverPanel.prefWidthProperty().bind(stage.widthProperty().multiply(0.5));
        recoverPanel.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 6);"
        );

        // Recover title label
        Label recoverLabel = new Label("Recuperar Senha");
        recoverLabel.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Arial", Math.min(stage.getWidth() * 0.025, 30)),
                stage.widthProperty()
        ));
        recoverLabel.setTextFill(Color.rgb(180, 160, 135));

        // Instruction label
        Label instructionLabel = new Label("Digite a senha de administrador para redefinir sua senha:");
        instructionLabel.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Arial", Math.min(stage.getWidth() * 0.015, 16)),
                stage.widthProperty()
        ));
        instructionLabel.setTextFill(Color.rgb(100, 100, 100));
        instructionLabel.setWrapText(true);
        instructionLabel.maxWidthProperty().bind(recoverPanel.prefWidthProperty().multiply(0.8));

        // Admin password field
        Label adminPasswordLabel = new Label("Senha de Administrador:");
        adminPasswordLabel.fontProperty().bind(Bindings.createObjectBinding(
                () -> Font.font("Arial", Math.min(stage.getWidth() * 0.015, 16)),
                stage.widthProperty()
        ));
        PasswordField adminPasswordField = new PasswordField();
        adminPasswordField.setPromptText("Digite a senha de administrador");
        adminPasswordField.maxWidthProperty().bind(recoverPanel.prefWidthProperty().multiply(0.65));
        adminPasswordField.styleProperty().bind(Bindings.createStringBinding(
                () -> "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: " + Math.min(stage.getHeight() * 0.015, 12) + ";" +
                        "-fx-font-size: " + Math.min(stage.getWidth() * 0.013, 15) + ";" +
                        "-fx-border-width: 1.5;",
                stage.widthProperty(), stage.heightProperty()
        ));

        // Send button
        Button sendButton = new Button("Enviar");
        sendButton.prefWidthProperty().bind(recoverPanel.prefWidthProperty().multiply(0.4));
        sendButton.prefHeightProperty().bind(Bindings.createDoubleBinding(
                () -> Math.min(stage.getHeight() * 0.08, 50),
                stage.heightProperty()
        ));
        sendButton.styleProperty().set(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        );
        sendButton.setOnMouseEntered(e -> sendButton.setStyle(
                "-fx-background-color: #a09077;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);"
        ));
        sendButton.setOnMouseExited(e -> sendButton.setStyle(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        ));
        sendButton.setOnAction(event -> {
            String adminPassword = adminPasswordField.getText().trim();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            if (adminPassword.isEmpty()) {
                alert.setContentText("Por favor, preencha o campo de senha!");
                alert.showAndWait();
            } else {
                String correctAdminPassword = "admin123"; // Hardcoded for simplicity
                if (adminPassword.equals(correctAdminPassword)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setContentText("Senha de administrador válida! Você pode agora redefinir sua senha.");
                    alert.showAndWait();
                    adminPasswordField.clear();
                } else {
                    alert.setContentText("Senha de administrador inválida!");
                    alert.showAndWait();
                }
            }
        });

        // Back button
        Button backButton = new Button("Voltar");
        backButton.styleProperty().bind(Bindings.createStringBinding(
                () -> "-fx-background-color: transparent;" +
                        "-fx-text-fill: #b4a087;" +
                        "-fx-font-size: " + Math.min(stage.getWidth() * 0.013, 14) + ";" +
                        "-fx-underline: true;",
                stage.widthProperty()
        ));
        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #a09077;" +
                        "-fx-font-size: " + Math.min(stage.getWidth() * 0.013, 14) + ";" +
                        "-fx-underline: true;"
        ));
        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #b4a087;" +
                        "-fx-font-size: " + Math.min(stage.getWidth() * 0.013, 14) + ";" +
                        "-fx-underline: true;"
        ));
        backButton.setOnAction(event -> {
            Login loginApp = new Login();
            Stage newStage = new Stage();
            loginApp.start(newStage);
            stage.close();
        });

        // Add elements to recover panel
        recoverPanel.getChildren().addAll(
                recoverLabel,
                instructionLabel,
                adminPasswordLabel,
                adminPasswordField,
                sendButton,
                backButton
        );

        // Right: Image panel
        StackPane imagePanel = new StackPane();
        imagePanel.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        try {
            Image image = new Image("img/iguatemi_esplanada.jpg", true);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(false);
            imageView.fitWidthProperty().bind(centerPanel.widthProperty().multiply(0.5));
            imageView.fitHeightProperty().bind(centerPanel.heightProperty());
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);");
            imagePanel.getChildren().add(imageView);
        } catch (Exception e) {
            Label placeholder = new Label("Imagem do Shopping");
            placeholder.fontProperty().bind(Bindings.createObjectBinding(
                    () -> Font.font("Arial", Math.min(stage.getWidth() * 0.018, 20)),
                    stage.widthProperty()
            ));
            placeholder.setTextFill(Color.GRAY);
            imagePanel.getChildren().add(placeholder);
        }

        // Add panels to center
        centerPanel.getChildren().addAll(recoverPanel, spacer, imagePanel);
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