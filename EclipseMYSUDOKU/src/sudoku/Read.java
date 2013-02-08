/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author J J
 */

public class Read {
        public int read(int[][] a,int[][] b,int[][] c,int number) throws Exception {
        int i,j,time = 0;
   /*   int[][] a=new int[20][20];
        int[][] b=new int[20][20];
        int[][] c=new int[20][20];*/
        String filename = "data";
        filename += number+".txt";
        java.io.File file=new java.io.File(filename);
        java.util.Scanner input=new java.util.Scanner(file);
        for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                a[i][j]=input.nextInt();    
        }
        for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                b[i][j]=input.nextInt();
        }
         for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                c[i][j]=input.nextInt();
        }
        time=input.nextInt();
        input.close();
        return time;
    }
        

}