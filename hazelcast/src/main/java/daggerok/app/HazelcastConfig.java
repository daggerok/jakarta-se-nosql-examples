package daggerok.app;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import org.jnosql.diana.api.Settings;
import org.jnosql.diana.api.key.BucketManager;
import org.jnosql.diana.api.key.BucketManagerFactory;
import org.jnosql.diana.api.key.KeyValueConfiguration;
import org.jnosql.diana.hazelcast.key.HazelcastKeyValueConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import static java.util.Collections.singletonMap;

@ApplicationScoped
public class HazelcastConfig {

    @Produces
    private KeyValueConfiguration keyValueConfiguration =
            new HazelcastKeyValueConfiguration();

    @Produces
    private BucketManagerFactory managerFactory = keyValueConfiguration.get(
            Settings.of(HashMap.<String, Object>of("hazelcast.instance.name", "bankAccount",
                                                   "hazelcast.host", "0.0.0.0",
                                                   "hazelcast.port", 5701)
                               .toJavaMap()));

    @Produces
    private BucketManager bucketManager =
            managerFactory.getBucketManager("bankAccount");
}
