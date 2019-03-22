import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * Reduce the size of 'Storage' directory 
 * This script will remove old files of the current TSuiteName sub-directory.
 */
MaterialStorage ms = (MaterialStorage)GlobalVariable.MATERIAL_STORAGE
assert ms != null

def size = ms.reduce(20_000_000)

// print messages emitted by ms.reduce()
StringWriter sw = new StringWriter()
ms.status(sw, new HashMap())
WebUI.comment(sw.toString())
