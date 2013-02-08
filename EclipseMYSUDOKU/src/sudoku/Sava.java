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
    /*public static void main(String[] args) throws Exception{
        int[][]a = new int[20][20];
        int[][]b = new int[20][20];
        int[][]c = new int[20][20];
        int d;
        int i,j;
        for(i=0;i<=8;i++)
            for(j=0;j<=8;j++)
            {a[i][j]=2;b[i][j]=2;c[i][j]=3;}
        d=4;
        save(a,b,c,d);
    }*/


    public void save(int[][]a,int[][]b,int[][]c,int d,int number)throws Exception{
        int i,j;
        java.io.File file=new java.io.File("data"+number+".txt");
        if(file.exists()){
           file.delete();
        }
        java.io.PrintWriter output=new java.io.PrintWriter(file);
        for(i=1;i<=9;i++)
        {for(j=1;j<=9;j++)
        output.print(a[i][j]+" ");}
        output.println();
        for(i=1;i<=9;i++)
        { for(j=1;j<=9;j++)
        output.print(b[i][j]+" ");}
        output.println();
        for(i=1;i<=9;i++)
        { for(j=1;j<=9;j++)
        output.print(c[i][j]+" ");}
        output.println();
        output.print(d);
        output.close();
    }
}