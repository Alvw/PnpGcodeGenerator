package com.biorecorder.mountsmd;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GCodeGenerator {

    public void generageGCode() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("mount_smd_top.csv"));
        List<ComponentsPack> componentsPackList = new ArrayList<ComponentsPack>();
        List<String> gCode = new ArrayList<String>();
        ComponentsPack componentsPack = null;
        try {
            String line = br.readLine();
            while (line != null) {

                line = br.readLine();
                if(line!=null){
//                    String[] parts = line.split("\\s+");
                    String[] parts = line.split(",");
                    if(parts.length == 0){
                        continue;
                    }else if("".equals(parts[0])){
                        continue;
                    }else if("rem".equals(parts[0])){
                        continue;
                    }else if("line0".equals(parts[0])){
                        componentsPack = new ComponentsPack();
                        componentsPack.step = Double.valueOf(parts[4]);
                        componentsPack.height = Double.valueOf(parts[5]);
                        componentsPack.feederHeight = Double.valueOf(parts[6]);
                        componentsPack.offset = Integer.valueOf(parts[7 ]);
                        componentsPackList.add(componentsPack);
                        componentsPack.lineList.add(getComponentsLine(parts));
                    }else if("line1".equals(parts[0])){
                        componentsPack.lineList.add(getComponentsLine(parts));
                    }else if("line2".equals(parts[0])){
                        componentsPack.lineList.add(getComponentsLine(parts));
                    }else if("line3".equals(parts[0])){
                        componentsPack.lineList.add(getComponentsLine(parts));
                    }else {
                        Component component = new Component();
                        component.name = parts[0];
                        component.x = Double.valueOf(parts[1]);
                        component.y = Double.valueOf(parts[2]);
                        component.angle = Integer.valueOf(parts[3]);
                        component.value = parts[4];
                        component.type = parts[5];
                        componentsPack.componentList.add(component);
                    }
                }
            }
        } finally {
            br.close();
        }
        for (ComponentsPack pack : componentsPackList) {
            List<String> packGcode = pack.generateGCode(getPcbListAngle0(false));
            gCode.addAll(packGcode);
        }
        FileUtils.saveToFile("top1.nc",gCode);
        System.out.printf("tralivali");
    }
    private ComponentsLine getComponentsLine(String[] lineString){
        ComponentsLine componentsLine = new ComponentsLine();
        componentsLine.x0 = Double.valueOf(lineString[1]);
        componentsLine.y0 = Double.valueOf(lineString[2]);
        componentsLine.lineSize = Integer.valueOf(lineString[3]);
        return componentsLine;
    }

    private List<PcbPosition> getPcbListAngle0(boolean isBottomSide){
        List<PcbPosition> pcbList = new ArrayList<PcbPosition>();
        pcbList.add(new PcbPosition(20,25,0,isBottomSide));
        pcbList.add(new PcbPosition(20,80,0,isBottomSide));
        pcbList.add(new PcbPosition(20,135,0,isBottomSide));
        return pcbList;
    }

    private List<PcbPosition> getPcbListAngle90(boolean isBottomSide){
        List<PcbPosition> pcbList = new ArrayList<PcbPosition>();
        pcbList.add(new PcbPosition(25,20,90,isBottomSide));
        pcbList.add(new PcbPosition(25,75,90,isBottomSide));
        pcbList.add(new PcbPosition(25,130,90,isBottomSide));
        return pcbList;
    }



}
