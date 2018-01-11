/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.repack.org.apache.commons.codec;

/**
 * <p>
 * Provides the highest level of abstraction for Encoders. This is the sister interface of {@link Decoder}. Every implementation of Encoder provides this common generic interface whic allows a user to pass a generic Object to any Encoder implementation in the codec package.
 * </p>
 * 
 * @author Apache Software Foundation
 * @version $Id: Encoder.java 634915 2008-03-08 09:30:25Z bayard $
 */
public interface Encoder {

  /**
   * Encodes an "Object" and returns the encoded content as an Object. The Objects here may just be <code>byte[]</code> or <code>String</code>s depending on the implementation used.
   * 
   * @param pObject
   *            An object ot encode
   * 
   * @return An "encoded" Object
   * 
   * @throws EncoderException
   *             an encoder exception is thrown if the encoder experiences a failure condition during the encoding process.
   */
  Object encode(Object pObject) throws EncoderException;
}
