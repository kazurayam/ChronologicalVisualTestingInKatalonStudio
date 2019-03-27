package com.kazurayam.visualtesting

import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.TSuiteName
import com.kazurayam.materials.VisualTestingLogger
import com.kazurayam.visualtesting.ImageCollectionDifferRunner.ChronosOptions
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable

public class ImageDiffer {

	@Keyword
	static void runChronos(String testSuiteId, ChronosOptions options) {

		Objects.requireNonNull(testSuiteId, "testSuiteId must not be null")
		Objects.requireNonNull(options, "options must not be null")

		/*
		 * Prepare runtime environment
		 */
		MaterialRepository mr = (MaterialRepository)GlobalVariable[GVName.MATERIAL_REPOSITORY.getName()]
		MaterialStorage    ms = (MaterialStorage)GlobalVariable[GVName.MATERIAL_STORAGE.getName()]
		assert mr != null
		assert ms != null

		/*
		 * make image diffs, write the result into the directory
		 *     'Materials/47news.chronos_exam/yyyyMMdd_hhmmss/ImageDiff_new' which is
		 *     'Materials/<current TSuiteName>/<current Timestamp>/<cuurent TCaseName>'
		 */

		ImageCollectionDifferRunner runner = new ImageCollectionDifferRunner(mr)
		VisualTestingLogger logger = new VisualTestingLoggerImpl()
		runner.setVisualTestingLogger(logger)

		boolean result = runner.chronos(new TSuiteName(testSuiteId), ms, options)

		if (! result ) {
			KeywordUtil.markFailed("One or more pairs of screenshot are different.")
		}
	}

	@Keyword
	static void runTwins() {
	}
}
