package platform.model

class Address(private val country: String,
              private val city: String) {
  override def toString: String = s"$city, $country"
}