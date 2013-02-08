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
 * 游戏界面
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private int lvl = 2;//难度设定
    boolean DIYon;//是否自主创建题目模式
    boolean finish;//是否完成
    boolean start = false;//是否开始计时
    private int z;//副界面数值传递临时变量
    private int i = 0, j = 0;
    JButton[] numbers = new JButton[9];
    JButton[][] bords = new JButton[20][20];//用来显示用户的输入界面
    JFrame subFrame = new JFrame();//用来选择的副窗口
    private int[][] form1 = new int[20][20];//用来处理相应的数据
    private int[][] form2 = new int[20][20];//用来辅助form1进行相关判断
    private int[][] form3 = new int[20][20];//再定义一个form3用来检验答案是否唯一
    private int[][] form4 = new int[20][20];//用来储存form1 的数据，用于重新开始菜单
    Initialize init = new Initialize();//初始化题目
    Dancing_link DL = new Dancing_link();//解题
    TimeKeeper time = new TimeKeeper();//创建一个计时器
    Sava sav = new Sava();//保存文档
    Read lod = new Read();//读取文档
    Check_puzzle ck = new Check_puzzle();//数独问题的随机生成
    /**
     * 子菜单
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
     * 菜单项
     */
    JMenu file = new JMenu("File"),
            game = new JMenu("Game"),
            difficulty = new JMenu("Difficulty"),
            about = new JMenu("About");
    /**
     * 单选菜单项
     */
    JCheckBoxMenuItem easy = new JCheckBoxMenuItem("EASY"),
            normal = new JCheckBoxMenuItem("NORMAL", true),
            hard = new JCheckBoxMenuItem("HARD");
    /**
     *  菜单条
     */
    JMenuBar bar = new JMenuBar();

    /**
     * 构造一个菜单栏
     */
    public void createMenu() {
        //添加菜单
        bar.add(file);
        bar.add(game);
        bar.add(about);
        //添加"FILE”的选项
        file.add(newGame);
        file.add(playgame);
        file.add(restart);
        file.addSeparator();
        file.add(diy);
        file.addSeparator();
        file.add(exit);
        //添加“GAME"的选项
        game.add(loadgame);
        game.add(savegame);
        game.addSeparator();
        game.add(answer);
        game.add(difficulty);
        // 添加"Difficulty"子菜单
        difficulty.add(easy);
        difficulty.add(normal);
        difficulty.add(hard);
        //添加”ABOUT“选项
        about.add(help);
        about.add(info);
        /**
         * 添加监听器
         */
        addMonitor();
    }

    public MainFrame() {
        subFrame.setTitle("请选择");
        for (int k = 0; k < 9; k++) {//设置9个选择按键
            numbers[k] = new JButton("" + (k + 1));//设置按键显示为1~9
            numbers[k].setFont(new Font("Dialog", Font.ITALIC, 30));//设置按键上文字的字体属性
            numbers[k].addActionListener(new NumActionListener());//添加按键监听
            numbers[k].setForeground(Color.red); //设置按键前景色
            subFrame.add(numbers[k]);//添加按键到窗口
        }
        subFrame.setSize(120, 120);//设置副窗口大小
        subFrame.setLayout(new GridLayout(3, 3));//设置布局GridLayout（3，3）
        subFrame.setResizable(false);//设置副窗口大小不能更改

        JPanel centrePanel = new JPanel();//游戏界面中间的部分
        JLabel bottomPanel = new JLabel("Welcome to SUDOKU World!");//游戏界面底部的部分
        bottomPanel.setHorizontalAlignment(SwingConstants.CENTER);//让文字居中
        centrePanel.setLayout(new GridLayout(9, 9));//设置布局GridLayout（9，9）
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                bords[x][y] = new JButton();//创建按键
                bords[x][y].setSize(35, 35);//设置按键大小
                bords[x][y].addActionListener(new LabelActionListener());//添加按键监听
                bords[x][y].setBorder(BorderFactory.createEtchedBorder());//添加按键边框效果
                bords[x][y].setFont(new Font("Dialog", Font.ITALIC, 25));//设置字体大小
                centrePanel.add(bords[x][y]);//添加按键到中间面板
            }
        }
        //改变中心面板背景颜色
        setBackground(3, 0, 5, 2);
        setBackground(0, 3, 2, 5);
        setBackground(6, 3, 8, 5);
        setBackground(3, 6, 5, 8);
        //让格子一开始不能填
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                bords[i][j].setEnabled(false);//设置按键不可点击
            }
        }
        time.setPreferredSize(new Dimension(80, 300));
        add(time, BorderLayout.EAST);//添加时间在右边
        createMenu();//添加菜单
        this.setJMenuBar(bar);//添加菜单条
        add(centrePanel, BorderLayout.CENTER);//添加中间面板到窗口
        add(bottomPanel, BorderLayout.SOUTH);//添加底部面板到窗口

    }

    /**设置按键能点击*/
    public void enableInput() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                bords[i][j].setEnabled(true);//设置按键可点击
            }
        }
    }

    /**
     * 判断两数组是否相同,判定游戏是否胜利
     * @param a 数组a[][]
     * @param b	数组b[][]
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
        subFrame.setVisible(false);//副窗口不可见
        JOptionPane.showMessageDialog(null, "游戏结束！\nCONGRATULATIONS！");
        start = false;//时间不动
        time.pause();//时间暂停
        finish = true;//游戏完成
        return true;
    }

    /**
     * 清空数据和界面
     */
    public void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                form1[i + 1][j + 1] = form4[i + 1][j + 1] = form3[i + 1][j + 1] = 0;
                bords[i][j].setText("");//主界面按键显示设置为空
            }
        }
    }

    /**
     * 把数组ary2[][]复制给ary1[][]
     * @param ary1
     * @param ary2
     */
    public void copy_array(int ary1[][], int ary2[][]) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                ary1[i][j] = ary2[i][j];//把数组ary2[][]复制给ary1[][]
            }
        }
    }

    public void set_puzzle() {
        for (int row = 1; row < 10; row++) {
            for (int column = 1; column < 10; column++) {
                bords[row - 1][column - 1].setForeground(Color.black);//设置按键前景色
                bords[row - 1][column - 1].setEnabled(true);//设置按键可点击
                if (form1[row][column] != 0) {
                    bords[row - 1][column - 1].setText("" + form1[row][column]);//若数组中位数字1~9，则在按键上显示为原数字
                } else {
                    bords[row - 1][column - 1].setText("");//若数组中位数字0，则在按键上显示为空
                }
                if (form1[row][column] != 0 && form4[row][column] == 0) {//当数组中的数为零
                    bords[row - 1][column - 1].setForeground(Color.red);//设置按键前景色
                }
            }
        }
    }

    /**
     * 改变指定区域Button 的背景
     * @param x0 起始x坐标
     * @param y0 起始y坐标
     * @param x1 结束x坐标
     * @param y1 结束y坐标
     */
    public void setBackground(int x0, int y0, int x1, int y1) {
        for (int i = x0; i <= x1; i++) {
            for (int j = y0; j <= y1; j++) {
                bords[i][j].setBackground(new Color(192, 192, 192));//RGB修改背景色
            }
        }
    }

    /**
     * 内部类用来添加 frame 中的每个按键的监听器
     */
    private class LabelActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (i = 0; i < 9; i++) {
                for (j = 0; j < 9; j++) {
                    if (!finish && e.getSource().equals(bords[i][j])) {
                        if (DIYon || form4[i + 1][j + 1] == 0) {
                            for (int k = 0; k < 9; k++) {
                                if (ck.check(i + 1, j + 1, k + 1)) {//判断k是否符合数独规则
                                    numbers[k].setForeground(Color.red);//设置副界面中按键前景色
                                } else {
                                    numbers[k].setForeground(Color.black);//设置副界面中按键前景色
                                }
                                if (k + 1 == form1[i + 1][j + 1]) {
                                    numbers[k].setForeground(Color.red);//设置副界面中按键前景色
                                }
                            }
                            subFrame.setSize(50, 50);//设置每个按键大小
                            subFrame.setLocationRelativeTo(bords[i][j]);//使subFrame  相对于当前打开
                            subFrame.setVisible(true);//副窗口可见
                        }
                        /**
                         * 动画效果"打开 "
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
     * 内部类用来添加subframe中的每个按键的监听器
     */
    public class NumActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int a = 0; a < 9; a++) {
                if (!finish && e.getSource().equals(numbers[a])) {
                    z = a + 1;//储存该位置的数值
                    if (DIYon) {//如果是DIY模式，form1用来显示，form4用来即时解题
                        if (form4[i + 1][j + 1] == z || !ck.check(i + 1, j + 1, z)) {
                            bords[i][j].setText("");//若数组中位数字0，则在按键上显示为空
                            bords[i][j].setForeground(Color.black);//设置按键前景色
                            form1[i + 1][j + 1] = form4[i + 1][j + 1] = 0;//对应form1和form4设为0
                        } else {
                            bords[i][j].setText("" + z);//显示用户填入的数字
                            bords[i][j].setForeground(Color.black);//设置按键前景色
                            form1[i + 1][j + 1] = form4[i + 1][j + 1] = z;//form1和form4设为点击的数值
                        }
                    } else if (form1[i + 1][j + 1] == z || !ck.check(i + 1, j + 1, z)) {//副界面再次点击，主界面数字清空
                        bords[i][j].setText("");//若数组中位数字0，则在按键上显示为空
                        bords[i][j].setForeground(Color.red);//设置按键前景色
                        form1[i + 1][j + 1] = form2[i + 1][j + 1] = 0;//对应form1和form4设为0
                    } else {
                        bords[i][j].setText("" + z);//显示用户填入的数字
                        bords[i][j].setForeground(Color.red);//设置按键前景色
                        form1[i + 1][j + 1] = form2[i + 1][j + 1] = z;//form1和form4设为点击的数值
                    }
                    if (isEqual(form1, form3));
                    ck.get_puzzle(form1);
                    i = j = z = 0;//使z复位
                    /**
                     * 填完后关闭子窗口,动画效果"关闭"
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
                    subFrame.setVisible(false);//副窗口不可见
                    return;
                }
            }
        }
    }

    /**
     *  为菜单项添加监听器
     */
    public void addMonitor() {
        /**
         * 快速开始游戏
         */
        newGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                restart.setEnabled(true);//设置按键可点击
                DIYon = false;
                finish = false;
                start = true;
                time.start();//开始计时
                time.setSecond(0);//重置时间为0
                init.initialize(form1, form3, lvl);//根据游戏的难度等级初始化各题目数组
                ck.get_puzzle(form1);
                enableInput();//设置按键能点击
                for (int row = 1; row < 10; row++) {
                    for (int column = 1; column < 10; column++) {
                        form4[row][column] = form1[row][column];
                        if (form1[row][column] != 0) {
                            bords[row - 1][column - 1].setText("" + form1[row][column]);//若数组中位数字1~9，则在按键上显示为原数字
                            bords[row - 1][column - 1].setEnabled(true);//设置按键可点击
                            bords[row - 1][column - 1].setForeground(Color.BLACK);//设置按键前景色
                        } else {//当数组中的数为零
                            bords[row - 1][column - 1].setText("");//若数组中位数字0，则在按键上显示为空
                        }
                    }
                }
            }
        });
        /**
         * 闯关模式开始游戏
         */
        playgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int number = 0;
                while (number < 1 || number > 9) {//当输入不为1~9时，重复操作
                    String input = "";
                    try {
                        input = JOptionPane.showInputDialog(null, "Enter a Stage(1~9):", "Play Game", JOptionPane.QUESTION_MESSAGE);//获取输入的字符串
                        if (input.equals("")) {//当输入为空时输出提示
                            JOptionPane.showMessageDialog(null, "Cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        try {
                            number = Integer.parseInt(input);//把字符串转换为整型
                        } catch (NumberFormatException e2) {//若输入中含有字符输出提示
                            JOptionPane.showMessageDialog(null, "Please input number 1~9", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NullPointerException e3) {//退出对话框回到主界面
                        return;
                    }
                }
                start = true;
                time.start();//开始计时
                int saveTime = 0;
                try {
                    saveTime = lod.read(form1, form3, form4, number);//读档
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finish = false;
                DIYon = false;
                restart.setEnabled(true);//设置按键可点击
                ck.get_puzzle(form1);
                set_puzzle();//修改背景函数
                time.setSecond(saveTime);//设置游戏时间从上次保存游戏的时间开始
            }
        });
        /**
         * 重新开始当前关卡
         */
        restart.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {//当准备重新开始当前的题目时，输出提示是否确定继续
                Object[] options = {"OK", "Cancel"};
                int returnVal = JOptionPane.showOptionDialog(null, "Are sure to RESTART this game？", "Warning",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (returnVal != JOptionPane.OK_OPTION) {
                    return;
                }
                finish = false;
                DIYon = false;
                start = true;
                //重新开始游戏
                for (int row = 1; row < 10; row++) {
                    for (int column = 1; column < 10; column++) {
                        form1[row][column] = form4[row][column];//数组答案给数组题目
                        if (form1[row][column] != 0) {
                            bords[row - 1][column - 1].setText("" + form1[row][column]);//若数组中位数字1~9，则在按键上显示为原数字
                        } else {//当数组中的数为零
                            bords[row - 1][column - 1].setText("");//若数组中位数字0，则在按键上显示为空
                        }
                    }
                }
                time.start();//开始计时
                time.setSecond(0);//重置时间为0
            }
        });
        /**
         * 自己录入题目
         */
        diy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                DIYon = true;
                if (DIYon) {
                    restart.setEnabled(false);//设置按键不可点击
                }
                JOptionPane.showMessageDialog(null, "DIYpuzzle");
                enableInput();//设置按键能点击
                clear();//清空数据和界面
                ck.get_puzzle(form4);
                finish = false;
                start = true;
                time.stop();//时间停止
            }
        });
        /**
         * 保存当前游戏
         */
        savegame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (finish) {//完成游戏不能存档
                    JOptionPane.showMessageDialog(null, "You have finished the game.");
                    return;
                }
                if (DIYon) {//自定义题目模式不能存档
                    JOptionPane.showMessageDialog(null, "DIYmode can't save game.");
                    return;
                }
                JOptionPane.showMessageDialog(null, "Game have saved.");//正常游戏显示成功保存
                int seconds = time.getSecond();//获取当前游戏时间
                try {
                    sav.save(form1, form3, form4, seconds, 0);//保存游戏
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        /**
         * 从文档读取游戏
         */
        loadgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Loadgame");
                start = true;
                time.start();//开始计时
                int saveTime = 0;
                try {
                    saveTime = lod.read(form1, form3, form4, 0);//读档
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finish = false;
                DIYon = false;
                restart.setEnabled(true);//设置按键可点击
                ck.get_puzzle(form1);
                set_puzzle();
                time.setSecond(saveTime);//设置游戏时间从上次保存游戏的时间开始
            }
        });
        /**
         * 智能解当前题目
         */
        answer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                time.stop();//时间停止
                start = false;
                finish = true;
                copy_array(form1, form4);//复制form4给form1
                int k = DL.calsudoku(form1, false);
                set_puzzle();
                if (DIYon) {//如果是自定义题目模式，有可能出现三种不同的情况的解
                    DIYon = false;
                    if (k == 0) {
                        JOptionPane.showMessageDialog(null, "Nosolution.");
                    } else if (k == 1) {
                        JOptionPane.showMessageDialog(null, "Here is the solution.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Mutisolution.This is one of the solutions of you SUDOKU.");
                    }
                }
            }
        });
        easy.addActionListener(new ActionListener() {//游戏难度为简单的监听器

            public void actionPerformed(ActionEvent e) {
                lvl = 1;//设置游戏难度等级为1
                easy.setState(true);//多选框状态
                normal.setState(false);
                hard.setState(false);
            }
        });
        normal.addActionListener(new ActionListener() {//游戏难度为中等的监听器

            public void actionPerformed(ActionEvent e) {
                lvl = 2;//设置游戏难度等级为2
                easy.setState(false);//多选框状态
                normal.setState(true);
                hard.setState(false);
            }
        });
        hard.addActionListener(new ActionListener() {//游戏难度为困难的监听器

            public void actionPerformed(ActionEvent e) {
                lvl = 3;//设置游戏难度等级为3
                easy.setState(false);//多选框状态
                normal.setState(false);
                hard.setState(true);
            }
        });
        /**
         * 弹出帮助提示
         */
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "每一行与每一列都有1到9的数字，\n每个小九宫格里也有1到9的数字，\n"
                        + "并且一个数字在每行、每列及每个小九宫格里只能出现一次，既不能重复也不能少",
                        "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        /**
         * 显示相关信息
         */
        info.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "SUDOKU\nVersion: 2.17.0\nBuild id: I20110613-1736\nThis product is developed by 林守煌 廖浩杰 林俊杰 梁康华",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        /**
         * 退出游戏
         */
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Object[] options = {"确定", "取消"};
                int returnVal = JOptionPane.showOptionDialog(null, "确定要退出游戏？", "Warning",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[1]);
                if (returnVal == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        MainFrame m = new MainFrame();//初始化主窗口
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;//获取你的屏幕的宽
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;//获取你的屏幕的高
        m.setTitle("SUDOKU");//设置主窗口名
        m.setLocation(width / 2 - 200, height / 2 - 250);//设置窗口位置
        m.setSize(450, 500);//设置窗口大小
        m.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        m.setVisible(true);//主窗口可见
        m.setResizable(false);//窗口大小不可更改
        m.addWindowListener(new WindowAdapter() {//窗口关闭的监听器

            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"确定", "取消"};
                int returnVal = JOptionPane.showOptionDialog(null, "确定要退出游戏？", "Warning",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[1]);
                if (returnVal == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
