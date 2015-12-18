/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.producer.jpa;

import java.util.Collections;
import java.util.List;

public class Chain implements Command {

  List<Command> commands;

  public Chain(List<Command> commands) {
    this.commands = Collections.unmodifiableList(commands);
  }

  /**
   * copied from http://commons.apache.org/chain. 
   */
  @Override
  public boolean execute(JPAContext context) {
    boolean saveResult = false;
    RuntimeException saveException = null;
    int i = 0;
    int n = commands.size();
    for (i = 0; i < n; i++) {
      try {
        saveResult = commands.get(i).execute(context);
        if (saveResult) {
          break;
        }
      } catch (RuntimeException e) {
        saveException = e;
        break;
      }
    }

    // Call postprocess methods on Filters in reverse order
    if (i >= n) { // Fell off the end of the chain
      i--;
    }
    boolean handled = false;
    boolean result = false;
    for (int j = i; j >= 0; j--) {
      if (commands.get(j) instanceof Filter) {
        try {
          result =
              ((Filter) commands.get(j)).postProcess(context,
                  saveException);
          if (result) {
            handled = true;
          }
        } catch (Exception e) {
          // Silently ignore
        }
      }
    }

    // Return the exception or result state from the last execute()
    if ((saveException != null) && !handled) {
      throw saveException;
    } else {
      return saveResult;
    }
  }
}