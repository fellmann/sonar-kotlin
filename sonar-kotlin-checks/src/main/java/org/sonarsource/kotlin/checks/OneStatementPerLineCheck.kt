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

import org.jetbrains.kotlin.psi.KtBlockExpression
import org.sonar.check.Rule
import org.sonarsource.kotlin.api.checks.AbstractCheck
import org.sonarsource.kotlin.api.reporting.SecondaryLocation
import org.sonarsource.kotlin.api.reporting.KotlinTextRanges.textRange
import org.sonarsource.kotlin.api.frontend.KotlinFileContext

@Rule(key = "S122")
class OneStatementPerLineCheck : AbstractCheck() {

    override fun visitBlockExpression(expression: KtBlockExpression, kotlinFileContext: KotlinFileContext) {
        val document = expression.containingKtFile.viewProvider.document!!
        expression.statements
            .groupBy { statement -> document.getLineNumber(statement.textRange.startOffset) }
            .forEach { (_, statements) ->
                if (statements.size > 1) {
                    kotlinFileContext.reportIssue(
                        statements[1],
                        "Reformat the code to have only one statement per line.",
                        secondaryLocations = statements
                            .asSequence()
                            .drop(2)
                            .map { SecondaryLocation(kotlinFileContext.textRange(it), null) }
                            .toList(),
                    )
                }
            }
    }

}
