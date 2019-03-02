import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.MaterialStorageFactory

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

class TL {
	
		static Path reportDir
		static Path materialsDir
		static Path storageDir
	
		static {
			// for example, reportDir = C:/Users/username/katalon-workspace/UsingMaterialsDemo/Reports/TS1/20180618_165141
			reportDir = Paths.get(RunConfiguration.getReportFolder())
			materialsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('Materials')
			// for example, materialsFolder == C:/Users/username/katalon-workspace/UsingMaterialsDemo/Materials
			storageDir = Paths.get(RunConfiguration.getProjectDir()).resolve('Storage')
		}
	
		/**
		 * Executes before every test suite starts.
		 * @param testSuiteContext: related information of the executed test suite.
		 */
		@BeforeTestSuite
		def beforeTestSuite(TestSuiteContext testSuiteContext) {
			def testSuiteId = testSuiteContext.getTestSuiteId()            // e.g. 'Test Suites/TS1'
			def testSuiteTimestamp = reportDir.getFileName().toString()    // e.g. '20180618_165141'
			WebUI.comment(">>> testSuiteId is '${testSuiteId}', testSuiteTimestamp is '${testSuiteTimestamp}'")
			GlobalVariable.CURRENT_TESTSUITE_ID = testSuiteId
			GlobalVariable.CURRENT_TESTSUITE_TIMESTAMP = testSuiteTimestamp
			// initializing MaterialRepository
			Files.createDirectories(materialsDir)
			MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir)
			mr.putCurrentTestSuite(testSuiteId, testSuiteTimestamp)
			GlobalVariable.MATERIAL_REPOSITORY = mr
			WebUI.comment(">>> Instance of MaterialRepository(${mr.getBaseDir().toString()})" +
				" is set to GlobalVariable.MATERIAL_REPOSITORY")
			// initializing MaterialStorage
			Files.createDirectories(storageDir)
			MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
			GlobalVariable.MATERIAL_STORAGE = ms
			WebUI.comment(">>> Instance of MaterialStorage(${ms.getBaseDir().toString()}" +
				" is set to GlobalVariable.MATERIAL_STORAGE")
		}
	
	
		/**
		 * Executes before every test case starts.
		 * @param testCaseContext relate information of the executed test case.
		 */
		@BeforeTestCase
		def beforeTestCase(TestCaseContext testCaseContext) {
			if (GlobalVariable.MATERIAL_REPOSITORY == null) {
				MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir)
				GlobalVariable.MATERIAL_REPOSITORY = mr
			}
			GlobalVariable.CURRENT_TESTCASE_ID = testCaseContext.getTestCaseId()   //  e.g. 'Test Cases/TC1'
			if (GlobalVariable.MATERIAL_STORAGE == null) {
				MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
				GlobalVariable.MATERIAL_STORAGE = ms
			}
		}
	
	}