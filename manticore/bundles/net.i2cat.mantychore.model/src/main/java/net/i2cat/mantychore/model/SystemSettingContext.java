/**
 * This file was auto-generated by mofcomp -j version 1.0.0 on Wed Jan 12
 * 09:21:06 CET 2011.
 */

package net.i2cat.mantychore.model;

import java.io.*;

/**
 * This Class contains accessor and mutator methods for all properties defined
 * in the CIM class SystemSettingContext as well as methods comparable to the
 * invokeMethods defined for this class. This Class implements the
 * SystemSettingContextBean Interface. The CIM class SystemSettingContext is
 * described as follows:
 *
 * Note: The Configuration and SystemConfiguration classes are deprecated in
 * lieu of using a ConcreteComponent relationship to indicate a hierarchy of
 * Settings or SettingData instances. Therefore, the aggregation of
 * SystemSettings into System Configurations is replaced by the
 * ConcreteComponent relationship between instances of Settings and
 * SettingData. Deprecated description: This relationship associates
 * System-specific Configuration objects with System-specific Setting
 * objects, similar to the SettingContext association.
 */
    @Deprecated
public class SystemSettingContext extends Association implements Serializable
    {

    /**
     * This constructor creates a SystemSettingContextBeanImpl Class which
     * implements the SystemSettingContextBean Interface, and encapsulates
     * the CIM class SystemSettingContext in a Java Bean. The CIM class
     * SystemSettingContext is described as follows:
     *
     * Note: The Configuration and SystemConfiguration classes are deprecated
     * in lieu of using a ConcreteComponent relationship to indicate a
     * hierarchy of Settings or SettingData instances. Therefore, the
     * aggregation of SystemSettings into System Configurations is replaced
     * by the ConcreteComponent relationship between instances of Settings
     * and SettingData. Deprecated description: This relationship associates
     * System-specific Configuration objects with System-specific Setting
     * objects, similar to the SettingContext association.
     */
    public SystemSettingContext(){};
    /**
     * This method create an Association of the type SystemSettingContext
     * between one SystemConfiguration object and SystemSetting object
     */
    @Deprecated
    public static SystemSettingContext link(SystemConfiguration
	context,SystemSetting setting){

    return (SystemSettingContext)
	Association.link(SystemSettingContext.class,context,setting);
    }//link

} // Class SystemSettingContext