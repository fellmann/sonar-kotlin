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
package org.sonarsource.kotlin.api.common

import org.sonar.api.config.Configuration
import org.sonar.api.resources.AbstractLanguage

class KotlinLanguage(
    private val configuration: Configuration,
) : AbstractLanguage(KOTLIN_LANGUAGE_KEY, KOTLIN_LANGUAGE_NAME) {
    override fun getFileSuffixes(): Array<String> =
        (configuration.getStringArray(KOTLIN_FILE_SUFFIXES_KEY)).let {
            if (it.isNullOrEmpty()) KOTLIN_FILE_SUFFIXES_DEFAULT_VALUE.split(",").toTypedArray()
            else it
        }
}
