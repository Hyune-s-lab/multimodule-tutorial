package service

import domain.Account
import domain.AccountHistory
import domain.AccountTransferType
import repository.AccountHistoryFindAllRepositoryRequest
import repository.AccountHistoryRepository
import repository.AccountRepository
import usecase.*

internal class AccountService(
    private val accountRepository: AccountRepository,
    private val accountHistoryRepository: AccountHistoryRepository,
) : AccountUsecase {
    override fun create(): Account {
        val account = accountRepository.create()
        accountHistoryRepository.save(AccountHistory(account))
        return account
    }

    override fun find(request: AccountFindRequest): Account? {
        return accountRepository.find(request.accountId)
    }

    override fun findAllHistory(request: AccountHistoryFindAllRequest): List<AccountHistory> {
        return accountHistoryRepository.findAll(AccountHistoryFindAllRepositoryRequest(request))
    }

    override fun deposit(request: AccountDepositRequest): Account {
        val account = accountRepository.find(request.accountId) ?: throw NullPointerException()
        account.deposit(request.amount)
        accountRepository.save(account)

        accountHistoryRepository.save(AccountHistory(account, AccountTransferType.DEPOSIT, request.amount))

        return account
    }

    override fun withdraw(request: AccountWithdrawRequest): Account {
        val account = accountRepository.find(request.accountId) ?: throw NullPointerException()
        account.withdraw(request.amount)
        accountRepository.save(account)

        accountHistoryRepository.save(AccountHistory(account, AccountTransferType.WITHDRAW, request.amount))

        return account
    }
}
