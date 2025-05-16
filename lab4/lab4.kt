import kotlin.math.abs
import kotlin.math.cos

/**
 * Exercise: Arithmetic Operations
 * Demonstrate various arithmetic operations on variables.
 */
fun demonstrateArithmetic() {
    var customers = 10

    // Some customers leave the queue
    customers = 8

    customers += 3
    customers += 7
    customers -= 3
    customers *= 2
    customers /= 3

    println("Final customer count: $customers")
}

/**
 * Exercise: Variable Declaration and Initialization
 * Demonstrate different ways of declaring and initializing variables.
 */
fun demonstrateVariables() {
    val d: Int

    d = 3

    val e: String = "hello"

    println("d = $d")
    println("e = $e")
}

/**
 * Exercise: Set Membership Check
 * Check if a protocol requested by a user is in the set of supported protocols.
 */
fun checkProtocol() {
    val SUPPORTED = setOf("HTTP", "HTTPS", "FTP")
    val requested = "smtp"
    val isSupported = requested.uppercase() in SUPPORTED
    println("Support for $requested: $isSupported")
}

/**
 * Exercise: Higher-Order Function
 * Repeat an action n times.
 * @param n Number of times to repeat the action
 * @param action The action to repeat
 */
fun repeatN(n: Int, action: () -> Unit) {
    println("Repeating action $n times:")
    for (i in 1..n) {
        action()
    }
}

/**
 * Exercise: Null Safety with Elvis Operator
 * Process a list of elements with null safety.
 * @param elements List of elements to process
 * @return Boolean True if a zero element is found, false otherwise
 */
fun processList(elements: List<Int?>): Boolean {
    for (element in elements) {
        val variable = element ?: run {
            println("Element is null or invalid, continuing...")
        }

        if (variable == 0) return true
    }
    return false
}

/**
 * Exercise: Number Formatting
 * Format a number to a hexadecimal string.
 * @param number Number to format
 * @param minLength Minimum length of the hex string
 * @return String The formatted hex string
 */
fun toHexString(number: Int, minLength: Int): String {
    return number.toString(16).padStart(minLength, '0')
}

/**
 * Exercise: Null Safety with Conditional
 * Print a hello message based on whether the name is null.
 * @param name Name to include in greeting (can be null)
 */
fun printHello(name: String?): Unit {
    if (name != null)
        println("Hello $name")
    else
        println("Hi there!")
}

/**
 * Exercise: Varargs and Collections
 * Convert a variable number of arguments into a list.
 * @param ts Variable number of elements to convert to a list
 * @return List<T> The resulting list
 */
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts)
        result.add(t)
    return result
}

/**
 * Exercise: Iterative Calculation
 * Find the fixed point of cosine function using iterative calculation.
 * @return Double The fixed point value
 */
private fun findFixPoint(): Double {
    var x = 1.0
    val eps = 1e-10

    while (true) {
        val y = cos(x)
        if (abs(x - y) < eps) return x
        x = cos(x)
    }
}

/**
 * Exercise: Function References
 * Demonstrate different ways to use function references.
 */
fun demonstrateFunctionReferences() {
    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))
    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3))
}

/**
 * Exercise: For Loop Iteration
 * Demonstrate iterating through a list with a for loop.
 */
fun demonstrateForLoop() {
    val cakes = listOf("carrot", "cheese", "chocolate")
    for (cake in cakes) {
        println("Yummy, it's a $cake cake!")
    }
}

/**
 * Exercise: While Loop
 * Demonstrate a basic while loop.
 */
fun demonstrateWhileLoop() {
    var cakesEaten = 0
    while (cakesEaten < 3) {
        println("Eat a cake")
        cakesEaten++
    }
}

fun main() {
    println("\n=== Exercise: Arithmetic Operations ===")
    demonstrateArithmetic()

    println("\n=== Exercise: Variable Declaration and Initialization ===")
    demonstrateVariables()

    println("\n=== Exercise: Set Membership Check ===")
    checkProtocol()

    println("\n=== Exercise: Higher-Order Function ===")
    repeatN(3) { println("Action executed!") }

    println("\n=== Exercise: Null Safety with Elvis Operator ===")
    val list1 = listOf(1, 2, null, 3, 0, 5)
    val list2 = listOf(1, 2, 3, 4, 5)
    println("List with zero: ${processList(list1)}")
    println("List without zero: ${processList(list2)}")

    println("\n=== Exercise: Number Formatting ===")
    println("93 in hex format with min length 4: ${toHexString(93, 4)}")

    println("\n=== Exercise: Null Safety with Conditional ===")
    printHello("Kotlin")
    printHello(null)

    println("\n=== Exercise: Varargs and Collections ===")
    val fruits = asList("apple", "banana", "cherry")
    println("Fruits list: $fruits")
    val numbers = asList(1, 2, 3, 4, 5)
    println("Numbers list: $numbers")

    println("\n=== Exercise: Iterative Calculation ===")
    println("Fixed point of cosine: ${findFixPoint()}")

    println("\n=== Exercise: Function References ===")
    demonstrateFunctionReferences()

    println("\n=== Exercise: For Loop Iteration ===")
    demonstrateForLoop()

    println("\n=== Exercise: While Loop ===")
    demonstrateWhileLoop()
}