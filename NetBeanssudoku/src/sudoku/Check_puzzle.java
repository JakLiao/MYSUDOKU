package sudoku;

/*
 * 检查数独合法性
 */
public class Check_puzzle
{
    final int K = 3;    //九宫格大小
    final int N = 9;    //数独大小
    private boolean vis[][][]=new boolean[4][20][20];   //标记数组

    /*
     * 初始化标记数组
     */
    private void init()
    {
        for(int i=0;i<=N;i++)
            for(int j=0;j<=N;j++)
                for(int k=0;k<3;k++)
                    vis[k][i][j]=true;
    }

    /*
     * 获取问题，设置标记数组
     * 参数S为正在求解的数独数组
     */
    public void get_puzzle(final int [][]S)
    {
        init();
        for(int i=1;i<=N;i++)
            for(int j=1;j<=N;j++)
                if(S[i][j]!=0)
                {
                    int t=S[i][j];
                    vis[0][i][t]=false;
                    vis[1][j][t]=false;
                    vis[2][(i-1)/K*K+(j-1)/K+1][t]=false;
                }
    }

    /*
     * 检查目标的合法性
     * 返回第r行第c列中是否能选择k
     * 如果能，返回true，否则，返回false
     */
    public boolean check(int r,int c,int k)
    {
        return vis[0][r][k]&&vis[1][c][k]&&vis[2][(r-1)/K*K+(c-1)/K+1][k];
    }
}
