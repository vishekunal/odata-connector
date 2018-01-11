/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.repack.org.apache.commons.codec;

/**
 * Thrown when a Decoder has encountered a failure condition during a decode.
 * 
 * @author Apache Software Foundation
 * @version $Id: DecoderException.java 797804 2009-07-25 17:27:04Z ggregory $
 */
public class DecoderException extends Exception {

  /**
   * Declares the Serial Version Uid.
   * 
   * @see <a href="http://c2.com/cgi/wiki?AlwaysDeclareSerialVersionUid">Always Declare Serial Version Uid</a>
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new exception with <code>null</code> as its detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   * 
   * @since 1.4
   */
  public DecoderException() {
    super();
  }

  /**
   * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
   * 
   * @param message
   *            The detail message which is saved for later retrieval by the {@link #getMessage()} method.
   */
  public DecoderException(String message) {
    super(message);
  }

  /**
   * Constructsa new exception with the specified detail message and cause.
   * 
   * <p>
   * Note that the detail message associated with <code>cause</code> is not automatically incorporated into this exception's detail message.
   * </p>
   * 
   * @param message
   *            The detail message which is saved for later retrieval by the {@link #getMessage()} method.
   * @param cause
   *            The cause which is saved for later retrieval by the {@link #getCause()} method. A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
   * @since 1.4
   */
  public DecoderException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message of <code>(cause==null ?
   * null : cause.toString())</code> (which typically contains the class and detail message of <code>cause</code>). This constructor is useful for exceptions that are little more than wrappers for other throwables.
   * 
   * @param cause
   *            The cause which is saved for later retrieval by the {@link #getCause()} method. A <code>null</code> value is permitted, and indicates that the cause is nonexistent or unknown.
   * @since 1.4
   */
  public DecoderException(Throwable cause) {
    super(cause);
  }
}