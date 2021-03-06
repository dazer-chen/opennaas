package org.opennaas.extensions.router.capability.ospfv3.shell;

/*
 * #%L
 * OpenNaaS :: Router :: OSPFv3 Capability
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

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.opennaas.core.resources.IResource;
import org.opennaas.core.resources.ResourceException;
import org.opennaas.core.resources.shell.GenericKarafCommand;
import org.opennaas.extensions.router.capability.ospfv3.IOSPFv3Capability;

@Command(scope = "ospfv3", name = "activate", description = "Activate OSPFv3 in given device")
public class ActivateCommand extends GenericKarafCommand {

	@Argument(index = 0, name = "resourceType:resourceName", description = "Name of the router to activate OSPFv3 on", required = true, multiValued = false)
	private String	resourceId;

	@Override
	protected Object doExecute() throws Exception {
		printInitCommand("Activate OSPFv3 ");
		try {
			IResource router = getResourceFromFriendlyName(resourceId);
			IOSPFv3Capability ospfCapability = (IOSPFv3Capability) router.getCapabilityByInterface(IOSPFv3Capability.class);
			ospfCapability.activateOSPFv3();
		} catch (ResourceException e) {
			printError(e);
			printEndCommand();
			return -1;
		} catch (Exception e) {
			printError("Error activating OSPFv3");
			printError(e);
			printEndCommand();
			return -1;
		}
		printEndCommand();
		return null;
	}
}