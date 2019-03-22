package br.fritzen.engine.core;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EngineLog {

	private static final Logger LOG = Logger.getLogger("br.fritzen");
	
	private static EngineLog log = new EngineLog();
	
	
	private class Data{
	
		String filename;
		//String className;
		String methodName;
		int line;
		
		public Data(StackTraceElement stack) {
			filename = stack.getFileName();
			line = stack.getLineNumber();
			//className = stack.getClassName();
			methodName = stack.getMethodName();
		}
	}
	
	
	private EngineLog() {
		
		LOG.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });
        LOG.addHandler(handler);
	}
	
	
	private static final EngineLog getInstance() {
		return log;
	}
	
	
	private final void log(String msg, String logLevel) {
		
		Data data = new Data(new Exception().getStackTrace()[2]);
		String outlog = "File: " + data.filename + ":" + data.line + "\tMethod: " + data.methodName  + "\tMsg: " + msg;
		
		if (logLevel.equals("INFO"))
			LOG.info(outlog);
		else if (logLevel.equals("SEVERE"))
			LOG.severe(outlog);
		else if (logLevel.equals("WARNING"))
			LOG.warning(outlog);
	}
	
	
	/**
	 * Default method for INFO Level Logging with the Fritzen Engine
	 * @param msg - An String with the message
	 */
	public static void info(String msg) {
		getInstance().log(msg, "INFO");
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
