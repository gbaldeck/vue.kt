import io.vuekt.framework.vue.external.VueObj
import mock.*
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by gbaldeck on 7/1/2017.
 */

class VueComponentComputedTests {
  @Test
  fun vueComponentInstanceShouldThrowNoAssignedSetterError(){

    val vuektcomponent = VueComponentMockComputed3()
    val actual = vuektcomponent.getActual()
    val constructor = VueObj.extend(actual)
    val component = js("new constructor().\$mount()")

    try {
      component.testComputed = "test2"
    } catch (e: Exception) {
      console.log(e.message)
      assertEquals(e.message!!.contains("was not assigned a setter"), true)
    }
  }

  @Test
  fun componentShouldThrowNoAssignedSetterError(){
    try {
      val vuektcomponent = VueComponentMockComputed4()
    } catch (e: Exception) {
      console.log(e.message)
      assertEquals(e.message!!.contains("was not assigned a setter"), true)
    }
  }

  @Test
  fun componentShouldThrowSetterNotAssignedAtThisPointError(){
    try {
      val vuektcomponent = VueComponentMockComputed5()
    } catch (e: Exception) {
      console.log(e.message)
      assertEquals(e.message!!.contains("has not been assigned before this call to set"), true)
    }
  }

  @Test
  fun componentShouldThrowGetterNotAssignedAtThisPointError(){
    try {
      val vuektcomponent = VueComponentMockComputed6()
    } catch (e: Exception) {
      console.log(e.message)
      assertEquals(e.message!!.contains("has not been assigned before this call to get"), true)
    }
  }

  @Test
  fun vueComponentInstanceTestComputedShouldEqualTest(){
    val vuektcomponent = VueComponentMockComputed7()
    val actual = vuektcomponent.getActual()
    val constructor = VueObj.extend(actual)
    val component = js("new constructor().\$mount()")

    assertEquals(component.testComputed, "test")
  }

  @Test
  fun componentTestComputedShouldEqualTest(){
    val vuektcomponent = VueComponentMockComputed8()
    assertEquals(vuektcomponent.testComputed, "test")
  }
}