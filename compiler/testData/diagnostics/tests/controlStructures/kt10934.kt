/** A parser can return one of two Results */
sealed class Result<TInput, TValue> {

    class Value<TInput, TValue>(val value: TValue, val rest: TInput) : Result<TInput, TValue>() {}

    class ParseError<TInput, TValue>(val productionLabel: String,
                                     val child: ParseError<TInput, *>?,
                                     val rest: TInput) : Result<TInput, TValue>() {}
}

class Parser<TInput, TValue>(val f: (TInput) -> Result<TInput, TValue>) {

    operator fun invoke(input: TInput): Result<TInput, TValue> = f(input)

    fun <TIntermediate, TValue2> mapJoin(
            selector: (TValue) -> Parser<TInput, TIntermediate>,
            projector: (TValue, TIntermediate) -> TValue2
    ): Parser<TInput, TValue2> {
        return Parser({ input ->
            val res = this(input)
            when (res) {
                is Result.ParseError -> Result.ParseError(<!DEBUG_INFO_SMARTCAST!>res<!>.productionLabel, <!DEBUG_INFO_SMARTCAST!>res<!>.child, <!DEBUG_INFO_SMARTCAST!>res<!>.rest)
                is Result.Value -> {
                    val v = <!DEBUG_INFO_SMARTCAST!>res<!>.value
                    val res2 = selector(v)(<!DEBUG_INFO_SMARTCAST!>res<!>.rest)
                    when (res2) {
                        is Result.ParseError -> Result.ParseError(<!DEBUG_INFO_SMARTCAST!>res2<!>.productionLabel, <!DEBUG_INFO_SMARTCAST!>res2<!>.child, <!DEBUG_INFO_SMARTCAST!>res2<!>.rest)
                        is Result.Value -> Result.Value(projector(v, <!DEBUG_INFO_SMARTCAST!>res2<!>.value), <!DEBUG_INFO_SMARTCAST!>res2<!>.rest)
                    }
                }
            }
        })
    }
}