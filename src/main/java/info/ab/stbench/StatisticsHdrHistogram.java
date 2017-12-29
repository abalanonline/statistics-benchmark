package info.ab.stbench;

import org.HdrHistogram.Histogram;

public class StatisticsHdrHistogram implements Statistics {
  private final Histogram histogram;
  public StatisticsHdrHistogram() {
    histogram = new Histogram(5);
  }

  @Override
  public String getName() {
    return "HdrHistogram";
  }

  @Override
  public void addValue(int value) {
    histogram.recordValue(value);
  }

  @Override
  public double getPercentile(int percent) {
    return histogram.getValueAtPercentile(percent / 100.0);
  }

  @Override
  public double getMedian() {
    return histogram.getValueAtPercentile(0.5);
  }
}
