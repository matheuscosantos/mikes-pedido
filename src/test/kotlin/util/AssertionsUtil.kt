package util

import org.junit.jupiter.api.fail

object AssertionsUtil {
    inline fun <reified T : Any> isInstanceType(instance: Any) {
        if (instance !is T) {
            fail { "${instance::class.java} are not a instance of ${T::class.java}" }
        }
    }
}
