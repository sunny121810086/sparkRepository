import java.util.Random;

public class QuickSort {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[10];
        for (int i=0; i < arr.length; i++) {
            arr[i] = random.nextInt(99);
        }
        myPrint(arr);
        System.out.println("-----------------------------");
        quickSort(arr,0,arr.length-1) ;
        myPrint(arr);
    }
    public static void quickSort(int[] arr,int left,int right) {
        int i = left;
        int j = right;
        int temp = 0;
        if (i <= j) {
            temp = arr[i];
            while (i != j) {
                while (i<j && temp <= arr[j]) {
                    j--;
                }
                arr[i] = arr[j];
                while (i<j && arr[i] <= temp) {
                    i++;
                }
                arr[j] = arr[i];
            }
            arr[i] = temp;
            quickSort(arr,left,i-1);
            quickSort(arr,j+1,right);
        }
    }

    public static void myPrint(int[] arr) {
        for (int i=0;i < arr.length;i++) {
            System.out.println(arr[i]);
        }
    }
}
