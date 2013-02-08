package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
*
* @author Jak
*/
/**
 * 设置时间
 */
public class TimeKeeper extends JPanel{
	private static final long serialVersionUID = 1L;
	private int second = 0;//初始化时间为0
	private boolean start = false;//判断始终是否开始
	
	Timer timer = new Timer(1000, new TimerListener());//创建一个时钟线程，使得时钟能每秒动一次
	
	public TimeKeeper(){
		setFont(new Font("Dialog", Font.ITALIC, 20));//修改时间显示的字体
	}
	
	public void paintComponent(Graphics g){//画图函数
		super.paintComponent(g);//调用父类构造函数进行初始化
		if(start) second++;//每次重绘时钟后时间加一秒钟
		String output;
		String hour=new String("");//定义三个变量，用于接收当前系统时间
		String minute=new String("");
		String second1=new String("");
        if(start == true){//下面一串if-else语句用于控制时钟显示的格式，使得个位数能以两位出现
        	if((second/3600)<10)
       	     	hour="0";
        	if((second%3600/60)<10)
           	 	minute="0";
        	if((second%60)<10)
        		second1="0";
        	output =hour+ (second / 3600) + ":" +minute+ (second % 3600 / 60) + ":" +second1+ (second % 60);
        }
        else output = "00:00:00";
        g.drawString(output,0,200);//在相应位置画出时分秒
	}
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();//重绘时钟
		}
	}
	/**
	 * 获得游戏的时间
	 * @return 游戏的时间
	 */
	int getSecond(){
		return second;
	}
	/**
	 * 用来改变游戏的时间
	 * @param n ：期望改变的时间
	 */
	public void setSecond(int second){
		this.second = second;
	}
	/**计时开始*/
	void start(){
    	start = true;
		if(start){
			timer.stop();//时间设为0
		    timer.start();
		}
	}
	/**计时暂停*/
    void pause(){
		if(start){
			timer.stop();
		}
	}
    /**计时结束*/
    void stop(){
		if(start){
			timer.stop();
			second = 0;
			start = false;
			repaint();//重绘时钟显示"00:00:00"
		}
	}
}