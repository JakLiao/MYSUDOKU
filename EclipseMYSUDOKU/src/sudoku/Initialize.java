
/***
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;
/**
 *
 * @author nemo
 */
public class Initialize {
    /**
     * @param args the command line arguments
     */
    int [][] map  = new int[20][20];
  //           mapQ = new int[20][20],
  //           mapA = new int[20][20];
    Dancing_link D = new Dancing_link();

    public void initialize(int [][]mapQ,int [][]mapA,int level) {
        do
        {
            for(int row=1;row <10;++row) {
                for(int column=1;column <10;++column)
                    map[row][column]=0;
                int rand=(int)(Math.random()*9)+1;
                map[row][rand]=row;
            }
        }while(D.calsudoku(map,false) == 0);

        for(int row=0;row < map.length;++row)
            for(int column=0;column <map[row].length;++column){
                mapQ[row][column] = map[row][column];
                mapA[row][column] = map[row][column];
            }
        dig(mapQ,mapA,level);
    }

    public void dig(int [][]mapQ,int [][]mapA,int level) {
    	 int remain = 81,i = 0,k = 0,row = 0,column = 0;
         switch(level) {
             case 1: level = 35 + (int)(Math.random()*5); break;
             case 2: level = 45 + (int)(Math.random()*5); break;
             default:level = 81;
         }
         int [] index = new int[100];
         for(i = 0;i < 100;i++)
             index[i] = i;
         for(k = 0;k<level&&remain != 0;) {
                 i = (int)(Math.random()*remain);
                 column = index[i];
                 index[i] = index[remain-1];
                 remain--;
                 row = column%9+1;
                 column = column/9+1;
                 mapQ[row][column] = 0;
                 k++;
                 if(D.calsudoku(mapQ,true) != 1) {
                     mapQ[row][column] = mapA[row][column];
                     k--;
                 }
         }
    }

}
