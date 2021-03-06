/**
 * This file was auto-generated by mofcomp -j version 1.0.0 on Wed Jan 12
 * 09:21:06 CET 2011.
 */

package org.opennaas.extensions.router.model;

import java.io.*;
import java.lang.Exception;

/**
 * This Class contains accessor and mutator methods for all properties defined in the CIM class RemoteAccessAvailableToElement as well as methods
 * comparable to the invokeMethods defined for this class. This Class implements the RemoteAccessAvailableToElementBean Interface. The CIM class
 * RemoteAccessAvailableToElement is described as follows:
 * 
 * Describes an element's knowledge regarding accessing other (i.e., remote) Servers and Systems.
 */
public class RemoteAccessAvailableToElement extends Dependency implements
		Serializable {

	/**
	 * This constructor creates a RemoteAccessAvailableToElementBeanImpl Class which implements the RemoteAccessAvailableToElementBean Interface, and
	 * encapsulates the CIM class RemoteAccessAvailableToElement in a Java Bean. The CIM class RemoteAccessAvailableToElement is described as follows:
	 * 
	 * Describes an element's knowledge regarding accessing other (i.e., remote) Servers and Systems.
	 */
	public RemoteAccessAvailableToElement() {
	};

	/**
	 * This method create an Association of the type RemoteAccessAvailableToElement between one RemoteServiceAccessPoint object and
	 * EnabledLogicalElement object
	 */
	public static RemoteAccessAvailableToElement link(RemoteServiceAccessPoint
			antecedent, EnabledLogicalElement dependent) {

		return (RemoteAccessAvailableToElement) Association.link(RemoteAccessAvailableToElement.class, antecedent, dependent);
	}// link

	/**
	 * The following constants are defined for use with the ValueMap/Values qualified property isDefault.
	 */
	private boolean	isDefault;

	/**
	 * This method returns the RemoteAccessAvailableToElement.isDefault property value. This property is described as follows:
	 * 
	 * Indicates that this access information is defined as a default configuration for the system.
	 * 
	 * @return boolean current isDefault property value
	 * @exception Exception
	 */
	public boolean isIsDefault() {

		return this.isDefault;
	} // getIsDefault

	/**
	 * This method sets the RemoteAccessAvailableToElement.isDefault property value. This property is described as follows:
	 * 
	 * Indicates that this access information is defined as a default configuration for the system.
	 * 
	 * @param boolean new isDefault property value
	 * @exception Exception
	 */
	public void setIsDefault(boolean isDefault) {

		this.isDefault = isDefault;
	} // setIsDefault

	/**
	 * The following constants are defined for use with the ValueMap/Values qualified property orderOfAccess.
	 */
	private int	orderOfAccess;

	/**
	 * This method returns the RemoteAccessAvailableToElement.orderOfAccess property value. This property is described as follows:
	 * 
	 * When an element is accessing remote services and systems, it MAY be necessary to order those accesses. This property defines that ordering -
	 * where lower numbers indicate a higher priority for access. A value of 0 (default) indicates that ordering does not apply. If multiple
	 * RemoteAccessPoint instances have the same value for OrderOfAccess, then these AccessPoints MAY be used in any sequence defined by the
	 * implementation.
	 * 
	 * @return int current orderOfAccess property value
	 * @exception Exception
	 */
	public int getOrderOfAccess() {

		return this.orderOfAccess;
	} // getOrderOfAccess

	/**
	 * This method sets the RemoteAccessAvailableToElement.orderOfAccess property value. This property is described as follows:
	 * 
	 * When an element is accessing remote services and systems, it MAY be necessary to order those accesses. This property defines that ordering -
	 * where lower numbers indicate a higher priority for access. A value of 0 (default) indicates that ordering does not apply. If multiple
	 * RemoteAccessPoint instances have the same value for OrderOfAccess, then these AccessPoints MAY be used in any sequence defined by the
	 * implementation.
	 * 
	 * @param int new orderOfAccess property value
	 * @exception Exception
	 */
	public void setOrderOfAccess(int orderOfAccess) {

		this.orderOfAccess = orderOfAccess;
	} // setOrderOfAccess

} // Class RemoteAccessAvailableToElement
