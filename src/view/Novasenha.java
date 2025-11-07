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

public class Novasenha extends Application {

    @Override
    public void start(Stage stage) {
        // Window setup
        stage.setTitle("Alterar Senha - Shopping Iguatemi Sorocaba");
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

        // Left: Change password form panel
        VBox passwordPanel = new VBox(25);
        passwordPanel.setPadding(new Insets(40, 50, 40, 50));
        passwordPanel.setAlignment(Pos.CENTER);
        passwordPanel.setPrefWidth(500); // Fixed width for form
        passwordPanel.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 6);"
        );

        // Welcome label
        Label welcomeLabel = new Label("Alterar Senha");
        welcomeLabel.setFont(Font.font("Arial", 30));
        welcomeLabel.setTextFill(Color.rgb(180, 160, 135));

        // New password field
        Label newPasswordLabel = new Label("Nova Senha:");
        newPasswordLabel.setFont(Font.font("Arial", 16));
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Digite sua nova senha");
        newPasswordField.setMaxWidth(320);
        newPasswordField.setStyle(
                "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 15;" +
                        "-fx-border-width: 1.5;"
        );

        // Confirm password field
        Label confirmPasswordLabel = new Label("Confirmar Senha:");
        confirmPasswordLabel.setFont(Font.font("Arial", 16));
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirme sua nova senha");
        confirmPasswordField.setMaxWidth(320);
        confirmPasswordField.setStyle(
                "-fx-background-color: #F8F8F8;" +
                        "-fx-border-color: #D0D0D0;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 12;" +
                        "-fx-font-size: 15;" +
                        "-fx-border-width: 1.5;"
        );

        // Change password button
        Button changePasswordButton = new Button("Alterar Senha");
        changePasswordButton.setPrefSize(200, 50);
        changePasswordButton.setStyle(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        );
        changePasswordButton.setOnMouseEntered(e -> changePasswordButton.setStyle(
                "-fx-background-color: #a09077;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);"
        ));
        changePasswordButton.setOnMouseExited(e -> changePasswordButton.setStyle(
                "-fx-background-color: #b4a087;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 18;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0, 0, 3);"
        ));
        changePasswordButton.setOnAction(event -> {
            String newPassword = newPasswordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                alert.setContentText("Por favor, preencha todos os campos!");
                alert.showAndWait();
            } else if (!newPassword.equals(confirmPassword)) {
                alert.setContentText("As senhas não coincidem!");
                alert.showAndWait();
            } else if (newPassword.length() < 8) {
                alert.setContentText("A senha deve ter pelo menos 8 caracteres!");
                alert.showAndWait();
            } else if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                alert.setContentText("A senha deve conter letras e números!");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Senha alterada com sucesso!");
                alert.showAndWait();
                newPasswordField.clear();
                confirmPasswordField.clear();
            }
        });

        // Add elements to password panel
        passwordPanel.getChildren().addAll(
                welcomeLabel,
                newPasswordLabel, newPasswordField,
                confirmPasswordLabel, confirmPasswordField,
                changePasswordButton
        );

        // Right: Image panel
        StackPane imagePanel = new StackPane();
        imagePanel.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Spacer to push image to fill right side
        try {
            Image image = new Image("img/iguatemi_esplanada.jpg", true);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(false);
            imageView.fitWidthProperty().bind(centerPanel.widthProperty().divide(2)); // Fill right half
            imageView.fitHeightProperty().bind(centerPanel.heightProperty());
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);");
            imagePanel.getChildren().add(imageView);
        } catch (Exception e) {
            Label placeholder = new Label("Imagem do Shopping");
            placeholder.setFont(Font.font("Arial", 20));
            placeholder.setTextFill(Color.GRAY);
            imagePanel.getChildren().add(placeholder);
        }

        // Add panels to center
        centerPanel.getChildren().addAll(passwordPanel, spacer, imagePanel);
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