package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    val cinemaScreen = CinemaScreen(rows, seatsPerRow)
    println("Total income:")
    println("$${ScreenCalculator(cinemaScreen).profit()}")
}

data class CinemaScreen(val rows: Int, val seatsPerRow: Int) {
    val totalSeats = rows * seatsPerRow
}

class ScreenCalculator(private val screen: CinemaScreen){
    fun profit(): Int {
        return if (screen.totalSeats <= 60) {
            screen.totalSeats * 10
        } else {
            val frontRowsCount = screen.rows / 2
            val backRowsCount = screen.rows - frontRowsCount

            frontRowsCount * screen.seatsPerRow * 10 + backRowsCount * screen.seatsPerRow * 8
        }
    }
}