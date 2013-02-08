package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
/**
*
* @author Jak
*/
/**
 * ��Ϸ����
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private int lvl = 2;//�Ѷ��趨
    boolean DIYon;//�Ƿ�����������Ŀģʽ
    boolean finish;//�Ƿ����
    boolean start = false;//�Ƿ�ʼ��ʱ
    private int z;//��������ֵ������ʱ����
    private int i = 0, j = 0;
    
    JButton[] numbers = new JButton[9];
    JButton[][] bords = new JButton[20][20];//������ʾ�û����������
    JFrame subFrame = new JFrame();//����ѡ��ĸ�����
    
    private int[][] form1 = new int[20][20];//����������Ӧ������
    private int[][] form2 = new int[20][20];//��������form1��������ж�
    private int[][] form3 = new int[20][20];//�ٶ���һ��form3����������Ƿ�Ψһ
    private int[][] form4 = new int[20][20];//��������form1 �����ݣ��������¿�ʼ�˵�
    
    Initialize init = new Initialize();//��ʼ����Ŀ
    Dancing_link DL = new Dancing_link();//����
    TimeKeeper time = new TimeKeeper();//����һ����ʱ��
    Sava sav = new Sava();//�����ĵ�
    Read lod = new Read();//��ȡ�ĵ�
    Check_puzzle ck = new Check_puzzle();//����������������
    /**
	 * �Ӳ˵�
	 */
    JMenuItem newGame = new JMenuItem("QUICK PLAY"),
            restart = new JMenuItem("PLAY AGAIN"),
            diy = new JMenuItem("DIYPuzzle"),
            playgame = new JMenuItem("PLAY GAME"),
            savegame = new JMenuItem("SAVE"),
            loadgame = new JMenuItem("LOAD"),
            answer = new JMenuItem("Answer"),
            exit = new JMenuItem("EXIT"),
            help = new JMenuItem("Help"),
            info = new JMenuItem("Info");
    /**
     * �˵���
     */
    JMenu file = new JMenu("File"),
            game = new JMenu("Game"),
            difficulty = new JMenu("Difficulty"),
            about = new JMenu("About");
    /**
	 * ��ѡ�˵���
	 */
    JCheckBoxMenuItem easy = new JCheckBoxMenuItem("EASY"),
            normal = new JCheckBoxMenuItem("NORMAL", true),
            hard = new JCheckBoxMenuItem("HARD");
    /**
     *  �˵���
     */
    JMenuBar bar = new JMenuBar();
    /**
	 * ����һ���˵���
	 */
    public void createMenu() {
    	//��Ӳ˵�
        bar.add(file);
        bar.add(game);
        bar.add(about);
        //���"FILE����ѡ��
        file.add(newGame);
        file.add(playgame);
        file.add(restart);
        file.addSeparator();
        file.add(diy);
        file.addSeparator();
        file.add(exit);
        //��ӡ�GAME"��ѡ��
        game.add(loadgame);
        game.add(savegame);
        game.addSeparator();
        game.add(answer);
        game.add(difficulty);
        // ���"Difficulty"�Ӳ˵�
        difficulty.add(easy);
        difficulty.add(normal);
        difficulty.add(hard);
        //��ӡ�ABOUT��ѡ��
        about.add(help);
        about.add(info);
        /**
		 * ��Ӽ�����
		 */
        addMonitor();
    }

    public MainFrame() {
        subFrame.setTitle("��ѡ��");
        for (int k = 0; k < 9; k++) {//����9��ѡ�񰴼�
            numbers[k] = new JButton("" + (k + 1));//���ð�����ʾΪ1~9
            numbers[k].setFont(new Font("Dialog", Font.ITALIC, 30));//���ð��������ֵ���������
            numbers[k].addActionListener(new NumActionListener());//��Ӱ�������
            numbers[k].setForeground(Color.red); //���ð���ǰ��ɫ
            subFrame.add(numbers[k]);//��Ӱ���������
        }
        subFrame.setSize(120, 120);//���ø����ڴ�С
        subFrame.setLayout(new GridLayout(3, 3));//���ò���GridLayout��3��3��
        subFrame.setResizable(false);//���ø����ڴ�С���ܸ���

        JPanel centrePanel = new JPanel();//��Ϸ�����м�Ĳ���
        JLabel bottomPanel = new JLabel("Welcome to SUDOKU World!");//��Ϸ����ײ��Ĳ���
        bottomPanel.setHorizontalAlignment(SwingConstants.CENTER);//�����־���
        centrePanel.setLayout(new GridLayout(9, 9));//���ò���GridLayout��9��9��
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                bords[x][y] = new JButton();//��������
                bords[x][y].setSize(35, 35);//���ð�����С
                bords[x][y].addActionListener(new LabelActionListener());//��Ӱ�������
                bords[x][y].setBorder(BorderFactory.createEtchedBorder());//��Ӱ����߿�Ч��
                bords[x][y].setFont(new Font("Dialog", Font.ITALIC, 25));//���������С
                centrePanel.add(bords[x][y]);//��Ӱ������м����
            }
        }
        //�ı�������屳����ɫ
        setBackground(3, 0, 5, 2);
        setBackground(0, 3, 2, 5);
        setBackground(6, 3, 8, 5);
        setBackground(3, 6, 5, 8);
        //�ø���һ��ʼ������
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                bords[i][j].setEnabled(false);//���ð������ɵ��
            }
        }
        time.setPreferredSize(new Dimension(80, 300));
        add(time, BorderLayout.EAST);//���ʱ�����ұ�
        createMenu();//��Ӳ˵�
        this.setJMenuBar(bar);//��Ӳ˵���
        add(centrePanel, BorderLayout.CENTER);//����м���嵽����
        add(bottomPanel, BorderLayout.SOUTH);//��ӵײ���嵽����

    }

    /**���ð����ܵ��*/
    public void enableInput() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                bords[i][j].setEnabled(true);//���ð����ɵ��
            }
        }
    }
    /**
     * �ж��������Ƿ���ͬ,�ж���Ϸ�Ƿ�ʤ��
     * @param a ����a[][]
     * @param b	����b[][]
     * @return boolean
     */
    public boolean isEqual(int[][] a, int[][] b) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (a[i][j] == 0 || a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        subFrame.setVisible(false);//�����ڲ��ɼ�
        JOptionPane.showMessageDialog(null, "��Ϸ������\nCONGRATULATIONS��");
        start = false;//ʱ�䲻��
        time.pause();//ʱ����ͣ
        finish = true;//��Ϸ���
        return true;
    }
    /**
     * ������ݺͽ���
     */
    public void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                form1[i + 1][j + 1] = form4[i + 1][j + 1] = form3[i + 1][j + 1] = 0;
                bords[i][j].setText("");//�����水����ʾ����Ϊ��
            }
        }
    }
    /**
     * ������ary2[][]���Ƹ�ary1[][]
     * @param ary1
     * @param ary2
     */
    public void copy_array(int ary1[][], int ary2[][]) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                ary1[i][j] = ary2[i][j];//������ary2[][]���Ƹ�ary1[][]
            }
        }
    }
    
    public void set_puzzle() {
        for (int row = 1; row < 10; row++) {
            for (int column = 1; column < 10; column++) {
                bords[row - 1][column - 1].setForeground(Color.black);//���ð���ǰ��ɫ
                bords[row - 1][column - 1].setEnabled(true);//���ð����ɵ��
                if (form1[row][column] != 0) {
                    bords[row - 1][column - 1].setText("" + form1[row][column]);//��������λ����1~9�����ڰ�������ʾΪԭ����
                } else {
                    bords[row - 1][column - 1].setText("");//��������λ����0�����ڰ�������ʾΪ��
                }
                if (form1[row][column] != 0 && form4[row][column] == 0) {//�������е���Ϊ��
                    bords[row - 1][column - 1].setForeground(Color.red);//���ð���ǰ��ɫ
                }
            }
        }
    }

    /**
     * �ı�ָ������Button �ı���
     * @param x0 ��ʼx����
     * @param y0 ��ʼy����
     * @param x1 ����x����
     * @param y1 ����y����
     */
    public void setBackground(int x0, int y0, int x1, int y1) {
        for (int i = x0; i <= x1; i++) {
            for (int j = y0; j <= y1; j++) {
                bords[i][j].setBackground(new Color(192, 192, 192));//RGB�޸ı���ɫ
            }
        }
    }
    /**
	 * �ڲ���������� frame �е�ÿ�������ļ�����
	 */
    private class LabelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (i = 0; i < 9; i++) {
                for (j = 0; j < 9; j++) {
                    if (!finish && e.getSource().equals(bords[i][j])) {
                        if(DIYon||form4[i + 1][j + 1] == 0) {
                            for (int k = 0; k < 9; k++) {
                                if (ck.check(i + 1, j + 1, k + 1)) {//�ж�k�Ƿ������������
                                    numbers[k].setForeground(Color.red);//���ø������а���ǰ��ɫ
                                } else {
                                    numbers[k].setForeground(Color.black);//���ø������а���ǰ��ɫ
                                }
                                if (k + 1 == form1[i + 1][j + 1]) {
                                    numbers[k].setForeground(Color.red);//���ø������а���ǰ��ɫ
                                }
                            }
                            subFrame.setSize(50, 50);//����ÿ��������С
                            subFrame.setLocationRelativeTo(bords[i][j]);//ʹsubFrame  ����ڵ�ǰ��
                            subFrame.setVisible(true);//�����ڿɼ�
                        } 
                        /**
                         * ����Ч��"�� "
                         */
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                        int location = 1;
                        while (location < 5) {
                            try {
                                Thread.sleep(20);
                                subFrame.setSize(40 + 40 * location, 80 + 40 * location);
                                location++;
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
    /**
	 * �ڲ����������subframe�е�ÿ�������ļ�����
	 */
    public class NumActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int a = 0; a < 9; a++) {
                if (!finish && e.getSource().equals(numbers[a])) {
                    z = a + 1;//�����λ�õ���ֵ
                    if(DIYon){//�����DIYģʽ��form1������ʾ��form4������ʱ����
                        if(form4[i + 1][j + 1] == z || !ck.check(i + 1, j + 1, z)) {
                            bords[i][j].setText("");//��������λ����0�����ڰ�������ʾΪ��
                            bords[i][j].setForeground(Color.black);//���ð���ǰ��ɫ
                            form1[i + 1][j + 1] = form4[i + 1][j + 1] = 0;//��Ӧform1��form4��Ϊ0
                        } else {
                            bords[i][j].setText("" + z);//��ʾ�û����������
                            bords[i][j].setForeground(Color.black);//���ð���ǰ��ɫ
                            form1[i + 1][j + 1] = form4[i + 1][j + 1] = z;//form1��form4��Ϊ�������ֵ
                        }
                    }
                    else if(form1[i + 1][j + 1] == z || !ck.check(i + 1, j + 1, z)) {//�������ٴε�����������������
                        bords[i][j].setText("");//��������λ����0�����ڰ�������ʾΪ��
                        bords[i][j].setForeground(Color.red);//���ð���ǰ��ɫ
                        form1[i + 1][j + 1] = form2[i + 1][j + 1] = 0;//��Ӧform1��form4��Ϊ0
                    } else {
                        bords[i][j].setText("" + z);//��ʾ�û����������
                        bords[i][j].setForeground(Color.red);//���ð���ǰ��ɫ
                        form1[i + 1][j + 1] = form2[i + 1][j + 1] = z;//form1��form4��Ϊ�������ֵ
                    }
                    if (isEqual(form1, form3));
                    ck.get_puzzle(form1); 
                    i = j = z = 0;//ʹz��λ
                    /**
                     * �����ر��Ӵ���,����Ч��"�ر�"
                     */
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    int location = 1;
                    while (location < 5) {
                        try {
                            Thread.sleep(20);
                            subFrame.setSize(200 - 40 * location, 200 - 40 * location);
                            location++;
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    subFrame.setVisible(false);//�����ڲ��ɼ�
                    return;
                }
            }
        }
    }
    /**
	 *  Ϊ�˵�����Ӽ�����
	 */
    public void addMonitor() {
    	/**
    	 * ���ٿ�ʼ��Ϸ
    	 */
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	restart.setEnabled(true);//���ð����ɵ��
                DIYon = false;
                finish = false;
                start = true;
                time.start();//��ʼ��ʱ
                time.setSecond(0);//����ʱ��Ϊ0
                init.initialize(form1, form3, lvl);//������Ϸ���Ѷȵȼ���ʼ������Ŀ����
                ck.get_puzzle(form1);
                enableInput();//���ð����ܵ��
                for (int row = 1; row < 10; row++) {
                    for (int column = 1; column < 10; column++) {
                        form4[row][column] = form1[row][column];
                        if (form1[row][column] != 0) {
                            bords[row - 1][column - 1].setText("" + form1[row][column]);//��������λ����1~9�����ڰ�������ʾΪԭ����
                            bords[row - 1][column - 1].setEnabled(true);//���ð����ɵ��
                            bords[row - 1][column - 1].setForeground(Color.BLACK);//���ð���ǰ��ɫ
                        } else {//�������е���Ϊ��
                            bords[row - 1][column - 1].setText("");//��������λ����0�����ڰ�������ʾΪ��
                        }
                    }
                }
            }
        });
        /**
         * ����ģʽ��ʼ��Ϸ
         */
        playgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	int number = 0;
				while(number < 1 || number >9){//�����벻Ϊ1~9ʱ���ظ�����
					String input = "";
					try{
						input = JOptionPane.showInputDialog(null,"Enter a Stage(1~9):","Play Game",JOptionPane.QUESTION_MESSAGE);//��ȡ������ַ���					
						if(input.equals("")){//������Ϊ��ʱ�����ʾ
							JOptionPane.showMessageDialog(null, "Cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						try {
							number = Integer.parseInt(input);//���ַ���ת��Ϊ����	
						} catch (NumberFormatException e2) {//�������к����ַ������ʾ
							JOptionPane.showMessageDialog(null, "Please input number 1~9", "Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					catch(NullPointerException e3){//�˳��Ի���ص�������
						return;
					}
				}				
				start = true;
                time.start();//��ʼ��ʱ
                int saveTime = 0;
                try {
                    saveTime = lod.read(form1, form3, form4,number);//����
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finish = false;
                DIYon = false;
                restart.setEnabled(true);//���ð����ɵ��
                ck.get_puzzle(form1);
                set_puzzle();//�޸ı�������
                time.setSecond(saveTime);//������Ϸʱ����ϴα�����Ϸ��ʱ�俪ʼ		
            }
        });
        /**
         * ���¿�ʼ��ǰ�ؿ�
         */
        restart.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {//��׼�����¿�ʼ��ǰ����Ŀʱ�������ʾ�Ƿ�ȷ������
                Object[] options = {"OK", "Cancel"};
                int returnVal = JOptionPane.showOptionDialog(null, "Are sure to RESTART this game��", "Warning",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (returnVal != JOptionPane.OK_OPTION) {
                    return;
                }
                finish = false;
                DIYon = false;
                start = true;
                //���¿�ʼ��Ϸ
                for (int row = 1; row < 10; row++) {
                    for (int column = 1; column < 10; column++) {
                        form1[row][column] = form4[row][column];//����𰸸�������Ŀ
                        if (form1[row][column] != 0) {
                            bords[row - 1][column - 1].setText("" + form1[row][column]);//��������λ����1~9�����ڰ�������ʾΪԭ����
                        } else {//�������е���Ϊ��
                            bords[row - 1][column - 1].setText("");//��������λ����0�����ڰ�������ʾΪ��
                        }
                    }
                }
                time.start();//��ʼ��ʱ
                time.setSecond(0);//����ʱ��Ϊ0
            }
        });
        /**
         * �Լ�¼����Ŀ
         */
        diy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DIYon = true;
            	if(DIYon) restart.setEnabled(false);//���ð������ɵ��
                JOptionPane.showMessageDialog(null, "DIYpuzzle");
                enableInput();//���ð����ܵ��
                clear();//������ݺͽ���
                ck.get_puzzle(form4);            
                finish = false;
                start = true;
                time.stop();//ʱ��ֹͣ
            }
        });
        /**
         * ���浱ǰ��Ϸ
         */
        savegame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (finish) {//�����Ϸ���ܴ浵
                    JOptionPane.showMessageDialog(null, "You have finished the game.");
                    return;
                }
                if(DIYon){//�Զ�����Ŀģʽ���ܴ浵
                    JOptionPane.showMessageDialog(null, "DIYmode can't save game.");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Game have saved.");//������Ϸ��ʾ�ɹ�����
                int seconds = time.getSecond();//��ȡ��ǰ��Ϸʱ��
                try {
                    sav.save(form1, form3, form4, seconds,0);//������Ϸ
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        /**
         * ���ĵ���ȡ��Ϸ
         */
        loadgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Loadgame");
                start = true;
                time.start();//��ʼ��ʱ
                int saveTime = 0;
                try {
                    saveTime = lod.read(form1, form3, form4,0);//����
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finish = false;
                DIYon = false;
                restart.setEnabled(true);//���ð����ɵ��
                ck.get_puzzle(form1);
                set_puzzle();
                time.setSecond(saveTime);//������Ϸʱ����ϴα�����Ϸ��ʱ�俪ʼ
            }
        });
        /**
         * ���ܽ⵱ǰ��Ŀ
         */
        answer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                time.stop();//ʱ��ֹͣ
                start = false;
                finish = true;
                copy_array(form1, form4);//����form4��form1
                int k=DL.calsudoku(form1, false);
                set_puzzle();
                if(DIYon){//������Զ�����Ŀģʽ���п��ܳ������ֲ�ͬ������Ľ�
                    DIYon = false;
                    if(k==0) JOptionPane.showMessageDialog(null, "Nosolution.");
                    else if(k==1) JOptionPane.showMessageDialog(null, "Here is the solution.");
                    else JOptionPane.showMessageDialog(null, "Mutisolution.This is one of the solutions of you SUDOKU.");
                }
            }
        });
        easy.addActionListener(new ActionListener() {//��Ϸ�Ѷ�Ϊ�򵥵ļ�����
            public void actionPerformed(ActionEvent e) {
                lvl = 1;//������Ϸ�Ѷȵȼ�Ϊ1
                easy.setState(true);//��ѡ��״̬
                normal.setState(false);
                hard.setState(false);
            }
        });
        normal.addActionListener(new ActionListener() {//��Ϸ�Ѷ�Ϊ�еȵļ�����
            public void actionPerformed(ActionEvent e) {
                lvl = 2;//������Ϸ�Ѷȵȼ�Ϊ2
                easy.setState(false);//��ѡ��״̬
                normal.setState(true);
                hard.setState(false);
            }
        });
        hard.addActionListener(new ActionListener() {//��Ϸ�Ѷ�Ϊ���ѵļ�����
            public void actionPerformed(ActionEvent e) {
                lvl = 3;//������Ϸ�Ѷȵȼ�Ϊ3
                easy.setState(false);//��ѡ��״̬
                normal.setState(false);
                hard.setState(true);
            }
        });
        /**
         * ����������ʾ
         */
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "ÿһ����ÿһ�ж���1��9�����֣�\nÿ��С�Ź�����Ҳ��1��9�����֣�\n"
                        + "����һ��������ÿ�С�ÿ�м�ÿ��С�Ź�����ֻ�ܳ���һ�Σ��Ȳ����ظ�Ҳ������",
                        "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        /**
         * ��ʾ�����Ϣ
         */
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "SUDOKU\nVersion: 2.17.0\nBuild id: I20110613-1736\nThis product is developed by ���ػ� �κƽ� �ֿ��� ������",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        /**
         * �˳���Ϸ
         */
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Object[] options = { "ȷ��", "ȡ��" };
				int returnVal = JOptionPane.showOptionDialog(null, "ȷ��Ҫ�˳���Ϸ��", "Warning", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);
			    if(returnVal == JOptionPane.OK_OPTION)
			    	System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        MainFrame m = new MainFrame();//��ʼ��������
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;//��ȡ�����Ļ�Ŀ�
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;//��ȡ�����Ļ�ĸ�
        m.setTitle("SUDOKU");//������������
        m.setLocation(width / 2 - 200, height / 2 - 250);//���ô���λ��
        m.setSize(450, 500);//���ô��ڴ�С
        m.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        m.setVisible(true);//�����ڿɼ�
        m.setResizable(false);//���ڴ�С���ɸ���
        m.addWindowListener(new WindowAdapter() {//���ڹرյļ�����
            @Override
            public void windowClosing(WindowEvent e) {
            	Object[] options = { "ȷ��", "ȡ��" };
				int returnVal = JOptionPane.showOptionDialog(null, "ȷ��Ҫ�˳���Ϸ��", "Warning", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);
			    if(returnVal == JOptionPane.OK_OPTION)
			    	System.exit(0);
            }
        });
    }
}
