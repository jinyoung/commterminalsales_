package commterminalsales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commterminalsales.config.kafka.KafkaProcessor;
import commterminalsales.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class 스펙재방문시타켓팅수치증가Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        스펙재방문시타켓팅수치증가Test.class
    );

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public RetargettingRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        Retargetting entity = new Retargetting();

        entity.setId(1L);
        entity.setCustomerId("C001");
        entity.setProductId("P001");
        entity.setReturnCount(2L);

        repository.save(entity);

        //when:

        SpecCompared event = new SpecCompared();

        event.setId(1L);
        event.setCustomerId("C001");
        event.setProductId("P001");
        event.setOptions(new Object[] { "Option A", "Option B" });

        MarketingApplication.applicationContext = applicationContext;

        ObjectMapper objectMapper = new ObjectMapper()
            .configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
            );
        try {
            String msg = objectMapper.writeValueAsString(event);

            processor
                .inboundTopic()
                .send(
                    MessageBuilder
                        .withPayload(msg)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .setHeader("type", event.getEventType())
                        .build()
                );

            //then:

            Message<String> received = (Message<String>) messageCollector
                .forChannel(processor.outboundTopic())
                .poll();

            assertNotNull("Resulted event must be published", received);

            DiscountPolicyActivated outputEvent = objectMapper.readValue(
                (String) received.getPayload(),
                DiscountPolicyActivated.class
            );

            LOGGER.info("Response received: {}", received.getPayload());

            assertEquals(String.valueOf(outputEvent.getId()), null);
            assertEquals(String.valueOf(outputEvent.getCustomerId()), "C001");
            assertEquals(String.valueOf(outputEvent.getProductId()), "P001");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            assertTrue(e.getMessage(), false);
        }
    }
}
