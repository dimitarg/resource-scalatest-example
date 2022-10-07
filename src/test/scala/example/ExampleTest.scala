package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExampleTest extends BaseServiceSpec {
  "Foo" should "foo" in {
    println("test")  
  }

  "Bar" should "bar" in {
    println("another test")  
  }
} 
