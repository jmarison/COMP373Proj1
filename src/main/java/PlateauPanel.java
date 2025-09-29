import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class PlateauPanel extends JPanel {
    private int maxX, maxY;
    private List<Point> path = new ArrayList<>();
    private Rover.Direction finalDir;

    public PlateauPanel(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void updatePath(List<Point> path, Rover.Direction dir) {
        this.path = new ArrayList<>(path);
        this.finalDir = dir;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = Math.min(getWidth() / (maxX + 1), getHeight() / (maxY + 1));

        // Draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= maxX + 1; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, (maxY + 1) * cellSize);
        }
        for (int j = 0; j <= maxY + 1; j++) {
            g.drawLine(0, j * cellSize, (maxX + 1) * cellSize, j * cellSize);
        }

        // Draw path
        g.setColor(Color.BLUE);
        for (int i = 0; i < path.size() - 1; i++) {
            Point p = path.get(i);
            int drawX = p.x * cellSize;
            int drawY = (maxY - p.y) * cellSize;
            g.fillOval(drawX + cellSize / 4, drawY + cellSize / 4, cellSize / 2, cellSize / 2);
        }

        // Draw rover’s final position + direction
        if (!path.isEmpty() && finalDir != null) {
            Point last = path.get(path.size() - 1);
            int cx = last.x * cellSize + cellSize / 2;
            int cy = (maxY - last.y) * cellSize + cellSize / 2;

            g.setColor(Color.RED);
            int size = cellSize / 2;

            Polygon arrow = new Polygon();
            switch (finalDir) {
                case NORTH:
                    arrow.addPoint(cx, cy - size / 2);
                    arrow.addPoint(cx - size / 2, cy + size / 2);
                    arrow.addPoint(cx + size / 2, cy + size / 2);
                    break;
                case SOUTH:
                    arrow.addPoint(cx, cy + size / 2);
                    arrow.addPoint(cx - size / 2, cy - size / 2);
                    arrow.addPoint(cx + size / 2, cy - size / 2);
                    break;
                case EAST:
                    arrow.addPoint(cx + size / 2, cy);
                    arrow.addPoint(cx - size / 2, cy - size / 2);
                    arrow.addPoint(cx - size / 2, cy + size / 2);
                    break;
                case WEST:
                    arrow.addPoint(cx - size / 2, cy);
                    arrow.addPoint(cx + size / 2, cy - size / 2);
                    arrow.addPoint(cx + size / 2, cy + size / 2);
                    break;
            }
            g.fillPolygon(arrow);
        }
    }

    public void setBounds(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        repaint();
    }
}
