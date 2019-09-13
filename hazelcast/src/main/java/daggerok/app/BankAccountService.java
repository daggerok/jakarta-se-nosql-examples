package daggerok.app;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@ApplicationScoped
public class BankAccountService {

    private final BankAccountRepository repository;

    @Inject
    public BankAccountService(BankAccountRepository repository) {
        log.debug("injecting {}", repository.getClass());
        this.repository = repository;
    }

    public void save(BankAccount bankAccount) {
        log.debug("saving {}", bankAccount);
        repository.save(bankAccount);
    }

    public Optional<BankAccount> findById(UUID id) {
        log.debug("getting by id: {}", id);
        return repository.findById(id);
    }
}
