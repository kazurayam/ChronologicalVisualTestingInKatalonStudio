package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import com.kazurayam.visualtesting.ManagedGlobalVariable as MGV

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import internal.GlobalVariable

@RunWith(JUnit4.class)
public class GlobalVariableHelpersTest {

	@Ignore
	@Test
	void test_touchGlobalVariable() {
		println "GlobalVariable.CURRENT_TESTCASE_ID is ${GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()]}"
	}

	@Test
	void test_addGlobalVariable() {
		GlobalVariableHelpers.addGlobalVariable('my_new_variable', 'foo')
		def actual   = GlobalVariable.getMy_new_variable()
		def expected = 'foo'
		assertThat(actual, is(expected))
	}

	@Test
	void test_isGlobalVariablePresent_predefined() {
		assertThat(GlobalVariableHelpers.isGlobalVariablePresent(MGV.CURRENT_TESTCASE_ID.getName()), is(true))
	}

	@Test
	void test_isGlobalVariablePresent_dynamicallyAdded() {
		GlobalVariableHelpers.addGlobalVariable('my_new_variable2', 'bar')
		assertThat(GlobalVariableHelpers.isGlobalVariablePresent('my_new_variable2'), is(true))
		println "GlobalVariable.my_new_variable2 is ${GlobalVariable.my_new_variable2}"
	}

	@Ignore
	@Test
	void test_ensureGlobalVariable_predefined() {
		GlobalVariableHelpers.ensureGlobalVariable(MGV.CURRENT_TESTCASE_ID, 'baz')
		assertThat(GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()], is('baz'))
		println "GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()] is ${GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()]}"
	}

	@Test
	void test_ensureGlobalVariable_dynamicallyAdded() {
		GlobalVariableHelpers.ensureGlobalVariable('my_new_variable3', 'fox')
		assertThat(GlobalVariable.my_new_variable3, is('fox'))
		println "GlobalVariable.my_new_variable3 is ${GlobalVariable.my_new_variable3}"
	}
}