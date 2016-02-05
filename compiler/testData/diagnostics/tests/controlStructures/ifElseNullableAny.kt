// FILE: Java.java

class Java {
    public static Object get(Object o) { return o; }
}

// FILE: test.kt

class Container<T>(val x: T)

fun call(arg: Container<*>): Any? {
    // No errors should be here
    return Java.get(if (true) arg.x else null)
}
