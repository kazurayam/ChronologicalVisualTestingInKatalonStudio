import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.nio.file.Files as Files
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kazurayam.materials.MaterialRepository
import internal.GlobalVariable as GlobalVariable

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY

// resolve output dir
URL url = new URL('https://www.47news.jp/')

// open browser
WebUI.openBrowser('')

// give tall enough view port as the AUT uses a fixed/sticky footer
WebUI.setViewPortSize(1280, 3400)

// navigate to the Google form page
WebUI.navigateToUrl(url.toExternalForm())

WebUI.verifyElementPresent(findTestObject('47NEWS/a_TOP'), 10)

// take screenshot and save it into a file under the ./Materials folder
Path fileNamedFixed = mr.resolveMaterialPath(GlobalVariable.CURRENT_TESTCASE_ID,
	"47NEWS_TOP.png")
CustomKeywords.'com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage'(
	fileNamedFixed.toFile())

// close browser
WebUI.closeBrowser()
