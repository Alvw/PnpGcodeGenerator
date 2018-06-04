package com.biorecorder.mountsmd;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComponentsPack {
    public static int componentsCounter = 0;
    double step;
    double height;
    double feederHeight;
    int offset;
    List<ComponentsLine> lineList = new ArrayList<ComponentsLine>();
    List<Component> componentList = new ArrayList<Component>();
    public List<String> generateGCode(List<PcbPosition> pcbPositionList){
        List<String> stringList = new ArrayList<String>();
        for (PcbPosition pcbPosition : pcbPositionList) {
            for (Component component : componentList) {
                Position from = getComponentFromPosition();
                offset++;
                Position to = getComponenToPosition(component,pcbPosition);
                String log = "from x " + fD(from.x) + " y " + fD(from.y) +
                        "       to x " + fD(to.x) + " y " + fD(to.y);
                System.out.println(log);
                String gCode = getGcodeForSingleComponent(from,to);
                componentsCounter++;
                stringList.add(gCode);
            }
        }
        System.out.println("number of components = " +componentsCounter);
        return stringList;
    }

    private Position getComponentFromPosition(){
       Position position = new Position();
       int lineNum = 0;
       int lineOffset = 0;
        for (int i = 0; i < offset; i++) {
             lineOffset++;
             if(lineList.get(lineNum).lineSize <= lineOffset){
                 lineOffset = 0;
                 lineNum++;
             }
        }
        position.x = lineList.get(lineNum).x0 + 3.5;
        position.y = lineList.get(lineNum).y0 + lineOffset * step;
        position.z = feederHeight + height;
        return position;
    }

    private Position getComponenToPosition(Component component, PcbPosition pcbPosition){
        Position position = new Position();
        if (pcbPosition.isBottomSide){
            if(pcbPosition.angle == 0){
                position.x = pcbPosition.x0 + pcbPosition.xSize - component.x;
                position.y = pcbPosition.y0 + component.y;
            }else if(pcbPosition.angle == 90){
                position.x = pcbPosition.x0 - component.y;
                position.y = pcbPosition.y0 + pcbPosition.xSize - component.x;
            }
        }else if (!pcbPosition.isBottomSide){
            if(pcbPosition.angle == 0){
                position.x = pcbPosition.x0 + component.x;
                position.y = pcbPosition.y0 + component.y;
            }else if(pcbPosition.angle == 90){
                position.x = pcbPosition.x0 + pcbPosition.ySize - component.y;
                position.y = pcbPosition.y0 + component.x;
            }
        }
        position.z = height;
        return position;
    }

    public String getGcodeForSingleComponent(Position from, Position to){

        String result = "x"+fD(from.x)+" y"+fD(from.y)+"\n";
        result += "G01 Z" +fD(from.z)+" F"+Settings.liftSpeed+"\n";
        result += "G00 Z" + Settings.needleLift+"\n";
        result += "x"+fD(to.x)+" y"+fD(to.y)+"\n";
        result += "G01 Z" +fD(to.z)+" F"+Settings.liftSpeed+"\n";
        result += "G00 Z" + Settings.needleLift+"\n\n";
        return result;
    }
    private String fD(double value){
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("##0.000", otherSymbols);
        return decimalFormat.format(value);
    }
}
