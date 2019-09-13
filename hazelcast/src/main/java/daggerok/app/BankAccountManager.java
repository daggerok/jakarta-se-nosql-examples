package daggerok.app;

import lombok.extern.log4j.Log4j2;
import org.jnosql.artemis.key.KeyValueTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@ApplicationScoped
public class BankAccountManager {

    private final KeyValueTemplate template;

    @Inject
    public BankAccountManager(KeyValueTemplate template) {
        log.debug("injecting {}", template.getClass());
        this.template = template;
    }

    public void save(BankAccount bankAccount) {
        log.debug("saving {}", bankAccount);
        template.put(bankAccount);
    }

    public Optional<BankAccount> find(UUID id) {
        log.debug("fetching bank account ny id {}", id);
        return template.prepare("get @id", BankAccount.class)
                       .bind("id", id)
                       .getSingleResult();
    }
}
