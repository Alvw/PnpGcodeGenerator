package com.biorecorder.mountsmd;

public class PcbPosition {
    double xSize = 47.6;
    double ySize = 32;
    double x0 = 20;
    double y0 = 25;//80, 135
    int angle = 0;
    boolean isBottomSide = true;

    public PcbPosition(double x0, double y0, int angle, boolean isBottomSide) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.x0 = x0;
        this.y0 = y0;
        this.angle = angle;
        this.isBottomSide = isBottomSide;
    }
}
