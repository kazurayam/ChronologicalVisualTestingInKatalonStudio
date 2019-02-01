import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.TSuiteTimestamp
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * copy the Material files under the ./Materials/<Test Suite Name> directory.
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
MaterialStorage    ms = (MaterialStorage)   GlobalVariable.MATERIAL_STORAGE

WebUI.comment("mr.getCurrentTestSuiteId(): ${mr.getCurrentTestSuiteId()}")

int count = ms.backup(mr, 
	new TSuiteName(mr.getCurrentTestSuiteId()),
	TSuiteTimestamp.newInstance(mr.getCurrentTestSuiteTimestamp())
	)

WebUI.comment("copied ${count} files from ${mr.getBaseDir().toString()} to ${ms.getBaseDir().toString()}")