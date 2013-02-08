package sudoku;
    /*
    * 该类中的方法用于随机生成含有唯一解的数独问题以及该问题对应的解
    * 其中数独问题分分简单、中等、困难三种模式，由参数level决定
    * 而数独问题的难度由数独挖空方式以及挖空个数决定
    */
public class Initialize {
    /*
    * 生成一个20*20的二维数组用于暂存数据
    * 新建Dancing_link对象，以调用该类中的方法
    */
    int [][] map  = new int[20][20];                   
    Dancing_link D = new Dancing_link();

    /*
     * 以下是对数组进行初始化，使mapQ生成有唯一解的数独问题，mapA为mapQ中数独问题的解
     * level表示生成问题的难度等级，分1、2、3级对应简单、中等、困难
     */
    public void initialize(int [][]mapQ,int [][]mapA,int level) {
      /*
       * 使用拉斯维加斯算法生成数独答案，即一个符合数独游戏规则的终盘
       * 生成方法为：1、在空盘中随机放入n个数字，此处放入9个数字；
       *            2、使用Dancing_link中的calsudoku方法对其求解，多解时取其一即可；
       *            3、若无解回到步骤1，知道map中生成终盘；
       *           （后来对步骤1做了修改，即在每行中的随机位置中放入等于该行数的数字，此修改杜绝冲突，大大提高了生成终盘的概率）
       */
        do
        {
            for(int row=1;row <10;++row) {
                for(int column=1;column <10;++column)
                    map[row][column]=0;
                int rand=(int)(Math.random()*9)+1;
                map[row][rand]=row;
            }
        }while(D.calsudoku(map,false) == 0);

      /*
       *生成的答案放到代表答案与问题的mapA与mapQ中
       */
        for(int row=0;row < map.length;++row)
            for(int column=0;column <map[row].length;++column){
                mapQ[row][column] = map[row][column];
                mapA[row][column] = map[row][column];
            }
      /*
       * 调用dig函数，对终盘进行挖空操作，生成一个唯一解的数独问题
       */
        dig(mapQ,mapA,level);
    }

      /*
       * 以下是挖空方法，挖空时首先保证生成问题的解的唯一性
       */
    public void dig(int [][]mapQ,int [][]mapA,int level) {
        /*
         * remain用于表示待挖空的剩余元素，初始化为81，即元素总数
         */
    	 int remain = 81,i = 0,k = 0,row = 0,column = 0;     
         switch(level) {                                              //根据level选择挖空的数量
             case 1: level = 35 + (int)(Math.random()*5); break;      //简单难度：挖掉35~40个空
             case 2: level = 45 + (int)(Math.random()*5); break;      //中等难度：挖掉45~50个空
             default:level = 81;                                      //困难难度：挖掉所有能挖的
         }
         /*
          * 生成一个一维数组用于存放0~80共81个数字，用于表示二维数组mapQ中的下标
          */
         int [] index = new int[100];                                 
         for(i = 0;i < 100;i++)
             index[i] = i;

         for(k = 0;k<level&&remain != 0;) {                          //挖掘，直到挖够level个数或者直到可以挖的数剩余量为0
                 i = (int)(Math.random()*remain);                    //随机在index数组中取一个数，然后将其从数组中删除，由于无所         
                 column = index[i];                                  //谓顺序，所以将数组最后一个覆盖掉该元素即可，并将剩余量减去1
                 index[i] = index[remain-1];                         //二维数组中的元素分为三种状态：已经挖掉的、不可挖的、尚待挖空的，此操作的
                 remain--;                                           //目的是将不可挖的与已经挖了的元素从index中剔除，避免作无所谓的挖空尝试
                 row = column%9+1;
                 column = column/9+1;
                 mapQ[row][column] = 0;
                 k++;                  
                 if(D.calsudoku(mapQ,true) != 1) {                  //进行挖空尝试，如果挖完后问题有多解，挖空操作失败，将已经挖掉的元素补回并将已经挖空的数量减1
                     mapQ[row][column] = mapA[row][column];
                     k--;
                 }
         }
    }

}
