package custom;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.*;

import com.google.gson.*;

// Helper class
class CallerClassGetter extends SecurityManager
{
   private static final CallerClassGetter INSTANCE = new CallerClassGetter();
   private CallerClassGetter() {}

   public static Class<?> getCallerClass() {CG.log("IN1 - 5439474383: { row: 19 }");
       return INSTANCE.getClassContext()[1];
   CG.log("OUT5 - 5439474383: { row: 21 }");}
   
   public static int getLineNumber(){CG.log("IN1 - 5435650133: { row: 23 }");
	   return new Throwable().getStackTrace()[0].getLineNumber();
   CG.log("OUT5 - 5435650133: { row: 25 }");}
}

public class CodeGenLog {
	
	private static final String LOG_PATH = "\\log\\";
	private static final String LOG_EXTENSION = ".log";
	private static final String JSON_EXTENSION = ".json";
	private static FileHandler fileHandler;
	private static Logger logger;
	private static ConsoleHandler handler = new ConsoleHandler();
	private static Gson gson;
	private static ArrayList<String> filePathList = new ArrayList<>();
	private static int lineNumber = 0;
	//Init anonymous class SimpleFormatter
	private static SimpleFormatter customSimpleFormatter;
	private static String prefix = "";
	private static int in = 0;
	private static int out = 0;
	
	private static String currentMethod = "";
	private static String preiodMethod = "";
	static {
		customSimpleFormatter = new SimpleFormatter() {
			private static final String format = "%s\n";

			@Override
			public String format(LogRecord record) {
				return String.format(format, record.getMessage());
			}
		};
		//Create Custom Serializer for Exception class
		class ExceptionSerializer implements JsonSerializer<Exception>
		{
		  @Override
		  public JsonElement serialize(Exception src, Type typeOfSrc, JsonSerializationContext context)
		  {
		    JsonObject jsonObject = new JsonObject();
		    jsonObject.add("stacktrace", new JsonPrimitive(getStackTrace(src)));
		    return jsonObject;
		  }
		}
		
		handler.setFormatter(customSimpleFormatter);
		
		//Init GsonBuiler
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeHierarchyAdapter(Exception.class, new ExceptionSerializer());
	    gson = gsonBuilder.create();
	}

	public CodeGenLog() {
		
	}
	
	public static void logIn () {
		in++;
		out = in;
		prefix = "IN" + in + " - ";
		doLog(getCallerClassName(), "", LOG_EXTENSION);
	}
	
	public static void logOut () {
		prefix = "OUT" + out +" - ";
		out--;
		doLog(getCallerClassName(), "", LOG_EXTENSION);
	}
	
	public static void log(String message) {
		prefix = "";
		doLog(getCallerClassName(), message, LOG_EXTENSION);
	}
	/**
	 * Log a message with specific path and log file
	 * 
	 * @param filePath
	 * @param className
	 * @param message
	 */
	public static void log(String className, String message) {
		className = getCallerClassName();
		prefix = "";
		doLog(className, message, LOG_EXTENSION);
	}

	/**
	 * Log a message with specific path and log file
	 * 
	 * @param filePath
	 * @param className
	 * @param message
	 */
	public static void log(String className, Object message) {
		prefix = "";
		try {
			String json = gson.toJson(message, message.getClass());
			doLog(className, json, LOG_EXTENSION);
		}catch(JsonIOException e) {
			if (message instanceof Exception) {
				Exception ex = Exception.class.cast(message);
			    String json = gson.toJson(ex, ex.getClass());
				doLog(className, json, LOG_EXTENSION);
			}
		}
	}
	
	public static void jsonLog(String fileName, Object object) {
		prefix = "";
		String className = getCallerClassName();
		try {
			String json = gson.toJson(object, object.getClass());
			doLog(className + "." + fileName, json, JSON_EXTENSION);
		}catch(JsonIOException e) {
			if (object instanceof Exception) {
				Exception ex = Exception.class.cast(object);
			    String json = gson.toJson(ex, ex.getClass());
				doLog(className + "." + fileName, json, JSON_EXTENSION);
			}
		}
	}
	
	public static void jsonLog(String className, String fileName, Object object) {
		prefix = "";
		try {
			String json = gson.toJson(object, object.getClass());
			doLog(className + "." + fileName, json, JSON_EXTENSION);
		}catch(JsonIOException e) {
			if (object instanceof Exception) {
				Exception ex = Exception.class.cast(object);
			    String json = gson.toJson(ex, ex.getClass());
				doLog(className + "." + fileName, json, JSON_EXTENSION);
			}
		}
	}
	
