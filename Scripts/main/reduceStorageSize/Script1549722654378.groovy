import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.TSuiteTimestamp
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
MaterialStorage    ms = (MaterialStorage)   GlobalVariable.MATERIAL_STORAGE
TSuiteName tsn        = new TSuiteName(mr.getCurrentTestSuiteId())
TSuiteTimestamp tst   = TSuiteTimestamp.newInstance(mr.getCurrentTestSuiteTimestamp())

def size = ms.reduce(20_000_000)

StringWriter sw = new StringWriter()
ms.list(sw, new HashMap())
WebUI.comment(sw.toString())
