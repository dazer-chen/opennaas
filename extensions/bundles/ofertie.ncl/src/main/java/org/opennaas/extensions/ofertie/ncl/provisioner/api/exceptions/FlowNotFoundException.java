package org.opennaas.extensions.ofertie.ncl.provisioner.api.exceptions;

/*
 * #%L
 * OpenNaaS :: OFERTIE :: NCL components
 * %%
 * Copyright (C) 2007 - 2014 Fundació Privada i2CAT, Internet i Innovació a Catalunya
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

public class FlowNotFoundException extends Exception {

	/**
	 * Auto-generated serial number.
	 */
	private static final long	serialVersionUID	= 4838623572545196501L;

	public FlowNotFoundException() {
		super();
	}

	public FlowNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlowNotFoundException(String message) {
		super(message);
	}

	public FlowNotFoundException(Throwable cause) {
		super(cause);
	}

}
