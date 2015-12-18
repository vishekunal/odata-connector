/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.repack.org.apache.commons.codec;

/**
 * Defines common decoding methods for byte array decoders.
 * 
 * @author Apache Software Foundation
 * @version $Id: BinaryDecoder.java 651573 2008-04-25 11:11:21Z niallp $
 */
public interface BinaryDecoder extends Decoder {

  /**
   * Decodes a byte array and returns the results as a byte array.
   * 
   * @param pArray
   *            A byte array which has been encoded with the appropriate encoder
   * 
   * @return a byte array that contains decoded content
   * 
   * @throws DecoderException
   *             A decoder exception is thrown if a Decoder encounters a failure condition during the decode process.
   */
  byte[] decode(byte[] pArray) throws DecoderException;
}
