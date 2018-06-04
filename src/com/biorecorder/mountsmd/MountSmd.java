package com.biorecorder.mountsmd;

import java.io.IOException;

public class MountSmd {
    public static void main(String[] args) throws IOException {
        GCodeGenerator gCodeGenerator = new GCodeGenerator();
        gCodeGenerator.generageGCode();
    }
}
