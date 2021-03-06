package org.opennaas.itests.router.chassis;

/*
 * #%L
 * OpenNaaS :: iTests :: Router
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

import static org.ops4j.pax.exam.CoreOptions.options;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennaas.core.resources.IResource;
import org.opennaas.core.resources.IResourceManager;
import org.opennaas.core.resources.ResourceException;
import org.opennaas.core.resources.capability.CapabilityException;
import org.opennaas.core.resources.descriptor.CapabilityDescriptor;
import org.opennaas.core.resources.descriptor.ResourceDescriptor;
import org.opennaas.core.resources.helpers.ResourceHelper;
import org.opennaas.core.resources.profile.IProfileManager;
import org.opennaas.core.resources.protocol.IProtocolManager;
import org.opennaas.core.resources.protocol.ProtocolException;
import org.opennaas.core.resources.queue.QueueResponse;
import org.opennaas.extensions.queuemanager.IQueueManagerCapability;
import org.opennaas.extensions.router.capability.chassis.IChassisCapability;
import org.opennaas.extensions.router.capability.ip.IIPCapability;
import org.opennaas.extensions.router.model.ComputerSystem;
import org.opennaas.extensions.router.model.EthernetPort;
import org.opennaas.extensions.router.model.LogicalPort;
import org.opennaas.itests.helpers.InitializerTestHelper;
import org.opennaas.itests.helpers.OpennaasExamOptions;
import org.opennaas.itests.helpers.TestsConstants;
import org.opennaas.itests.router.helpers.ExistanceHelper;
import org.opennaas.itests.router.helpers.ParamCreationHelper;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.BundleContext;
import org.osgi.service.blueprint.container.BlueprintContainer;

/**
 * These tests check the subinterface configurations
 * 
 * http://jira.i2cat.net:8080/browse/MANTYCHORE-272
 * 
 */

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ChassisLRIntegrationTest
{

	private final static Log	log					= LogFactory.getLog(ChassisLRIntegrationTest.class);

	private final static String	RESOURCE_INFO_NAME	= "LogicalRouter Test";
	@SuppressWarnings("unused")
	@Inject
	private BundleContext		bundleContext;

	@Inject
	private IResourceManager	resourceManager;

	protected IResource			routerResource;

	@SuppressWarnings("unused")
	@Inject
	private IProfileManager		profileManager;

	@Inject
	private IProtocolManager	protocolManager;

	private boolean				isMock;
	private IResource			resource;
	private EthernetPort		iface;
	private IResource			LRresource;
	private String				LRName				= "cpe2";
	private String				interfaceName		= "fe-0/0/3.1";

	@Inject
	@Filter(value = "(osgi.blueprint.container.symbolicname=org.opennaas.extensions.router.repository)", timeout = 20000)
	private BlueprintContainer	routerRepoService;

	@Inject
	@Filter(value = "(osgi.blueprint.container.symbolicname=org.opennaas.extensions.router.capability.ip)", timeout = 20000)
	private BlueprintContainer	ipService;

	@Inject
	@Filter(value = "(osgi.blueprint.container.symbolicname=org.opennaas.extensions.queuemanager)", timeout = 20000)
	private BlueprintContainer	queueService;

	@Configuration
	public static Option[] configuration() {
		return options(
				OpennaasExamOptions.opennaasDistributionConfiguration(),
				OpennaasExamOptions.includeFeatures("opennaas-router", "opennaas-router-driver-junos", "itests-helpers"),
				OpennaasExamOptions.noConsole(), OpennaasExamOptions.doNotDelayShell(), 
				OpennaasExamOptions.keepLogConfiguration(),
				OpennaasExamOptions.keepRuntimeFolder());
	}

	public ChassisLRIntegrationTest() {
		this.isMock = true;
		EthernetPort ethernetPort = new EthernetPort();
		ethernetPort.setName(interfaceName);
		ethernetPort.setPortNumber(1);
		iface = ethernetPort;
	}

	@Test
	public void GRESubInterfaceConfigurationTest() throws CapabilityException, ProtocolException {

		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));

		Assert.assertNotNull(chassisCapability);

		EthernetPort ethernetPort = (EthernetPort) ParamCreationHelper.newParamsInterfaceGRE();
		chassisCapability.createSubInterface(ethernetPort);

		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));

		Assert.assertNotNull(queueCapability);

		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsInterface((ComputerSystem) resource.getModel(), ethernetPort);
		Assert.assertTrue(pos != -1);
	}

	/**
	 * Test the possibility to configure subinterfaces with an encapsulation
	 * 
	 * @throws ProtocolException
	 * @throws CapabilityException
	 * */
	@Test
	public void subInterfaceConfigurationTest() throws ProtocolException, CapabilityException {

		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));

		Assert.assertNotNull(chassisCapability);

		EthernetPort ethernetPort = (EthernetPort) ParamCreationHelper.newParamsInterfaceEtherVLAN();

		chassisCapability.createSubInterface(ethernetPort);

		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));

		Assert.assertNotNull(queueCapability);

		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsInterface((ComputerSystem) resource.getModel(), ethernetPort);
		Assert.assertTrue(pos != -1);

	}

	/**
	 * Put related task
	 * 
	 * @throws ProtocolException
	 * @throws CapabilityException
	 * */
	@Test
	public void simpleSubInterfaceConfigurationTest() throws ProtocolException, CapabilityException {

		/* send action */

		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));

		Assert.assertNotNull(chassisCapability);

		EthernetPort ethernetPort = (EthernetPort) ParamCreationHelper.newParamsInterfaceEthernet();

		chassisCapability.createSubInterface(ethernetPort);

		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));
		Assert.assertNotNull(queueCapability);

		/* execute action */
		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsInterface((ComputerSystem) resource.getModel(), ethernetPort);
		Assert.assertTrue(pos != -1);

	}

	@Test
	public void setSubInterfaceDescriptionTest()
			throws CapabilityException, ResourceException, ProtocolException
	{
		/* send action */
		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));
		Assert.assertNotNull(chassisCapability);

		IIPCapability ipCapability = (IIPCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.IP_CAPABILITY_TYPE));
		Assert.assertNotNull(ipCapability);

		EthernetPort ethernetPort = new EthernetPort();
		ethernetPort.setName(iface.getName());
		ethernetPort.setPortNumber(iface.getPortNumber());
		ethernetPort.setDescription("Description for the setSubInterfaceDescription test");
		ethernetPort.setElementName(LRName);

		ipCapability.setInterfaceDescription(ethernetPort);

		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));
		Assert.assertNotNull(queueCapability);

		/* execute action */
		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		resourceManager.startResource(LRresource.getResourceIdentifier());

		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsSubInterface((ComputerSystem) LRresource.getModel(), ethernetPort);
		Assert.assertTrue(pos != -1);

		String desc = ((EthernetPort) ((ComputerSystem) LRresource.getModel()).getLogicalDevices().get(pos)).getDescription();
		Assert.assertTrue(desc.equals(ethernetPort.getDescription()));
	}

	/**
	 * Test the possibility to configure subinterfaces with an encapsulation
	 * 
	 * @throws ProtocolException
	 * */
	@Test
	public void setInterfaceDescriptionTest()
			throws CapabilityException, ResourceException, ProtocolException
	{
		/* send action */
		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));
		Assert.assertNotNull(chassisCapability);

		IIPCapability ipCapability = (IIPCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.IP_CAPABILITY_TYPE));
		Assert.assertNotNull(ipCapability);

		LogicalPort logicalPort = new LogicalPort();
		logicalPort.setName("fe-0/3/2");
		logicalPort.setDescription("Description for the setSubInterfaceDescription test");
		logicalPort.setElementName(LRName);

		ipCapability.setInterfaceDescription(logicalPort);

		/* execute action */
		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));

		Assert.assertNotNull(queueCapability);

		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		resourceManager.startResource(LRresource.getResourceIdentifier());

		/* check the update model, it is only possible to check it with a real router */
		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsInterface((ComputerSystem) LRresource.getModel(), logicalPort);
		Assert.assertTrue(pos != -1);

		String desc = ((LogicalPort) ((ComputerSystem) LRresource.getModel()).getLogicalDevices().get(pos)).getDescription();
		Assert.assertTrue(desc.equals(logicalPort.getDescription()));
	}

	/**
	 * Put related task
	 * 
	 * @throws ProtocolException
	 * */
	@Test
	public void setSubInterfaceDescriptioninLRTest()
			throws CapabilityException, ResourceException, ProtocolException
	{
		/* send action */
		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));
		Assert.assertNotNull(chassisCapability);

		IIPCapability ipCapability = (IIPCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.IP_CAPABILITY_TYPE));
		Assert.assertNotNull(ipCapability);

		EthernetPort ethernetPort = new EthernetPort();
		ethernetPort.setName(iface.getName());
		ethernetPort.setPortNumber(iface.getPortNumber());
		ethernetPort.setDescription("Description for the setSubInterfaceDescription test");
		ethernetPort.setElementName(LRName);

		ipCapability.setInterfaceDescription(ethernetPort);

		/* execute action */
		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));

		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		resourceManager.startResource(LRresource.getResourceIdentifier());

		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsSubInterface((ComputerSystem) LRresource.getModel(), ethernetPort);
		Assert.assertTrue(pos != -1);

		String desc = ((EthernetPort) ((ComputerSystem) LRresource.getModel()).getLogicalDevices().get(pos)).getDescription();
		Assert.assertTrue(desc.equals(ethernetPort.getDescription()));
	}

	/**
	 * Test the possibility to configure subinterfaces with an encapsulation
	 * 
	 * @throws ProtocolException
	 * */
	@Test
	public void setInterfaceDescriptionInLRTest()
			throws CapabilityException, ResourceException, ProtocolException
	{
		/* send action */
		IChassisCapability chassisCapability = (IChassisCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.CHASSIS_CAPABILITY_TYPE));
		Assert.assertNotNull(chassisCapability);

		IIPCapability ipCapability = (IIPCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.IP_CAPABILITY_TYPE));
		Assert.assertNotNull(ipCapability);

		LogicalPort logicalPort = new LogicalPort();
		logicalPort.setName("fe-0/3/2");
		logicalPort.setDescription("Description for the setSubInterfaceDescription test");
		logicalPort.setElementName(LRName);

		ipCapability.setInterfaceDescription(logicalPort);

		IQueueManagerCapability queueCapability = (IQueueManagerCapability) routerResource
				.getCapability(InitializerTestHelper.getCapabilityInformation(TestsConstants.QUEUE_CAPABILIY_TYPE));

		QueueResponse queueResponse = queueCapability.execute();
		Assert.assertTrue(queueResponse.isOk());

		if (isMock)
			return;

		resourceManager.startResource(LRresource.getResourceIdentifier());

		/* check the update model, it is only possible to check it with a real router */
		/* check the update model, it is only possible to check it with a real router */
		int pos = ExistanceHelper.containsInterface((ComputerSystem) LRresource.getModel(), logicalPort);
		Assert.assertTrue(pos != -1);

		String desc = ((LogicalPort) ((ComputerSystem) LRresource.getModel()).getLogicalDevices().get(pos)).getDescription();
		Assert.assertTrue(desc.equals(logicalPort.getDescription()));
	}

	public void startResource() throws ResourceException, ProtocolException {

		List<CapabilityDescriptor> lCapabilityDescriptors = new ArrayList<CapabilityDescriptor>();

		CapabilityDescriptor chassisCapabilityDescriptor = ResourceHelper.newCapabilityDescriptor(TestsConstants.ACTION_NAME,
				TestsConstants.CAPABILIY_VERSION,
				TestsConstants.CHASSIS_CAPABILITY_TYPE,
				TestsConstants.CAPABILITY_URI);
		lCapabilityDescriptors.add(chassisCapabilityDescriptor);

		// Add IP capability Descriptor

		CapabilityDescriptor ipCapabilityDescriptor = ResourceHelper.newCapabilityDescriptor(TestsConstants.ACTION_NAME,
				TestsConstants.CAPABILIY_VERSION,
				TestsConstants.IP_CAPABILITY_TYPE,
				TestsConstants.CAPABILITY_URI);
		lCapabilityDescriptors.add(ipCapabilityDescriptor);

		// Add Queue Capability Descriptor
		CapabilityDescriptor queueCapabilityDescriptor = ResourceHelper.newQueueCapabilityDescriptor();
		lCapabilityDescriptors.add(queueCapabilityDescriptor);

		// Router Resource Descriptor
		ResourceDescriptor resourceDescriptor = ResourceHelper.newResourceDescriptor(lCapabilityDescriptors, TestsConstants.RESOURCE_TYPE,
				TestsConstants.RESOURCE_URI,
				RESOURCE_INFO_NAME);

		routerResource = resourceManager.createResource(resourceDescriptor);

		// If not exists the protocol session manager, it's created and add the session context
		InitializerTestHelper.addSessionContext(protocolManager, routerResource.getResourceIdentifier().getId(), TestsConstants.RESOURCE_URI);

		// Start resource
		resourceManager.startResource(routerResource.getResourceIdentifier());
	}

	@Before
	public void initBundle() throws ResourceException, ProtocolException {
		InitializerTestHelper.removeResources(resourceManager);
		log.info("Initialized!");
		startResource();
	}

	@After
	public void stopBundle() throws ResourceException {
		InitializerTestHelper.removeResources(resourceManager);
		log.info("Stopped!");

	}

}