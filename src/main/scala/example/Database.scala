package example

import cats.effect.kernel.Resource
import cats.effect.IO

final case class Database(
  host: String
) {
  def initSchema: Unit = {
    println("executing migrator ...")
  }
}

object Database {
  def make(host: String): Resource[IO, Database] = Resource.make {

    // IO.blocking instead of IO.delay if the action performs blocking IO - will make sure to execute it on the blocking IO thread pool
    IO.blocking {
      // the acquire action, some side-effecting code here
      println("acquiring database")
      Database(host)
    }
  } { db =>
    // the release action, some side-effecting code here
    IO.blocking {
      println(s"disposing database ${db.host}. Bye!")
    }
  }
}
