package dinotrie

const val WHITESPACE = " "

class Taxonomy {
    private val root = Rank()

    fun addSpecies(ranks: List<String>) {
        root.addSpecies(ranks)
    }

    private fun contains(ranks: List<String>, exact: Boolean): Boolean {
        var currentRank: Rank = root
        for (rank in ranks) {
            currentRank = currentRank.children[rank] ?: return false
        }
        return !exact || currentRank.isSpecies
    }

    fun containsRank(ranks: List<String>) = contains(ranks, false)

    fun containsSpecies(ranks: List<String>) = contains(ranks, true)

    private fun suggestHelper(currentRank: Rank, result: ArrayList<String>, suggestion: ArrayList<String>) {
        if (currentRank.isSpecies) {
            result.add(suggestion.joinToString(WHITESPACE))
        } else {
            for (child in currentRank.children) {
                suggestion.add(child.key)
                suggestHelper(child.value, result, suggestion)
                suggestion.removeLast()

            }
        }
    }

    fun suggest(rankPrefix: List<String>): List<String> {
        val result = ArrayList<String>()

        val suggestion = ArrayList<String>()
        var currentRank = root

        for (rank in rankPrefix) {
           currentRank = currentRank.children[rank] ?: return result
           suggestion.add(rank)
        }
        suggestHelper(currentRank, result, suggestion)
        return result
    }

    override fun toString() = root.toString()

}