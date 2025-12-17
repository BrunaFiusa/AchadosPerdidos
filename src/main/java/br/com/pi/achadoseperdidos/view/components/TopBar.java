package br.com.pi.achadoseperdidos.view.components;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopBar extends HBox {
    private static final double SPACING = 10.0;
    private static final double HEIGHT = 60.0;

    public TopBar() {
        this.setSpacing(SPACING);
        this.setPadding(new Insets(10));
        this.setPrefHeight(HEIGHT);
        this.setStyle("-fx-border-color: #919191; -fx-background-color: #c4af95;");
        this.setAlignment(Pos.CENTER_LEFT);

        createLogo();
        createNavigation();
        createNotificationArea();
    }

    private void createLogo() {
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("")));
        logo.setFitHeight(40);
        logo.setPreserveRatio(true);
        this.getChildren().add(logo);
    }

    private void createNavigation() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.getChildren().add(spacer);

        HBox navigation = new HBox(10);
        navigation.setAlignment(Pos.CENTER);

        Button objectsBtn = new Button("Objetos");
        Button requestsBtn = new Button("Solicitações");
        Button returnsBtn = new Button("Devoluções");
        Button reportsBtn = new Button("Relatórios");

        navigation.getChildren().addAll(objectsBtn, requestsBtn, returnsBtn, reportsBtn);
        this.getChildren().add(navigation);
    }

    private void createNotificationArea() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.getChildren().add(spacer);

        ImageView bellIcon = new ImageView(new Image(getClass().getResourceAsStream("")));
        bellIcon.setFitHeight(24);
        bellIcon.setPreserveRatio(true);

        ImageView userIcon = new ImageView(new Image(getClass().getResourceAsStream("")));
        userIcon.setFitHeight(32);
        userIcon.setPreserveRatio(true);

        HBox notificationArea = new HBox(10);
        notificationArea.setAlignment(Pos.CENTER);
        notificationArea.getChildren().addAll(bellIcon, userIcon);

        this.getChildren().add(notificationArea);
    }
}