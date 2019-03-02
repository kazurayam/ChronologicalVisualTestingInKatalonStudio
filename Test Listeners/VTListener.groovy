import com.kazurayam.visualtesting.CoreListener
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class VTListener {
	
	static CoreListener listener = new CoreListener()
		
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		listener.beforeTestSuite(testSuiteContext)
	}
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		listener.beforeTestCase(testCaseContext)
	}

}