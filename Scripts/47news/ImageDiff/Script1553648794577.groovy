import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.VisualTestingLogger
import com.kazurayam.visualtesting.ImageCollectionDifferRunner
import com.kazurayam.visualtesting.VisualTestingLoggerImpl
import com.kazurayam.visualtesting.ImageCollectionDifferRunner.ChronosOptions
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable as GlobalVariable

/*
 * (1) Declare parameters
 */
String TESTSUITE_ID = '47news/chronos_capture'
double SHIFT_CRITERIA_PERCANTAGE_BY = 0.0

/*
 * (2) Prepare runtime environment
 */
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
MaterialStorage    ms = (MaterialStorage)GlobalVariable.MATERIAL_STORAGE
assert mr != null
assert ms != null

/*
 * (3) make image diffs, write the result into the directory
 *     'Materials/47news.chronos_exam/yyyyMMdd_hhmmss/ImageDiff_new' which is
 *     'Materials/<current TSuiteName>/<current Timestamp>/<cuurent TCaseName>'
 */
ChronosOptions options = 
    new ChronosOptions.Builder().
			filterDataLessThan(0.0).
			shiftCriteriaPercentageBy(SHIFT_CRITERIA_PERCANTAGE_BY).
			build()

ImageCollectionDifferRunner runner = new ImageCollectionDifferRunner(mr)
VisualTestingLogger logger = new VisualTestingLoggerImpl()
runner.setVisualTestingLogger(logger)

boolean result = runner.chronos(new TSuiteName(TESTSUITE_ID), ms, options)

if (! result ) {
	KeywordUtil.markFailed("One or more pairs of screenshot are different.")
}
