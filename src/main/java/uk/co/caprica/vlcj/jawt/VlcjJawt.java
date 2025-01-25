/*
 * This file is part of VLCJ-JAWT.
 *
 * VLCJ-JAWT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ-JAWT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ-JAWT.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2025 Caprica Software Limited.
 */

package uk.co.caprica.vlcj.jawt;

import java.awt.Component;
import java.nio.file.Path;

/**
 * Native AWT bridge for vlcj.
 */
public class VlcjJawt {

    /**
     * Create an AWT bridge component that extracts the vlcj native library
     * from a jar artefact on the classpath to the system temporary directory,
     * and then loads that library.
     * <p>
     * The library file will be deleted when the JVM exits.
     */
    public VlcjJawt() {
        NativeLibraryLoader.loadNativeLibrary();
    }

    /**
     * Create an AWT bridge component that extracts the vlcj native library
     * from a jar artefact on the classpath to a specified directory, and then
     * loads that library.
     * <p>
     * The library file will be deleted when the JVM exits.
     *
     * @param libraryDirectory directory in which to extract the native library
     */
    public VlcjJawt(Path libraryDirectory) {
        NativeLibraryLoader.loadNativeLibrary(libraryDirectory);
    }

    /**
     * Get the native window handle for am AWT (heavyweight) component.
     * <p>
     * The component must be in a displayable state, or visible, before using
     * this method - a component can be in a displayable state without being
     * visible.
     * <p>
     * This can only be used on Linux and Windows, it must not be used on macOS.
     */
    public native long getNativeWindowHandle(Component c);
}
