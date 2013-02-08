package sudoku;

public class Dancing_link
{
    final int MAXC = 9*9*4+10;
    final int MAXR = 9*9*9+10;
    final int MAXL = MAXC*4+MAXR*4;
    final int INF = 1<<30;
    final int K = 3;
    final int[] N = {0,9,9*9,9*9*9};
    int L[] = new int[MAXL];
    int R[] = new int[MAXL];
    int U[] = new int[MAXL];
    int D[] = new int[MAXL];
    int C[] = new int[MAXL];
    int ROW[] = new int[MAXL];
    int H[] = new int[MAXR];
    int S[] = new int[MAXR];
    int ans[] = new int[MAXR];
    int sz,head,Ans;
    boolean F;

    void memset(int arg[],int val,int length)
    {
        for(int i=0;i<length;i++)
            arg[i]=val;
    }

    void init()
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

    void link(int r,int c)
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

    void Remove(int c)
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

    void Resume(int c)
    {
        L[R[c]]=R[L[c]]=c;
        for(int i=U[c];i!=c;i=U[i])
            for(int j=L[i];j!=i;j=L[j])
            {
                U[D[j]]=D[U[j]]=j;
                S[C[j]]++;
            }
    }

    boolean DLX(int k,int grid[][]) {
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

    int getrow(int i,int j,int k)
    {
        return (i-1)*N[2]+(j-1)*N[1]+k;
    }

    void Insert(int r,int c,int k)
    {
        link(getrow(r,c,k),(r-1)*N[1]+c);
        link(getrow(r,c,k),(r-1)*N[1]+k+N[2]);
        link(getrow(r,c,k),(c-1)*N[1]+k+N[2]*2);
        link(getrow(r,c,k),((r-1)/K*K+(c-1)/K)*N[1]+k+N[2]*3);
    }

    void Insertgrid(int r,int c,int k)
    {
        if(k<=N[1]&&k>=1) Insert(r,c,k);
        else for(int i=1;i<=N[1];i++) Insert(r,c,i);
    }

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
