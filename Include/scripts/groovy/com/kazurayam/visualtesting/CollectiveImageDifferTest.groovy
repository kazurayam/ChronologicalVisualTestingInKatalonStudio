package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.materials.Helpers
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.MaterialStorageFactory
import com.kazurayam.materials.TSuiteName
import com.kazurayam.visualtesting.CollectiveImageDiffer.ChronosOptions
import com.kms.katalon.core.configuration.RunConfiguration

@RunWith(JUnit4.class)
class CollectiveImageDifferTest {

	private Path testOutputDir
	private Path fixtureDir

	@Before
	void setup() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		fixtureDir = projectDir.resolve('Include').resolve('fixture')
		testOutputDir = projectDir.resolve('tmp').resolve('testOutput')
		Helpers.deleteDirectoryContents(testOutputDir)
	}

	@Test
	void testChronos_normal() {
		// setup:
		Path caseOutputDir = testOutputDir.resolve('testChronos')
		Files.createDirectories(caseOutputDir)
		Helpers.copyDirectory(fixtureDir, caseOutputDir)
		Path materialsDir = caseOutputDir.resolve('Materials')
		Path reportsDir   = caseOutputDir.resolve('Reports')
		Path storageDir   = caseOutputDir.resolve('Storage')
		// when:
		Helpers.copyDirectory(storageDir, materialsDir)  //
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir, reportsDir)
		TSuiteName capturingTSuiteName = new TSuiteName('47News_chronos_capture')
		CollectiveImageDiffer cid = new CollectiveImageDiffer(mr)
		//
		MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
		ChronosOptions chronosOptions = new ChronosOptions.Builder().
				filterDataLessThan(5.0).
				shiftCriteriaPercentageBy(10.0).
				build()
		cid.chronos(capturingTSuiteName, ms, chronosOptions)
		//
		Path dir = materialsDir.resolve('_').resolve('_').
				resolve('test.com.kazurayam.visualtesting.CollectiveImageDifferTestRunner').
				resolve('main.TC_47News.visitSite')
		assertThat(Files.exists(dir), is(true))
		List<Path> files = Files.list(dir).collect(Collectors.toList());
		assertThat(files.size(), is(1))
		Path png = files.get(0)
		println png.getFileName()
		assertThat(png.getFileName().toString(), endsWith('FAILED.png'))
	}

	@Ignore
	@Test
	void testTwins() {
	}
}