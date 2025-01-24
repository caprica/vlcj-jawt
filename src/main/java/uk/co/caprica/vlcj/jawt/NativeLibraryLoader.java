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

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

final class NativeLibraryLoader {

    private static final String LIBRARY_NAME = "libvlcj-jawt";

    private static final String PREFIX = "vlcj-jawt-";

    private static final String SUFFIX = ".lib";

    static void loadNativeLibrary() {
        loadNativeLibrary(null);
    }

    static void loadNativeLibrary(Path libraryDirectory) {
        try {
            String libraryPath = String.format("/lib/%s.%s", LIBRARY_NAME, getLibraryExtension());
            InputStream in = NativeLibraryLoader.class.getResourceAsStream(libraryPath);
            if (in == null) {
                throw new RuntimeException("Failed to find native library artefact");
            }
            Path tempFile = libraryDirectory == null ?
                Files.createTempFile(PREFIX, SUFFIX) :
                Files.createTempFile(libraryDirectory, PREFIX, SUFFIX);
            tempFile.toFile().deleteOnExit();
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            System.load(tempFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getLibraryExtension() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("nix") || os.contains("nux") || os.contains("freebsd")) {
            return "so";
        }
        if (os.contains("win")) {
            return "dll";
        }
        throw new RuntimeException("Unsupported OS");
    }
}
