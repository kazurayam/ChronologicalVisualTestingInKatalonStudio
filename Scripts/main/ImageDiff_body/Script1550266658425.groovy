import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import com.kazurayam.ksbackyard.Assert
import com.kazurayam.materials.FileType
import com.kazurayam.materials.MaterialPair
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TCaseName
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.TSuiteTimestamp
import com.kazurayam.materials.imagedifference.ImageCollectionDiffer
import com.kazurayam.materials.stats.ImageDeltaStats
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

TSuiteName tSuiteNameExam           = new TSuiteName(      GlobalVariable.CURRENT_TESTSUITE_ID )
TSuiteTimestamp tSuiteTimestampExam = new TSuiteTimestamp( GlobalVariable.CURRENT_TESTSUITE_TIMESTAMP )
TCaseName  tCaseNameExam            = new TCaseName(       GlobalVariable.CURRENT_TESTCASE_ID  )
Path previousIDS = StorageScanner.findLatestImageDeltaStats(ms, tSuiteNameExam, tCaseNameExam)

println "#imageDiff_body previousIDS is ${previousIDS.toString()}"
println "#imageDiff_body Paths.get('.').toAbsolutePath() is ${Paths.get('.').toAbsolutePath()}"

StorageScanner.Options options = new StorageScanner.Options.Builder().
		    previousImageDeltaStats( previousIDS ).
			shiftCriteriaPercentageBy( SHIFT_CRITERIA_PERCENTAGE_BY ).
			filterDataLessThan(5.0).
			build()

println "#imageDiff_body options.getPreviousImageDeltaStats() is ${options.getPreviousImageDeltaStats().toString()}"

StorageScanner storageScanner = new StorageScanner(ms, options)

// calculate the criteriaPercentages for each screenshot images based on the diffs of previous images
ImageDeltaStats imageDeltaStats = storageScanner.scan(new TSuiteName( TESTSUITE_ID ))

// persit the imageDeltaStats of this time into disk. It will be reused as previousIDS when this script run next time
storageScanner.persist(imageDeltaStats, tSuiteNameExam, tSuiteTimestampExam, tCaseNameExam)

// copy the ImageDeltaStats into ./Materials/image-delta-stats.json for later reference
imageDeltaStats.write(mr.resolveMaterialPath(GlobalVariable.CURRENT_TESTCASE_ID, 'image-delta-stats.json'))

// make ImageDiff files in the ./Materials/ImageDiff directory
new ImageCollectionDiffer(mr).makeImageCollectionDifferences(
		materialPairs,
		new TCaseName(GlobalVariable.CURRENT_TESTCASE_ID),  // 'Test Cases/main/ImageDiff'
		imageDeltaStats
	)


