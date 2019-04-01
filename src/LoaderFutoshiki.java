import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LoaderFutoshiki {
    private int N;
    private int [][] array;
    private List<RelationPair> relations = new ArrayList<>();
    private Map<Indices,List<Integer>> map;


    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }
    public int[][] getArray() {
        return array;
    }
    public void setArray(int[][] array) {
        this.array = array;
    }
    public List<RelationPair> getRelations() {
        return relations;
    }

    public void load(String filepath)
    {
        File file = new File(filepath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            N = Integer.valueOf(bufferedReader.readLine());
            bufferedReader.readLine();
            array = new int[N][N];
            int rowNum = 0;
            String line;
            while((line=bufferedReader.readLine())!=null && !line.startsWith("REL"))
            {
                System.out.println(line);
                fillArrayRow(line,rowNum);
                rowNum++;
            }
            while((line=bufferedReader.readLine())!=null)
            {
                relations.add(createRelationPair(line));
            }
            fillMap();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void fillMap()
    {
        map = new HashMap<>();

        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array.length;j++)
            {
                List<Integer> scope = new ArrayList<>();
                if(array[i][j]!=0) {
                    scope.add(array[i][j]);
                }
                else {
                    for (int h = 0; h < N; h++) {
                        scope.add(h + 1);
                    }
                    scope.removeAll(getForbiddenValuesInRow(i));
                    scope.removeAll(getForbiddenValuesInColumn(j));
                }
                map.put(new Indices(i,j),scope);
            }
        }

    }
    
    private void fillArrayRow(String line, int row) {
        String [] values = line.split(";");
        for (int i=0;i< values.length;i++) {
            array[row][i] = Integer.valueOf(values[i]);
        }
    }

    private RelationPair createRelationPair(String line)
    {
        String [] values = line.split(";");
        String first = values[0];
        String second = values[1];

        Coordinate firstCoordinate = new Coordinate(first.charAt(0),Integer.valueOf(first.substring(1)));
        Coordinate secondCoordinate = new Coordinate(second.charAt(0),Integer.valueOf(second.substring(1)));
        return new RelationPair(firstCoordinate,secondCoordinate);
    }
    public void displayArray(){
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
                System.out.print(array[i][j] + "    ");
            System.out.println();
        }
        System.out.println();

    }

    public boolean checkFutoshiki()
    {
        boolean ok = true;
        for(int i=0;i<N ;i++)
        {
            if(!checkRow(i) || !checkColumn(i))
                ok = false;
        }
        for(RelationPair relationPair: relations)
        {
            int firstElem = array[relationPair.getSmaller().getRow()][relationPair.getSmaller().getColumn()];
            int secondElem = array[relationPair.getHigher().getRow()][relationPair.getHigher().getColumn()];
            if(firstElem!=0 && secondElem!=0) {
                if (firstElem>=secondElem)
                ok = false;
            }
        }

        return ok;
    }

    private boolean checkRow(int rowNum)
    {
        List<Integer> visited = new ArrayList<>(N);
        for(int i=0;i<N;i++)
        {
            if(array[rowNum][i]!=0) {
                if (!visited.contains(array[rowNum][i]))
                    visited.add(array[rowNum][i]);
                else {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> getForbiddenValuesInRow(int rowNum)
    {
        List<Integer> visited = new ArrayList<>(N);
        for(int i=0;i<N;i++)
        {
            if(array[rowNum][i]!=0) {
                    visited.add(array[rowNum][i]);
            }
        }
        return visited;
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

    private List<Integer> getForbiddenValuesInColumn(int colNum)
    {
        List<Integer> visited = new ArrayList<>(N);
        for(int i=0;i<N;i++)
        {
            if(array[i][colNum]!=0) {
                    visited.add(array[i][colNum]);
            }
        }
        return visited;
    }

    private List<Integer> getIntegerFromIndices(int x, int y)
    {
        for (Indices ind: map.keySet()
             ) {
            if(ind.x==x && ind.y==y)
                return map.get(ind);
        }
        return null;
    }
public boolean backTracking()
{
    Indices currIndices = findFreeCell();
    //zacznij od -1-1
    //
    boolean isFull = currIndices.x==-1;
    System.out.println(currIndices);
    if(isFull)
    {
        return true;
    }
    else
    {
        for(int val=1;val<=N;val++)
        {
            array[currIndices.x][currIndices.y] = val;
            if(checkFutoshiki())
            {
                if(backTracking())
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

//w forward wykonuje rekurencje jesli napotkam pusta dziedzine
    public boolean forward()
    {
        System.out.print("WYWOLUJE" );
        Indices freeCell = findFreeCell();
        //zacznij od -1-1
        //
        boolean isFreePlace = (freeCell.x!=-1);
        System.out.println(freeCell);
        if(isFreePlace)
        {
            List<Integer> possibleValues = getIntegerFromIndices(freeCell.x,freeCell.y);
            if(possibleValues == null || possibleValues.size()==0) {
                return false;
            }

            for (int i=0;i<possibleValues.size();i++
            ) {
                Map<Indices, List<Integer>>deleted = new HashMap<>();

                array[freeCell.x][freeCell.y]= possibleValues.get(i);
                while(!checkFutoshiki())
                {
                    if(i<possibleValues.size()-1)
                        array[freeCell.x][freeCell.y]= possibleValues.get(++i);
                    else {
                        array[freeCell.x][freeCell.y]=0;
                        System.out.println("Nie mam wiecej zwracam false");
                        return false;
                    }
                }
                System.out.println("Wybrana " + freeCell + "  "  + array[freeCell.x][freeCell.y] );

                boolean isEmpty = false;
                Iterator<Indices> iterator = map.keySet().iterator();
                while(iterator.hasNext() && !isEmpty)
                {
                    Indices ind = iterator.next();
                    if (ind.x == freeCell.x && ind.y > freeCell.y || ind.y == freeCell.y && ind.x > freeCell.x) {
                        deleted.put(ind,new ArrayList<>());
                        System.out.println("INDEX " + ind);
                        List<Integer> list = map.get(ind);
                        System.out.println("PRZED USUWANIEM" + list);
                        Integer intToDelete = new Integer(array[freeCell.x][freeCell.y]);
                        if(list.remove(intToDelete))
                            deleted.get(ind).add(intToDelete);
                        for (RelationPair relPair : relations
                        ) {
                            Coordinate smaller = relPair.getSmaller();
                            Coordinate higher = relPair.getHigher();
                            if (smaller.getRow() == freeCell.x && smaller.getColumn() == freeCell.y
                                    &&higher.getRow()==ind.x && higher.getColumn()==ind.y)
                            {
                                System.out.println("TUUU" + smaller.getRow() + " "+ smaller.getColumn());

                                removeSmaller(list, array[freeCell.x][freeCell.y], deleted.get(ind));
                            }
                            if (higher.getRow() == freeCell.x && higher.getColumn() == freeCell.y
                                    && smaller.getRow()==ind.x && smaller.getColumn()==ind.y){
                                System.out.println("TAAM" + smaller.getRow() + " "+ smaller.getColumn());

                                removeHigher(list, array[freeCell.x][freeCell.y],deleted.get(ind));
                            }
                        }
                        System.out.println("PO USUWANIU: " + list);

                        if (list.isEmpty())
                        {
                            isEmpty = true;
                            for (Indices del: deleted.keySet()
                            ) {
                                for(Integer key: deleted.get(del)) {
                                    map.get(del).add(key);
                                }
                                Collections.sort(map.get(del));

                            }
                        }

                    }
                }
                if(!isEmpty)
                {
                    if(forward())
                        return true;
                    else
                    {
                        for (Indices del: deleted.keySet()
                        ) {
                            for(Integer key: deleted.get(del)) {
                                map.get(del).add(key);
                            }
                            Collections.sort(map.get(del));

                        }
                    }

                }

            }
            if(checkFutoshiki() && findFreeCell().x==-1)
                return true;
            else {
                array[freeCell.x][freeCell.y] = 0;

            }
            return false;
        }
        else
        {
            return true;
        }
    }


//    public boolean forwardChecking()
//    {
//        int firstIndex = 0;
//        Indices freeCell = findFreeCell();
//        boolean isFreePlace = (freeCell.x!=-1);
//        if(isFreePlace)
//        {
//            List<Integer> possibleValues = getIntegerFromIndices(freeCell.x,freeCell.y);
//            if(possibleValues == null)
//                return false;
//            for (Integer possibleVal : possibleValues
//                 ) {
////                    boolean isEmpty = false;
////                    System.out.println("Pierwszy strzał"  + possibleVal );
////                    array[freeCell.x][freeCell.y]= possibleValues.get(firstIndex);
////                    Map<Indices, List<Integer>>deleted = new HashMap<>();
////                    for (Indices ind : map.keySet())
////                    {
////                        if (ind.x == freeCell.x && ind.y > freeCell.y || ind.y == freeCell.y && ind.x > freeCell.x) {
////                            deleted.put(ind,new ArrayList<>());
////                            List<Integer> list = map.get(ind);
////                            Integer intToDelete = new Integer(array[freeCell.x][freeCell.y]);
////                            if(list.remove(intToDelete))
////                                deleted.get(ind).add(intToDelete);
////                            for (RelationPair relPair : relations
////                            ) {
////                                Coordinate smaller = relPair.getSmaller();
////                                Coordinate higher = relPair.getHigher();
////                                if (smaller.getRow() == freeCell.x && smaller.getColumn() == freeCell.y)
////                                {
////                                    removeSmaller(list, array[freeCell.x][freeCell.y], deleted.get(ind));
////                                }
////                                if (higher.getRow() == freeCell.x && higher.getColumn() == freeCell.y){
////                                    removeHigher(list, array[freeCell.x][freeCell.y],deleted.get(ind));
////                                }
////                            }
////                            if (list.isEmpty())
////                            {
////                                isEmpty = true;
////                                for (Indices del: deleted.keySet()
////                                ) {
////                                    for(Integer key: deleted.get(del))
////                                        map.get(del).add(key);
////                                }
////                                break;
////                            }
////                        }
////                    }
//
//            }
//        }
//        else
//        {
//            return true;
//        }
//
//        while(isFreePlace){
//            firstIndex = 0;
//            displayArray();
//            List<Integer> possibleValues = getIntegerFromIndices(freeCell.x,freeCell.y);
//            System.out.println(possibleValues);
//            array[freeCell.x][freeCell.y]= possibleValues.get(firstIndex);
//            System.out.println("Pierwszy strzał"  +array[freeCell.x][freeCell.y] );
//
//            while(!checkFutoshiki()){
//                array[freeCell.x][freeCell.y]=possibleValues.get(++firstIndex);
//            }
//            boolean stop = false;
////            while(!stop) {
////                System.out.println("Wybrano"  +array[freeCell.x][freeCell.y] );
////                boolean isEmpty = false;
////                Map<Indices, List<Integer>>deleted = new HashMap<>();
////                 for (Indices ind : map.keySet())
////                {
////                    if (ind.x == freeCell.x && ind.y > freeCell.y || ind.y == freeCell.y && ind.x > freeCell.x) {
////                        deleted.put(ind,new ArrayList<>());
////                        List<Integer> list = map.get(ind);
////                        Integer intToDelete = new Integer(array[freeCell.x][freeCell.y]);
////                        if(list.remove(intToDelete))
////                            deleted.get(ind).add(intToDelete);
////                        for (RelationPair relPair : relations
////                        ) {
////                            Coordinate smaller = relPair.getSmaller();
////                            Coordinate higher = relPair.getHigher();
////                            if (smaller.getRow() == freeCell.x && smaller.getColumn() == freeCell.y)
////                            {
////                                removeSmaller(list, array[freeCell.x][freeCell.y], deleted.get(ind));
////                            }
////                            if (higher.getRow() == freeCell.x && higher.getColumn() == freeCell.y){
////                                removeHigher(list, array[freeCell.x][freeCell.y],deleted.get(ind));
////                            }
////                        }
////                        if (list.isEmpty())
////                        {
////                            isEmpty = true;
////                            for (Indices del: deleted.keySet()
////                                 ) {
////                                for(Integer key: deleted.get(del))
////                                    map.get(del).add(key);
////                            }
////                            break;
////                        }
////                    }
////                }
//                if(!isEmpty) {
//                    stop = true;
//                }
//                else
//                    array[freeCell.x][freeCell.y]=possibleValues.get(++firstIndex);
//            }
//            freeCell = findFreeCell();
//            isFreePlace = (freeCell.x!=-1);
//        }





    private void removeSmaller(List<Integer> list, int value, List<Integer> deleted)
    {
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext())
        {
            int valToRem= iterator.next();
            if( valToRem<value) {
                deleted.add(valToRem);
                iterator.remove();
            }
        }
    }

    private void removeHigher(List<Integer> list, int value,List<Integer> deleted)
    {
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext())
        {
            int valToRem= iterator.next();
            if(valToRem>value) {
                deleted.add(valToRem);
                iterator.remove();
            }
        }
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
