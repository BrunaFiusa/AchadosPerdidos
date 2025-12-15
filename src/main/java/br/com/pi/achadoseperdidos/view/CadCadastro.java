package br.com.pi.achadoseperdidos.view;

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
import java.util.ArrayList;
import java.util.List;

public class CadCadastro extends Application {

    private File arquivoSelecionado = null;
    private final List<File> arquivosFotos = new ArrayList<>();

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #c4af95;");

        VBox content = new VBox(12); // Espaçamento ainda menor
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20, 30, 25, 30)); // Padding reduzido em cima e embaixo

        // Título
        Label title = new Label("Cadastro de Objeto");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Imagem menor
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setStyle(
                "-fx-border-color: #919191; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-style: dashed; " +
                        "-fx-background-color: #d6c4b0; " +
                        "-fx-background-radius: 16; " +
                        "-fx-border-radius: 16;"
        );

        // Botão upload
        Button uploadButton = new Button("Escolher Foto(s)");
        styleButton(uploadButton);
        uploadButton.setPrefWidth(260);

        // Campos mais baixos
        TextField nomeField = createCompactField("Nome do Objeto *");
        TextField lacreField = createCompactField("Número do Lacre *");
        TextField observacaoField = createCompactField("Observação");
        TextField marcaField = createCompactField("Marca");
        TextField condicaoField = createCompactField("Condição");

        // Botões finais
        Button salvarButton = new Button("Salvar");
        styleButton(salvarButton);
        salvarButton.setPrefWidth(130);

        Button cancelButton = new Button("Cancelar");
        styleButton(cancelButton);
        cancelButton.setStyle(cancelButton.getStyle() + "-fx-background-color: #a0522d;");
        cancelButton.setPrefWidth(130);

        HBox buttons = new HBox(15, salvarButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        // Monta tudo
        content.getChildren().addAll(
                title,
                imageView,
                uploadButton,
                nomeField,
                lacreField,
                observacaoField,
                marcaField,
                condicaoField,
                buttons
        );

        root.setCenter(content);

        // Ação: Upload
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            List<File> files = fileChooser.showOpenMultipleDialog(stage);
            if (files != null && !files.isEmpty()) {
                arquivosFotos.clear();
                arquivosFotos.addAll(files);
                arquivoSelecionado = files.get(0);

                Image image = new Image(arquivoSelecionado.toURI().toString());
                imageView.setImage(image);

                imageView.setStyle(
                        "-fx-border-color: #5e8c61; " +
                                "-fx-border-width: 3; " +
                                "-fx-border-style: solid; " +
                                "-fx-background-color: #d6c4b0; " +
                                "-fx-background-radius: 16; " +
                                "-fx-border-radius: 16;"
                );
            }
        });

        // Ação: Cancelar
        cancelButton.setOnAction(e -> {
            nomeField.clear();
            lacreField.clear();
            observacaoField.clear();
            marcaField.clear();
            condicaoField.clear();
            imageView.setImage(null);
            arquivosFotos.clear();
            arquivoSelecionado = null;
            imageView.setStyle(
                    "-fx-border-color: #919191; " +
                            "-fx-border-width: 3; " +
                            "-fx-border-style: dashed; " +
                            "-fx-background-color: #d6c4b0; " +
                            "-fx-background-radius: 16; " +
                            "-fx-border-radius: 16;"
            );
        });

        // Ação: Salvar (simulação)
        salvarButton.setOnAction(e -> {
            String nome = nomeField.getText().trim();
            String lacre = lacreField.getText().trim();

            if (nome.isEmpty() || lacre.isEmpty() || arquivosFotos.isEmpty()) {
                showAlert("Atenção", "Preencha nome, lacre e selecione uma foto.");
                return;
            }

            showAlert("Sucesso!", "Tudo preenchido corretamente!\nA tela está compacta e funcionando.");
        });

        Scene scene = new Scene(root, 460, 620); // Tela mais curta
        stage.setScene(scene);
        stage.setTitle("Cadastro de Objeto");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private TextField createCompactField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(38);
        tf.setMaxWidth(300);
        styleTextField(tf);
        return tf;
    }

    private void styleButton(Button button) {
        button.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 10 18;"
        );
    }

    private void styleTextField(TextField textField) {
        textField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #919191; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10;"
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}