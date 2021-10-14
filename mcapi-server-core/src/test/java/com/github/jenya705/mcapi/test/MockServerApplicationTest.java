package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.mock.MockServerApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MockServerApplicationTest {

    @Test
    public void runTest() {
        MockServerApplication mockServerApplication = MockServerApplication.mock();
        mockServerApplication.start();
        Assertions.assertTrue(mockServerApplication.isEnabled());
        Assertions.assertTrue(mockServerApplication.isInitialized());
        mockServerApplication.stop();
        Assertions.assertFalse(mockServerApplication.isEnabled());
        Assertions.assertFalse(mockServerApplication.isInitialized());
    }

}
