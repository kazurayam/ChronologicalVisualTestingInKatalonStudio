package com.kazurayam.visualtesting

import java.nio.file.Path
import java.nio.file.Paths
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*
import com.kms.katalon.core.configuration.RunConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import internal.GlobalVariable
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

@RunWith(JUnit4.class)
public class CoreListenerTest {

	@Test
	void test_beforeTestSuite() {
		CoreListener listener = new CoreListener()
		String testSuiteId = 'Test Suites/TS1'
		TestSuiteContext tsContext = new TestSuiteContextImpl(testSuiteId)
		listener.beforeTestSuite(tsContext)
		assertThat(GlobalVariable[GVName.CURRENT_TESTSUITE_ID.getName()], is(testSuiteId))
		//
		/*
		Path reportDir    = Paths.get(RunConfiguration.getReportFolder())
		String testSuiteTimestamp = reportDir.getFileName().toString()
		assertThat(GlobalVariable[GVName.CURRENT_TESTSUITE_TIMESTAMP.getName()], testSuiteTimestamp)
		*/
	}
	
	@Test
	void test_beforeTestCase() {
		CoreListener listener = new CoreListener()
		String testCaseId = 'Test Caes/TC1'
		TestCaseContext tcContext = new TestCaseContextImpl(testCaseId)
		listener.beforeTestCase(tcContext)
		assertThat(GlobalVariable[GVName.CURRENT_TESTCASE_ID.getName()], is(testCaseId))
	}
}
