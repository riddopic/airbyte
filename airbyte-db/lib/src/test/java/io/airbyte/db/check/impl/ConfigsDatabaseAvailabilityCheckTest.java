/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.db.check.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test suite for the {@link ConfigsDatabaseAvailabilityCheck} class.
 */
public class ConfigsDatabaseAvailabilityCheckTest extends AbstractDatabaseAvailabilityCheckTest {

  @Test
  void checkDatabaseAvailability() {
    final var check = new ConfigsDatabaseAvailabilityCheck(dslContext, 60000L);
    Assertions.assertDoesNotThrow(() -> check.check());
  }

  @Test
  void checkDatabaseAvailabilityTimeout() {
    final DSLContext dslContext = mock(DSLContext.class);
    when(dslContext.fetchExists(any(Select.class))).thenThrow(new DataAccessException("test"));
    final var check = new ConfigsDatabaseAvailabilityCheck(dslContext, 2000L);
    Assertions.assertThrows(InterruptedException.class, () -> check.check());
  }

  @Test
  void checkDatabaseAvailabilityNullDslContext() {
    final var check = new ConfigsDatabaseAvailabilityCheck(null, 2000L);
    Assertions.assertThrows(InterruptedException.class, () -> check.check());
  }

}
