/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.repack.org.apache.commons.codec;

/**
 * <p>
 * Provides the highest level of abstraction for Decoders. This is the sister interface of {@link Encoder}. All Decoders implement this common generic interface.
 * </p>
 * 
 * <p>
 * Allows a user to pass a generic Object to any Decoder implementation in the codec package.
 * </p>
 * 
 * <p>
 * One of the two interfaces at the center of the codec package.
 * </p>
 * 
 * @author Apache Software Foundation
 * @version $Id: Decoder.java 797690 2009-07-24 23:28:35Z ggregory $
 */
public interface Decoder {

  /**
   * Decodes an "encoded" Object and returns a "decoded" Object. Note that the implementation of this interface will try to cast the Object parameter to the specific type expected by a particular Decoder implementation. If a {@link ClassCastException} occurs this decode method will throw a DecoderException.
   * 
   * @param pObject
   *            an object to "decode"
   * 
   * @return a 'decoded" object
   * 
   * @throws DecoderException
   *             a decoder exception can be thrown for any number of reasons. Some good candidates are that the parameter passed to this method is null, a param cannot be cast to the appropriate type for a specific encoder.
   */
  Object decode(Object pObject) throws DecoderException;
}
