package platform.service

import platform.main_platform.Platform

object Simulation {
  def runMonths(count: Int): Unit = {
    for (month <- 1 to count) {
      Platform.nextMonth(month)
    }
  }
}
