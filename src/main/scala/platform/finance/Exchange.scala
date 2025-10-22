package platform.finance

import platform.finance.{Token, Trading}

object Exchange extends Trading {
  private var fiat: Double = _
  private var token: Token = _
  private var initial: Boolean = false

  def init(fiat: Double, token: Token): Unit = {
    if (!initial) {
      this.fiat = fiat
      this.token = token
      this.initial = true
    } else {
      println("..")
    }
  }

  private def getTokenPrice: Double = token.amount / this.fiat

  def get(): (Double, String) = (fiat, token.Token_inf())

  override def sell(value: Token): Unit = {
    token = token.addAmount(value)
    fiat -= value.amount * getTokenPrice
  }

  override def buy(value: Token): Unit = {
    if (token.amount >= value.amount) {
      token = token.substructAmount(value)
      fiat += value.amount * getTokenPrice
    }
  }

  def printExchangeBalance(): Unit = {
    println(f"Exchange balance: Fiat = $fiat%.2f, Tokens = ${token.Token_inf()}")
  }
}
