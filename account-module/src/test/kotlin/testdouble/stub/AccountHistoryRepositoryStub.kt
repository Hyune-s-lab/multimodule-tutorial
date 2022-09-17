package testdouble.stub

import domain.AccountHistory
import repository.AccountHistoryFindAllRepositoryRequest
import repository.AccountHistoryRepository
import java.util.*

class AccountHistoryRepositoryStub(
    private val accountHistories: MutableMap<UUID, MutableList<AccountHistory>> = mutableMapOf()
) : AccountHistoryRepository {
    override fun save(accountHistory: AccountHistory): AccountHistory {
        val accountHistoriesByAccountId = accountHistories[accountHistory.accountId] ?: mutableListOf<AccountHistory>()
        accountHistoriesByAccountId.add(accountHistory)
        accountHistories.put(accountHistory.accountId, accountHistoriesByAccountId)

        return accountHistory
    }

    override fun findAll(): List<AccountHistory> {
        return accountHistories.flatMap { it -> it.value }
    }

    override fun findAll(request: AccountHistoryFindAllRepositoryRequest): List<AccountHistory> {
        return accountHistories[request.accountId] ?: mutableListOf<AccountHistory>()
    }
}
