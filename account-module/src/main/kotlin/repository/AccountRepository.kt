package repository

import domain.Account
import java.util.*

interface AccountRepository {
    fun create(): Account
    fun save(account: Account): Account
    fun find(accountId: UUID): Account?
}
