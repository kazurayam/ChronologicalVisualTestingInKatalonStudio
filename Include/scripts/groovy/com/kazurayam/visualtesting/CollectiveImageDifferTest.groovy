package com.kazurayam.visualtesting

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import internal.GlobalVariable

import com.kms.katalon.core.configuration.RunConfiguration
import com.kazurayam.materials.Helpers

@RunWith(JUnit4.class)
class CollectiveImageDifferTest {
	
	private Path testOutputDir
	private Path fixtureDir
	
	@Before
	void setup() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		fixtureDir = projectDir.resolve('Include').resolve('fixture')
		testOutputDir = projectDir.resolve('tmp').resolve('testOutput')
		Files.createDirectories(testOutputDir)
		Helpers.copyDirectory(fixtureDir, testOutputDir)
	}
	
	@Ignore
	@Test
	void testTwins() {
	}
	
	@Test
	void testChronos() {
		
	}
}