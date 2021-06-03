package storyline.controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import storyline.model.EventCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class TimelineController {

    @FXML
    public ScrollPane timelineScrollPane;

    @FXML
    GridPane timelineGridPane;

    @FXML
    VBox root;

    @FXML
    private void initialize() {


        ArrayList<EventCard> timelineEventCards = new ArrayList<>();

        timelineGridPane.setOnDragOver(event -> {
            if (event.getGestureSource() != timelineGridPane) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        timelineGridPane.setOnDragDropped(event -> {

            HBox source = (HBox) event.getGestureSource();
//            if (event.getGestureSource() instanceof HBox) {
//                source = (HBox) event.getGestureSource();
//            }

//            if (source.getParent() instanceof GridPane) {
//                System.out.println("gridpane");
//                int columnIndex = GridPane.getColumnIndex(source);
//                int rowIndex = GridPane.getRowIndex(source);
//            }

            System.out.println("source.getUserData() = " + source.getUserData());
            double mouseX = event.getX();
            double mouseY = event.getY();

            Bounds cellBounds = timelineGridPane.impl_getCellBounds(0,0);

            int columnIndex = (int) Math.floor(mouseX/cellBounds.getWidth());
            int rowIndex = (int) Math.floor(mouseY/cellBounds.getHeight());
            System.out.println("rowIndex = " + rowIndex);
            System.out.println("columnIndex = " + columnIndex);

            EventCard sourceEventCard = (EventCard)source.getUserData();

            boolean overlap = false;
            for (EventCard eventCard : timelineEventCards) {
                System.out.println("x =" + eventCard.x + " y = " + eventCard.y);
                if (eventCard.x == columnIndex && eventCard.y == rowIndex) {
                    System.out.println("overlap!");
                    overlap = true;
                }
            }
            if(overlap == false) {
                timelineGridPane.getChildren().removeIf(node -> node == source);
                timelineGridPane.add(source, columnIndex, rowIndex);
                if (!timelineEventCards.contains(sourceEventCard))timelineEventCards.add(sourceEventCard);

                sourceEventCard.x = columnIndex;
                sourceEventCard.y = rowIndex;
            }

            event.consume();
        });


    }
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void populateGrid(GridPane gridPane) {

        int numCols = gridPane.getColumnConstraints().size();
        int numRows = gridPane.getRowConstraints().size();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(gridPane, i, j);
            }
        }
    }

    private void addPane(GridPane gridPane, int colIndex, int rowIndex) {
        Pane pane = new Pane();

        gridPane.add(pane, colIndex, rowIndex);
    }
}


