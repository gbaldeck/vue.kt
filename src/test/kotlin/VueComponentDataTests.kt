import io.vuekt.framework.vue.external.VueObj
import mock.VueComponentMockData1
import mock.VueComponentMockData2
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by gbaldeck on 7/1/2017.
 */
class VueComponentDataTests {
  @Test
  fun vueComponentInstanceInitialValue() {
    val vuektcomponent = VueComponentMockData1()
    val actual = vuektcomponent.getActual()
    val constructor = VueObj.extend(actual)
    val component = js("new constructor().\$mount()")

    assertEquals(component.test, "test")
  }

  @Test
  fun componentInitialValue() {
    val vuektcomponent = VueComponentMockData1()

    assertEquals(vuektcomponent.test, "test")
  }

  @Test
  fun vueComponentReassignInitialValue() {
    val vuektcomponent = VueComponentMockData2()
    val actual = vuektcomponent.getActual()
    val constructor = VueObj.extend(actual)
    val component = js("new constructor().\$mount()")

    assertEquals(component.test, "test2")
  }

  @Test
  fun componentReassignInitialValue() {
    val vuektcomponent = VueComponentMockData2()

    assertEquals(vuektcomponent.test, "test2")
  }
}