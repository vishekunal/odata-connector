/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format;

import java.io.Reader;

import org.mule.odata4j.core.OEntity;

/**
 * Deals with parsing the resulting stream into a <code>Entry</code> or
 * <code>Feed</code> and converting it to a <code>OEntity</code>. The
 * implementation depends on the format Atom or Json.
 *
 * @param <T>            Atom or json
 *
 * @see Entry
 * @see Feed
 * @see OEntity
 */
public interface FormatParser<T> {

  T parse(Reader reader);

}
