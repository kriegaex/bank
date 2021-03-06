package pl.sii.bank.transaction.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.sii.bank.transaction.domain.*

@Configuration
class TransactionConfiguration {

    @Bean
    fun initializeTransactionUseCase(transactionStore: TransactionStore): InitializeTransactionUseCase =
        InitializeTransactionUseCase(transactionStore)

    @Bean
    fun confirmTransactionUseCase(transactionStore: TransactionStore): ConfirmTransactionUseCase =
        ConfirmTransactionUseCase(transactionStore)

    @Bean
    fun authorizeTransactionUseCase(transactionStore: TransactionStore): AuthorizeTransactionUseCase =
        AuthorizeTransactionUseCase(transactionStore)

    @Bean
    fun fetchTransactionStatusUseCase(transactionStore: TransactionStore): FetchTransactionStatusUseCase =
        FetchTransactionStatusUseCase(transactionStore)
}