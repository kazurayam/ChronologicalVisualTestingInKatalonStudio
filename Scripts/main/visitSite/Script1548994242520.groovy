import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path as Path

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.ksbackyard.ScreenshotDriver.Options
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * Test Cases/main/visit47NEWS
 */

/*
 *  Visi a page, take screenshot, save it into the Materials directory.
 *  
 *  The filename must be unique within a test case.
 */
def visitPage(MaterialRepository mr, URL url, String fileName) {
	// navigate to the Google form page
	WebUI.navigateToUrl(url.toExternalForm())
	WebUI.verifyElementPresent(findTestObject('47NEWS/div_main-post01'), 10)
	// take screenshot and save it into a file under the ./Materials folder
	Path fileNamedFixed = mr.resolveMaterialPath(GlobalVariable.CURRENT_TESTCASE_ID, fileName)
	Options options = new Options.Builder().timeout(200).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_main-bnr')).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_sidebar')).
						build()
	CustomKeywords.'com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage'(
		fileNamedFixed.toFile(),
		options)
}

// prepare environement
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY

// open browser
WebUI.openBrowser('')

// give tall enough view port.
// this is necessary because the AUT uses a fixed/sticky footer.
WebUI.setViewPortSize(1280, 4000)

// visit pages and take screenshots
visitPage(mr, new URL('https://www.47news.jp/'), '47NEWS_TOP.png')
// ... can visit more pages
// ... can visit more pages
// ... can visit more pages

// close browser
WebUI.closeBrowser()

// let MaterialRepository to scan the Materials directory for the updated Material files
mr.scan()


