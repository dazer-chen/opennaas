package org.opennaas.core.resources.shell;

import java.util.List;


import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.opennaas.core.resources.IResourceIdentifier;
import org.opennaas.core.resources.ResourceException;
import org.opennaas.core.resources.ResourceManager;

/**
 * List the Resources that are in the IaaS Container
 * 
 * @author Scott Campbell (CRC)
 * 
 */
@Command(scope = "resource", name = "remove", description = "Remove one or more resources from the platform")
public class RemoveResourceCommand extends GenericKarafCommand {

	@Argument(index = 0, name = "resourceType:resourceName", description = "A space delimited list of resource type:name to be deleted", required = true, multiValued = true)
	private List<String>	resourceIDs;

	@Override
	protected Object doExecute() throws Exception {
		initcommand("remove resource");

		try {
			ResourceManager manager = (ResourceManager) getResourceManager();
			for (String id : resourceIDs) {

				if (!splitResourceName(id))
					return null;

				IResourceIdentifier identifier = null;
				try {
					identifier = manager.getIdentifierFromResourceName(argsRouterName[0], argsRouterName[1]);
					if (identifier != null) {

						printInfo("Removing Resource: "
								+ argsRouterName[1]);
						manager.removeResource(identifier);
						counter++;
						printInfo("Resource " + argsRouterName[1] + " removed.");
					} else {
						printError("The resource " + argsRouterName[1] +
										" is not found on repository.");
					}
				} catch (ResourceException e) {
					printError(e);
				}
				printSymbol(horizontalSeparator);

			}
			printInfo("Removed " + counter + " resource/s from " + resourceIDs.size());

		} catch (Exception e) {
			printError("Error removing Resource.");
			printError(e);
		}
		endcommand();
		return null;
	}

}