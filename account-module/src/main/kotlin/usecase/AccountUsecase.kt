package usecase

import domain.Account
import domain.AccountHistory
import java.math.BigDecimal
import java.util.*

interface AccountUsecase {
    fun create(): Account
    fun find(request: AccountFindRequest): Account?
    fun findAllHistory(request: AccountHistoryFindAllRequest): List<AccountHistory>
    fun deposit(request: AccountDepositRequest): Account
    fun withdraw(request: AccountWithdrawRequest): Account
}

data class AccountFindRequest(val accountId: UUID)
data class AccountHistoryFindAllRequest(val accountId: UUID)
data class AccountDepositRequest(val accountId: UUID, val amount: BigDecimal)
data class AccountWithdrawRequest(val accountId: UUID, val amount: BigDecimal)
