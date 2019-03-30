package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import com.kazurayam.visualtesting.ManagedGlobalVariable as MGV

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import internal.GlobalVariable

@RunWith(JUnit4.class)
public class ManagedGlobalVariableTest {

	@Test
	void testCURRENT_TESTSUITE_ID() {
		def actual   = MGV.CURRENT_TESTSUITE_ID.getName()
		def expected = 'VT_CURRENT_TESTSUITE_ID'
		assertThat(actual, is(expected))
	}

	@Test
	void testCURRENT_TESTSUITE_TIMESTAMP() {
		def actual   = MGV.CURRENT_TESTSUITE_TIMESTAMP.getName()
		def expected = 'VT_CURRENT_TESTSUITE_TIMESTAMP'
		assertThat(actual, is(expected))
	}

	@Test
	void testCURRENT_TESTCASE_ID() {
		def actual   = MGV.CURRENT_TESTCASE_ID.getName()
		def expected = 'VT_CURRENT_TESTCASE_ID'
		assertThat(actual, is(expected))
	}

	@Test
	void testMATERIAL_REPOSITORY() {
		def actual   = MGV.MATERIAL_REPOSITORY.getName()
		def expected = 'VT_MATERIAL_REPOSITORY'
		assertThat(actual, is(expected))
	}

	@Test
	void testMATERIAL_STORAGE() {
		def actual   = MGV.MATERIAL_STORAGE.getName()
		def expected = 'VT_MATERIAL_STORAGE'
		assertThat(actual, is(expected))
	}
}