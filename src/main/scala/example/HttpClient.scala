package example

import cats.effect.kernel.Resource
import cats.effect.IO

final case class HttpClient() extends AutoCloseable {
  override def close(): Unit = {
    println("disposing of http client")
  }
}

object HttpClient {
  def make: Resource[IO, HttpClient] = {
    // if your module under construction implements AutoCloseable, there's a dedicated shorthand instead of Resource.make
    Resource.fromAutoCloseable {
      IO.delay {
        println("initialising HTTP Client")
        HttpClient()
      }
    }
  }
}