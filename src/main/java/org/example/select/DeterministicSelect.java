package org.example.select;
import org.example.metrics.Metrics;
public class DeterministicSelect {
    private Metrics m;
    public DeterministicSelect(Metrics m){ this.m=m; }
    public int select(int[] a, int k){
        if(a==null || a.length==0) throw new IllegalArgumentException("empty array");
        if(k<0 || k>=a.length) throw new IllegalArgumentException("k out of range");
        return select(a,0,a.length-1,k);
    }
    private int select(int[] a, int l, int r, int k){
        m.enter();
        if(l==r){ m.exit(); return a[l]; }
        int n = r-l+1;
        int numGroups = (n+4)/5;
        for(int i=0;i<numGroups;i++){
            int s = l + i*5;
            int e = Math.min(s+4, r);
            insertionSort(a, s, e);
            int mid = (s+e)/2;
            int tmp = a[l+i]; a[l+i] = a[mid]; a[mid] = tmp;
            m.swaps++;
        }
        int medianOfMedians = (numGroups==1) ? a[l] : select(a, l, l+numGroups-1, numGroups/2);
        int pivot = medianOfMedians;
        int i = l, j = r;
        while(i<=j){
            while(i<=j){ m.comparisons++; if(a[i]<pivot) i++; else break; }
            while(i<=j){ m.comparisons++; if(a[j]>pivot) j--; else break; }
            if(i<=j){ int t=a[i]; a[i]=a[j]; a[j]=t; m.swaps++; i++; j--; }
        }
        int leftSize = j - l + 1;
        int res;
        if(k < leftSize) res = select(a, l, j, k);
        else if(k > leftSize) res = select(a, i, r, k - leftSize - (i - j - 1));
        else res = a[j+1];
        m.exit();
        return res;
    }
    private void insertionSort(int[] a, int l, int r){
        for(int i=l;i<=r;i++){
            for(int j=i;j>l;j--){
                m.comparisons++;
                if(a[j]<a[j-1]){ int t=a[j]; a[j]=a[j-1]; a[j-1]=t; m.swaps++; } else break;
            }
        }
    }
}
