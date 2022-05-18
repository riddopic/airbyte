/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.db.check;

/**
 * Defines the interface for performing checks against a database.
 */
public interface DatabaseCheck {

  /**
   * Checks whether the configured database is available.
   *
   * @throws InterruptedException if unable to perform the check.
   */
  void check() throws InterruptedException;

}
