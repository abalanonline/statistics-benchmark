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
import java.util.Random;
import java.util.Set;

public class StatisticsTester {

  private static final Logger LOGGER = LoggerFactory.getLogger("");

  private static final int NUM_VALUES = 10_000;

  private static final int NUM_CALCULATIONS = 10_000;

  private final Set<Class<? extends Statistics>> statisticsSet;

  public StatisticsTester() {
    statisticsSet = new LinkedHashSet<>();
    statisticsSet.add(StatisticsJOrphan.class);
    statisticsSet.add(StatisticsApacheMath.class);
  }

  public void logResult(final String engineName, final String medianType, final boolean haveBug,
      final long timeAdd, final long timeCalc, final long memAddCalc) {
    LOGGER.info(String.format("%20s | %10s | %10s | %14d | %14d | %14d",
        engineName, medianType, haveBug?"bug":"ok",
        timeAdd/1_000_000, timeCalc/1_000_000, memAddCalc/1024));
  }

  public void test(Statistics statistics) {
    final Random random = new Random();
    statistics.addValue(0);
    statistics.addValue(100);
    final double median = statistics.getMedian();
    final String medianType;
    if (median < 30.0) medianType = "low"; else
      if (median > 70.0) medianType = "high"; else medianType = "mid";
    final boolean haveBug = statistics.getPercentile(51) < 100.0;
    final long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    final long time1 = System.nanoTime();
    for (int i = 0; i < NUM_VALUES; i++) {
      statistics.addValue(random.nextInt());
    }
    final long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    final long time2 = System.nanoTime();
    for (int i = 0; i < NUM_CALCULATIONS; i++) {
      statistics.getPercentile(random.nextInt(100) + 1);
    }
    final long mem3 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    final long time3 = System.nanoTime();
    logResult(statistics.getName(), medianType, haveBug, time2-time1, time3-time2, mem3-mem1);
  }

  public void runOnce() {
    LOGGER.info("   statistics engine |     median | percentile |       add (ms) | calculate (ms) |    memory (KB)");
    for (Class<? extends Statistics> statisticsClass : statisticsSet) {
      final StringBuilder stringBuilder = new StringBuilder();
      try {
        test(statisticsClass.newInstance());
      } catch (final ReflectiveOperationException e) {
        LOGGER.error("", e);
      }
    }
  }
}
