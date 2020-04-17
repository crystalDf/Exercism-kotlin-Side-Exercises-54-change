class ChangeCalculator(private val coins: List<Int>) {

    fun computeMostEfficientChange(grandTotal: Int): List<Int> {

        require(grandTotal >= 0) { "Negative totals are not allowed." }
        require(grandTotal == 0 || grandTotal >= coins.min()!!)
        { "The total $grandTotal cannot be represented in the given currency." }

        return computeSubMostEfficientChange(grandTotal)?.toList() ?:
        throw IllegalArgumentException("The total $grandTotal cannot be represented in the given currency.")
    }

    private fun computeSubMostEfficientChange(grandTotal: Int): List<Int>? {

        if (grandTotal == 0) return emptyList()
        if (grandTotal in coins) return listOf(grandTotal)

        return coins.filter { it < grandTotal }.mapNotNull { it ->
            if (1 in coins) computeSubMostEfficientChange(grandTotal % it)
                    ?.plus(generateSequence { it }.take(grandTotal / it))
            else computeSubMostEfficientChange(grandTotal - it)
                    ?.plus(it)
        }.minBy { it.size }
    }
}
