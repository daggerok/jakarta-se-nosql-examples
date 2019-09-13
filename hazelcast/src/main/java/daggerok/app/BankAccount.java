package daggerok.app;

import lombok.*;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@ToString
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 7142782713411353478L;

    @Id
    private UUID aggregateId;
    private BigDecimal balance;
}
