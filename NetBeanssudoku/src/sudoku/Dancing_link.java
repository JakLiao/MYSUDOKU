package sudoku;

/*
 * 对象定义
 * 数据结构：Dancing link
 * Dancing link是一种双向十字链表
 * 其删除结点和恢复结点的时间复杂度都是O(1)
 * 根据Dancing link X算法，用于求解精确覆盖问题和重复覆盖的问题
 * 对于能转化为精确覆盖问题或者重复覆盖问题的问题
 * 均可以用Dancing link X算法求解
 * 实质上，Dancing link X算法是对于深度优先搜索的一个超强剪枝
 * 也是一种通用的搜索技巧
 *
 * 数独问题的求解是Dancing link X算法的经典应用
 * 也是目前人工智能搜索求解数独的最快的算法
 */
public class Dancing_link
{
    /*
     * 设置数独大小，链表长度等
     */
    final int K = 3;                    //九宫格大小
    final int[] N = {0,9,9*9,9*9*9};    //数独大小
    final int MAXC = 9*9*4+10;          //十字链表的列长度
    final int MAXR = 9*9*9+10;          //十字链表的行长度
    final int MAXL = MAXC*4+MAXR*4;     //静态链表的总长度
    final int INF = 1<<30;              //假定的无穷大

    /*
     * 定义链表结点，采用静态链表
     */
    int L[] = new int[MAXL];    //左节点指针
    int R[] = new int[MAXL];    //右节点指针
    int U[] = new int[MAXL];    //上结点指针
    int D[] = new int[MAXL];    //下结点指针
    int H[] = new int[MAXR];    //行表头指针
    int C[] = new int[MAXL];    //指向列表头的指针
    int ROW[] = new int[MAXL];  //指向行表头的指针
    int S[] = new int[MAXR];    //启发函数，记录列结点数量
    int ans[] = new int[MAXR];  //记录答案
    int sz;     //当前链表大小
    int head;   //列表头头指针
    int Ans;    //记录解的数量
    boolean F;  //标记是否修改待求解的数组

    /*
     * 初始化数组
     * arg为需要初始化的数组，val为初始化的值，length为数组长度
     */
    private void memset(int arg[],int val,int length)
    {
        for(int i=0;i<length;i++)
            arg[i]=val;
    }

    /*
     * 初始化链表
     */
    private void init()
    {
        memset(H,-1,H.length);
        head=0;
        for(sz=0;sz<=N[2]*4;sz++)
        {
            S[sz]=0;
            D[sz]=U[sz]=sz;
            L[sz+1]=sz;
            R[sz]=sz+1;
        }
        R[sz-1]=head;
        L[head]=sz-1;
    }

    /*
     * 插入链表结点
     * r,c为待插入结点的位置
     * 功能为在Dancing link的第r行第c列插入一个结点
     */
    private void link(int r,int c)
    {
        C[sz]=c;
        S[c]++;
        D[sz]=D[c];
        U[D[c]]=sz;
        U[sz]=c;
        D[c]=sz;
        if(H[r]<0) H[r]=R[sz]=L[sz]=sz;
        else
        {
            R[sz]=R[H[r]];
            L[R[sz]]=sz;
            L[sz]=H[r];
            R[H[r]]=sz;
        }
        ROW[sz++]=r;
    }

    /*
     * 删除第c行的所有结点
     */
    private void Remove(int c)
    {
        L[R[c]]=L[c];
        R[L[c]]=R[c];
        for(int i=D[c];i!=c;i=D[i])
            for(int j=R[i];j!=i;j=R[j])
            {
                U[D[j]]=U[j];
                D[U[j]]=D[j];
                S[C[j]]--;
            }
    }

    /*
     * 恢复第c行的所有结点
     */
    private void Resume(int c)
    {
        L[R[c]]=R[L[c]]=c;
        for(int i=U[c];i!=c;i=U[i])
            for(int j=L[i];j!=i;j=L[j])
            {
                U[D[j]]=D[U[j]]=j;
                S[C[j]]++;
            }
    }

    /*
     * DLX算法，求解精确覆盖问题
     * k为搜索深度，grid为正在求解的数独的二维数组
     * 当R[head]==head，即列指针为空时，则求得一个可行解
     */
    private boolean DLX(int k,int grid[][]) {
        if(R[head]==head) {
            if(!F)
                for(int i=0;i<k;i++)
                    grid[(ans[i]-1)/N[2]+1][(ans[i]-1)/N[1]%N[1]+1]=(ans[i]-1)%N[1]+1;
            F=true;
            Ans++;
            if(Ans>1) return true;
            return false;
        }
        int s=INF,c=-1;
        for(int i=R[head];i!=head;i=R[i])
            if(S[i]<s){
                s=S[c=i];
                if(s==1) break;
            }
        Remove(c);
        for(int i=D[c];i!=c;i=D[i]) {
            if(!F) ans[k]=ROW[i];
            for(int j=R[i];j!=i;j=R[j]) Remove(C[j]);
            if(DLX(k+1,grid)) return true;
            for(int j=L[i];j!=i;j=L[j]) Resume(C[j]);
        }
        Resume(c);
        return false;
    }

    /*
     * 根据数独中的行、列、数计算行表头
     * r为行，c为列，k为数独中i行j列上的数字
     */
    private int getrow(int r,int c,int k)
    {
        return (r-1)*N[2]+(c-1)*N[1]+k;
    }

    /*
     * 根据数独中的行、列、数、插入结点
     * r为行，c为列，k为数独中i行j列上的数字
     * 若数字不为0，则根据k，在Dancing link中对应的行列中插入结点
     * 若数字为0，对于k=1,2,..,9，在Dancing link中对应的行列中插入结点
     */
    private void Insert(int r,int c,int k)
    {
        link(getrow(r,c,k),(r-1)*N[1]+c);
        link(getrow(r,c,k),(r-1)*N[1]+k+N[2]);
        link(getrow(r,c,k),(c-1)*N[1]+k+N[2]*2);
        link(getrow(r,c,k),((r-1)/K*K+(c-1)/K)*N[1]+k+N[2]*3);
    }

    private void Insertgrid(int r,int c,int k)
    {
        if(k<=N[1]&&k>=1) Insert(r,c,k);
        else for(int i=1;i<=N[1];i++) Insert(r,c,i);
    }

    /*
     * 求解数独
     * grid为待求解的数独，f标记是否更改grid中的值
     * 若f为false时，则grid会变成数独的解
     * 若f为true时，grid不变
     * 无解返回0，唯一解返回1，多解返回2
     */
    public int calsudoku(int [][]grid,boolean f)
    {
        init();
        for(int i=1;i<=N[1];i++)
            for(int j=1;j<=N[1];j++)
                Insertgrid(i,j,grid[i][j]);
        Ans=0;
        F=f;
        DLX(0,grid);
        if(Ans==0) return 0;
        else if(Ans!=1) return 2;
        else return 1;
    }

}
