package info.ab.stbench;

import org.apache.jorphan.math.StatCalculatorLong;

public class StatisticsJOrphan implements Statistics {
  private final StatCalculatorLong calc;
  public StatisticsJOrphan() {
    calc = new StatCalculatorLong();
  }

  @Override
  public void addValue(int value) {
    calc.addValue(value);
  }

  @Override
  public double getPercentile(int percent) {
    return calc.getPercentPoint(percent / 100D);
  }

  @Override
  public double getMedian() {
    return calc.getMedian();
  }
}
