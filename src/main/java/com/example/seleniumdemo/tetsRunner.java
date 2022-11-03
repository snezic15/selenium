package com.example.seleniumdemo;

public class tetsRunner {

    public static void main( String [] args) {

        int[] arr1 = {9, 3, 9, 3, 9, 7, 9};

        //calculate length of the arrayWithDuplicates array
        int len = arr1.length;

        //create an integer array to store distinct elements
        int[] newArray1 = new int[7];

        //integer variable to increment array index
        int index = 0;

        // outermost loop to take element one by one from the leftmost side
        for (int i = 0; i < len; i++) {
            int flag = 0;
            //innermost loop to make comparision between elemnts and skip duplicate elements
            for (int j = 0; j < i; j++) {
                if (arr1[i] == arr1[j]) {
                    flag = 1;
                    break;
                }
            }
            //store or print the element if it is not present in the distinctArray
            if (flag == 0) {
                newArray1[index] = arr1[i];
                index++;    //increment index value
            }
        }
        //print distinctArray
        for (int i = 0; i < index; i++) {
            System.out.print( newArray1[i] + " Non repeating number");
        }

    }

}
