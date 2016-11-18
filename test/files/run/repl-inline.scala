import scala.tools.nsc._

object Test {
  val testCode = """
def callerOfCaller = Thread.currentThread.getStackTrace.drop(2).head.getMethodName
def g = callerOfCaller
def h = g
assert(h == "g", h)
@inline def g = callerOfCaller
def h = g
assert(h == "h", h)
  """

  def main(args: Array[String]) {
    val settings = new Settings()
    settings.processArgumentString("-opt:l:classpath")
    settings.usejavacp.value = true
    val repl = new interpreter.IMain(settings)
    testCode.linesIterator.foreach(repl.interpret(_))
  }
}
