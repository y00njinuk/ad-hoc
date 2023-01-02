import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;

public class TopicCreatorTest {
    private AdminClient admin;

    public class TopicCreator {
        private AdminClient admin;

        public TopicCreator(AdminClient admin) {
            this.admin = admin;
        }

        public void maybeCreateTopic(String topicName) throws ExecutionException, InterruptedException {
            Collection<NewTopic> topics = new ArrayList();
            topics.add(new NewTopic(topicName, 1, (short) 1));

            if (topicName.toLowerCase().startsWith("test")) {
                admin.createTopics(topics);

                ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
                ConfigEntry compaction = new ConfigEntry(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT);
                Collection<AlterConfigOp> configOps = new ArrayList<>();
                configOps.add(new AlterConfigOp(compaction, AlterConfigOp.OpType.SET));
                Map<ConfigResource, Collection<AlterConfigOp>> alterConf = new HashMap<>();
                alterConf.put(configResource, configOps);
                admin.incrementalAlterConfigs(alterConf).all().get();

            }
        }
    }

    @Before
    public void setUp() {
        Node broker = new Node(0, "localhost", 9092);
        this.admin = spy(new MockAdminClient(Collections.singletonList(broker), broker));

        AlterConfigsResult emptyResult = mock(AlterConfigsResult.class);
        doReturn(KafkaFuture.completedFuture(null)).when(emptyResult).all();
        doReturn(emptyResult).when(admin).incrementalAlterConfigs(any());
    }

    @Test
    public void testCreateTestTopic() throws ExecutionException, InterruptedException {
        TopicCreator tc = new TopicCreator(admin);
        tc.maybeCreateTopic("test.is.a.test.topic");
        verify(admin, times(1)).createTopics(any());
    }

    @Test
    public void testNotTopic() throws ExecutionException, InterruptedException {
        TopicCreator tc = new TopicCreator(admin);
        tc.maybeCreateTopic("not.a.test");
        verify(admin, never()).createTopics(any());
    }
}