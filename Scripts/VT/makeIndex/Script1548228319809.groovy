import java.nio.file.Path

import com.kazurayam.materials.MaterialRepository
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * This test case creates ./Materials/index.html file
 *
 * This test case makes no interaction with web.
 * It just read files and write a file.
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

Path index = mr.makeIndex()

WebUI.comment(">>> ${index.toString()} is updated")