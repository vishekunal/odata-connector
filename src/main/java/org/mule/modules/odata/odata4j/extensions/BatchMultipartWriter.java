/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata.odata4j.extensions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.odata4j.core.Guid;
import org.odata4j.jersey.internal.StringProvider2;

import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

/**
 * Multipart writer with custom management
 * of the boundary string intended for consuming
 * batch services
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
@Produces("multipart/*")
public class BatchMultipartWriter extends MultiPartWriter {
	
	private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
	
	public BatchMultipartWriter() {
		super(null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void writeTo(MultiPart entity, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream stream)
			throws IOException, WebApplicationException {
		
		
		// Verify that there is at least one body part
        if ((entity.getBodyParts() == null) || (entity.getBodyParts().size() < 1)) {
            throw new WebApplicationException(new IllegalArgumentException("Must specify at least one body part"));
        }

        // If our entity is not nested, make sure the MIME-Version header is set
        if (entity.getParent() == null) {
            Object value = headers.getFirst("MIME-Version");
            if (value == null) {
                headers.putSingle("MIME-Version", "1.0");
            }
        }

        // Initialize local variables we need
        Writer writer = new BufferedWriter(new OutputStreamWriter(stream)); // FIXME - charset???

        // Determine the boundary string to be used, creating one if needed
        MediaType entityMediaType = (MediaType) headers.getFirst("Content-Type");
        if (entityMediaType == null) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("boundary", createBoundary());
            entityMediaType = new MediaType("multipart", "mixed", parameters);
            headers.putSingle("Content-Type", entityMediaType);
        }
        
        String boundaryString = entityMediaType.getParameters().get("boundary");
        if (boundaryString == null) {
            boundaryString = createBoundary();
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.putAll(entityMediaType.getParameters());
            parameters.put("boundary", boundaryString);
            entityMediaType = new MediaType(entityMediaType.getType(),
                    entityMediaType.getSubtype(),
                    parameters);
            headers.putSingle("Content-Type", entityMediaType);
        }

        boolean isFirst = true;
        // Iterate through the body parts for this message
        for (BodyPart bodyPart : entity.getBodyParts()) {
            
        	
        	// Write the leading boundary string
            writer.write("\r\n--");
            writer.write(boundaryString);
            writer.write("\r\n");

            if (isFirst) {
            	isFirst = false;
            	boundaryString = bodyPart.getMediaType().getParameters().get("boundary");
            }
            
            // Write the headers for this body part
            MediaType bodyMediaType = bodyPart.getMediaType();
            if (bodyMediaType == null) {
                throw new WebApplicationException(new IllegalArgumentException("Missing body part media type"));
            }
            MultivaluedMap<String, String> bodyHeaders = bodyPart.getHeaders();
            bodyHeaders.putSingle("Content-Type", bodyMediaType.toString());

            if (bodyHeaders.getFirst("Content-Disposition") == null
                    && bodyPart.getContentDisposition() != null) {
                bodyHeaders.putSingle("Content-Disposition", bodyPart.getContentDisposition().toString());
            }

            // Iterate for the nested body parts
            for (Map.Entry<String, List<String>> entry : bodyHeaders.entrySet()) {

                // Only headers that match "Content-*" are allowed on body parts
                if (!entry.getKey().toLowerCase().startsWith("content-")) {
                    throw new WebApplicationException(new IllegalArgumentException("Invalid body part header '" + entry.getKey() + "', only Content-* allowed"));
                }

                // Write this header and its value(s)
                writer.write(entry.getKey());
                writer.write(':');
                boolean first = true;
                for (String value : entry.getValue()) {
                    if (first) {
                        writer.write(' ');
                        first = false;
                    } else {
                        writer.write(',');
                    }
                    writer.write(value);
                }
                writer.write("\r\n");
            }

            // Mark the end of the headers for this body part
            writer.write("\r\n");
            writer.flush();

            // Write the entity for this body part
            Object bodyEntity = bodyPart.getEntity();
            if (bodyEntity == null) {
                throw new WebApplicationException(
                        new IllegalArgumentException("Missing body part entity of type '" + bodyMediaType + "'"));
            }

            @SuppressWarnings("rawtypes")
			Class bodyClass = bodyEntity.getClass();
            if (bodyEntity instanceof BodyPartEntity) {
                bodyClass = InputStream.class;
                bodyEntity = ((BodyPartEntity) bodyEntity).getInputStream();
            }

            @SuppressWarnings("rawtypes")
            MessageBodyWriter bodyWriter = new StringProvider2();

            bodyWriter.writeTo(
                    bodyEntity,
                    bodyClass,
                    bodyClass,
                    EMPTY_ANNOTATIONS,
                    bodyMediaType,
                    bodyHeaders,
                    stream);
        }

        // Write the final boundary string
        writer.write("\r\n--");
        writer.write(boundaryString);
        writer.write("--\r\n");
        writer.flush();
	}
	
	/**
    * <p>Create and return a unique value for the <code>boundary</code>
    * parameter of our <code>Content-Type</code> header field.</p>
    */
   private String createBoundary() {
       return "Boundary_" + Guid.randomGuid().toString();
   }

}
