package com.kazurayam.visualtesting

import com.kms.katalon.core.context.TestSuiteContext

public class TestSuiteContextImpl implements TestSuiteContext {

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
