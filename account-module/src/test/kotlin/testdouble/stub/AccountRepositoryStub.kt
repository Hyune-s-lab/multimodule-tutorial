package testdouble.stub

import domain.Account
import repository.AccountRepository
import java.util.*

class AccountRepositoryStub(
    private val accounts: MutableMap<UUID, Account> = mutableMapOf<UUID, Account>()
) : AccountRepository {
    override fun create(): Account {
        val account = Account(UUID.randomUUID())
        return save(account)
    }

    override fun save(account: Account): Account {
        accounts[account.accountId] = account
        return account
    }

    override fun find(accountId: UUID): Account? {
        return accounts[accountId]
    }
}
