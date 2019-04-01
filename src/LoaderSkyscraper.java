import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoaderSkyscraper {
    private int N;
    public int [][]array;
    private int[]G;
    private int[]D;
    private int[]L;
    private int[]P;

    public void load(String filepath)
    {
        File file = new File(filepath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            N = Integer.valueOf(bufferedReader.readLine());
            G = new int[N];
            L = new int[N];
            D = new int[N];
            P = new int[N];
            array = new int[N][N];

            fillArray(G,bufferedReader.readLine());
            fillArray(D,bufferedReader.readLine());
            fillArray(L,bufferedReader.readLine());
            fillArray(P,bufferedReader.readLine());

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void fillArray(int[] array, String line) {
        String [] values = line.split(";");
        for(int i=0;i<array.length;i++)
            array[i] = Integer.valueOf(values[i+1]);
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int[] getG() {
        return G;
    }

    public void setG(int[] g) {
        G = g;
    }

    public int[] getD() {
        return D;
    }

    public void setD(int[] d) {
        D = d;
    }

    public int[] getL() {
        return L;
    }

    public void setL(int[] l) {
        L = l;
    }

    public int[] getP() {
        return P;
    }

    public void setP(int[] p) {
        P = p;
    }


    public boolean checkSkyscraper()
    {
        for(int i=0;i<N ;i++)
        {
            if(!checkRow(i) || !checkColumn(i))
                return false;
        }
        //checking columns
        for(int i=0;i<N ;i++)
        {
            boolean upResult = true;
            boolean downResult = true;
            if(G[i]!=0)
                upResult = checkColumnUp(i);
            if(D[i]!=0)
                downResult = checkColumnDown(i);
            if(!upResult || !downResult)
                return false;
        }
//        checking rows
        for(int i=0;i<N ;i++)
        {
            boolean leftResult = true;
            boolean rightResult = true;
            if(L[i]!=0)
                leftResult = checkRowLeft(i);
            if(P[i]!=0)
                rightResult = checkRowRight(i);
            if(!leftResult || !rightResult)
                return false;
        }

        return true;
    }

    public boolean checkColumnUp(int colNum)
    {
        if(hasEmptyCellsCol(colNum))
            return true;
        int upValue = G[colNum];
        int visibleBuildingsNum = 1;
        int currVal = array[0][colNum];
        for(int i=1;i<N;i++)
        {
            if(array[i][colNum]!=0 && array[i][colNum]>currVal)
            {
                visibleBuildingsNum++;
                currVal = array[i][colNum];
            }
        }

        return visibleBuildingsNum==upValue;


    }

    public boolean hasEmptyCellsCol(int colNum)
    {
        for(int i=0;i<N;i++)
        {
            if(array[i][colNum]==0)
                return true;
        }
        return false;
    }

    public boolean checkColumnDown(int colNum)
    {
        if(hasEmptyCellsCol(colNum))
            return true;
        int downValue = D[colNum];
        int visibleBuildingsNum = 1;
        int currVal = array[N-1][colNum];
        for(int i=N-2;i>=0;i--)
        {
            if(array[i][colNum]!=0 && array[i][colNum]>currVal)
            {
                visibleBuildingsNum++;
                currVal = array[i][colNum];
            }
        }

        return visibleBuildingsNum==downValue;

    }

    public boolean hasEmptyCellsRow(int rowNum)
    {
        for(int i=0;i<N;i++)
        {
            if(array[rowNum][i]==0)
                return true;
        }
        return false;
    }

    private boolean checkRowLeft(int rowNum)
    {
        if(hasEmptyCellsRow(rowNum))
            return true;
        int leftValue = L[rowNum];
        int visibleBuildingsNum = 1;
        int currVal = array[rowNum][0];
        for(int i=1;i<N;i++)
        {
            if(array[rowNum][i]!=0 && array[rowNum][i]>currVal)
            {
                visibleBuildingsNum++;
                currVal = array[rowNum][i];
            }
        }

        return visibleBuildingsNum==leftValue;
    }

    private boolean checkRowRight(int rowNum)
    {
        if(hasEmptyCellsRow(rowNum))
            return true;
        int rightValue = P[rowNum];
        int visibleBuildingsNum = 1;
        int currVal = array[rowNum][N-1];
        for(int i=N-2;i>=0;i--)
        {
            if(array[rowNum][i]!=0 && array[rowNum][i]>currVal)
            {
                visibleBuildingsNum++;
                currVal = array[rowNum][i];
            }
        }

        return visibleBuildingsNum==rightValue;
    }


    private boolean checkRow(int rowNum)
    {
        List<Integer> visited = new ArrayList<>(N);
        for(int i=0;i<N;i++)
        {
            if(array[rowNum][i]!=0) {
                if (!visited.contains(array[rowNum][i])) {
                    visited.add(array[rowNum][i]);
                }
                else
                    return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int colNum)
    {
        List<Integer> visited = new ArrayList<>(N);
        for(int i=0;i<N;i++)
        {
            if(array[i][colNum]!=0) {
                if (!visited.contains(array[i][colNum]))
                    visited.add(array[i][colNum]);
                else
                    return false;
            }
        }
        return true;
    }

    public boolean back()
    {
        Indices currIndices = findFreeCell();
        //zacznij od -1-1
        //
        boolean isFull = (currIndices.x==-1);
        System.out.println(currIndices);
        if(isFull)
        {
            return true;
        }
        else
        {
            for(int val=1;val<=N;val++)
            {
                System.out.println("Val " + val);
                array[currIndices.x][currIndices.y] = val;
                if(checkSkyscraper())
                {
                    if(back())
                        return true;
                    else
                        array[currIndices.x][currIndices.y] = 0;
                }
                else
                {
                    array[currIndices.x][currIndices.y] = 0;

                }

            }
        }
        return false;


        //w petli znajdz pierwsza pusta wartosc

        //jak znajdzie to ustawia koordynaty -1 -1 na i j a isFull na false
        //dodaj petle przechodzaca po wszystkich wartosci od 1 do N
        //jesli niepelna, sprawdz czy dla danego koordynatu sa poprawne wartosci
        //jesli sa wywolaj te metode jeszcze raz w parametrze podajac aktualna tablice
        //a jak nie to pod koordyanty obecne podpisac 0
    }

    private Indices findFreeCell()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(array[i][j]==0)
                {
                    return new Indices(i,j);
                }
            }
        }
        return new Indices(-1,-1);
    }

}
