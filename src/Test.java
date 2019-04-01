import java.util.Arrays;

public class Test {

    public static void displayArray(int[][] array)
    {
        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array.length;j++)
            {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String [] args)
    {
        LoaderFutoshiki loaderFutoshiki = new LoaderFutoshiki();
        loaderFutoshiki.load("C:\\Users\\Agata Rychter\\Documents\\Studia\\AI\\Lab2_dane_testowe_v1.0\\futoshiki_4_1.txt");
        System.out.println(loaderFutoshiki.getN());
        loaderFutoshiki.displayArray();
//        System.out.println(loaderFutoshiki.backTracking());
        System.out.println(loaderFutoshiki.forward());
        loaderFutoshiki.displayArray();

//        1    4    2    3
//        3    1    4    2
//        2    3    1    4
//        4    2    3    1


//        LoaderSkyscraper loaderSkyscraper = new LoaderSkyscraper();
//        loaderSkyscraper.load("C:\\Users\\Agata Rychter\\Documents\\Studia\\AI\\Lab2_dane_testowe_v1.0\\skyscrapper_4_3.txt");
//
//
//        loaderSkyscraper.array[0][0] = 4;
//        loaderSkyscraper.array[0][1] = 1;
//        loaderSkyscraper.array[0][2] = 2;
//        loaderSkyscraper.array[0][3] = 3;
//
//        loaderSkyscraper.array[1][0] = 2;
//        loaderSkyscraper.array[1][1] = 3;
//        loaderSkyscraper.array[1][2] = 4;
//        loaderSkyscraper.array[1][3] = 1;
//
//        loaderSkyscraper.array[2][0] = 1;
//        loaderSkyscraper.array[2][1] = 2;
//        loaderSkyscraper.array[2][2] = 3;
//        loaderSkyscraper.array[2][3] = 4;
//
//        loaderSkyscraper.array[3][0] = 3;
//        loaderSkyscraper.array[3][1] = 4;
//        loaderSkyscraper.array[3][2] = 1;
//        loaderSkyscraper.array[3][3] = 2;

//        skyscrapper 4-3przyklad
//        4 1 2 3
//        2 3 4 1
//        1 2 3 4
//        3 4 1 2

//        displayArray(loaderSkyscraper.array);
//        System.out.println(loaderSkyscraper.checkColumnDown(1));
//
//
//        System.out.println(loaderSkyscraper.checkSkyscraper());
//        displayArray(loaderSkyscraper.array);
//
//        System.out.println(loaderSkyscraper.hasEmptyCellsRow(0));
//
//        System.out.println(loaderSkyscraper.back());
//        displayArray(loaderSkyscraper.array);


    }
}
