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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatisticsTester {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsTester.class);

  private final Set<Class<? extends Statistics>> statisticsSet;

  public StatisticsTester() {
    statisticsSet = new LinkedHashSet<>();
    statisticsSet.add(StatisticsJOrphan.class);
  }

  public String test(Statistics statistics) {
    final StringBuilder stringBuilder = new StringBuilder();
    statistics.addValue(0);
    statistics.addValue(100);
    final double median = statistics.getMedian();
    if (median < 30.0) { stringBuilder.append("  low"); }
    else if (median > 70.0) { stringBuilder.append(" high"); }
    else { stringBuilder.append("  mid"); }
    stringBuilder.append(" ");
    stringBuilder.append(statistics.getPercentile(1));
    return stringBuilder.toString();
  }

  public void runOnce() {
    for (Class<? extends Statistics> statisticsClass : statisticsSet) {
      final StringBuilder stringBuilder = new StringBuilder();
      try {
        LOGGER.info(test(statisticsClass.newInstance()));
      } catch (final ReflectiveOperationException e) {
        LOGGER.error("", e);
      }
    }
  }
}
