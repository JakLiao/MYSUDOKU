package sudoku;

public class Check_puzzle
{
    final int K = 3;
    final int N = 9;
    boolean vis[][][]=new boolean[4][20][20];

    void init()
    {
        for(int i=0;i<=N;i++)
            for(int j=0;j<=N;j++)
                for(int k=0;k<K;k++)
                    vis[k][i][j]=true;
    }

    void get_puzzle(final int [][]S)
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

    boolean check(int r,int c,int k)
    {
        return vis[0][r][k]&&vis[1][c][k]&&vis[2][(r-1)/K*K+(c-1)/K+1][k];
    }
}
