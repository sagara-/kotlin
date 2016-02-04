/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.jps.build

import com.intellij.openapi.util.Condition
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.containers.MultiMap
import org.jetbrains.jps.ModuleChunk
import org.jetbrains.jps.builders.java.JavaModuleBuildTargetType
import org.jetbrains.jps.incremental.CompileContext
import org.jetbrains.jps.incremental.ModuleBuildTarget
import org.jetbrains.jps.incremental.ProjectBuildException
import org.jetbrains.jps.model.java.JpsJavaExtensionService
import org.jetbrains.kotlin.build.JvmSourceRoot
import org.jetbrains.kotlin.config.IncrementalCompilation
import org.jetbrains.kotlin.jps.build.JpsUtils.getAllDependencies
import org.jetbrains.kotlin.modules.KotlinModuleXmlBuilder
import java.io.File
import java.io.IOException
import java.util.*

object KotlinBuilderModuleScriptGenerator {

    @Throws(IOException::class, ProjectBuildException::class)
    fun generateModuleDescription(
            context: CompileContext,
            chunk: ModuleChunk,
            sourceFiles: MultiMap<ModuleBuildTarget, File>, // ignored for non-incremental compilation
            hasRemovedFiles: Boolean): File? {
        val builder = KotlinModuleXmlBuilder()

        var noSources = true

        val outputDirs = HashSet<File>()
        for (target in chunk.targets) {
            outputDirs.add(getOutputDirSafe(target))
        }
        val logger = context.loggingManager.projectBuilderLogger
        for (target in chunk.targets) {
            val outputDir = getOutputDirSafe(target)
            val friendDirs = ArrayList<File>()
            val friendDir = getFriendDirSafe(target)
            if (friendDir != null) {
                friendDirs.add(friendDir)
            }

            val moduleSources = ArrayList(
                    if (IncrementalCompilation.isEnabled())
                        sourceFiles.get(target)
                    else
                        KotlinSourceFileCollector.getAllKotlinSourceFiles(target))

            if (moduleSources.size > 0 || hasRemovedFiles) {
                noSources = false

                if (logger.isEnabled) {
                    logger.logCompiledFiles(moduleSources, KotlinBuilder.KOTLIN_BUILDER_NAME, "Compiling files:")
                }
            }

            val targetType = target.targetType
            assert(targetType is JavaModuleBuildTargetType)
            builder.addModule(
                    target.id,
                    outputDir.absolutePath,
                    moduleSources,
                    findSourceRoots(context, target),
                    findClassPathRoots(target),
                    (targetType as JavaModuleBuildTargetType).typeId,
                    targetType.isTests,
                    // this excludes the output directories from the class path, to be removed for true incremental compilation
                    outputDirs,
                    friendDirs)
        }

        if (noSources) return null

        val scriptFile = File.createTempFile("kjps", StringUtil.sanitizeJavaIdentifier(chunk.name) + ".script.xml")

        FileUtil.writeToFile(scriptFile, builder.asText().toString())

        return scriptFile
    }

    @Throws(ProjectBuildException::class)
    fun getOutputDirSafe(target: ModuleBuildTarget): File {
        val outputDir = target.outputDir ?: throw ProjectBuildException("No output directory found for " + target)
        return outputDir
    }

    @Throws(ProjectBuildException::class)
    private fun getFriendDirSafe(target: ModuleBuildTarget): File? {
        if (!target.isTests) return null

        val outputDirForProduction = JpsJavaExtensionService.getInstance().getOutputDirectory(target.module, false) ?: return null
        return outputDirForProduction
    }

    private fun findClassPathRoots(target: ModuleBuildTarget): Collection<File> {
        return ContainerUtil.filter(getAllDependencies(target).classes().roots, Condition<java.io.File> { file ->
            if (!file.exists()) {
                val extension = file.extension

                // Don't filter out files, we want to report warnings about absence through the common place
                if (!(extension == "class" || extension == "jar")) {
                    return@Condition false
                }
            }

            true
        })
    }

    private fun findSourceRoots(context: CompileContext, target: ModuleBuildTarget): List<JvmSourceRoot> {
        val roots = context.projectDescriptor.buildRootIndex.getTargetRoots(target, context)
        val result = ContainerUtil.newArrayList<JvmSourceRoot>()
        for (root in roots) {
            val file = root.rootFile
            val prefix = root.packagePrefix
            if (file.exists()) {
                result.add(JvmSourceRoot(file, if (prefix.isEmpty()) null else prefix))
            }
        }
        return result
    }
}
