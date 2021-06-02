package storyline.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import storyline.model.EventCard;

public class TimelineController {

    @FXML
    public ScrollPane timelineScrollPane;

    @FXML
    GridPane timelineGridPane;

    @FXML
    VBox root;

    @FXML
    private void initialize() {

        //the grid is populated with empty panes to make it easier to get the get column and row index of the drag event
        populateGrid(timelineGridPane);

        timelineGridPane.setOnDragOver(event -> {
            if (event.getGestureSource() != timelineGridPane) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });


        timelineGridPane.setOnDragDropped(event -> {

            HBox source;
            if (event.getGestureSource() instanceof HBox) {
                source = (HBox) event.getGestureSource();
            } else return;

            double mouseX = event.getX();
            double mouseY = event.getY();

            for (Node node : timelineGridPane.getChildren()) {
                if (node.getBoundsInParent().contains(mouseX, mouseY)) {
                    int columnIndex = GridPane.getColumnIndex(node);
                    int rowIndex = GridPane.getRowIndex(node);

                    timelineGridPane.add(source, columnIndex, rowIndex);
                }
            }
            event.consume();
        });


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


