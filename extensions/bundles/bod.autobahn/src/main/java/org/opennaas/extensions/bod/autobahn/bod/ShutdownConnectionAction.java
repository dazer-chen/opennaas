package org.opennaas.extensions.bod.autobahn.bod;

/*
 * #%L
 * OpenNaaS :: BoD :: Autobahn driver
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

import net.geant.autobahn.useraccesspoint.UserAccessPoint;

import org.opennaas.core.resources.action.ActionException;
import org.opennaas.core.resources.action.ActionResponse;
import org.opennaas.core.resources.protocol.IProtocolSessionManager;
import org.opennaas.core.resources.protocol.ProtocolException;
import org.opennaas.extensions.bod.autobahn.AutobahnAction;
import org.opennaas.extensions.bod.autobahn.commands.CancelServiceCommand;
import org.opennaas.extensions.bod.autobahn.commands.IAutobahnCommand;
import org.opennaas.extensions.bod.autobahn.commands.Transaction;
import org.opennaas.extensions.bod.autobahn.model.AutobahnLink;
import org.opennaas.extensions.bod.capability.l2bod.L2BoDActionSet;

public class ShutdownConnectionAction extends AutobahnAction
{
	public final static String	ACTIONID	= L2BoDActionSet.SHUTDOWN_CONNECTION;

	public ShutdownConnectionAction()
	{
		setActionID(ACTIONID);
	}

	@Override
	public ActionResponse execute(IProtocolSessionManager protocolSessionManager)
			throws ActionException
	{
		try {
			UserAccessPoint userAccessPoint =
					getUserAccessPointService(protocolSessionManager);
			IAutobahnCommand command =
					new CancelServiceCommand(userAccessPoint, getLink());

			Transaction.getInstance().add(command);

			return ActionResponse.okResponse(getActionID());
		} catch (ProtocolException e) {
			throw new ActionException(e);
		}
	}

	protected AutobahnLink getLink()
			throws ActionException
	{
		return (AutobahnLink) params;
	}
}
