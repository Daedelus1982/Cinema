package cinema

const val ROWS = 7
const val SEATS_PER_ROW = 8

fun main() {
    val seats = Array(ROWS) {CharArray(SEATS_PER_ROW) {'S'} }
    println("Cinema:")
    println((1..SEATS_PER_ROW).joinToString(" ", prefix = "  "))
    seats.indices.forEach { println("${it + 1} ${seats[it].joinToString(" ")}") }
}