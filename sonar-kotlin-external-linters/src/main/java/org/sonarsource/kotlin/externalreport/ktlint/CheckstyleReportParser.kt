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
package org.sonarsource.kotlin.externalreport.ktlint

import org.sonar.api.batch.sensor.SensorContext
import org.sonar.api.rule.RuleKey
import org.sonarsource.kotlin.externalreport.ktlint.KtlintRulesDefinition.Companion.EXPERIMENTAL_RULE_PREFIX
import org.sonarsource.kotlin.externalreport.common.CheckstyleFormatImporterWithRuleLoader
import org.sonarsource.kotlin.externalreport.common.FALLBACK_RULE_KEY

internal class CheckstyleReportParser(context: SensorContext) : CheckstyleFormatImporterWithRuleLoader(
    context,
    KtlintSensor.LINTER_KEY,
    KtlintRulesDefinition.RULE_LOADER,
) {
    override fun createRuleKey(source: String): RuleKey? {
        val preliminaryRuleKey =
            if (source.startsWith(EXPERIMENTAL_RULE_PREFIX)) source.substring(EXPERIMENTAL_RULE_PREFIX.length)
            else source

        val ruleKey =
            if (KtlintRulesDefinition.RULE_LOADER.ruleKeys().contains(preliminaryRuleKey)) preliminaryRuleKey
            else FALLBACK_RULE_KEY

        return super.createRuleKey(ruleKey)
    }
}
