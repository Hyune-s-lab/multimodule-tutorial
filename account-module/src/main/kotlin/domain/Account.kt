package domain

import java.math.BigDecimal
import java.util.*

class Account(
    val accountId: UUID,
    var balance: BigDecimal = BigDecimal.ZERO
) {
    fun deposit(amount: BigDecimal) {
        this.balance += amount
    }

    fun withdraw(amount: BigDecimal) {
        this.balance -= amount
    }
}
