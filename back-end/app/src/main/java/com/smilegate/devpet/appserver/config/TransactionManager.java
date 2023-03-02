package com.smilegate.devpet.appserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class TransactionManager {
    @Bean
    @Primary
    @Autowired
    public PlatformTransactionManager chainedTransactionManager(
            @Qualifier("mongoTransactionManager") PlatformTransactionManager firstTxManager
            , @Qualifier("redisTransactionManager") PlatformTransactionManager secondTxManager)
    {
        return new ChainedTransactionManager(firstTxManager, secondTxManager);
    }
}
