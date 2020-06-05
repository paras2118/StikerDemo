package com.duckinfotech.stikerdemo.utility

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

object ZipUtility {

    private fun isDirectoryExists(destinationPath: String): Boolean {
        val fileDirectory = File(destinationPath)
        return fileDirectory.exists()
    }

    fun unZip(sourcePath: File, destinationPath: File) {
        if (sourcePath == null && destinationPath == null) {
            return
        }

        FileInputStream(sourcePath).use { fis ->
            Log.d("ZipUtility", sourcePath.toString())
            ZipInputStream(fis).use { zis ->

                while (true) {
                    val zEntery = zis.nextEntry ?: break
                    Log.d("ZipUtility", zEntery.name)

                    FileOutputStream("${destinationPath}/${zEntery.name}").use { fos ->
                        // fos.write(zis.read())
                        while (true) {
                            val hh = zis.read()
                            if (hh == -1) {
                                break
                            }
                            fos.write(hh)
                        }
                    }
                }
            }
        }


    }


}