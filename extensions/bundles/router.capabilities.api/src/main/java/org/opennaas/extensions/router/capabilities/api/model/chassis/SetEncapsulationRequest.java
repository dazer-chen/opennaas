/**
 * 
 */
package org.opennaas.extensions.router.capabilities.api.model.chassis;

/*
 * #%L
 * OpenNaaS :: CIM Model
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

import javax.xml.bind.annotation.XmlRootElement;

import org.opennaas.extensions.router.model.LogicalPort;
import org.opennaas.extensions.router.model.ProtocolEndpoint.ProtocolIFType;

/**
 * @author Jordi
 */
@XmlRootElement
public class SetEncapsulationRequest {

	private LogicalPort		iface;
	private ProtocolIFType	encapsulation;

	/**
	 * @return the iface
	 */
	public LogicalPort getIface() {
		return iface;
	}

	/**
	 * @param iface
	 *            the iface to set
	 */
	public void setIface(LogicalPort iface) {
		this.iface = iface;
	}

	/**
	 * @return the encapsulation
	 */
	public ProtocolIFType getEncapsulation() {
		return encapsulation;
	}

	/**
	 * @param encapsulation
	 *            the encapsulation to set
	 */
	public void setEncapsulation(ProtocolIFType encapsulation) {
		this.encapsulation = encapsulation;
	}

}