package platform.finance

import platform.finance.Token

trait Trading {
  def sell(amount:Token):Unit
  def buy(amount:Token):Unit

}
