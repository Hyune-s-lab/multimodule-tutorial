package domain

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

class AccountHistory(
    val accountId: UUID,
    val transferType: AccountTransferType,
    val amount: BigDecimal,
    val balance: BigDecimal,
    val dateTime: ZonedDateTime = ZonedDateTime.now()
) {
    constructor(account: Account) : this(
        accountId = account.accountId,
        transferType = AccountTransferType.CREATE,
        amount = BigDecimal.ZERO,
        balance = BigDecimal.ZERO
    )

    constructor(account: Account, transferType: AccountTransferType, amount: BigDecimal) : this(
        accountId = account.accountId,
        transferType = transferType,
        amount = amount,
        balance = account.balance
    )
}
