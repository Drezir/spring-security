/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.server.csrf;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.util.Assert;

import reactor.core.publisher.Mono;

/**
 * {@link CsrfServerLogoutHandler} is in charge of removing the {@link CsrfToken} upon
 * logout. A new {@link CsrfToken} will then be generated by the framework upon the next
 * request.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
public class CsrfServerLogoutHandler implements ServerLogoutHandler {

	private final ServerCsrfTokenRepository csrfTokenRepository;

	/**
	 * Creates a new instance
	 * @param csrfTokenRepository The {@link ServerCsrfTokenRepository} to use
	 */
	public CsrfServerLogoutHandler(ServerCsrfTokenRepository csrfTokenRepository) {
		Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
		this.csrfTokenRepository = csrfTokenRepository;
	}

	/**
	 * Clears the {@link CsrfToken}
	 * @param exchange the exchange
	 * @param authentication the {@link Authentication}
	 * @return A completion notification (success or error)
	 */
	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return this.csrfTokenRepository.saveToken(exchange.getExchange(), null);
	}

}
