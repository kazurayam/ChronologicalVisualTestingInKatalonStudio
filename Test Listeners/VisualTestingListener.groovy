import com.kazurayam.visualtesting.VisualTestingListenerImpl
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class VisualTestingListener {
	
	static VisualTestingListenerImpl listener = new VisualTestingListenerImpl()
		
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		listener.beforeTestSuite(testSuiteContext)
	}
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		listener.beforeTestCase(testCaseContext)
	}

}