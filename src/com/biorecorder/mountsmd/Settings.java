package com.biorecorder.mountsmd;

public class Settings {
    static final double  needleLift = 3.0; // высота подъема иглы над платой
    static final double liftSpeed = 300.0; //скорость опускания иглы
    static final String gCodeInitLines = "G00 G49 G40.1 G17 G80 G50 G90\nG21\n"+"G00 Z"+Settings.needleLift+"\n";
    static final String gCodeFinishLines = "M5 M9\n" + "M30\n";

}
//        x2 y56.5
//        G01 Z1.0000  F300.0
//        G00 Z3.0000
//        x5 y10
//        G01 Z0.5000  F300.0
//        G00 Z3.0000
