package tool;

import org.apache.log4j.Logger;

/**
 * 日志处理工具类
 * @author content
 * @version 1.0
 * create at 2012-5-11
 */

public class 日志工具 {
	public static Logger fileLog = Logger.getLogger("fileLog");
	public static Logger fileErr = Logger.getLogger("fileErr");
	public static Logger fileTrack = Logger.getLogger("fileTrack");
	public static Logger fileAlert = Logger.getLogger("fileAlert");
}
