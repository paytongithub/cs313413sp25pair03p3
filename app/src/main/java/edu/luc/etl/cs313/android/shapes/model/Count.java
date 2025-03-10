package edu.luc.etl.cs313.android.shapes.model;



/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) {
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        int count = 0;
        /*for(Iterator<? extends Shape> s = g.getShapes().iterator(); s.hasNext();){count++;}
        return count;*/
        for (int i = 0; i < g.getShapes().size(); i++) {
                count += g.getShapes().get(i).accept(this);
            }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        if (o.getShape() instanceof  Group && !(o.getShape() instanceof Polygon)){
            return o.accept(this);
        }
        return o.getShape().accept(this);}


    @Override
    public Integer onFill(final Fill c) {
        if ((c.getShape() instanceof  Group) && !(c.getShape() instanceof Polygon)){
            return onGroup((Group) c.getShape());
        }
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        if (l.getShape() instanceof Group && !(l.getShape() instanceof Polygon)){
            return onGroup((Group) l.getShape());
        }
        return l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        if (c.getShape() instanceof Group && !(c.getShape() instanceof Polygon)) {
            return onGroup((Group) c.getShape());
        }
        return c.getShape().accept(this);

    }
}
