package daggerok.app;

import org.jnosql.artemis.Repository;

import java.util.UUID;

public interface BankAccountRepository extends Repository<BankAccount, UUID> {}
