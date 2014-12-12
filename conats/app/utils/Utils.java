package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import play.Logger;

public class Utils {
	
	public static String typeCheck(String code) {
		try {
			File temp = File.createTempFile("temp-file-name", ".dats");
			Logger.info("temp is " + temp.getAbsolutePath());
            FileWriter tempWriter = new FileWriter(temp.getAbsolutePath());
            BufferedWriter bfWriter = new BufferedWriter(tempWriter);
            bfWriter.write(code);
            bfWriter.close();
            
            Logger.info("code is " + code);
			
        	ProcessBuilder pb = new ProcessBuilder("patsopt", "-tc", "-d", temp.getAbsolutePath());
        	pb.redirectErrorStream(true);
        	Process child = pb.start();
        	int returnCode = child.waitFor();
        	if (0 == returnCode) {
        		return "Type Checking succeeded.";
        	}
    		String line = "";
    		String lines = "";
    		BufferedReader reader = new BufferedReader(new InputStreamReader(child.getInputStream()));
    		while ((line = reader.readLine()) != null) {
    			lines += (line + "\n");
    		}
    		Logger.info("lines is " + lines);
    		return lines;      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "Type Checking failed.";
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "Type Checking failed.";
		}
		
	}

}
