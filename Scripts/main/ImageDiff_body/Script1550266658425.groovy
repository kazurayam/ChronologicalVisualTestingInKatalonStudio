import java.nio.file.Path
import java.util.stream.Collectors

import com.kazurayam.ksbackyard.Assert
import com.kazurayam.materials.FileType
import com.kazurayam.materials.ImageDeltaStats
import com.kazurayam.materials.MaterialPair
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TCaseName
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.imagedifference.ImageCollectionDiffer
import com.kazurayam.materials.stats.StorageScanner
import com.kms.katalon.core.model.FailureHandling

import internal.GlobalVariable

/**
 * main/ImageDiff
 *
 * This test case reads 2 sets of PNG files and creates a set of PNG files.
 *
 * This test case compares 2 img files, calculate how much different these are, and
 * generates 1 ImageDiff file.
 *
 * This test case makes no interaction with web.
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

// scan the ./Materials directory to make a list of MateriaPair object.
// The 1st and 2nd latest TSuiteResult 'Test Suites/Monitor47News' are looked up.
// The list will be filtered to include PNG files only.
List<MaterialPair> materialPairs = mr.createMaterialPairs(
		new TSuiteName( TESTSUITE_ID )
		).stream().filter { mp ->
			mp.getLeft().getFileType() == FileType.PNG
		}.collect(Collectors.toList())

// make sure the list of MateriaPairs is not empty
Assert.assertTrue(">>> materialPairs.size() is 0. there must be something wrong.",
	materialPairs.size() > 0,
	FailureHandling.STOP_ON_FAILURE)

// scan the Storage directory to prepare a ImageDiffStats object
MaterialStorage ms = (MaterialStorage)GlobalVariable.MATERIAL_STORAGE
StorageScanner storageScanner = 
	new StorageScanner(
		ms,
		new StorageScanner.Options.Builder().
			defaultCriteriaPercentage(CRITERIA_PERCENTAGE).
			filterDataLessThan(3.0).
			build())

// calculate the criteriaPercentages for each screenshot images based on the diffs of previous images
ImageDeltaStats imageDeltaStats = storageScanner.scan(new TSuiteName( TESTSUITE_ID ))

// save the ImageDeltaStats into ./Materials/image-delta-stats.json for later reference
Path imageDeltaStatsJson = mr.resolveMaterialPath(GlobalVariable.CURRENT_TESTCASE_ID, 'image-delta-stats.json')
imageDeltaStats.write(imageDeltaStatsJson)

// make ImageDiff files in the ./Materials/ImageDiff directory
new ImageCollectionDiffer(mr).makeImageCollectionDifferences(
		materialPairs,
		new TCaseName(GlobalVariable.CURRENT_TESTCASE_ID),  // 'Test Cases/main/ImageDiff'
		imageDeltaStats
	)


