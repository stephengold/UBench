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

import org.apache.commons.math3.util.FastMath;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Measure the performance of methods in the Apache commons-math library using
 * the Java Microbenchmark Harness (JMH). The accuracy of the returned values is
 * not evaluated.
 *
 * @author Stephen Gold sgold@sonic.net
 */
@State(Scope.Benchmark)
public class Apache {

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.acos()} for
     * single-precision arc cosines.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void acos(Blackhole hole, TestData data) {
        float ww = (float) FastMath.acos(data.w);
        hole.consume(ww);
        float xx = (float) FastMath.acos(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.acos(data.y);
        hole.consume(yy);
    }

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.atan()} for
     * single-precision arc tangents.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void atan(Blackhole hole, TestData data) {
        float ww = (float) FastMath.atan(data.w);
        hole.consume(ww);
        float xx = (float) FastMath.atan(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.atan(data.y);
        hole.consume(yy);
        float zz = (float) FastMath.atan(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.cos()} for
     * single-precision cosine ratios.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void cos(Blackhole hole, TestData data) {
        float ww = (float) FastMath.cos(data.w);
        hole.consume(ww);
        float xx = (float) FastMath.cos(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.cos(data.y);
        hole.consume(yy);
        float zz = (float) FastMath.cos(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.exp()} for
     * single-precision exponentials.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void exp(Blackhole hole, TestData data) {
        float ww = (float) FastMath.exp(data.w);
        hole.consume(ww);
        float xx = (float) FastMath.exp(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.exp(data.y);
        hole.consume(yy);
        float zz = (float) FastMath.exp(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.sin()} for
     * single-precision sine ratios.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void sin(Blackhole hole, TestData data) {
        float ww = (float) FastMath.sin(data.w);
        hole.consume(ww);
        float xx = (float) FastMath.sin(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.sin(data.y);
        hole.consume(yy);
        float zz = (float) FastMath.sin(data.z);
        hole.consume(zz);
    }

    /**
     * Test {@code org.apache.commons.math3.util.FastMath.sqrt()} for
     * single-precision square roots.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    //@Benchmark
    public void sqrt(Blackhole hole, TestData data) {
        float xx = (float) FastMath.sqrt(data.x);
        hole.consume(xx);
        float yy = (float) FastMath.sqrt(data.y);
        hole.consume(yy);
        float zz = (float) FastMath.sqrt(data.z);
        hole.consume(zz);
    }
}
