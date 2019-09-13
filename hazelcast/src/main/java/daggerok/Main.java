package daggerok;

import daggerok.app.BankAccount;
import daggerok.app.BankAccountManager;
import daggerok.app.BankAccountRepository;
import daggerok.app.BankAccountService;
import lombok.extern.log4j.Log4j2;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerShutdown;
import org.jnosql.artemis.key.KeyValueTemplate;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.math.BigDecimal;
import java.util.UUID;

@Log4j2
public class Main {

    public static void main(String[] args) {
        // Weld weld = new Weld().setClassLoader(Main.class.getClassLoader());              // (2.1)
        try (WeldContainer context = new Weld().initialize()) {                             // (2.2)
            useKeyValueTemplate(context);
            useBankAccountManager(context);
            useBankAccountService(context);
            useBankAccountRepository(context);
        }
        // try (SeContainer context = SeContainerInitializer.newInstance().initialize()) {  // (1)
        //     useKeyValueTemplate(context);
        //     useBankAccountManager(context);
        //     useBankAccountService(context);
        //     useBankAccountRepository(context);
        // }
    }

    private static void useBankAccountRepository(SeContainer container) {
        log.debug("4");
        BankAccountRepository repository = container.select(BankAccountRepository.class).get();
        UUID accountId = UUID.randomUUID();
        repository.save(BankAccount.of(accountId, new BigDecimal("11.11")));
        repository.findById(accountId)
                  .ifPresent(ba -> log.debug("bank account: {} by using {}", ba, repository.getClass()));
    }

    private static void useBankAccountService(SeContainer container) {
        log.debug("3");
        BankAccountService service = container.select(BankAccountService.class).get();
        UUID accountId = UUID.randomUUID();
        service.save(BankAccount.of(accountId, new BigDecimal("1.23")));
        service.findById(accountId)
               .ifPresent(ba -> log.debug("bank account: {} by using {}", ba, service.getClass()));
    }

    private static void useBankAccountManager(SeContainer container) {
        log.debug("2");
        BankAccountManager manager = container.select(BankAccountManager.class).get();
        UUID accountId = UUID.randomUUID();
        manager.save(BankAccount.of(accountId, new BigDecimal("123.45")));
        manager.find(accountId)
               .ifPresent(ba -> log.debug("bank account: {} by using {}", ba, manager.getClass()));
    }

    private static void useKeyValueTemplate(SeContainer container) {
        log.debug("1");
        KeyValueTemplate template = container.select(KeyValueTemplate.class).get();
        UUID accountId = UUID.randomUUID();
        template.put(BankAccount.of(accountId, new BigDecimal("0.99")));
        template.get(accountId, BankAccount.class)
                .ifPresent(ba -> log.debug("bank account: {} by using {}", ba, template.getClass()));
    }

    private static void on(@Observes ContainerShutdown containerShutdown) {
        log.debug("finally exiting...");
        System.exit(0);
    }
}
