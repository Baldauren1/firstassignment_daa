package org.example.sort;
import org.example.metrics.Metrics;
import org.example.util.ArrayUtil;
import java.util.Random;
public class QuickSort {
    private Metrics m;
    private Random rnd = new Random(12345);
    public QuickSort(Metrics m){ this.m=m; }
    public void sort(int[] a){
        if(a==null || a.length<=1) return;
        ArrayUtil.shuffle(a, rnd);
        sort(a,0,a.length-1);
    }
    private void sort(int[] a, int l, int r){
        while(l<r){
            m.enter();
            if(r-l+1<=16){
                for(int i=l;i<=r;i++){
                    for(int j=i;j>l;j--){
                        m.comparisons++;
                        if(a[j]<a[j-1]){ int t=a[j]; a[j]=a[j-1]; a[j-1]=t; m.swaps++; } else break;
                    }
                }
                m.exit();
                return;
            }
            int p = partition(a,l,r);
            if(p-l < r-p){
                sort(a,l,p-1);
                l = p+1;
            } else {
                sort(a,p+1,r);
                r = p-1;
            }
            m.exit();
        }
    }
    private int partition(int[] a, int l, int r){
        int pivot = a[l + rnd.nextInt(r-l+1)];
        int i = l, j = r;
        while(i<=j){
            while(i<=j){ m.comparisons++; if(a[i]<pivot) i++; else break; }
            while(i<=j){ m.comparisons++; if(a[j]>pivot) j--; else break; }
            if(i<=j){ int t=a[i]; a[i]=a[j]; a[j]=t; m.swaps++; i++; j--; }
        }
        return i-1;
    }
}
