@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("CollectionsKt")

package kotlin.collections

import java.util.*

/**
 * Removes a single instance of the specified element from this
 * collection, if it is present.
 *
 * Allows to overcome type-safety restriction of `remove` that requires to pass an element of type `E`.
 *
 * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
 */
@kotlin.internal.InlineOnly
public inline fun <@kotlin.internal.OnlyInputTypes T> MutableCollection<out T>.remove(element: T): Boolean = (this as MutableCollection<T>).remove(element)

/**
 * Removes all of this collection's elements that are also contained in the specified collection.

 * Allows to overcome type-safety restriction of `removeAll` that requires to pass a collection of type `Collection<E>`.
 *
 * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
 */
@kotlin.internal.InlineOnly
public inline fun <@kotlin.internal.OnlyInputTypes T> MutableCollection<out T>.removeAll(elements: Collection<T>): Boolean = (this as MutableCollection<T>).removeAll(elements)

/**
 * Retains only the elements in this collection that are contained in the specified collection.
 *
 * Allows to overcome type-safety restriction of `retailAll` that requires to pass a collection of type `Collection<E>`.
 *
 * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
 */
@kotlin.internal.InlineOnly
public inline fun <@kotlin.internal.OnlyInputTypes T> MutableCollection<out T>.retainAll(elements: Collection<T>): Boolean = (this as MutableCollection<T>).retainAll(elements)

/**
 * Adds the specified [element] to this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {
    this.add(element)
}

/**
 * Adds all elements of the given [elements] collection to this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Iterable<T>) {
    this.addAll(elements)
}

/**
 * Adds all elements of the given [elements] array to this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Array<T>) {
    this.addAll(elements)
}

/**
 * Adds all elements of the given [elements] sequence to this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Sequence<T>) {
    this.addAll(elements)
}

/**
 * Removes a single instance of the specified [element] from this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.minusAssign(element: T) {
    this.remove(element)
}

/**
 * Removes all elements contained in the given [elements] collection from this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Iterable<T>) {
    this.removeAll(elements)
}

/**
 * Removes all elements contained in the given [elements] array from this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Array<T>) {
    this.removeAll(elements)
}

/**
 * Removes all elements contained in the given [elements] sequence from this mutable collection.
 */
@kotlin.internal.InlineOnly
public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Sequence<T>) {
    this.removeAll(elements)
}

/**
 * Adds all elements of the given [elements] collection to this [MutableCollection].
 */
public fun <T> MutableCollection<in T>.addAll(elements: Iterable<T>): Boolean {
    when (elements) {
        is Collection -> return addAll(elements)
        else -> {
            var result: Boolean = false
            for (item in elements)
                if (add(item)) result = true
            return result
        }
    }
}

/**
 * Adds all elements of the given [elements] sequence to this [MutableCollection].
 */
public fun <T> MutableCollection<in T>.addAll(elements: Sequence<T>): Boolean {
    var result: Boolean = false
    for (item in elements) {
        if (add(item)) result = true
    }
    return result
}

/**
 * Adds all elements of the given [elements] array to this [MutableCollection].
 */
public fun <T> MutableCollection<in T>.addAll(elements: Array<out T>): Boolean {
    return addAll(elements.asList())
}

/**
 * Removes all elements from this [MutableIterable] that match the given [predicate].
 */
public fun <T> MutableIterable<T>.removeAll(predicate: (T) -> Boolean): Boolean = filterInPlace(predicate, true)

/**
 * Retains only elements of this [MutableIterable] that match the given [predicate].
 */
public fun <T> MutableIterable<T>.retainAll(predicate: (T) -> Boolean): Boolean = filterInPlace(predicate, false)

private fun <T> MutableIterable<T>.filterInPlace(predicate: (T) -> Boolean, predicateResultToRemove: Boolean): Boolean {
    var result = false
    with (iterator()) {
        while (hasNext())
            if (predicate(next()) == predicateResultToRemove) {
                remove()
                result = true
            }
    }
    return result
}

/**
 * Removes all elements from this [MutableList] that match the given [predicate].
 */
public fun <T> MutableList<T>.removeAll(predicate: (T) -> Boolean): Boolean = filterInPlace(predicate, true)

/**
 * Retains only elements of this [MutableList] that match the given [predicate].
 */
public fun <T> MutableList<T>.retainAll(predicate: (T) -> Boolean): Boolean = filterInPlace(predicate, false)

private fun <T> MutableList<T>.filterInPlace(predicate: (T) -> Boolean, predicateResultToRemove: Boolean): Boolean {
    var writeIndex: Int = 0
    for (readIndex in 0..lastIndex) {
        val element = this[readIndex]
        if (predicate(element) == predicateResultToRemove)
            continue

        if (writeIndex != readIndex)
            this[writeIndex] = element

        writeIndex++
    }
    if (writeIndex < size) {
        for (removeIndex in lastIndex downTo writeIndex)
            removeAt(removeIndex)

        return true
    }
    else {
        return false
    }
}

/**
 * Removes all elements from this [MutableCollection] that are also contained in the given [elements] collection.
 */
public fun <T> MutableCollection<in T>.removeAll(elements: Iterable<T>): Boolean {
    return removeAll(elements.convertToSetForSetOperationWith(this))
}

/**
 * Removes all elements from this [MutableCollection] that are also contained in the given [elements] sequence.
 */
public fun <T> MutableCollection<in T>.removeAll(elements: Sequence<T>): Boolean {
    val set = elements.toHashSet()
    return set.isNotEmpty() && removeAll(set)
}

/**
 * Removes all elements from this [MutableCollection] that are also contained in the given [elements] array.
 */
public fun <T> MutableCollection<in T>.removeAll(elements: Array<out T>): Boolean {
    return elements.isNotEmpty() && removeAll(elements.toHashSet())
}

/**
 * Retains only elements of this [MutableCollection] that are contained in the given [elements] collection.
 */
public fun <T> MutableCollection<in T>.retainAll(elements: Iterable<T>): Boolean {
    return retainAll(elements.convertToSetForSetOperationWith(this))
}

/**
 * Retains only elements of this [MutableCollection] that are contained in the given [elements] array.
 */
public fun <T> MutableCollection<in T>.retainAll(elements: Array<out T>): Boolean {
    if (elements.isNotEmpty())
        return retainAll(elements.toHashSet())
    else
        return retainNothing()
}

/**
 * Retains only elements of this [MutableCollection] that are contained in the given [elements] sequence.
 */
public fun <T> MutableCollection<in T>.retainAll(elements: Sequence<T>): Boolean {
    val set = elements.toHashSet()
    if (set.isNotEmpty())
        return retainAll(set)
    else
        return retainNothing()
}

private fun MutableCollection<*>.retainNothing(): Boolean {
    val result = isNotEmpty()
    clear()
    return result
}

/**
 * Sorts elements in the list in-place according to their natural sort order.
 * */
public fun <T: Comparable<T>> MutableList<T>.sort(): Unit {
    if (size > 1) java.util.Collections.sort(this)
}

/**
 *  Sorts elements in the list in-place according to order specified with [comparator].
 */
public fun <T> MutableList<T>.sortWith(comparator: Comparator<in T>): Unit {
    if (size > 1) java.util.Collections.sort(this, comparator)
}
