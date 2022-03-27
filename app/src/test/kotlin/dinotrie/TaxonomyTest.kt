package dinotrie

import aardonyx
import ammosaurus
import ankylosaurus
import melanorosaurus
import org.junit.Before
import org.junit.Test
import riojasaurus
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TaxonomyTest {

    private val taxonomy = Taxonomy()
    private val speciesList = listOf(aardonyx, ammosaurus, ankylosaurus, melanorosaurus, riojasaurus)

    @Before
    fun setUp() {
        for (species in speciesList) taxonomy.addSpecies(species)
    }

    @Test
    fun `should create a single taxonomy and find each rank and species`() {
        val taxonomy = Taxonomy()
        taxonomy.addSpecies(aardonyx)
        for (i in aardonyx.size - 1 downTo 0) {
            assertTrue { taxonomy.containsRank(aardonyx.slice(0..i)) }
        }

        assertTrue { taxonomy.containsSpecies(aardonyx) }
    }

    @Test
    fun `should create multiple taxonomies and find each rank and species`() {
        for (species in speciesList) {
            for (i in species.size -1 downTo 0) {
                assertTrue { taxonomy.containsRank(species.slice(0..i)) }
            }

            assertTrue { taxonomy.containsSpecies(species) }
        }
    }

    @Test
    fun `should create not find related taxa if not present`() {
        val taxonomy = Taxonomy()
        val speciesList = listOf(aardonyx, ammosaurus, melanorosaurus, riojasaurus)
        for (species in speciesList) taxonomy.addSpecies(species)

        assertFalse { taxonomy.containsSpecies(ankylosaurus) }
    }

    @Test
    fun `should suggest species with same prefix taxa`() {
        val melanorosauridae = melanorosaurus.dropLast(1)
        val suggestions = taxonomy.suggest(melanorosauridae)

        assertEquals(listOf(melanorosaurus.joinToString(WHITESPACE), riojasaurus.joinToString(WHITESPACE)), suggestions)
    }

    @Test
    fun `should suggest single species if it equals a given prefix`() {
        val suggestions = taxonomy.suggest(aardonyx)

        assertEquals(listOf(aardonyx.joinToString(WHITESPACE)), suggestions)
    }

    @Test
    fun `should not suggest any species if prefix is not present`() {
        val taxonomy = Taxonomy()
        val speciesList = listOf(aardonyx, ammosaurus, ankylosaurus)
        for (species in speciesList) taxonomy.addSpecies(species)

        val melanorosauridae = melanorosaurus.dropLast(1)
        val suggestions = taxonomy.suggest(melanorosauridae)

        assertEquals(listOf(), suggestions)
    }

    @Test
    fun `print taxonomy`() {
        println(taxonomy)
    }
}