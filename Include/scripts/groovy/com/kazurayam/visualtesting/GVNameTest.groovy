package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import internal.GlobalVariable

@RunWith(JUnit4.class)
public class GVNameTest {

	@Test
	void testCURRENT_TESTSUITE_ID() {
		def actual   = GVName.CURRENT_TESTSUITE_ID.getName()
		def expected = 'CURRENT_TESTSUITE_ID'
		assertThat(actual, is(expected))
	}

	@Test
	void testCURRENT_TESTSUITE_TIMESTAMP() {
		def actual   = GVName.CURRENT_TESTSUITE_TIMESTAMP.getName()
		def expected = 'CURRENT_TESTSUITE_TIMESTAMP'
		assertThat(actual, is(expected))
	}

	@Test
	void testCURRENT_TESTCASE_ID() {
		def actual   = GVName.CURRENT_TESTCASE_ID.getName()
		def expected = 'CURRENT_TESTCASE_ID'
		assertThat(actual, is(expected))
	}

	@Test
	void testMATERIAL_REPOSITORY() {
		def actual   = GVName.MATERIAL_REPOSITORY.getName()
		def expected = 'MATERIAL_REPOSITORY'
		assertThat(actual, is(expected))
	}

	@Test
	void testMATERIAL_STORAGE() {
		def actual   = GVName.MATERIAL_STORAGE.getName()
		def expected = 'MATERIAL_STORAGE'
		assertThat(actual, is(expected))
	}
}