	public static String getCallerClassName() { 
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(CodeGenLog.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
            	lineNumber = ste.getLineNumber();
        		currentMethod = ste.getMethodName();
                return ste.getClassName();
            }
        }
        return null;
     }
	/**
	 * Check if current log is valid then log to file with name filePath
	 * 
	 * @param className
	 * @param message
	 */
	private static void doLog(String className, String message, String extension) {
		//Get logger by name and configure file handler
		logger = Logger.getLogger(className);
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
		if (isValidPath(className)) {
			File file = createNewFileLog(className, extension);
			
			// Add simple formatter
			fileHandler.setFormatter(customSimpleFormatter);
			if (file == null) {
				logger.removeHandler(handler);
				return;
			}
		}else {
			Logger.getLogger(CodeGenLog.class.getName()).log(Level.SEVERE, "Path is not valid or not exist");
		}
		try {
			// Process log by logger
			if (!currentMethod.isEmpty() && currentMethod.compareTo(preiodMethod) != 0) {
				logger.log(Level.INFO, "[" + currentMethod + "]");
				preiodMethod = currentMethod;
			}
			logger.log(Level.INFO, message);
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			//Remove file handler after using
			logger.removeHandler(handler);
		}
	}

	/**
	 * Create new file if does not exist
	 * 
	 * @param filePath
	 * @return File
	 */
	private static File createNewFileLog(String className, String extension) {
		String pathClass = createPathClassLog(className);
		File newFile;
		//Convert name pattern from "com.fsoft" to "com\fsoft"
		className = className.replaceAll("\\.+", "\\\\");
		
		//Get last 
		String[] splitPath = className.split("\\\\+");
		if (splitPath.length > 1)
			className = pathClass + "\\" + splitPath[splitPath.length - 1] + extension;
		else
			className = getLogFolderPath() + splitPath[splitPath.length - 1] + extension;
		newFile = new File(className);
		try {
			//If file is not exist
			//Then create new file in system and add handler to logger
			if (!newFile.exists()) {
				newFile.createNewFile();
				filePathList.add(newFile.getPath());
				fileHandler = new FileHandler(newFile.getPath());
				logger.addHandler(fileHandler);
			}else{
				if (!filePathList.contains(newFile.getPath())){
					filePathList.add(newFile.getPath());
					fileHandler = new FileHandler(newFile.getPath());
					logger.addHandler(fileHandler);
				}
			}
		} catch (IOException e) {
			return null;
		} catch (SecurityException e1) {
			Logger.getLogger(CodeGenLog.class.getName())
			.log(Level.SEVERE, "Can not create new file!", e1);
			return null;
		}

		return new File(getLogFolderPath() + className);
	}

	/**
	 * Create folder "log" in project.
	 * 
	 * @return String
	 */
	private static String createLogFolder() {
		String rootPath = getRootProject();

		File logFile = new File(rootPath + LOG_PATH);
		if (!logFile.exists()) {
			logFile.mkdir();
		}

		return logFile.getPath();
	}

	/**
	 * <p>Create folder follow by  specific path
	 * Example: \com\fsoft\Main                  
	 * 			com.fsoft.Main.java => \com\fsoft\Main   
	 * 			/com/fsoft/Main
	 * </p>
	 * @param logPath
	 * @return
	 */
	private static String createPathClassLog(String logPath) {
		if (!isValidPath(logPath)) {
			Logger.getLogger(CodeGenLog.class.getName())
			.log(Level.SEVERE, "Path is not valid");
			return null;
		}
		String logFolder = getLogFolderPath();
		String tmpLogPath = logPath;
		
		if (logPath.split("\\.").length > 1){
			logPath = logPath.replaceAll("\\.\\w+$", "");
		}
		logPath = logPath.replaceAll("\\.+", "\\\\").replaceAll("\\\\+", "\\\\");
		
		
		File file = new File(logFolder + logPath);
		
		if (tmpLogPath.split("\\.").length > 1){
			tmpLogPath = tmpLogPath.replaceAll("\\.\\w+$", "");
			tmpLogPath = tmpLogPath.replaceAll("\\.+", "\\\\").replaceAll("\\\\+", "\\\\");
			file = new File(logFolder + tmpLogPath);
			file.mkdirs();
		}
		return file.getPath();
	}

	/**
	 * Get root path of project
	 * 
	 * @return String
	 */
	private static String getRootProject() {
		File file;
		try {
			file = new File("").getCanonicalFile();
		} catch (IOException e) {
			Logger.getLogger(CodeGenLog.class.getName())
				.log(Level.SEVERE, "Can not get root path project!", e);
			return null;
		}

		return file.getPath();
	}

	/**
	 * Get path of log folder
	 * 
	 * @return String is Log folder path
	 */
	private static String getLogFolderPath() {
		if (getRootProject() == null) {
			return null;
		}
		
		File file = new File(getRootProject() + LOG_PATH);
		if (!file.exists()) {
			createLogFolder();
		}
		
		return new File(getRootProject() + LOG_PATH).getPath() + "\\";
	}
	
	/**
	 * Check path is valid directory or valid file's name
	 * Valid pattern example:
	 * 		C:\a\b\c
	 * 		a\b\c\d
	 * 		a
	 * 		a.b.c.d
	 * @param path
	 * @return true if is valid
	 * 		   false if is not valid
	 */
	private static boolean isValidPath(String path) {
		if (path.matches("([a-zA-Z]:)?(\\\\?[^\\\\\\/:*?\\\"<>|]+)+\\\\?") 
				|| path.matches("\\w+") 
				|| path.matches("(\\w+\\.)*\\w+")) {
			return true;
		} else {
			
			return false;
		}
	}
	/**
	 * Convert stack trace to string
	 * 
	 * @param throwable
	 * @return
	 */
	private static String getStackTrace(final Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return String.format(sw.getBuffer().toString());
	}
}

