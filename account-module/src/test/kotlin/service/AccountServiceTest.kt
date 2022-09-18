package service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import testdouble.stub.AccountHistoryRepositoryStub
import testdouble.stub.AccountRepositoryStub
import usecase.AccountDepositRequest
import usecase.AccountFindRequest
import usecase.AccountHistoryFindAllRequest
import usecase.AccountWithdrawRequest
import java.math.BigDecimal

class AccountServiceTest : BehaviorSpec({
    val accountRepository = AccountRepositoryStub();
    val accountHistoryRepository = AccountHistoryRepositoryStub();
    val accountService = AccountService(accountRepository, accountHistoryRepository)

    Given("계좌 service") {
        val account = accountService.create();

        When("계좌 조회 find") {
            val findAccount = accountService.find(AccountFindRequest(account.accountId))

            Then("[account] accountId 일치, balance 0") {
                findAccount!!.run {
                    accountId shouldBe account.accountId
                    balance shouldBe BigDecimal.ZERO
                }
            }
        }

        val depositAmount1 = BigDecimal.valueOf(3000)
        val depositAmount2 = BigDecimal.valueOf(8000)

        When("입금 deposit 1") {
            accountService.deposit(AccountDepositRequest(account.accountId, depositAmount1))

            Then("[account] accountId 일치, balance 일치") {
                accountService.find(AccountFindRequest(account.accountId))!!.run {
                    accountId shouldBe account.accountId
                    balance shouldBe depositAmount1
                }
            }
        }

        When("입금 deposit 2") {
            accountService.deposit(AccountDepositRequest(account.accountId, depositAmount2))

            Then("[account] accountId 일치, balance 일치") {
                accountService.find(AccountFindRequest(account.accountId))!!.run {
                    accountId shouldBe account.accountId
                    balance shouldBe depositAmount1 + depositAmount2
                }
            }
        }

        val withdrawAmount1 = BigDecimal.valueOf(2000)
        val withdrawAmount2 = BigDecimal.valueOf(3000)

        When("출금 withdraw 1") {
            accountService.withdraw(AccountWithdrawRequest(account.accountId, withdrawAmount1))

            Then("[account] accountId 일치, balance 일치") {
                accountService.find(AccountFindRequest(account.accountId))!!.run {
                    accountId shouldBe account.accountId
                    balance shouldBe depositAmount1 + depositAmount2 - withdrawAmount1
                }
            }
        }

        When("출금 withdraw 2") {
            accountService.withdraw(AccountWithdrawRequest(account.accountId, withdrawAmount2))

            Then("[account] accountId 일치, balance 일치") {
                accountService.find(AccountFindRequest(account.accountId))!!.run {
                    accountId shouldBe account.accountId
                    balance shouldBe depositAmount1 + depositAmount2 - withdrawAmount1 - withdrawAmount2
                }
            }
        }

        When("계좌 기록 전체 조회 findAllHistory") {
            val findAllHistory = accountService.findAllHistory(AccountHistoryFindAllRequest(account.accountId))

            Then("[accountHistory] total size 5 = create 1 + deposit 2 + withdraw 2") {
                findAllHistory.size shouldBe 5
            }
        }
    }
})
