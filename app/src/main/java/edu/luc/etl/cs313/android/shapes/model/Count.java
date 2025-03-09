package edu.luc.etl.cs313.android.shapes.model;

import java.util.Iterator;

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
        Integer count = 0;
        /*for(Iterator<? extends Shape> s = g.getShapes().iterator(); s.hasNext();){count++;}
        return count;*/
        for(int i = 0; i<g.getShapes().size(); i++){
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
        return 1;
    }

    @Override
    public Integer onFill(final Fill c) {
        return 1;
    }

    @Override
    public Integer onLocation(final Location l) {
        return 1;
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return 1;
    }
}
