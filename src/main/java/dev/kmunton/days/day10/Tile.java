package dev.kmunton.days.day10;

import java.util.List;

public class Tile {

    private final String type;
    private final RowCol rowCol;
    private final List<RowCol> possibleNeighbours;

    public Tile(String type, RowCol rowCol) {
        this.type = type;
        this.rowCol = rowCol;
        this.possibleNeighbours = setPossibleNeighbours(rowCol);
    }

    private List<RowCol> setPossibleNeighbours(RowCol rowCol) {
        return switch (type) {
            case "S" -> List.of(
                new RowCol(rowCol.getRow() - 1, rowCol.getCol()),
                new RowCol(rowCol.getRow() + 1, rowCol.getCol()),
                new RowCol(rowCol.getRow(), rowCol.getCol() - 1),
                new RowCol(rowCol.getRow(), rowCol.getCol() + 1)
            );
            case "|" -> List.of(
                new RowCol(rowCol.getRow() - 1, rowCol.getCol()),
                new RowCol(rowCol.getRow() + 1, rowCol.getCol())
            );
            case "-" -> List.of(
                new RowCol(rowCol.getRow(), rowCol.getCol() - 1),
                new RowCol(rowCol.getRow(), rowCol.getCol() + 1)
            );
            case "F" -> List.of(
                new RowCol(rowCol.getRow() + 1, rowCol.getCol()),
                new RowCol(rowCol.getRow(), rowCol.getCol() + 1)
            );
            case "L" -> List.of(
                new RowCol(rowCol.getRow() - 1, rowCol.getCol()),
                new RowCol(rowCol.getRow(), rowCol.getCol() + 1)
            );
            case "J" -> List.of(
                new RowCol(rowCol.getRow() - 1, rowCol.getCol()),
                new RowCol(rowCol.getRow(), rowCol.getCol() - 1)
            );
            case "7" -> List.of(
                new RowCol(rowCol.getRow() + 1, rowCol.getCol()),
                new RowCol(rowCol.getRow(), rowCol.getCol() - 1)
            );
            default -> List.of();
        };
    }

    public String getType() {
        return type;
    }

    public List<RowCol> getPossibleNeighbours() {
        return possibleNeighbours;
    }

    public boolean isMainLoop(List<RowCol> mainLoopRowCols) {
        return mainLoopRowCols.contains(rowCol);
    }

    @Override
    public String toString() {
        return "Tile{" +
            "type=" + type +
            ", rowCol=" + rowCol +
            ", possibleNeighbours=" + possibleNeighbours +
            '}';
    }

    public RowCol getRowCol() {
        return rowCol;
    }
}
