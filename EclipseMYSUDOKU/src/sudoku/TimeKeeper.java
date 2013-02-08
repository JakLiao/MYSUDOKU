package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
*
* @author Jak
*/
/**
 * ����ʱ��
 */
public class TimeKeeper extends JPanel{
	private static final long serialVersionUID = 1L;
	private int second = 0;//��ʼ��ʱ��Ϊ0
	private boolean start = false;//�ж�ʼ���Ƿ�ʼ
	
	Timer timer = new Timer(1000, new TimerListener());//����һ��ʱ���̣߳�ʹ��ʱ����ÿ�붯һ��
	
	public TimeKeeper(){
		setFont(new Font("Dialog", Font.ITALIC, 20));//�޸�ʱ����ʾ������
	}
	
	public void paintComponent(Graphics g){//��ͼ����
		super.paintComponent(g);//���ø��๹�캯�����г�ʼ��
		if(start) second++;//ÿ���ػ�ʱ�Ӻ�ʱ���һ����
		String output;
		String hour=new String("");//�����������������ڽ��յ�ǰϵͳʱ��
		String minute=new String("");
		String second1=new String("");
        if(start == true){//����һ��if-else������ڿ���ʱ����ʾ�ĸ�ʽ��ʹ�ø�λ��������λ����
        	if((second/3600)<10)
       	     	hour="0";
        	if((second%3600/60)<10)
           	 	minute="0";
        	if((second%60)<10)
        		second1="0";
        	output =hour+ (second / 3600) + ":" +minute+ (second % 3600 / 60) + ":" +second1+ (second % 60);
        }
        else output = "00:00:00";
        g.drawString(output,0,200);//����Ӧλ�û���ʱ����
	}
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();//�ػ�ʱ��
		}
	}
	/**
	 * �����Ϸ��ʱ��
	 * @return ��Ϸ��ʱ��
	 */
	int getSecond(){
		return second;
	}
	/**
	 * �����ı���Ϸ��ʱ��
	 * @param n �������ı��ʱ��
	 */
	public void setSecond(int second){
		this.second = second;
	}
	/**��ʱ��ʼ*/
	void start(){
    	start = true;
		if(start){
			timer.stop();//ʱ����Ϊ0
		    timer.start();
		}
	}
	/**��ʱ��ͣ*/
    void pause(){
		if(start){
			timer.stop();
		}
	}
    /**��ʱ����*/
    void stop(){
		if(start){
			timer.stop();
			second = 0;
			start = false;
			repaint();//�ػ�ʱ����ʾ"00:00:00"
		}
	}
}