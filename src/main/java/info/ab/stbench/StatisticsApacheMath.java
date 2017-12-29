/*
 * Copyright 2017 Aleksei Balan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.ab.stbench;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class StatisticsApacheMath implements Statistics {
  private final DescriptiveStatistics statistics;
  public StatisticsApacheMath() {
    statistics = new DescriptiveStatistics();
    statistics.setPercentileImpl((new Percentile()).withEstimationType(Percentile.EstimationType.R_1));
  }

  @Override
  public String getName() {
    return "Apache Math";
  }

  @Override
  public void addValue(int value) {
    statistics.addValue(value);
  }

  @Override
  public double getPercentile(int percent) {
    return statistics.getPercentile(percent);
  }

  @Override
  public double getMedian() {
    return statistics.getPercentile(50);
  }
}
