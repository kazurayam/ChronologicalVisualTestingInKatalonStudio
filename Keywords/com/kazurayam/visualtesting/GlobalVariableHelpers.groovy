package com.kazurayam.visualtesting

import java.lang.reflect.Field
import java.lang.reflect.Modifier

import internal.GlobalVariable

public class GlobalVariableHelpers {

	private GlobalVariableHelpers() {}

	/**
	 * insert a public static property of type java.lang.Object
	 * into the internal.GlobalVarialbe runtime.
	 *
	 * e.g, addGlobalVariable('my_new_variable','foo') makes
	 * internal.GlobalVariable.getMy_new_variale() to return 'foo'
	 *
	 * @param name
	 * @param value
	 */
	static void addGlobalVariable(String name, Object value) {
		GroovyShell sh = new GroovyShell()
		MetaClass mc = sh.evaluate("internal.GlobalVariable").metaClass
		String getterName = 'get' + ((CharSequence)name).capitalize()
		mc.'static'."${getterName}" = {-> return value }
		mc.'static'."${name}"       = value
	}
	
	static void addGlobalVariable(GVName gvName, Object value) {
		addGlobalVariable(gvName.getName(), value)
	}

	/**
	 * @return true if GlobalVarialbe.name is defined, otherwise false
	 */
	static boolean isGlobalVariablePresent(String name) {
		return internal.GlobalVariable.metaClass.hasProperty( internal.GlobalVariable, name ) &&
				internal.GlobalVariable.name
	}
	
	static boolean isGlobalVariablePresent(GVName gvName) {
		return isGlobalVariablePresent(gvName.getName())
	}

	/**
	 * If GlobaleVariable.name is present, set the value into it.
	 * Otherwise create GlobalVariable.name dynamically and set the value into it.
	 * 
	 * @param name
	 * @param value
	 */
	static void ensureGlobalVariable(String name, Object value) {
		if (isGlobalVariablePresent(name)) {
			GlobalVariable[name] = value
		} else {
			addGlobalVariable(name, value)
		}
	}

	static void ensureGlobalVariable(GVName gvName, Object value) {
		ensureGlobalVariable(gvName.getName(), value)
	}
}
