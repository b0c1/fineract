/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.infrastructure.core.exceptionmapper;

import static org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse.serverSideError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.exception.CommandFailedException;
import org.apache.fineract.infrastructure.core.exception.CommandProcessedException;
import org.apache.fineract.infrastructure.core.exception.CommandUnderProcessingException;
import org.apache.fineract.infrastructure.core.exception.DuplicateCommandException;
import org.springframework.stereotype.Component;

@Provider
@Component
@Slf4j
public class DuplicateCommandExceptionMapper implements ExceptionMapper<DuplicateCommandException> {

    public static final String IDEMPOTENT_CACHE_HEADER = "x-served-from-cache";

    @Override
    public Response toResponse(final DuplicateCommandException exception) {
        log.debug("Duplicate request: {}", exception.getMessage());
        if (exception instanceof CommandProcessedException) {
            return Response.status(Status.OK).entity(exception.getResponse()).header(IDEMPOTENT_CACHE_HEADER, "true")
                    .type(MediaType.APPLICATION_JSON).build();
        } else if (exception instanceof CommandFailedException) {
            String response = exception.getResponse();
            Status statusCode = Status.fromStatusCode(((CommandFailedException) exception).getStatusCode());
            return Response.status(statusCode).entity(response).header(IDEMPOTENT_CACHE_HEADER, "true").type(MediaType.APPLICATION_JSON)
                    .build();
        } else if (exception instanceof CommandUnderProcessingException) {
            return Response.status(Status.CONFLICT).entity(exception.getResponse()).header(IDEMPOTENT_CACHE_HEADER, "true")
                    .type(MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(serverSideError("500", "")).type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
