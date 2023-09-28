package cinema

fun main() {
    val cinemaScreen = createScreen()
    while (true) {
        println()
        println(displayMenu())
        when(readln().toInt()) {
            2 -> bookSeat(cinemaScreen)
            1 -> println("\n$cinemaScreen")
            0 -> return
        }
    }
}

fun createScreen(): CinemaScreen {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    return CinemaScreen(rows, seatsPerRow)
}

fun bookSeat(cinemaScreen: CinemaScreen) {
    println("\nEnter a row number:")
    val bookingRow = readln().toInt()
    println("Enter a seat number in that row:")
    val bookingSeat = readln().toInt()
    cinemaScreen.bookSeat(bookingRow, bookingSeat)
    println("Ticket price: $${ScreenCalculator(cinemaScreen).cost(bookingRow)}")
}

fun displayMenu(): String {
    return "" +
    "1. Show the seats\n" +
    "2. Buy a ticket\n" +
    "0. Exit"
}

data class CinemaScreen(val rows: Int, val seatsPerRow: Int) {
    private val seats = Array(rows) {CharArray(seatsPerRow) {'S'} }
    val totalSeats = rows * seatsPerRow

    fun bookSeat(row: Int, seatNum: Int) {
        seats[row - 1][seatNum - 1] = 'B'
    }

    override fun toString(): String {
        return "Cinema:\n" +
                (1..seatsPerRow).joinToString(" ", prefix = "  ", postfix = "\n") +
                seats.indices.joinToString("\n") { "${it + 1} ${seats[it].joinToString(" ")}" }
    }
}

class ScreenCalculator(private val screen: CinemaScreen){
    fun cost(seatRow: Int): Int {
        return if (screen.totalSeats <= 60) {
            10
        } else {
            val frontRowsCount = screen.rows / 2
            if (seatRow <= frontRowsCount) 10 else 8
        }
    }
}