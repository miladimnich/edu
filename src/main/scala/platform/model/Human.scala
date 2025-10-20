package platform.model

class Human(val id: Int,
            val name: String,
            val address: Address) extends Trading {

  private var token: Token = new Token(0, "MP")

  def setToken(t: Token): Unit = {
    token = t
  }

  def getToken: Token = {
    token
  }


  override def sell(value: Token): Unit = {
    if (token.amount >= value.amount) {
      token.amount -= value.amount
    } else {
      println("Not enough money to sell")
    }
  }

  override def buy(amount: Token): Unit = {
    Exchange.buy(amount)
    token = token.addAmount(amount)
  }
}
