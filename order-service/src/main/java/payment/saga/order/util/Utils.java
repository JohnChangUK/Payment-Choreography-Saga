package payment.saga.order.util;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static final Map<Integer, Integer> USER_BALANCE = new HashMap<>() {{
        put(1, 100);
        put(2, 500);
        put(3, 1000);
    }};

    public static final Map<Integer, Integer> PRODUCT_PRICES = Map.of(
            1, 50,
            2, 100,
            3, 150
    );
}
