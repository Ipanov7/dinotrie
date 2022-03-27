package dinotrie

class Rank {
    val children = HashMap<String, Rank>()
    var isSpecies = false

    constructor()

    constructor(ranks: List<String>) {
        this.addSpecies(ranks)
    }

    fun addSpecies(ranks: List<String>) {
        if (ranks.isEmpty()) {
            isSpecies = true
        } else {
            val rankName = ranks.first()
            if (children.containsKey(rankName)) children[rankName]?.addSpecies(ranks.drop(1))
            else children[rankName] = Rank(ranks.drop(1))
        }
    }

    override fun toString(): String {
        val buffer = StringBuilder()
        display(buffer, "", "", "")
        return buffer.toString()
    }

    private fun display(buffer: StringBuilder, prefix: String, previousPrefix: String, name: String) {
        var childrenPrefix = previousPrefix
        if (name.isNotBlank()) {
            if (buffer.isNotBlank()) buffer.append(prefix)
            else childrenPrefix = ""
            buffer.append(name)
            buffer.append('\n')
        }
        val it = children.iterator()
        while(it.hasNext()) {
            val next = it.next()
            if (it.hasNext()) {
                next.value.display(buffer, "$childrenPrefix├── ", "$childrenPrefix│   ", next.key)
            } else {
                next.value.display(buffer, "$childrenPrefix└── ", "$childrenPrefix    ", next.key)
            }
        }
    }
}