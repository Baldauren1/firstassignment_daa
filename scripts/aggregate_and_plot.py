import pandas as pd
import matplotlib.pyplot as plt
import os

os.makedirs('plots', exist_ok=True)

df = pd.read_csv('metrics_ns.csv')
summary = df.groupby(['algo','n'])['time_ns'].agg(['mean','median','std','count']).reset_index()
summary = summary.rename(columns={'mean':'avg_ns','median':'median_ns','std':'std_ns','count':'count'})
summary.to_csv('metrics_summary_ns.csv', index=False)

plt.figure()
for algo in summary['algo'].unique():
    sub = summary[summary['algo']==algo].sort_values('n')
    plt.plot(sub['n'], sub['avg_ns'], marker='o', label=algo)
plt.xlabel('n')
plt.ylabel('avg time (ns)')
plt.title('Average execution time (ns) vs n')
plt.legend()
plt.grid(True)
plt.savefig('plots/time_vs_n_ns.png')
plt.close()

md = summary.pivot_table(index='n', columns='algo', values='avg_ns')
md = md.sort_index()
md.to_markdown('plots/execution_time_table.md')
print('Saved metrics_summary_ns.csv, plots/time_vs_n_ns.png, plots/execution_time_table.md')
