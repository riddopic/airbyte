/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.db.init.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import io.airbyte.commons.resources.MoreResources;
import io.airbyte.db.check.DatabaseAvailabilityCheck;
import io.airbyte.db.instance.jobs.JobsDatabaseInstance;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test suite for the {@link JobsDatabaseInitializer} class.
 */
public class JobsDatabaseInitializerTest extends AbstractDatabaseInitializerTest {

  @Test
  void testInitializingSchema() throws IOException {
    final var databaseAvailabilityCheck = mock(DatabaseAvailabilityCheck.class);
    final var initialSchema = MoreResources.readResource(JobsDatabaseInstance.SCHEMA_PATH);
    final var initializer = new JobsDatabaseInitializer(databaseAvailabilityCheck, dslContext, initialSchema);

    Assertions.assertDoesNotThrow(() -> initializer.init());
    assertTrue(initializer.hasTable(dslContext, initializer.getTableNames().stream().findFirst().get()));
  }

  @Test
  void testInitializingSchemaAlreadyExists() throws IOException {
    final var databaseAvailabilityCheck = mock(DatabaseAvailabilityCheck.class);
    final var initialSchema = MoreResources.readResource(JobsDatabaseInstance.SCHEMA_PATH);
    dslContext.execute(initialSchema);
    final var initializer = new JobsDatabaseInitializer(databaseAvailabilityCheck, dslContext, initialSchema);

    Assertions.assertDoesNotThrow(() -> initializer.init());
    assertTrue(initializer.hasTable(dslContext, initializer.getTableNames().stream().findFirst().get()));
  }

  @Test
  void testInitializationException() throws IOException, InterruptedException {
    final var databaseAvailabilityCheck = mock(DatabaseAvailabilityCheck.class);
    final var initialSchema = MoreResources.readResource(JobsDatabaseInstance.SCHEMA_PATH);

    doThrow(new InterruptedException("test")).when(databaseAvailabilityCheck).check();

    final var initializer = new JobsDatabaseInitializer(databaseAvailabilityCheck, dslContext, initialSchema);
    Assertions.assertThrows(InterruptedException.class, () -> initializer.init());
  }

  @Test
  void testInitializationNullAvailabilityCheck() throws IOException {
    final var initialSchema = MoreResources.readResource(JobsDatabaseInstance.SCHEMA_PATH);
    final var initializer = new JobsDatabaseInitializer(null, dslContext, initialSchema);
    Assertions.assertThrows(InterruptedException.class, () -> initializer.init());
  }

  @Test
  void testInitializationNullDslContext() throws IOException {
    final var databaseAvailabilityCheck = mock(DatabaseAvailabilityCheck.class);
    final var initialSchema = MoreResources.readResource(JobsDatabaseInstance.SCHEMA_PATH);
    final var initializer = new JobsDatabaseInitializer(databaseAvailabilityCheck, null, initialSchema);
    Assertions.assertThrows(InterruptedException.class, () -> initializer.init());
  }

}
