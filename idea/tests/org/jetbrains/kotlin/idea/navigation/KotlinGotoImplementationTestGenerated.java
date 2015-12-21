/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.navigation;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/navigation/implementations")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class KotlinGotoImplementationTestGenerated extends AbstractKotlinGotoImplementationTest {
    @TestMetadata("AbstractClassImplementorsWithDeclaration.kt")
    public void testAbstractClassImplementorsWithDeclaration() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/AbstractClassImplementorsWithDeclaration.kt");
        doTest(fileName);
    }

    public void testAllFilesPresentInImplementations() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/navigation/implementations"), Pattern.compile("^(.+)\\.kt$"), false);
    }

    @TestMetadata("ClassImplementorsWithDeclaration.kt")
    public void testClassImplementorsWithDeclaration() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/ClassImplementorsWithDeclaration.kt");
        doTest(fileName);
    }

    @TestMetadata("ClassNavigation.kt")
    public void testClassNavigation() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/ClassNavigation.kt");
        doTest(fileName);
    }

    @TestMetadata("ConstructorPropertyOverriddenNavigation.kt")
    public void testConstructorPropertyOverriddenNavigation() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/ConstructorPropertyOverriddenNavigation.kt");
        doTest(fileName);
    }

    @TestMetadata("DefaultImplFunction.kt")
    public void testDefaultImplFunction() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/DefaultImplFunction.kt");
        doTest(fileName);
    }

    @TestMetadata("DefaultImplProperty.kt")
    public void testDefaultImplProperty() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/DefaultImplProperty.kt");
        doTest(fileName);
    }

    @TestMetadata("DelegatedAndDefaultImplFunction.kt")
    public void testDelegatedAndDefaultImplFunction() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/DelegatedAndDefaultImplFunction.kt");
        doTest(fileName);
    }

    @TestMetadata("DelegatedFunction.kt")
    public void testDelegatedFunction() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/DelegatedFunction.kt");
        doTest(fileName);
    }

    @TestMetadata("DelegatedProperty.kt")
    public void testDelegatedProperty() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/DelegatedProperty.kt");
        doTest(fileName);
    }

    @TestMetadata("EnumEntriesInheritance.kt")
    public void testEnumEntriesInheritance() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/EnumEntriesInheritance.kt");
        doTest(fileName);
    }

    @TestMetadata("FunctionOverrideNavigation.kt")
    public void testFunctionOverrideNavigation() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/FunctionOverrideNavigation.kt");
        doTest(fileName);
    }

    @TestMetadata("ImplementGenericWithPrimitives.kt")
    public void testImplementGenericWithPrimitives() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/ImplementGenericWithPrimitives.kt");
        doTest(fileName);
    }

    @TestMetadata("OverridesInEnumEntries.kt")
    public void testOverridesInEnumEntries() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/OverridesInEnumEntries.kt");
        doTest(fileName);
    }

    @TestMetadata("PropertyOverriddenNavigation.kt")
    public void testPropertyOverriddenNavigation() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/PropertyOverriddenNavigation.kt");
        doTest(fileName);
    }

    @TestMetadata("TraitImplementorsWithDeclaration.kt")
    public void testTraitImplementorsWithDeclaration() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/navigation/implementations/TraitImplementorsWithDeclaration.kt");
        doTest(fileName);
    }
}
