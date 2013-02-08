/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.FileNotFoundException;

/**
 *
 * @author J J
 */

public class Read {

        final int encry1 = 456;
        final int encry2 = 321;
        final int encry3 = 789;                                      /*加密相关数字*/

        public int read(int[][] a,int[][] b,int[][] c,int number) throws Exception {
        int i,j,time,k = 0;
        String filename = "data";
        filename += number+".dat";
        java.io.File file=new java.io.File(filename);                /*打开对应名字文件*/
        java.util.Scanner input=new java.util.Scanner(file);     /*用java.util.Scanner类从文本中读取数据*/
        for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                a[i][j]=input.nextInt()^encry1^encry2^encry3^(i*i*j+i+i+j)^(j*j*i+j+j+i);   /*通过循环依次把存档里的数据解密再放入数组a*/
        }
        for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                b[i][j]=input.nextInt()^encry1^encry2^encry3^(i*i*j+i+j+j)^(j*j*i+j+i+i); /*把存档的数据解密后放入数组b*/
        }
         for(i=1;i<=9;i++)
        {
            for(j=1;j<=9;j++)
                c[i][j]=input.nextInt()^encry1^encry2^encry3^(i*i*j+j+j+j)^(j*j*i+i+i+i); /*把存档的数据解密后放入数组c*/
        }
        time=input.nextInt();    /*把时间载入*/
        input.close();           /*用 close()关闭文件*/
        return time^encry1^encry2^encry3;
    }
        

}