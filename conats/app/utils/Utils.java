package utils;


import jats.utfpl.stfpl.ModelGenerater;
import jats.utfpl.utils.FilenameUtils;
import jats.utfpl.utils.SystemEnv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import play.Logger;

public class Utils {
	
	private static String readFromProcess(InputStream is) throws IOException {
		String line = null;
		String lines = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		while ((line = reader.readLine()) != null) {
			lines += line + "\n";
		}
		return lines;
	}
	
	static String getProcessCommand(ProcessBuilder pb) {
		List<String> coms = pb.command();
		String command = "";
		for (String com: coms) {
			command += com + " ";
		}
		
		return command;
	}
	
	public static String typeCheck(String code) {
		try {
        	// Create temporary directory.
        	File tempDir = new File(System.getProperty("java.io.tmpdir", null), "conats" + Long.toString(System.nanoTime()));
            if (!tempDir.exists() && !tempDir.mkdir())
                throw new IOException("Failed to create temporary directory " + tempDir);
            Logger.info("tempDir is " + tempDir.getAbsolutePath());
            
            // Write ATS file.
            File atsFile = new File(tempDir, "model.dats");
            atsFile.createNewFile();  // Impossible that the file already exists.
			Logger.info("ATS file is " + atsFile.getAbsolutePath());
            FileWriter tempWriter = new FileWriter(atsFile.getAbsolutePath());
            BufferedWriter bfWriter = new BufferedWriter(tempWriter);
            bfWriter.write(code);
            bfWriter.close();
            
//            Logger.info("code is " + code);
            
            // Generate list for remote files.
            File listFile = new File(tempDir.getAbsolutePath(), "pdgreloc.json");
        	ProcessBuilder pb = new ProcessBuilder("patsopt", 
        			"--pkgreloc", "-DATS", "ATSPKGRELOCROOT=\"" + tempDir.getAbsolutePath() + "\"",
        			"--output-w", listFile.getAbsolutePath(),
        			"-d", atsFile.getAbsolutePath());

        	Logger.info("cmd is " + getProcessCommand(pb));
        	pb.redirectErrorStream(true);
        	Process child = pb.start();
        	String lines = readFromProcess(child.getInputStream());
        	int returnCode = child.waitFor();
        	Logger.info("returnCode is " + returnCode);
        	if (0 != returnCode) {
        		lines = "\"patsopt --pkgreloc\" failed.\n" + lines;
        		Logger.info(lines);
        		return lines;
        	}
        	
           	// Download remote files.
        	pb = new ProcessBuilder("atspkgreloc_curl", listFile.getAbsolutePath());
        	Logger.info("cmd is " + getProcessCommand(pb));
        	pb.redirectErrorStream(true);
        	child = pb.start();
        	lines = readFromProcess(child.getInputStream());
        	returnCode = child.waitFor();
        	Logger.info("returnCode is " + returnCode);
        	if (0 != returnCode) {
        		lines = "\"atspkgreloc_curl\" failed.\n" + lines;
        		Logger.info(lines);
        		return lines;
        	}
        	
        	// Type check the input file
        	pb = new ProcessBuilder("patsopt", 
        			"-DATS", "ATSPKGRELOCROOT=\"" + tempDir.getAbsolutePath() + "\"",
        			"-tc", "-d", atsFile.getAbsolutePath());
        	Logger.info("cmd is " + getProcessCommand(pb));
        	pb.redirectErrorStream(true);
        	child = pb.start();
    		lines = readFromProcess(child.getInputStream());
        	returnCode = child.waitFor();
        	Logger.info("returnCode is " + returnCode);
        	if (0 != returnCode) {
        		lines = "\"patsopt -tc\" failed.\n" + lines;
        		Logger.info(lines);
        		return lines;
        	}

        	return "Type Checking succeeded.\n" + lines;
 
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
	
	public static String genModel(String code) throws IOException, InterruptedException {
		File temp = File.createTempFile("temp-file-name", ".dats");
		Logger.info("temp is " + temp.getAbsolutePath());
        FileWriter tempWriter = new FileWriter(temp.getAbsolutePath());
        BufferedWriter bfWriter = new BufferedWriter(tempWriter);
        bfWriter.write(code);
        bfWriter.close();
        
    	ModelGenerater mcGen = new ModelGenerater(temp.getAbsolutePath(), null, null);
        mcGen.generate(7);
        return mcGen.getPATModel();
	}
	
	public static class ModelCheckResult {
		public int m_errno;
		public String m_msg;
		public String m_res;
		
		public ModelCheckResult(int errno, String msg, String result) {
			m_errno = errno;
			m_msg = msg;
			m_res = result;
		}
	}

	public static ModelCheckResult modelCheck(String code, int engine) {

		try {
			File file_model = File.createTempFile("temp-file-name", ".csp");
			Logger.info("csp# file is " + file_model.getAbsolutePath());
			FileWriter tempWriter = new FileWriter(file_model.getAbsolutePath());
	        BufferedWriter bfWriter = new BufferedWriter(tempWriter);
	        bfWriter.write(code);
	        bfWriter.close();
	        
	        File file_result = FilenameUtils.changeExt(file_model, FilenameUtils.cTxt);
        	ProcessBuilder pbpat3 = new ProcessBuilder("mono", 
        			SystemEnv.getPATPath(), 
        			"-engine", Integer.toString(engine),
        			"-csp", file_model.getAbsolutePath(), 
        			file_result.getAbsolutePath());

            
        	String cmd = "";
        	for (String s: pbpat3.command()) {
        		cmd += (s + " ");
        	}
        	Logger.info("cmd is " + cmd);
        	pbpat3.redirectErrorStream(true);
        	Process childpat = pbpat3.start();
        	
    		String msg = readFromProcess(childpat.getInputStream());
        	int returnCode = childpat.waitFor();
        	Logger.info("returnCode is " + returnCode + " msg is " + msg);
        	if (0 == returnCode) {  // returnCode is useless
        		Scanner scan = new Scanner(file_result).useDelimiter("\\Z");
        		String result = null;
        		if (scan.hasNext()) {
        			result = scan.next();
        		}
        		if (msg.startsWith("PAT finished successfully.")) {
        			return new ModelCheckResult(0, msg, result);
        		} else {
        			return new ModelCheckResult(1, msg, result);
        		}
        		
        	} else {
        		return new ModelCheckResult(1, msg, "");
        	}
		} catch (IOException e) {
			return new ModelCheckResult(1, e.getMessage(), null);
		} catch (InterruptedException e) {
			return new ModelCheckResult(1, e.getMessage(), null);
		}
		


		
	}

}












