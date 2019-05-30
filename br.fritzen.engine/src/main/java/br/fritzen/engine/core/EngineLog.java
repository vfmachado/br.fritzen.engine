package br.fritzen.engine.core;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Non instatiable class
 * 
 * Used to easily log info / warnings in console or File
 * 
 * To log to a file use EngineLog.setOutStream passing a valid FileHandler
 * 
 * @author fritz
 *
 */
public class EngineLog {

	private static final Logger LOG = Logger.getLogger("br.fritzen");
	
	private static EngineLog log = new EngineLog();
	
	public enum LogLevel {
		INFO, WARNING, SEVERE;
	}
	
	private class Data {
	
		String filename;
		String methodName;
		int line;
		
		public Data(StackTraceElement stack) {
			filename = stack.getFileName();
			line = stack.getLineNumber();
			methodName = stack.getMethodName();
		}
	}
	
	
	private EngineLog() {
		
		LOG.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(getFormatter());
        LOG.addHandler(handler);
	}
	
	
	private static SimpleFormatter getFormatter() {
		
		SimpleFormatter formatter = new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        };
        
        return formatter;
	}
	
	
	private static final EngineLog getInstance() {
		return log;
	}
	
	
	public static final void setOutStream(FileHandler fileHandler) {
		
		LOG.setUseParentHandlers(false);
		fileHandler.setFormatter(getFormatter());
		LOG.addHandler(fileHandler);
	
	}
	
	
	public static final void setSeverity(LogLevel level) {
		LOG.setLevel(Level.parse(level.name()));
	}
	
	
	private final void log(String msg, String logLevel) {
		
		Data data = new Data(new Exception().getStackTrace()[2]);
		String outlog = "File: " + data.filename + ":" + data.line + "\tMethod: " + data.methodName  + "\tMsg: " + msg;
		
		if (logLevel.equals("INFO"))
			LOG.info(outlog);
		else if (logLevel.equals("WARNING"))
			LOG.warning(outlog);
		else if (logLevel.equals("SEVERE"))
			LOG.severe(outlog);
		
	}
	
	
	/**
	 * Default method for INFO Level Logging with the Fritzen Engine
	 * @param msg - An String with the message
	 */
	public static void info(String msg) {
		getInstance().log(msg, "INFO");
	}

	
	/**
	 * Info for any Object
	 * @param msg
	 */
	public static void info(Object msg) {
		getInstance().log(msg.toString(), "INFO");
	}
	
	
	
	/**
	 * Default method for SEVRE Level Logging with the Fritzen Engine
	 * @param msg - An String with the message
	 */
	public static void severe(String msg) {
		getInstance().log(msg, "SEVERE");
	}
	
	
	/**
	 * Default method for WARNING Level Logging with the Fritzen Engine
	 * @param msg - An String with the message
	 */
	public static void warning(String msg) {
		getInstance().log(msg, "WARNING");
	}


}
