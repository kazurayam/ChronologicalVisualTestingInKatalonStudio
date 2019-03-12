import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * main/TC_47News/ImageDiff
 *
 * This test case reads 2 sets of PNG files and creates a set of PNG files.
 *
 * This test case compares 2 img files, calculate how much different these are, and
 * generates 1 ImageDiff file.
 *
 * This test case makes no interaction with web.
 */

WebUI.callTestCase(findTestCase('main/ImageDiff_body'),
	[
		'TESTSUITE_ID': '47News_chronos_capture',
		'SHIFT_CRITERIA_PERCENTAGE_BY': 2.0
	])
