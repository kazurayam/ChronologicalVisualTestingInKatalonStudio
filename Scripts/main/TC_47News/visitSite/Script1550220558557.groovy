import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path as Path

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

import com.kazurayam.ksbackyard.ScreenshotDriver.Options
import com.kazurayam.materials.MaterialRepository
import com.kms.katalon.core.webui.driver.DriverFactory
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
	//
	// modify the style of <div class="global-nav fixed"> to have position:static
	// to make the screenshot pretty looking
	JavascriptExecutor js = (JavascriptExecutor)DriverFactory.getWebDriver()
	js.executeScript("document.head.appendChild(document.createElement(\"style\"))" +
		".innerHTML = \".fixed {position: static !important; }\"")
	
	WebUI.delay(3)
	
	// take screenshot with width=640 px and save it into a file under the ./Materials folder
	Path fileNamedFixed = mr.resolveMaterialPath(GlobalVariable.CURRENT_TESTCASE_ID, fileName)
	Options options = new Options.Builder().timeout(500).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_main-post02')).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_main-bnr')).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_sidebar')).
						addIgnoredElement(findTestObject('Object Repository/47NEWS/div_footer-ad')).
						// width(640).
						build()
	CustomKeywords.'com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage'(
		fileNamedFixed.toFile(),
		options)
}

// prepare environement
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY

// open browser
WebUI.openBrowser('')

// set appropriate window size
WebUI.setViewPortSize(1280, 800)

// visit pages and take screenshots
visitPage(mr, new URL('https://www.47news.jp/'), '47NEWS_TOP.png')
// ... can visit more pages
// ... can visit more pages
// ... can visit more pages

// close browser
WebUI.closeBrowser()

// let MaterialRepository to scan the Materials directory for the updated Material files
mr.scan()


