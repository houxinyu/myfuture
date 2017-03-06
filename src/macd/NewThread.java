package macd;

import java.io.File;

import macd.MyMACD;
import macd.ReverseGapAlert;
import tool.日志工具;
import tool.时间工具;
import tool.配置文件;

public class NewThread implements Runnable {
	
    public enum ThreadType  
    {  
        异步加载线程, 异步合并线程,反向跳空线程,重启线程;
    } 
	private ThreadType threadType;
	public NewThread(ThreadType type){
		threadType=type;
	}

	public void run() {
		// TODO Auto-generated method stub
		switch (threadType){
		case 异步加载线程:
			加载数据线程();
			break;
		case 异步合并线程:
			合并数据线程();
			break;
		case 反向跳空线程:
			反向跳空线程();
			break;
		case 重启线程:
			重启程序();
			break;
		default:
				;
		}
		
	}
	

	private void 加载数据线程(){
		while(true){
			long start=时间工具.获得现在时间();
//			DataHandle.加载当天大富翁数据(true);
			DataHandle.异步导入分时数据();
			long end=时间工具.获得现在时间();
//			System.out.println("异步加载线程:"+时间工具.耗时毫秒(start, end));
//			时间工具.休眠秒数(1);
			时间工具.休眠毫秒数(200);
		}
	}
	
	private void 合并数据线程(){
		while(true){
			long start=时间工具.获得现在时间();
			DataHandle.分钟转多个时间合并();
//			时间工具.休眠秒数(1);
			long end=时间工具.获得现在时间();
//			System.out.println("合并数据线程:"+时间工具.耗时毫秒(start, end));
			时间工具.休眠毫秒数(200);
			
		}
	}

	
	private void 反向跳空线程(){
		while(true){
			try {
				ReverseGapAlert.检查反向跳空预警();
//				System.out.println("预警计算线程:"+时间工具.耗时毫秒(start, end));
				时间工具.休眠秒数(1);
			} catch (Exception e) {
				// TODO: handle exception
				日志工具.fileErr.error(e, e);
			}

			
		}
	}
	
	
	private void 重启程序(){
		
		while(true){
			int hour=时间工具.获得现在小时();
			int week=时间工具.获得今天星期几();
			int min=Integer.valueOf(时间工具.取的格式化时间("mm"));
			String[] resTime=配置文件.获取配置项("restart_time").split(":");
			if(hour==Integer.valueOf(resTime[0])&&min==Integer.valueOf(resTime[1])&&week!=0&&week!=6){
				日志工具.fileLog.info("程序1分钟后重启...");
				时间工具.休眠秒数(60);
				String cmd = "cmd /c start "+配置文件.获取配置项("run_path");// pass
		        try {
		        	Runtime.getRuntime().exec(cmd);
//		            Process ps = Runtime.getRuntime().exec(cmd);
//		            ps.waitFor();
		        } catch (Exception ioe) {
		            日志工具.fileErr.error(ioe,ioe);
		        } 
				System.exit(0);
			}

			if(hour==0&&min==1&&week!=6&&week!=0){
				日志工具.fileLog.info("1分钟后触发log4j日志备份");
				时间工具.休眠秒数(60);
				日志工具.fileTrack.info("备份日志==============================="+时间工具.取的格式化时间("yyyyMMddhhmmss"));
			}
			时间工具.休眠秒数(10);
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
