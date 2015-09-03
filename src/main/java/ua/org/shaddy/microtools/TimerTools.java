package ua.org.shaddy.microtools;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTools {
	public static TimerTask setTimeout(final Runnable task,long delay){
		TimerTask timerTask = new TimerTask(){
			public void run(){
				task.run();
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, delay);
		return timerTask;
	}
	
	public static TimerTask setInterval(final Runnable task,long delay){
		TimerTask timerTask = new TimerTask(){
			public void run(){
				task.run();
			}
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, delay, delay);
		return timerTask;
	}
	
	public static void clearTimeout(TimerTask timerTask){
		timerTask.cancel();
	}
	
	public static void clearInterval(TimerTask timerTask){
		timerTask.cancel();
	}
}
