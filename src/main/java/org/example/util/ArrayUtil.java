package org.example.util;
import java.util.Random;
public class ArrayUtil {
    public static void shuffle(int[] a, Random rnd){
        for(int i=a.length-1;i>0;i--){
            int j=rnd.nextInt(i+1);
            int t=a[i]; a[i]=a[j]; a[j]=t;
        }
    }
}
