/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.expression;

import org.mule.odata4j.core.UnsignedByte;

/** 0 (0x00) to 255 (0xFF) */
public interface ByteLiteral extends LiteralExpression {

  UnsignedByte getValue();

}
