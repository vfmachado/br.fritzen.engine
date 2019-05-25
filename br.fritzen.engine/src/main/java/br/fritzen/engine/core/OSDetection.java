package br.fritzen.engine.core;

/**
 * 
 * Use this class with the only available method @getOs to get an OSType value
 * <ul>
 * <li>Windows</li>
 * <li>MacOS</li>
 * <li>Linux</li>
 * <li>Other - when the system wasn't recognized.</li>
 * </ul>
 * @author fritz
 *
 */
public class OSDetection {

	
	public enum OSType {
	    Windows, MacOS, Linux, Other
	};
	
	
	public static OSType getOS() {
		
		if (detectedOS == null) {
			detectOS();
		}
		
		return detectedOS;
	}
	
	  
	private static String OS = System.getProperty("os.name").toLowerCase();

	private static OSType detectedOS = null;;
	
	
    private static void detectOS() {
    
    	if (isWindows()) {
        	detectedOS = OSType.Windows;
        } else if (isMac()) {
        	detectedOS = OSType.MacOS;
        } else if (isUnix()) {
        	detectedOS = OSType.Linux;
        } else {
        	detectedOS = OSType.Other;
        }
    	
    }

    
    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    
    private static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    
    private static boolean isUnix() {
        return (OS.indexOf("nux") >= 0);
    }
	
	
}
