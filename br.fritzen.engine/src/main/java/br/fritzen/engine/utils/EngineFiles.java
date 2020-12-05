package br.fritzen.engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;

public class EngineFiles {

	/**
	 * Load the file content in a String and return the value.
	 * 
	 * @param filename - the path related to resources folder
	 * @return String with the file content
	 * @throws IOException if anny errors occurs to open or read the file
	 */
	public static String loadTextFile(final String filename) throws IOException {
		
		long time = System.currentTimeMillis();

		File file = new File(EngineFiles.class.getClassLoader().getResource(filename).getFile());
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line;
		int lines = 0;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
			lines++;
		}
		
		reader.close();
		time = System.currentTimeMillis() - time;
		
		if (EngineState.DEBUG_FILE_UTILS) { 
			EngineLog.info("The file " + filename + " was loaded in " + time + "ms. Total of #" + lines + " lines.");
		}
		
		return sb.toString();
		
	}
	
}
