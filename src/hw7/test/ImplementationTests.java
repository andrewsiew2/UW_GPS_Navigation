package hw7.test;

import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.*;

import hw8.test.CoordinateModelTest;
import hw8.test.CoordinateParserTest;
import hw8.test.CoordinateTest;

/**
 * ImplementationTests is a test suite used to encapsulate all
 * tests specific to your implementation of this problem set.
 *
 * For instance, unit tests for your individual methods would
 * go here.
 */

@RunWith(Suite.class)
@SuiteClasses({ CheckAsserts.class, MarvelPaths2Test.class, CoordinateTest.class,
				CoordinateParserTest.class, CoordinateModelTest.class /* list classes here */ })

public final class ImplementationTests
{
    // This class is a placeholder for the suite, so it has no members.
}

