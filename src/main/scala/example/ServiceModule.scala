package example

import cats.effect.kernel.Resource
import cats.effect.IO

final case class ServiceModule(
  db: Database,
  httpClient: HttpClient
)

object ServiceModule {

  // the good thing about resources is they compose
  def make(dbHost: String): Resource[IO, ServiceModule] = for {
    db <- Database.make(dbHost)
    client <- HttpClient.make
  } yield ServiceModule(
    db = db,
    httpClient = client
  )
  
}
