package cinema

fun main() {
    val cinemaScreen = createScreen()
    val screenStats = ScreenStats(cinemaScreen)
    while (true) {
        println()
        println(displayMenu())
        when(readln().toInt()) {
            3 -> println("\n$screenStats")
            2 -> bookSeat(cinemaScreen, screenStats)
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

fun bookSeat(cinemaScreen: CinemaScreen, screenStats: ScreenStats) {
    println("\nEnter a row number:")
    val bookingRow = readln().toInt()
    println("Enter a seat number in that row:")
    val bookingSeat = readln().toInt()
    try {
        cinemaScreen.bookSeat(bookingRow, bookingSeat)
        val price = ScreenCalculator(cinemaScreen).cost(bookingRow)
        println("\nTicket price: $$price")
        screenStats.ticketsSold++
        screenStats.current += price
    } catch (e: Exception) {
        println("\n${e.message}")
        bookSeat(cinemaScreen, screenStats)
    }
}

fun displayMenu(): String {
    return "" +
    "1. Show the seats\n" +
    "2. Buy a ticket\n" +
    "3. Statistics\n" +
    "0. Exit"
}

class CinemaScreen(val rows: Int, val seatsPerRow: Int) {
    private val seats = Array(rows) {CharArray(seatsPerRow) {'S'} }
    val totalSeats = rows * seatsPerRow

    fun bookSeat(row: Int, seatNum: Int) {
        if (row < 1 || row > rows
            || seatNum < 1 || seatNum > seatsPerRow) throw Exception("Wrong Input!")
        if (seats[row -1][seatNum - 1] != 'S') throw Exception("That ticket has already been purchased!")
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

class ScreenStats(private val screen: CinemaScreen) {
    var ticketsSold: Int = 0
    var current: Int = 0

    private fun fullScreenTotal(): Int {
        return if (screen.totalSeats <= 60) {
            screen.totalSeats * 10
        } else {
            val frontRowsCount = screen.rows / 2
            val backRowsCount = screen.rows - frontRowsCount
            frontRowsCount * screen.seatsPerRow * 10 + backRowsCount * screen.seatsPerRow * 8
        }
    }

    private fun getPercentFull(): Double = 100.0 / screen.totalSeats * ticketsSold

    override fun toString(): String {
        return  "Number of purchased tickets: $ticketsSold\n" +
                "Percentage: ${"%.2f".format(getPercentFull())}%\n" +
                "Current income: \$$current\n" +
                "Total income: \$${fullScreenTotal()}"
    }
}