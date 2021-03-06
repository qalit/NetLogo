// (C) Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.headless

// Most testing of error messages can go in test/commands/*.txt.  Some tests are here because
// they go beyond the capabilities of the txt-based stuff.  (In the long run, perhaps
// that framework should be extended so these tests could be done in it.)  - ST 3/18/08, 8/21/13

import org.scalatest.{ FunSuite, BeforeAndAfterEach }
import org.nlogo.{ api, nvm }

class TestErrorMessages extends FunSuite with AbstractTestLanguage with BeforeAndAfterEach {

  override def beforeEach() { init() }
  override def afterEach() { workspace.dispose() }

  test("perspectiveChangeWithOf") {
    testCommand(
      "create-frogs 3 [ set spots turtle ((who + 1) mod count turtles) ]")
    testCommand(
      "ask frog 2 [ die ]")
    val ex = intercept[nvm.EngineException] {
      testCommand(
        "ask turtle 0 [ __ignore [who] of frogs with " +
        "[age = ([age] of [spots] of self)]]")
    }
    // is the error message correct?
    assertResult("That frog is dead.")(ex.getMessage)
    // is the error message attributed to the right agent? frog 2 is dead,
    // but it's frog 1 that actually encountered the error
    assertResult("frog 1")(ex.context.agent.toString)
  }

  test("argumentTypeException") {
    testCommand("set glob1 [1.4]")
    val ex = intercept[nvm.ArgumentTypeException] {
      testCommand("__ignore 0 < position 5 item 0 glob1") }
    val message =
      "POSITION expected input to be a string or list but got the number 1.4 instead."
    assertResult(message)(ex.getMessage)
    assertResult("POSITION")(ex.instruction.token.name.toUpperCase)
  }

  test("breedOwnRedeclaration") {
    val ex = intercept[api.CompilerException] {
      compiler.compileProgram(
        "breed [hunters hunter] hunters-own [fear] hunters-own [loathing]",
        api.Program.empty(api.Version.is3D),
        workspace.getExtensionManager)
    }
    assertResult("Redeclaration of HUNTERS-OWN")(ex.getMessage)
  }

}
