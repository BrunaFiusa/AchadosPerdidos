package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class CadCadastro extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #c4af95; -fx-border-radius: 10;"); // Added border radius to root

        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #c4af95; -fx-border-radius: 10;"); // Consistent corner rounding

        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-border-color: #919191; -fx-border-width: 2; -fx-background-color: #ffffff; -fx-border-radius: 12;"); // Increased border radius

        HBox imageContainer = new HBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPadding(new Insets(15));
        imageContainer.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 12;"); // Increased border radius
        imageContainer.getChildren().add(imageView);

        Button uploadButton = new Button("Escolher Imagem");
        styleButton(uploadButton);
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        });

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(300);
        form.setStyle("-fx-background-color: #c4af95; -fx-border-radius: 10;"); // Added border radius to form

        Label title = new Label("Cadastro de Produto");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        TextField nomeProduto = new TextField();
        nomeProduto.setPromptText("Nome do Produto");
        nomeProduto.setPrefHeight(40);
        styleTextField(nomeProduto);

        TextField descricao = new TextField();
        descricao.setPromptText("Descrição");
        descricao.setPrefHeight(40);
        styleTextField(descricao);

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);

        Button salvarButton = new Button("Salvar");
        styleButton(salvarButton);
        salvarButton.setPrefWidth(100);
        salvarButton.setOnAction(e -> {
            if (nomeProduto.getText().trim().isEmpty()) {
                showAlert("Erro", "O nome do produto é obrigatório!");
                return;
            }
            if (imageView.getImage() == null) {
                showAlert("Erro", "Por favor, selecione uma imagem!");
                return;
            }
            showAlert("Sucesso", "Produto salvo com sucesso!");
        });

        Button cancelButton = new Button("Cancelar");
        styleButton(cancelButton);
        cancelButton.setPrefWidth(100);
        cancelButton.setOnAction(e -> {
            nomeProduto.clear();
            descricao.clear();
            imageView.setImage(null);
        });

        buttons.getChildren().addAll(salvarButton, cancelButton);

        form.getChildren().addAll(title, nomeProduto, descricao);
        content.getChildren().addAll(imageContainer, uploadButton, form, buttons);

        root.setCenter(content);

        Scene scene = new Scene(root, 500, 550);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cadastro de Produto");
        stage.centerOnScreen();
        stage.show();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 12; -fx-border-radius: 12;"); // Increased corner radius
    }

    private void styleTextField(TextField textField) {
        textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #919191; -fx-border-radius: 12; -fx-font-size: 16px; -fx-text-fill: #333333; -fx-prompt-text-fill: #cccccc; -fx-background-radius: 12;"); // Increased corner radius
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}