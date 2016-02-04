@file:JvmVersion
@file:JvmName("ConstantsKt")
package kotlin.io

/**
 * Returns the default buffer size when working with buffered streams.
*/

public const val DEFAULT_BUFFER_SIZE: Int = 16 * 1024

@Deprecated("Use DEFAULT_BUFFER_SIZE constant instead.", ReplaceWith("kotlin.io.DEFAULT_BUFFER_SIZE"), level = DeprecationLevel.ERROR)
public val defaultBufferSize: Int = DEFAULT_BUFFER_SIZE

/**
 * Returns the default block size for forEachBlock().
 */
internal const val DEFAULT_BLOCK_SIZE: Int = 4096
/**
 * Returns the minimum block size for forEachBlock().
 */
internal const val MINIMUM_BLOCK_SIZE: Int = 512