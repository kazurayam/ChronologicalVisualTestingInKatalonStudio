package com.kazurayam.visualtesting

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.MaterialStorageFactory
import com.kazurayam.visualtesting.GlobalVariableHelpers as GVH
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

public class CoreListener {

	private Path reportDir
	private Path materialsDir
	private Path storageDir

	CoreListener() {
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
		//
		GVH.ensureGlobalVariable(GVName.CURRENT_TESTCASE_ID, testCaseContext.getTestCaseId())
		//
		if ( ! GVH.isGlobalVariablePresent(GVName.MATERIAL_REPOSITORY) ) {
			MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir)
			GVH.ensureGlobalVariable(GVName.MATERIAL_REPOSITORY, mr)
		}
		if ( ! GVH.isGlobalVariablePresent(GVName.MATERIAL_STORAGE) ) {
			MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
			GVH.ensureGlobalVariable(GVName.MATERIAL_STORAGE, ms)
		}
	}

	void afterTestCase(TestCaseContext testCaseContext) {
		// nothing to do
	}

	void afterTestSuite(TestSuiteContext testSuiteContext) {
		// nothing to do
	}
}
