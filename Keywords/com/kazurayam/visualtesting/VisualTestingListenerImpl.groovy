package com.kazurayam.visualtesting

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.MaterialStorageFactory
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.TSuiteTimestamp
import com.kazurayam.visualtesting.GlobalVariableHelpers as GVH
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class VisualTestingListenerImpl {

	private Path reportDir
	private Path materialsDir
	private Path storageDir

	VisualTestingListenerImpl() {
		// For example,
		// reportDir    -> C:/Users/username/katalon-workspace/VisualTestingInKatalonStudio/Reports/TS1/20180618_165141
		// materialsDir -> C:/Users/username/katalon-workspace/VisualTestingInKatalonStudio/Materials
		// storageDir   -> C:/Users/username/katalon-workspace/VisualTestingInKatalonStudio/Storage
		reportDir    = Paths.get(RunConfiguration.getReportFolder())
		materialsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('Materials')
		storageDir   = Paths.get(RunConfiguration.getProjectDir()).resolve('Storage')
	}

	/**
	 * 
	 * @param testSuiteContext
	 */
	void beforeTestSuite(TestSuiteContext testSuiteContext) {
		Objects.requireNonNull(testSuiteContext, "testSuiteContext must not be null")
		String testSuiteId        = testSuiteContext.getTestSuiteId()     // e.g. 'Test Suites/TS1'
		String testSuiteTimestamp = reportDir.getFileName().toString()    // e.g. '20180618_165141'
		//
		GVH.ensureGlobalVariable(GVName.CURRENT_TESTSUITE_ID, testSuiteId)
		GVH.ensureGlobalVariable(GVName.CURRENT_TESTSUITE_TIMESTAMP, testSuiteTimestamp)

		// create the MaterialRepository object
		Files.createDirectories(materialsDir)
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir)
		mr.putCurrentTestSuite(testSuiteId, testSuiteTimestamp)
		GVH.ensureGlobalVariable(GVName.MATERIAL_REPOSITORY, mr)

		// create the MaterialStorage object
		Files.createDirectories(storageDir)
		MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
		GVH.ensureGlobalVariable(GVName.MATERIAL_STORAGE, ms)
	}

	void beforeTestCase(TestCaseContext testCaseContext) {
		Objects.requireNonNull(testCaseContext, "testCaseContext must not be null")

		if ( ! GVH.isGlobalVariablePresent(GVName.CURRENT_TESTSUITE_ID) ) {
			GVH.ensureGlobalVariable(GVName.CURRENT_TESTSUITE_ID, TSuiteName.SUITELESS_DIRNAME)
		}
		if ( ! GVH.isGlobalVariablePresent(GVName.CURRENT_TESTSUITE_TIMESTAMP) ) {
			GVH.ensureGlobalVariable(GVName.CURRENT_TESTSUITE_TIMESTAMP, TSuiteTimestamp.TIMELESS_DIRNAME)
		}

		GVH.ensureGlobalVariable(GVName.CURRENT_TESTCASE_ID, testCaseContext.getTestCaseId())

		WebUI.comment("VisualTestingListenerImpl#beforeTestCase ${GVName.CURRENT_TESTSUITE_ID} is \"${GVH.getGlobalVariableValue(GVName.CURRENT_TESTSUITE_ID)}\"")
		WebUI.comment("VisualTestingListenerImpl#beforeTestCase ${GVName.CURRENT_TESTSUITE_TIMESTAMP} is \"${GVH.getGlobalVariableValue(GVName.CURRENT_TESTSUITE_TIMESTAMP)}\"")
		WebUI.comment("VisualTestingListenerImpl#beforeTestCase ${GVName.CURRENT_TESTCASE_ID} is \"${GVH.getGlobalVariableValue(GVName.CURRENT_TESTCASE_ID)}\"")

		//
		if ( ! GVH.isGlobalVariablePresent(GVName.MATERIAL_REPOSITORY) ) {
			Files.createDirectories(materialsDir)
			MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir)
			mr.putCurrentTestSuite(TSuiteName.SUITELESS_DIRNAME, TSuiteTimestamp.TIMELESS_DIRNAME)
			GVH.ensureGlobalVariable(GVName.MATERIAL_REPOSITORY, mr)
		}
		WebUI.comment("VisualTestingListenerImpl#beforeTestCase ${GVName.MATERIAL_REPOSITORY} is \"${GVH.getGlobalVariableValue(GVName.MATERIAL_REPOSITORY).toString()}\"")

		if ( ! GVH.isGlobalVariablePresent(GVName.MATERIAL_STORAGE) ) {
			Files.createDirectories(storageDir)
			MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
			GVH.ensureGlobalVariable(GVName.MATERIAL_STORAGE, ms)
		}
		WebUI.comment("VisualTestingListenerImpl#beforeTestCase ${GVName.MATERIAL_STORAGE} is \"${GVH.getGlobalVariableValue(GVName.MATERIAL_STORAGE).toString()}\"")

	}

	void afterTestCase(TestCaseContext testCaseContext) {
		// nothing to do
	}

	void afterTestSuite(TestSuiteContext testSuiteContext) {
		// nothing to do
	}
}
