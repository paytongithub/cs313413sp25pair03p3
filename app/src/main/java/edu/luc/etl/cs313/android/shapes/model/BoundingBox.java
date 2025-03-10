package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        final Location fillLocation = f.getShape().accept(this);
        return new Location (fillLocation.getX(), fillLocation.getY(), fillLocation.getShape());
    }


    // Currently ONLY works for simple, need it to account for other shapes like StrokeColor, Polygons, Outlines
    @Override
    public Location onGroup(final Group g) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Shape shape : g.getShapes()) {
            Shape bbox = shape.accept(this);
            if (bbox instanceof Location) {
                Location loc = (Location) bbox;
                Shape innerShape = loc.getShape();
                if (innerShape instanceof Rectangle) {
                    Rectangle rect = (Rectangle) innerShape;

                    // Compute boundaries
                    minX = Math.min(minX, loc.getX());
                    minY = Math.min(minY, loc.getY());
                    maxX = Math.max(maxX, loc.getX() + rect.getWidth());
                    maxY = Math.max(maxY, loc.getY() + rect.getHeight());
                }
            }
        }

        int width = maxX - minX;
        int height = maxY - minY;

        return new Location(minX, minY, new Rectangle(width, height));
    }

    @Override
    public Location onLocation(final Location l) {
        final Location innerBox = l.getShape().accept(this); // Shape at the location

        return new Location (l.getX() + innerBox.getX(), l.getY() + innerBox.getY(), innerBox.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0, 0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        final Location strokeColorLocation = c.getShape().accept(this);
        return new Location (strokeColorLocation.getX(), strokeColorLocation.getY(), strokeColorLocation.getShape());
    }

    @Override
    public Location onOutline(final Outline o) {
        final Location outlineLocation = o.getShape().accept(this);
        return new Location (outlineLocation.getX(), outlineLocation.getY(), new Rectangle(o.getShape().accept(this).getX(), o.getShape().accept(this).getY()));
    }

    @Override
    public Location onPolygon(final Polygon s) {
        return onGroup((Group) s); // can just cast it as group and reuse
    }
}
