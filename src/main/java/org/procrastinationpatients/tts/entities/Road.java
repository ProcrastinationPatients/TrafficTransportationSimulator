package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;

/**
 * 南北走向
 * Created by decker on 15-1-28.
 */
public class Road extends Link {


    public Road(Integer id) {
        super(id);
    }

    @Override
    protected void refreshLaneLength() {
        if(this.getA()==null||this.getB()==null)
        {
            return;
        }
        Point2D positionA = this.getA().getPosition();
        Point2D positionB = this.getB().getPosition();
        Line centerLine = new Line(positionA.getX(), positionA.getY() + 60, positionB.getX(), positionB.getY() + 60);
        Double d = Math.sqrt(Math.pow(Math.abs(centerLine.getStartX() - centerLine.getEndX()), 2) + Math.pow(Math.abs(centerLine.getStartY() - centerLine.getEndY()), 2)) / StaticConfig.LANE_POINT_SKIP_DISTANCE;
        Integer items = (int) Math.round(d);
        for (Lane lane : this.getLanes()) {
            lane.setLength(items);
        }
    }


    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        Point2D positionA = this.getA().getPosition();
        Point2D positionB = this.getB().getPosition();

        Point2D[] a = new Point2D[7];
        Point2D[] b = new Point2D[7];

        a[0] = new Point2D(positionA.getX() - 30D, positionA.getY() - 60D);
        a[1] = new Point2D(positionA.getX() - 20D, positionA.getY() - 60D);
        a[2] = new Point2D(positionA.getX() - 10D, positionA.getY() - 60D);
        a[3] = new Point2D(positionA.getX() - 0D, positionA.getY() - 60D);
        a[4] = new Point2D(positionA.getX() + 10D, positionA.getY() - 60D);
        a[5] = new Point2D(positionA.getX() + 20D, positionA.getY() - 60D);
        a[6] = new Point2D(positionA.getX() + 30D, positionA.getY() - 60D);

        b[0] = new Point2D(positionB.getX() - 30D, positionB.getY() + 60D);
        b[1] = new Point2D(positionB.getX() - 20D, positionB.getY() + 60D);
        b[2] = new Point2D(positionB.getX() - 10D, positionB.getY() + 60D);
        b[3] = new Point2D(positionB.getX() - 0D, positionB.getY() + 60D);
        b[4] = new Point2D(positionB.getX() + 10D, positionB.getY() + 60D);
        b[5] = new Point2D(positionB.getX() + 20D, positionB.getY() + 60D);
        b[6] = new Point2D(positionB.getX() + 30D, positionB.getY() + 60D);

        DrawUtils.drawLine(gc, a[0], a[6], Color.BROWN, 5);
        DrawUtils.drawLine(gc, b[0], b[6], Color.BROWN, 5);

        for (int i = 0; i < 7; i++) {
            DrawUtils.drawLine(gc, a[i], b[i], Color.BLACK, 2);
        }

        DrawUtils.drawText(gc, new Point2D(a[0].getX() - 40D, a[0].getY()), Color.BLUE, "A" + this.getId());
        DrawUtils.drawText(gc, new Point2D(b[0].getX() - 40D, b[0].getY()), Color.GREEN, "B" + this.getId());
        return;
    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {
        Point2D positionA = this.getA().getPosition();
        Point2D positionB = this.getB().getPosition();
        Double distX=positionA.getX() - positionB.getX();
        Double distY=positionA.getY() - positionB.getY();
        Line leftLine = new Line(positionA.getX()-30D, positionA.getY() + 60D, positionB.getX()-30D, positionB.getY() - 60D);
        Double lineLength= Math.sqrt(Math.pow(Math.abs(leftLine.getStartX() - leftLine.getEndX()), 2) + Math.pow(Math.abs(leftLine.getStartY() - leftLine.getEndY()), 2));
        Double perc=StaticConfig.LANE_POINT_SKIP_DISTANCE/lineLength;
        for (int i=0;i<this.getLanes().length;i++)
        {
            Lane lane=this.getLanes()[i];
            for(int j=0;j<lane.getVehicles().length;j++)
            {
                    if(lane.getVehicles()[j]==null)
                    {
                        DrawUtils.drawBallAtCoordinate(gc,new Point2D(leftLine.getStartX()+distX*perc*j,leftLine.getStartY()+distY*perc*j),4,Color.YELLOW);
                    }
            }
        }
    }

    public Dot getNorthDot() {
        return getA();
    }

    public void setNorthDot(Dot northDot) {
        this.setA(northDot);
    }

    public Dot getSouthDot() {
        return getB();
    }

    public void setSouthDot(Dot southDot) {
        this.setB(southDot);
    }

}
