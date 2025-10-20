package platform.model

trait Trading {
  def sell(amount:Token):Unit
  def buy(amount:Token):Unit

}
