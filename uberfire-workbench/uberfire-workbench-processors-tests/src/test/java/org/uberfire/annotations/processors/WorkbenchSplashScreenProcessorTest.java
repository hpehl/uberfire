/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uberfire.annotations.processors;

import java.io.FileNotFoundException;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Splash Screen related class generation
 */
public class WorkbenchSplashScreenProcessorTest extends AbstractProcessorTest {

    @Test
    public void testNoWorkbenchScreenAnnotation() throws FileNotFoundException {
        final Result result = new Result();
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( final String code ) {
                result.setActualCode( code );
            }
        } ),
                                                                                "org/uberfire/annotations/processors/WorkbenchSplashScreenTest1" );
        assertSuccessfulCompilation( diagnostics );
        assertNull( result.getActualCode() );
    }

    @Test
    public void testWorkbenchScreenMissingViewAnnotation() {
        final Result result = new Result();
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( final String code ) {
                result.setActualCode( code );
            }
        } ), "org/uberfire/annotations/processors/WorkbenchSplashScreenTest2" );

        assertFailedCompilation( diagnostics );
        assertCompilationError( diagnostics,
                                "org.uberfire.annotations.processors.WorkbenchSplashScreenTest2Activity: The WorkbenchSplashScreen must either extend IsWidget or provide a @WorkbenchPartView annotated method to return a com.google.gwt.user.client.ui.IsWidget." );
        assertNull( result.getActualCode() );
    }

    @Test
    public void testWorkbenchScreenHasViewAnnotationMissingTitleAnnotation() {
        final Result result = new Result();
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( final String code ) {
                result.setActualCode( code );
            }
        } ),
                                                                                "org/uberfire/annotations/processors/WorkbenchSplashScreenTest3" );
        assertFailedCompilation( diagnostics );
        assertCompilationError( diagnostics,
                                "org.uberfire.annotations.processors.WorkbenchSplashScreenTest3Activity: The WorkbenchSplashScreen must provide a @WorkbenchPartTitle annotated method to return a java.lang.String." );
        assertNull( result.getActualCode() );
    }

    @Test
    public void testWorkbenchScreenMissingViewAnnotationHasTitleAnnotation() {
        final Result result = new Result();
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( final String code ) {
                result.setActualCode( code );
            }
        } ),
                                                                                "org/uberfire/annotations/processors/WorkbenchSplashScreenTest4" );
        assertFailedCompilation( diagnostics );
        assertCompilationError( diagnostics,
                                "org.uberfire.annotations.processors.WorkbenchSplashScreenTest4Activity: The WorkbenchSplashScreen must either extend IsWidget or provide a @WorkbenchPartView annotated method to return a com.google.gwt.user.client.ui.IsWidget." );
        assertNull( result.getActualCode() );
    }

    @Test
    public void testIncorrectReturnTypeWithoutArguments() throws FileNotFoundException {
        final Result result = new Result();
        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( String code ) {
                result.setActualCode( code );
            }
        } ),
                                                                                "org/uberfire/annotations/processors/WorkbenchSplashScreenTest5" );
        assertCompilationError( diagnostics,
                                "org.uberfire.annotations.processors.WorkbenchSplashScreenTest5Activity: The WorkbenchSplashScreen must provide a @SplashFilter annotated method to return a org.uberfire.workbench.model.SplashScreenFilter." );
        assertNull( result.getActualCode() );
    }

    @Test
    public void testWorkbenchScreenExtendsIsWidget() throws FileNotFoundException {
        final String pathCompilationUnit = "org/uberfire/annotations/processors/WorkbenchSplashScreenTest6";
        final String pathExpectedResult = "org/uberfire/annotations/processors/expected/WorkbenchSplashScreenTest6.expected";

        final Result result = new Result();
        result.setExpectedCode( getExpectedSourceCode( pathExpectedResult ) );

        final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile( new WorkbenchSplashScreenProcessor( new GenerationCompleteCallback() {

            @Override
            public void generationComplete( String code ) {
                result.setActualCode( code );
            }
        } ),
                                                                                pathCompilationUnit );
        assertSuccessfulCompilation( diagnostics );
        assertNotNull( result.getActualCode() );
        assertNotNull( result.getExpectedCode() );
        assertEquals( result.getActualCode(),
                      result.getExpectedCode() );
    }
}
