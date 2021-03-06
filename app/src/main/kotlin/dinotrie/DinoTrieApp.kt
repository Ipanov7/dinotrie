/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package dinotrie

import speciesList


class DinoTrieApp {
    fun start() {
        val taxonomy = Taxonomy()
        for (species in speciesList) taxonomy.addSpecies(species)
        print(taxonomy)
    }
}

fun main() {
    DinoTrieApp().start()
}
