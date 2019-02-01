import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * backupAllMaterials
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
MaterialStorage    ms = (MaterialStorage)   GlobalVariable.MATERIAL_STORAGE

int count = ms.backup(mr)

WebUI.comment("${count} files were copied from ${mr.getBaseDir().toString()} into ${ms.getBaseDir().toString()}")