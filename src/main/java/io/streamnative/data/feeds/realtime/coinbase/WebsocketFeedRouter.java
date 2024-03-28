package io.streamnative.data.feeds.realtime.coinbase;

import io.streamnative.data.feeds.realtime.coinbase.schemas.RfqMatch;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class WebsocketFeedRouter implements Function<String, Void> {

    private static final String TOPIC_MAP = "topicMap";
    private static final String RFQ_TOPIC = "persistent://public/default/coinbase-rfq";
    private ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, String> topicMap;

    @Override
    public Void process(String jsonString, Context ctx) throws Exception {
        String feedName = ctx.getCurrentRecord().getKey().orElse("UNKNOWN");

        if (feedName != null && feedName.equalsIgnoreCase("rfq_match")) {
            ctx.newOutputMessage(RFQ_TOPIC, Schema.JSON(RfqMatch.class))
                    .value(objectMapper.readValue(jsonString, RfqMatch.class))
                    .send();
        }
        return null;
    }

    @Override
    public void initialize(Context ctx) throws Exception {
        Function.super.initialize(ctx);
    }

    @Override
    public void close() throws Exception {
        Function.super.close();
    }
}
