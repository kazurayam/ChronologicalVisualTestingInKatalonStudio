package com.kazurayam.visualtesting

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.materials.Helpers
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.MaterialStorage
import com.kazurayam.materials.MaterialStorageFactory
import com.kazurayam.materials.TCaseName
import com.kazurayam.materials.TSuiteName
import com.kazurayam.visualtesting.ManagedGlobalVariable as MGV
import com.kazurayam.visualtesting.ImageCollectionDifferDriver.ChronosOptions
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

@RunWith(JUnit4.class)
class ImageCollectionDifferDriverTest {

	private static Path fixtureDir
	private static Path testOutputDir
	private static Path specOutputDir

	@BeforeClass
	static void onlyOnce() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		fixtureDir = projectDir.resolve('Include').resolve('fixture')
		testOutputDir = projectDir.resolve('tmp').resolve('testOutput')
		Helpers.deleteDirectoryContents(testOutputDir)
		specOutputDir = testOutputDir.resolve(ImageCollectionDifferDriverTest.class.getSimpleName())
		Files.createDirectories(specOutputDir)
	}

	@Test
	void testChronos_normal() {
		// setup:
		Path caseOutputDir = specOutputDir.resolve('testChronos_normal')
		Files.createDirectories(caseOutputDir)
		Helpers.copyDirectory(fixtureDir, caseOutputDir)
		Path materialsDir = caseOutputDir.resolve('Materials')
		Path reportsDir   = caseOutputDir.resolve('Reports')
		Path storageDir   = caseOutputDir.resolve('Storage')
		// when:
		Helpers.copyDirectory(storageDir, materialsDir)  //
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir, reportsDir)
		TSuiteName capturingTSuiteName = new TSuiteName('47News_chronos_capture')

		ImageCollectionDifferDriver icdd = new ImageCollectionDifferDriver(mr)
		//
		MaterialStorage ms = MaterialStorageFactory.createInstance(storageDir)
		ChronosOptions chronosOptions = new ChronosOptions.Builder().
				filterDataLessThan(5.0).
				shiftCriteriaPercentageBy(10.0).
				build()
		icdd.chronos(capturingTSuiteName, ms, chronosOptions)
		//
		Path dir = materialsDir.resolve('_').resolve('_').
				resolve(new TCaseName(GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()]).getValue()).
				resolve('main.TC_47News.visitSite')
		assertThat(Files.exists(dir), is(true))
		List<Path> files = Files.list(dir).collect(Collectors.toList());
		assertThat(files.size(), is(1))
		Path png = files.get(0)
		assertThat(png.getFileName().toString(), endsWith('FAILED.png'))
		WebUI.comment(">>> ${png.getFileName()}")
	}

	@Test
	void testTwins() {
		// setup:
		Path caseOutputDir = specOutputDir.resolve('testTwins')
		Files.createDirectories(caseOutputDir)
		assert Files.exists(caseOutputDir)
		Helpers.copyDirectory(fixtureDir, caseOutputDir)
		Path materialsDir = caseOutputDir.resolve('Materials')
		Path reportsDir   = caseOutputDir.resolve('Reports')
		Path storageDir   = caseOutputDir.resolve('Storage')
		// when:
		Helpers.copyDirectory(storageDir, materialsDir)  //
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsDir, reportsDir)
		TSuiteName capturingTSuiteName = new TSuiteName('47News_chronos_capture')

		ImageCollectionDifferDriver icdd = new ImageCollectionDifferDriver(mr)
		//
		icdd.twins(capturingTSuiteName, 10.0)
		//
		Path dir = materialsDir.resolve('_').resolve('_').
				resolve(new TCaseName(GlobalVariable[MGV.CURRENT_TESTCASE_ID.getName()]).getValue()).
				resolve('main.TC_47News.visitSite')
		assertThat("${dir} is not present", Files.exists(dir), is(true))
		List<Path> files = Files.list(dir).collect(Collectors.toList());
		assertThat(files.size(), is(1))
		Path png = files.get(0)
		assertThat(png.getFileName().toString(), endsWith('FAILED.png'))
		WebUI.comment(">>> ${png.getFileName()}")
	}
}