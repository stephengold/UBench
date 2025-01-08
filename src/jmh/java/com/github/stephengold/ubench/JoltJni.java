/*
Copyright (c) 2025 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.github.stephengold.ubench;

import com.github.stephengold.joltjni.std.Std;
import electrostatic4j.snaploader.LibraryInfo;
import electrostatic4j.snaploader.LoadingCriterion;
import electrostatic4j.snaploader.NativeBinaryLoader;
import electrostatic4j.snaploader.filesystem.DirectoryPath;
import electrostatic4j.snaploader.platform.NativeDynamicLibrary;
import electrostatic4j.snaploader.platform.util.PlatformPredicate;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Measure the performance of methods in the jolt-jni library using the Java
 * Microbenchmark Harness (JMH). The accuracy of the returned values is not
 * evaluated.
 *
 * @author Stephen Gold sgold@sonic.net
 */
@State(Scope.Benchmark)
public class JoltJni {

    /**
     * Load the jolt-jni native library using SnapLoader.
     */
    static {
        NativeDynamicLibrary[] libraries = new NativeDynamicLibrary[]{
            new NativeDynamicLibrary("linux/aarch64/com/github/stephengold", PlatformPredicate.LINUX_ARM_64),
            new NativeDynamicLibrary("linux/armhf/com/github/stephengold", PlatformPredicate.LINUX_ARM_32),
            new NativeDynamicLibrary("linux/x86-64/com/github/stephengold", PlatformPredicate.LINUX_X86_64),
            new NativeDynamicLibrary("osx/aarch64/com/github/stephengold", PlatformPredicate.MACOS_ARM_64),
            new NativeDynamicLibrary("osx/x86-64/com/github/stephengold", PlatformPredicate.MACOS_X86_64),
            new NativeDynamicLibrary("windows/x86-64/com/github/stephengold", PlatformPredicate.WIN_X86_64)
        };
        LibraryInfo info = new LibraryInfo(
                new DirectoryPath("linux/x86-64/com/github/stephengold"),
                "joltjni", DirectoryPath.USER_DIR);
        NativeBinaryLoader loader = new NativeBinaryLoader(info);
        loader.registerNativeLibraries(libraries).initPlatformLibrary();
        loader.setLoggingEnabled(true);
        loader.setRetryWithCleanExtraction(true);

        try {
            loader.loadLibrary(LoadingCriterion.INCREMENTAL_LOADING);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load the jolt-jni native library!");
        }
    }

    /**
     * Test {@code Std.acos()} for single-precision arc cosines.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void acos(Blackhole hole, TestData data) {
        float ww = Std.acos(data.w);
        hole.consume(ww);
        float xx = Std.acos(data.x);
        hole.consume(xx);
        float yy = Std.acos(data.y);
        hole.consume(yy);
    }

    /**
     * Test {@code Std.atan()} for single-precision arc tangents.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void atan(Blackhole hole, TestData data) {
        float ww = Std.atan(data.w);
        hole.consume(ww);
        float xx = Std.atan(data.x);
        hole.consume(xx);
        float yy = Std.atan(data.y);
        hole.consume(yy);
        float zz = Std.atan(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code Std.cos()} for single-precision cosine ratios.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void cos(Blackhole hole, TestData data) {
        float ww = Std.cos(data.w);
        hole.consume(ww);
        float xx = Std.cos(data.x);
        hole.consume(xx);
        float yy = Std.cos(data.y);
        hole.consume(yy);
        float zz = Std.cos(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code Std.exp()} for single-precision exponentials.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void exp(Blackhole hole, TestData data) {
        float ww = Std.exp(data.w);
        hole.consume(ww);
        float xx = Std.exp(data.x);
        hole.consume(xx);
        float yy = Std.exp(data.y);
        hole.consume(yy);
        float zz = Std.exp(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code Std.pow()} for single-precision powers.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void pow(Blackhole hole, TestData data) {
        float xw = Std.pow(data.x, data.w);
        hole.consume(xw);
        float yx = Std.pow(data.y, data.x);
        hole.consume(yx);
        float zy = Std.pow(data.z, data.y);
        hole.consume(zy);
    }

    /**
     * Test {@code Std.sin()} for single-precision sine ratios.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void sin(Blackhole hole, TestData data) {
        float ww = Std.sin(data.w);
        hole.consume(ww);
        float xx = Std.sin(data.x);
        hole.consume(xx);
        float yy = Std.sin(data.y);
        hole.consume(yy);
        float zz = Std.sin(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code Std.sqrt()} for single-precision square roots.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void sqrt(Blackhole hole, TestData data) {
        float xx = Std.sqrt(data.x);
        hole.consume(xx);
        float yy = Std.sqrt(data.y);
        hole.consume(yy);
        float zz = Std.sqrt(data.z);
        hole.consume(zz);
    }
}
