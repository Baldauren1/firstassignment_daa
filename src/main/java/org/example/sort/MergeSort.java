package org.example.sort;
import org.example.metrics.Metrics;
public class MergeSort {
    private Metrics m;
    public MergeSort(Metrics m){ this.m=m; }
    public void sort(int[] a){
        if(a==null || a.length<=1) return;
        int[] buf = new int[a.length];
        sort(a, buf, 0, a.length-1);
    }
    private void sort(int[] a, int[] buf, int l, int r){
        m.enter();
        if(l>=r){ m.exit(); return; }
        if(r-l+1<=16){ // insertion cutoff
            for(int i=l;i<=r;i++){
                for(int j=i;j>l;j--){
                    m.comparisons++;
                    if(a[j]<a[j-1]){ int t=a[j]; a[j]=a[j-1]; a[j-1]=t; m.swaps++; } else break;
                }
            }
            m.exit();
            return;
        }
        int mid=(l+r)/2;
        sort(a,buf,l,mid);
        sort(a,buf,mid+1,r);
        int i=l,j=mid+1,k=l;
        while(i<=mid && j<=r){
            m.comparisons++;
            if(a[i]<=a[j]) buf[k++]=a[i++]; else buf[k++]=a[j++];
        }
        while(i<=mid) buf[k++]=a[i++];
        while(j<=r) buf[k++]=a[j++];
        for(k=l;k<=r;k++) a[k]=buf[k];
        m.exit();
    }
}
