package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterAll

import cats.effect.kernel.Resource
import cats.effect.IO

import cats.effect.unsafe.implicits.global

trait BaseServiceSpec extends AnyFlatSpec with BeforeAndAfterAll {
  
  // note the expression type. There is nothing "happening" yet, this is just a program description
  val modules: Resource[IO, ServiceModule] = ServiceModule.make("db-test-host")

  // from the ivory tower to side-effect land. This approach should also translate to the real Main / Boot class
  // Initially, we can start with just wrapping database / HyraxDb in Resource, and convert more modules to Resources as we go.
  lazy val (serviceModule, disposeServiceModule) = modules
    .allocated // IO[(ServiceModule, IO[Unit])]
    .unsafeRunSync() // (ServiceModule, IO[Unit])

  override protected def beforeAll(): Unit = {
    serviceModule.db.initSchema
    // other stuff ...
  }

  override protected def afterAll(): Unit = {
    disposeServiceModule.unsafeRunSync()
  }

}
