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

import org.apache.jorphan.math.StatCalculatorLong;

public class StatisticsJOrphan implements Statistics {
  private final StatCalculatorLong calc;
  public StatisticsJOrphan() {
    calc = new StatCalculatorLong();
  }

  @Override
  public String getName() {
    return "JOrphan";
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
