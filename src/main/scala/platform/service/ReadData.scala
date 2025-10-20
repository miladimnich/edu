package platform.service

import ujson._
import scala.io.Source

class ReadData {

  def readJsonArray(file: String): ujson.Arr = {
    val source = Source.fromFile(file)
    try {
      ujson.read(source.mkString).arr
    } finally {
      source.close()
    }
  }
}
