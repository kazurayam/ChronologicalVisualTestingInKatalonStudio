import com.kazurayam.materials.Helpers
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.visualtesting.GVName

import internal.GlobalVariable as GlobalVariable

/**
 * delete the contents of the ./Materials directory
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable[GVName.MATERIAL_REPOSITORY.getName()]
assert mr != null

Helpers.deleteDirectoryContents(mr.getBaseDir())