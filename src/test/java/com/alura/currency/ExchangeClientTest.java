package com.alura.currency;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeClientTest {
    @Test
    public void testLatest() throws Exception {
        ExchangeClient c = new ExchangeClient("https://api.exchangerate.host", "");
        RateResponse r = c.latest("USD"); // may fail if network unavailable
        assertNotNull(r);
        assertEquals("USD", r.getBase());
        assertTrue(r.getRates().containsKey("BRL"));
    }
}
