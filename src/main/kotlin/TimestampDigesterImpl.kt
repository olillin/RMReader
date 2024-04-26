package com.olillin.rmreader

import java.io.DataOutputStream
import java.io.FileOutputStream

class TimestampDigesterImpl(
    val timestampsFilepath: String? = "timestamps",
    val summaryFilepath: String? = "summary.txt",
) : TimestampDigester {

    private val timestampsStream: DataOutputStream? =
        if (timestampsFilepath != null) DataOutputStream(FileOutputStream(timestampsFilepath))
        else null

    private val sTimestampsStream: DataOutputStream? =
        if (timestampsFilepath != null) DataOutputStream(FileOutputStream("$timestampsFilepath.txt"))
        else null

    private val summaryStream: DataOutputStream? =
        if (summaryFilepath != null) DataOutputStream(FileOutputStream(summaryFilepath))
        else null

    private var prevTimestamp: Int = 0

    override fun processTimestamp(timestamp: Int, line: Int) {
        // Log
        timestampsStream?.writeInt(timestamp)
        sTimestampsStream?.writeBytes("$timestamp\n")

        if (summaryStream != null) {
            // Gap detection
            val gap = timestamp - prevTimestamp
            if (gap < 0) {
                summaryStream.writeBytes("Negative gap ($gap) at line $line\n")
            }
            if (gap > 1000) {
                summaryStream.writeBytes("Large gap ($gap) at line $line\n")
            }
        }
        prevTimestamp = timestamp
    }
}