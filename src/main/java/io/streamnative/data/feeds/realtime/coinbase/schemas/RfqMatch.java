package io.streamnative.data.feeds.realtime.coinbase.schemas;

import lombok.Data;

@Data
public class RfqMatch {
    private String maker_order_id;
    private String taker_order_id;
    private String side;
    private double size;
    private double price;
    private String product_id;
    private String time;
}
