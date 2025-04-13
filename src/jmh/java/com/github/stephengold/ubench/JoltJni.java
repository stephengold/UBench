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

import com.github.stephengold.joltjni.Body;
import com.github.stephengold.joltjni.BodyCreationSettings;
import com.github.stephengold.joltjni.BodyInterface;
import com.github.stephengold.joltjni.BoxShape;
import com.github.stephengold.joltjni.Jolt;
import com.github.stephengold.joltjni.JoltPhysicsObject;
import com.github.stephengold.joltjni.MapObj2Bp;
import com.github.stephengold.joltjni.ObjVsBpFilter;
import com.github.stephengold.joltjni.ObjVsObjFilter;
import com.github.stephengold.joltjni.PhysicsSystem;
import com.github.stephengold.joltjni.enumerate.EActivation;
import com.github.stephengold.joltjni.enumerate.EOverrideMassProperties;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import electrostatic4j.snaploader.LibraryInfo;
import electrostatic4j.snaploader.LoadingCriterion;
import electrostatic4j.snaploader.NativeBinaryLoader;
import electrostatic4j.snaploader.filesystem.DirectoryPath;
import electrostatic4j.snaploader.platform.NativeDynamicLibrary;
import electrostatic4j.snaploader.platform.util.PlatformPredicate;
import java.io.PrintStream;
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

    static Body box;

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
        LibraryInfo info = new LibraryInfo(null, "joltjni", DirectoryPath.USER_DIR);
        NativeBinaryLoader loader = new NativeBinaryLoader(info);
        loader.registerNativeLibraries(libraries).initPlatformLibrary();
        loader.setLoggingEnabled(true);
        loader.setRetryWithCleanExtraction(true);

        try {
            loader.loadLibrary(LoadingCriterion.CLEAN_EXTRACTION);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to load the jolt-jni native library!");
        }

        JoltPhysicsObject.startCleaner(); // to free Jolt objects automatically
        Jolt.registerDefaultAllocator();
        Jolt.installDefaultAssertCallback();
        Jolt.installDefaultTraceCallback();
        boolean success = Jolt.newFactory();
        assert success;
        Jolt.registerTypes();

        printLibraryInfo(System.out);
        if (areAssertionsEnabled()) {
            System.out.println("Warning: assertions are enabled.");
        }

        ConstShape boxShape = new BoxShape(1f);
        BodyCreationSettings boxBcs = new BodyCreationSettings()
                .setOverrideMassProperties(EOverrideMassProperties.CalculateInertia)
                .setShape(boxShape);
        boxBcs.getMassPropertiesOverride().setMass(1f);

        // Create and add a box:
        PhysicsSystem physicsSystem = createSystem();
        BodyInterface bi = physicsSystem.getBodyInterface();
        boxBcs.setPosition(1f, 2f, 3f);
        box = bi.createBody(boxBcs);
        bi.addBody(box, EActivation.Activate);
    }

    /**
     * Test {@code Quat} creation and access.
     *
     * @param hole the consumer to use (not null)
     * @param data the test data to use (not null)
     */
    @Benchmark
    public void quat(Blackhole hole, TestData data) {
        QuatArg q = box.getRotation();

        float ww = q.getW();
        hole.consume(ww);
        float xx = q.getX();
        hole.consume(xx);
        float yy = q.getY();
        hole.consume(yy);
        float zz = q.getZ();
        hole.consume(zz);
    }

    /**
     * Test whether assertions are enabled.
     *
     * @return true if enabled, otherwise false
     */
    private static boolean areAssertionsEnabled() {
        boolean enabled = false;
        assert enabled = true; // Note: intentional side effect.

        return enabled;
    }

    /**
     * Create the PhysicsSystem. Invoked once during initialization.
     *
     * @return a new object
     */
    private static PhysicsSystem createSystem() {
        int numBpLayers = 1;
        int numObjLayers = 1;
        MapObj2Bp mapObj2Bp = new MapObj2Bp(numObjLayers, numBpLayers).add(0, 0);
        ObjVsBpFilter objVsBpFilter = new ObjVsBpFilter(numObjLayers, numBpLayers);
        ObjVsObjFilter objVsObjFilter = new ObjVsObjFilter(numObjLayers);

        int maxBodies = 1_800;
        int numBodyMutexes = 0; // 0 means "use the default value"
        int maxBodyPairs = 65_536;
        int maxContacts = 20_480;
        PhysicsSystem result = new PhysicsSystem();
        result.init(maxBodies, numBodyMutexes, maxBodyPairs, maxContacts,
                mapObj2Bp, objVsBpFilter, objVsObjFilter);

        return result;
    }

    /**
     * Print basic library information to the specified stream during
     * initialization.
     *
     * @param printStream where to print the information (not null)
     */
    private static void printLibraryInfo(PrintStream printStream) {
        printStream.print("Jolt JNI version ");
        printStream.print(Jolt.versionString());
        printStream.print('-');

        String buildType = Jolt.buildType();
        printStream.print(buildType);

        if (Jolt.isDoublePrecision()) {
            printStream.print("Dp");
        } else {
            printStream.print("Sp");
        }

        printStream.println(" initializing...");
        printStream.println();
        printStream.flush();
    }
}
