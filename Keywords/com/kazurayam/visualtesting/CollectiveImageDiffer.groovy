package com.kazurayam.visualtesting

import java.nio.file.Path
import java.util.stream.Collectors

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
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable


public class CollectiveImageDiffer {

	private MaterialRepository mr
	private TSuiteName capturingTSuiteName
	
	CollectiveImageDiffer(MaterialRepository mr, TSuiteName capturingTSuiteName) {
		Objects.requireNonNull(mr, "mr must not be null")
		Objects.requireNonNull(capturingTSuiteName, "capturingTSuiteName must not be null")
		this.mr                  = mr
		this.capturingTSuiteName = capturingTSuiteName
		
	}


	public void chronos(MaterialRepository mr, TSuiteName examiningTSuiteName, 
						MaterialStorage ms,
						ChronosOptions options) {
		Objects.requireNonNull(mr, "mr must not be null")
		Objects.requireNonNull(examiningTSuiteName, "examiningTSuiteName must not be null")
		Objects.requireNonNull(ms, "ms must not be null")
		Objects.requireNonNull(options, "options must not be null")
		ImageDeltaStats stats = this.createImageDeltaStats(ms, capturingTSuiteName, options)
		ImageCollectionDiffer icDiffer = new ImageCollectionDiffer(mr)
		icDiffer.makeImageCollectionDifferences(
				this.createMaterialPairs(examiningTSuiteName, mr),
				new TCaseName( GlobalVariable[GVName.CURRENT_TESTCASE_ID] ),
				stats)
	}

	/**
	 * 
	 * @param tSuiteName
	 * @param mr
	 * @param criteriaPercentage
	 */
	public void twins(MaterialRepository mr, TSuiteName examiningTSuiteName,
						double criteriaPercentage) {
		ImageCollectionDiffer icDiffer = new ImageCollectionDiffer(mr)
		icDiffer.makeImageCollectionDifferences(
				this.createMaterialPairs(examiningTSuiteName, mr),
				new TCaseName( GlobalVariable[GVName.CURRENT_TESTCASE_ID] ),
				criteriaPercentage)
	}

	/**
	 * 
	 * @param tSuiteName
	 * @param mr
	 * @return
	 */
	private List<MaterialPair> createMaterialPairs(TSuiteName capturingTSuiteName, MaterialRepository mr) {
		List<MaterialPair> materialPairs = mr.createMaterialPairs(
				capturingTSuiteName
				).stream().filter { mp ->
					mp.getLeft().getFileType() == FileType.PNG
				}.collect(Collectors.toList())

		if (materialPairs.size() == 0) {
			KeywordUtil.markFailedAndStop(
				">>> The capturingTSuiteName is \"${capturingTSuiteName.getId()}\"" + 
				", which has materialPairs.size() of 0.")
		}

		return materialPairs
	}

	/**
	 * 
	 * @return
	 */
	private ImageDeltaStats createImageDeltaStats(MaterialStorage ms,
													TSuiteName capturingTSuiteName,
													ChronosOptions options ) {
		TSuiteName tSuiteNameExam           = new TSuiteName(      GlobalVariable[GVName.CURRENT_TESTSUITE_ID]        )
		TSuiteTimestamp tSuiteTimestampExam = new TSuiteTimestamp( GlobalVariable[GVName.CURRENT_TESTSUITE_TIMESTAMP] )
		TCaseName  tCaseNameExam            = new TCaseName(       GlobalVariable[GVName.CURRENT_TESTCASE_ID]         )
		Path previousIDS = StorageScanner.findLatestImageDeltaStats(ms, tSuiteNameExam, tCaseNameExam)
		//
		StorageScanner storageScanner =
		new StorageScanner(
			ms,
			new StorageScanner.Options.Builder().
				previousImageDeltaStats( previousIDS ).
				filterDataLessThan( options.getFilterDataLessThan() ).
				shiftCriteriaPercentageBy( options.getShiftCriteriaPercentageBy() ).
				build())

		// calculate the criteriaPercentages for each screenshot images based on the diffs of previous images
		ImageDeltaStats imageDeltaStats = storageScanner.scan(capturingTSuiteName)

		// persit the imageDeltaStats of this time into disk. It will be reused as previousIDS when this script run next time
		storageScanner.persist(imageDeltaStats, tSuiteNameExam, tSuiteTimestampExam, tCaseNameExam)

		//
		return imageDeltaStats
	}

	/**
	 *
	 */
	static class ChronosOptions {
		private double filterDataLessThan
		private double shiftCriteriaPercentageBy
		
		static class Builder {
			private double filterDataLessThan
			private double shiftCriteriaPercentageBy
			Builder() {
				this.filterDataLessThan = 0.0
				this.shiftCriteriaPercentageBy = 0.0
			}
			Builder filterDataLessThan(double value) {
				if (value < 0.0) {
					throw new IllegalArgumentException("filterDataLessThan must not be negative")
				}
				if (value > 100.0) {
					throw new IllegalArgumentException("filterDataLessThan must not be  > 100.0")
				}
				this.filterDataLessThan = value
				return this
			}
			Builder shiftCriteriaPercentageBy(double value) {
				if (value < 0.0) {
					throw new IllegalArgumentException("shiftCriteriaPercentageBy must not be negative")
				}
				if (value > 100.0) {
					throw new IllegalArgumentException("shiftCriteriaPercentageBy must not be > 100.0")
				}
				this.shiftCriteriaPercentageBy = value
				return this
			}
			ChronosOptions build() {
				return new ChronosOptions(this)
			}
		}
		
		private ChronosOptions(Builder builder) {
			this.filterDataLessThan = builder.filterDataLessThan
			this.shiftCriteriaPercentageBy = builder.filterDataLessThan
		}
		
		double getFilterDataLessThan() {
			return this.filterDataLessThan
		}
		
		double getShiftCriteriaPercentageBy() {
			return this.shiftCriteriaPercentageBy
		}
		
		@Override
		String toString() {
			this.toJsonText()
		}
		
		String toJsonText() {
			StringBuilder sb = new StringBuilder()
			sb.append("{")
			//
			sb.append("\"shiftCriteriaPercentageBy\":")
			sb.append(this.getShiftCriteriaPercentageBy())
			sb.append(",")
			//
			sb.append("\"filterDataLessThan\":")
			sb.append(this.getFilterDataLessThan())
			sb.append("}")
			return sb.toString()
		}
	}												
}
