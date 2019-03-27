import com.kazurayam.materials.MaterialStorage

import internal.GlobalVariable as GlobalVariable

/**
 * remove ALL files and directories in the MaterialStorage
 * 
 */
MaterialStorage    ms = (MaterialStorage)   GlobalVariable.MATERIAL_STORAGE

ms.empty()

