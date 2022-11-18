/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.gradle

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Taken from https://github.com/bmuschko/gradle-tomcat-plugin/blob/0b0830932140d7270205bcf1f0b7473626b99a6c/embedded/src/main/groovy/com/bmuschko/gradle/tomcat/fixture/AvailablePortFinder.groovy
 */
class AvailablePortFinder {
    private static final int MIN_PRIVATE_PORT = 49152
    private static final int MAX_PRIVATE_PORT = 65535

    private final Lock lock = new ReentrantLock()
    private final int startPort
    private int current
    private static final AvailablePortFinder INSTANCE = new AvailablePortFinder()

    /**
     * Creates a port finder that operates on private ports.
     *
     * @return a port finder that operates on private ports
     */
    static AvailablePortFinder createPrivate() {
        INSTANCE
    }

    private AvailablePortFinder() {
        startPort = new Random().nextInt(MAX_PRIVATE_PORT - MIN_PRIVATE_PORT) + MIN_PRIVATE_PORT
        current = startPort
    }

    /**
     * Gets the next available port.
     *
     * <p>Tries to avoid returning the same port on successive invocations (but it may happen if no other available ports are found).
     *
     * @return the next available port
     * @throws NoSuchElementException if no available port is found
     */
    int getNextAvailable() {
        lock.lock()
        try {
            while (true) {
                if (current >= MAX_PRIVATE_PORT) {
                    current = MIN_PRIVATE_PORT
                } else {
                    current++
                }
                if (current == startPort) {
                    throw new NoSuchElementException("Could not find an available port within port range.")
                }
                int candidate = current
                if (available(candidate)) {
                    return candidate
                }
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     * @return <tt>true</tt> if the port is available, <tt>false</tt> otherwise
     */
    private boolean available(int port) {
        try {
            ServerSocket ss = new ServerSocket(port)
            try {
                ss.setReuseAddress(true)
            } finally {
                ss.close()
            }
            DatagramSocket ds = new DatagramSocket(port)
            try {
                ds.setReuseAddress(true)
            } finally {
                ds.close()
            }
            return true
        } catch (IOException e) {
            return false
        }
    }
}