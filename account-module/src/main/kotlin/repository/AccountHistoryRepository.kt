package repository

import domain.AccountHistory
import usecase.AccountHistoryFindAllRequest
import java.util.*

interface AccountHistoryRepository {
    fun save(accountHistory: AccountHistory): AccountHistory
    fun findAll(): List<AccountHistory>
    fun findAll(request: AccountHistoryFindAllRepositoryRequest): List<AccountHistory>
}

data class AccountHistoryFindAllRepositoryRequest(val accountId: UUID) {
    constructor(request: AccountHistoryFindAllRequest) : this(request.accountId)
}
