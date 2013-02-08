/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author J J
 */
public class Sava {
    /**
     * @param args the command line arguments
     */
    final int encry1 = 456;
    final int encry2 = 321;
    final int encry3 = 789;                                          /*加密相关数字*/

    public void save(int[][]a,int[][]b,int[][]c,int d,int number)throws Exception{
        int i,j;
        java.io.File file=new java.io.File("data"+number+".dat");    /*打开对应名字的文件 */
        if(file.exists()){                                           /*如果文件已存在先删除*/
           file.delete();
        }
        java.io.PrintWriter output=new java.io.PrintWriter(file);  /*用java.io.PrintWriter类向文本写数据*/ 
        for(i=1;i<=9;i++)
        {for(j=1;j<=9;j++)
        output.print((a[i][j]^encry1^encry2^encry3^(i*i*j+i+i+j)^(j*j*i+j+j+i))+" ");}    /*通过循环依次把第一个数组加密后放入存档*/
        output.println();
        for(i=1;i<=9;i++)
        { for(j=1;j<=9;j++)
        output.print((b[i][j]^encry1^encry2^encry3^(i*i*j+i+j+j)^(j*j*i+j+i+i))+" ");}    /*把第二个数组加密后存入存档*/
        output.println();
        for(i=1;i<=9;i++)
        { for(j=1;j<=9;j++)
        output.print((c[i][j]^encry1^encry2^encry3^(i*i*j+j+j+j)^(j*j*i+i+i+i))+" ");}   /*把第三个数组加密后存入存档*/
        output.println();
        output.print(d^encry1^encry2^encry3);       /*把时间存入存档*/
        output.close();                             /*用 close()关闭文件*/
    }
}