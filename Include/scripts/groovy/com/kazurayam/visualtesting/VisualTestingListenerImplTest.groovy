package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.util.Map

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kazurayam.visualtesting.ManagedGlobalVariable as MGV

import internal.GlobalVariable

@RunWith(JUnit4.class)
public class VisualTestingListenerImplTest {

	@Test
	void test_beforeTestSuite() {
		VisualTestingListenerImpl listener = new VisualTestingListenerImpl()
		String testSuiteId = 'Test Suites/TS1'
		TestSuiteContext tsContext = new TestSuiteContextImpl(testSuiteId)
		listener.beforeTestSuite(tsContext)
		assertThat(GlobalVariable[MGV.CURRENT_TESTSUITE_ID.getName()], is(testSuiteId))
		//
		/*
		 Path reportDir    = Paths.get(RunConfiguration.getReportFolder())
		 String testSuiteTimestamp = reportDir.getFileName().toString()
		 assertThat(GlobalVariable[MGV.CURRENT_TESTSUITE_TIMESTAMP.getName()], testSuiteTimestamp)
		 */
	}

	@Test
	void test_beforeTestCase() {
		VisualTestingListenerImpl listener = new VisualTestingListenerImpl()
		String testCaseId = 'Test Caes/TC1'
		TestCaseContext tcContext = new TestCaseContextImpl(testCaseId)
		listener.beforeTestCase(tcContext)
		assertThat(GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()], is(testCaseId))
	}




	/**
	 * 
	 * @author urayamakazuaki
	 *
	 */
	static class TestCaseContextImpl implements TestCaseContext {
		private String testCaseId_

		TestCaseContextImpl(String testCaseId) {
			this.testCaseId_ = testCaseId
		}

		@Override
		public String getTestCaseId() {
			return this.testCaseId_
		}

		@Override
		public Map<String, Object> getTestCaseVariables() {
			return new HashMap<String, Object>()
		}

		@Override
		public String getTestCaseStatus() {
			return "FIXME"
		}

		@Override
		public String getMessage() {
			return "FIXME"
		}

		@Override
		public void skipThisTestCase() {}

		@Override
		public boolean isSkipped() {
			return false
		}
	}


	/**
	 * 
	 */
	static class TestSuiteContextImpl implements TestSuiteContext {

		private String testSuiteId_

		TestSuiteContextImpl(String testSuiteId) {
			this.testSuiteId_ = testSuiteId
		}

		@Override
		String getTestSuiteId() {
			return testSuiteId_
		}

		@Override
		String getStatus() {
			return 'FIXME'
		}
	}
}
