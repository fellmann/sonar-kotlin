/*
 * SonarSource Kotlin
 * Copyright (C) 2018-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.kotlin.checks

import org.junit.jupiter.api.Test
import org.sonarsource.kotlin.api.checks.AbstractCheck
import org.sonarsource.kotlin.testapi.KotlinVerifier

private const val TEST_FILE_POSTFIX = "Sample.kt"

abstract class CheckTest(
    val check: AbstractCheck,
    val sampleFileSemantics: String? = null,
    val classpath: List<String>? = null,
    val dependencies: List<String>? = null,
    val isAndroid: Boolean = false
) {
    protected val checkName = check::class.java.simpleName

    @Test
    fun `with semantics`() {
        KotlinVerifier(check) {
            this.fileName = sampleFileSemantics ?: "$checkName$TEST_FILE_POSTFIX"
            this@CheckTest.classpath?.let { this.classpath = it }
            this@CheckTest.dependencies?.let { this.deps = it }
            this.isAndroid = this@CheckTest.isAndroid
        }.verify()
    }
}
