package info.ab.stbench;

import com.tdunning.math.stats.MergingDigest;
import com.tdunning.math.stats.TDigest;

public class StatisticsTDigest implements Statistics {
  final TDigest tDigest;
  public StatisticsTDigest() {
    tDigest = new MergingDigest(100);
  }

  @Override
  public String getName() {
    return "Merging t-digest";
  }

  @Override
  public void addValue(int value) {
    tDigest.add(value);
  }

  @Override
  public double getPercentile(int percent) {
    return tDigest.quantile(percent / 100.0);
  }

  @Override
  public double getMedian() {
    return tDigest.quantile(0.5);
  }
}
