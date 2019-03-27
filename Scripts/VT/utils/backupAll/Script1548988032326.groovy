import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.visualtesting.GVName
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

/**
 * backupAllMaterials
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable[GVName.MATERIAL_REPOSITORY.getName()]
MaterialStorage    ms = (MaterialStorage)   GlobalVariable[GVName.MATERIAL_STORAGE.getName()]

int count = ms.backup(mr)

WebUI.comment("${count} files were copied from ${mr.getBaseDir().toString()} into ${ms.getBaseDir().toString()}")