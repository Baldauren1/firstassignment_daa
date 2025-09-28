package org.example.closest;
import org.example.metrics.Metrics;
import java.util.Arrays;
import java.util.Comparator;
public class ClosestPair {
    private Metrics m;
    public ClosestPair(Metrics m){ this.m=m; }
    public double closest(Point[] pts){
        if(pts==null || pts.length<2) return Double.POSITIVE_INFINITY;
        Arrays.sort(pts, Comparator.comparingDouble(p->p.x));
        return rec(Arrays.copyOf(pts, pts.length), 0, pts.length-1);
    }
    private double rec(Point[] pts, int l, int r){
        m.enter();
        if(r-l<=0){ m.exit(); return Double.POSITIVE_INFINITY; }
        if(r-l==1){ m.exit(); return dist(pts[l], pts[r]); }
        int mid=(l+r)/2;
        double midx = pts[mid].x;
        double d = Math.min(rec(pts,l,mid), rec(pts,mid+1,r));
        Point[] strip = new Point[r-l+1];
        int cnt=0;
        for(int i=l;i<=r;i++) if(Math.abs(pts[i].x - midx) < d) strip[cnt++]=pts[i];
        Arrays.sort(strip,0,cnt, Comparator.comparingDouble(p->p.y));
        for(int i=0;i<cnt;i++){
            for(int j=i+1;j<cnt && j<i+8;j++){
                m.comparisons++;
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }
        m.exit();
        return d;
    }
    private double dist(Point a, Point b){
        double dx=a.x-b.x, dy=a.y-b.y;
        return Math.hypot(dx,dy);
    }
    public static class Point{ public double x,y; public Point(double x,double y){this.x=x;this.y=y;} }
}
