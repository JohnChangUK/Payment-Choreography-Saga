package payment.saga.payment.util;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static final Map<Integer, Integer> USER_BALANCE = new HashMap<>() {{
        put(1, 100);
        put(2, 500);
        put(3, 1000);
    }};
}
