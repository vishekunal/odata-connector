/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.repack.org.apache.commons.codec;

/**
 * Defines common encoding methods for byte array encoders.
 * 
 * @author Apache Software Foundation
 * @version $Id: BinaryEncoder.java 651573 2008-04-25 11:11:21Z niallp $
 */
public interface BinaryEncoder extends Encoder {

  /**
   * Encodes a byte array and return the encoded data as a byte array.
   * 
   * @param pArray
   *            Data to be encoded
   * 
   * @return A byte array containing the encoded data
   * 
   * @throws EncoderException
   *             thrown if the Encoder encounters a failure condition during the encoding process.
   */
  byte[] encode(byte[] pArray) throws EncoderException;
}
