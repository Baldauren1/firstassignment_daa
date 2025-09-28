# Algorithms — Assignment 1 (Updated)

This repo contains implementations, tests, and measurement harness for:
- MergeSort
- QuickSort
- Deterministic Select (Median-of-Medians)
- Closest Pair (2D)

## What I added in this updated package
- measurement harness using `System.nanoTime()` (produces `metrics_ns.csv`)
- detailed JUnit tests conforming to "Testing 10%" requirements
- `scripts/aggregate_and_plot.py` to produce `metrics_summary_ns.csv`, `plots/time_vs_n_ns.png`, and `plots/execution_time_table.md`

## Quick start (Windows + IntelliJ IDEA 2024.3.1)
1. Unzip `assignment_repo_updated.zip` to a folder, e.g. `C:\\Users\\<you>\\projects\\assignment_repo_updated`.
2. Open IntelliJ -> File -> Open -> choose the folder above (it contains `pom.xml`).
3. Wait for Maven to import dependencies.
4. Confirm Project SDK is set to JDK 17+: File -> Project Structure -> Project SDK.

## Run tests
- In IntelliJ: Right-click `src/test/java` -> Run 'All Tests'
- Or in terminal: `mvn test`

## Generate measurements (nanoTime)
- In IntelliJ: open `src/main/java/org/example/cli/Main.java` -> Right-click -> Run 'Main.main()'
- Or in terminal: `mvn -q compile exec:java -Dexec.mainClass=org.example.cli.Main`
- After run you will get `metrics_ns.csv` in project root.

## Aggregate and plot (Python)
1. Install requirements: `pip install -r requirements.txt`.
2. Run: `python scripts/aggregate_and_plot.py`.
3. Outputs: `metrics_summary_ns.csv`, `plots/time_vs_n_ns.png`, `plots/execution_time_table.md`.

## What to commit/push (recommended workflow)
Create and push small, focused branches (example):
- feature/mergesort
- feature/quicksort
- feature/select
- feature/closest
- feature/metrics
- docs/results

Make descriptive commits as you work (see `git_commit_log.txt` for example messages).

## Notes
- `metrics_ns.csv` is **not ignored**; include it in your repo if the instructor requires real measurements.
- After generating plots, paste the contents of `plots/execution_time_table.md` into README under "Execution time (nanoseconds)" and commit.
