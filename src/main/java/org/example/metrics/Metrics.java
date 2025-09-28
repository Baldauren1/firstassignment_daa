package org.example.metrics;
public class Metrics {
    public long comparisons = 0;
    public long swaps = 0;
    private int maxDepth = 0;
    private ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
    public void enter() { depth.set(depth.get()+1); if(depth.get()>maxDepth) maxDepth = depth.get(); }
    public void exit() { depth.set(depth.get()-1); }
    public void reset() { comparisons = 0; swaps = 0; maxDepth = 0; depth.set(0); }
    public int getMaxRecursionDepth() { return maxDepth; }
}
