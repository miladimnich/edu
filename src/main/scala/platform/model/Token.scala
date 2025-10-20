package platform.model

class Token(var amount: Int, val symb: String) {

  def addAmount(value: Token): Token = {
    if (this.symb == value.symb) {
      new Token(this.amount + value.amount, this.symb)
    } else {
      throw new IllegalArgumentException("Not match token symb!")
    }
  }

  def substructAmount(value: Token): Token = {
    if (this.symb == value.symb && this.amount >= value.amount) {
      new Token(this.amount - value.amount, this.symb)
    } else {
      throw new IllegalArgumentException("Not match token symb!")
    }
  }

  def Token_inf(): String = "Amount: " + amount + symb
  override def toString: String = s"Token(amount=$amount, symb=$symb)"
}
