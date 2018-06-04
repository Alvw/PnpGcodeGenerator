package com.biorecorder.mountsmd;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {
    public static void saveToFile(String filename,List<String> text){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter( filename));
            writer.write(Settings.gCodeInitLines);
            for (String segment : text) {
                writer.write(segment);
            }
            writer.write(Settings.gCodeFinishLines);

        }
        catch ( IOException e){
            System.out.println(e);
        }
        finally{
            try{
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e){
                System.out.println(e);
            }
        }
    }

}
