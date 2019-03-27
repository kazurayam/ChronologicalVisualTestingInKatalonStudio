import com.kazurayam.visualtesting.ImageCollectionDifferRunner
import com.kazurayam.visualtesting.ImageCollectionDifferRunner.ChronosOptions

/*
 * Declare parameters
 */
String TESTSUITE_ID = '47news/chronos_capture'
ChronosOptions options = new ChronosOptions.Builder().
							filterDataLessThan(0.0).
							shiftCriteriaPercentageBy(2.0).
							build()

CustomKeywords.'com.kazurayam.visualtesting.ImageDiff.runChronos'(TESTSUITE_ID, options)
