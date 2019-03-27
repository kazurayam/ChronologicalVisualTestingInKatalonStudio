import com.kazurayam.materials.MaterialStorage
import com.kazurayam.visualtesting.GVName
import internal.GlobalVariable as GlobalVariable

/**
 * remove ALL files and directories in the MaterialStorage
 * 
 */
MaterialStorage    ms = (MaterialStorage)   GlobalVariable[GVName.MATERIAL_STORAGE.getName()]

ms.empty()

