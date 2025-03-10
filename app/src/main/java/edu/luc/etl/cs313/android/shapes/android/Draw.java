package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

import java.util.List;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {

        return null;
    }

    @Override
    public Void onFill(final Fill f) {

        return null;
    }

    @Override
    public Void onGroup(final Group g) {

        return null;
    }

    @Override
    public Void onLocation(final Location l) {

        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {

        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        List<? extends Point> pointList = s.getPoints();
        Point firstpoint = pointList.get(0);
        //grab first point to repeat it to fill in the last line.
        float[] Coords = new float[2*pointList.size()+2];
        for(int i = 0; i < pointList.size(); i++){
            Coords[i] = pointList.get(i).getX();
            Coords[i+1]=pointList.get(i).getY();
        }
        Coords[Coords.length-2] = firstpoint.getX();
        Coords[Coords.length-1] = firstpoint.getY();
        //naive attempt above.
        final float[] pts = Coords;

        canvas.drawLines(pts, paint);
        return null;
    }
}
