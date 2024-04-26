package com.olillin.rmreader

import java.io.*

fun main(args: Array<String>) {
    val filepath = args[0]
    val stream = DataInputStream(FileInputStream(filepath))


    if (filepath.endsWith(".tmcpr")) {
        val digester = TimestampDigesterImpl("timestamps", "summary")
        readRecording(stream, digester)
    } else {
        val digester = TimestampDigesterImpl(null, "summary")
        readTimestamps(stream, digester)
    }
}

const val BYTES_IN_MEGABYTE: Int = 1048576

fun readRecording(stream: DataInputStream, digester: TimestampDigester) {
    // Track progress
    var prevRemaining: Int = stream.available() / 1024
    var readBytes: Int = 0
    var line: Int = 0
    try {
        while (true) {
            val timestamp: Int = stream.readInt()
            val packetLength: Int = stream.readInt()
            stream.skipBytes(packetLength)

            readBytes += 4 * 2 + packetLength
            val remaining: Int = stream.available() / BYTES_IN_MEGABYTE
            if (remaining < prevRemaining) {
                print("\u001B[2K\rRemaining: $remaining MB")

                prevRemaining = remaining
            }

            digester.processTimestamp(timestamp, line)

            line++
        }
    } catch (e: EOFException) {
        println("\nReached end of file")
    }
}

fun readTimestamps(stream: DataInputStream, digester: TimestampDigester) {
    // Track progress
    var prevRemaining: Int = stream.available() / 1024
    var readBytes: Int = 0
    var line: Int = 0
    try {
        while (true) {
            val timestamp: Int = stream.readInt()
            readBytes += 4

            val remaining: Int = stream.available() / BYTES_IN_MEGABYTE
            if (remaining < prevRemaining) {
                print("\u001B[2K\rRemaining: $remaining MB")

                prevRemaining = remaining
            }

            digester.processTimestamp(timestamp, line)

            line++
        }
    } catch (e: EOFException) {
        println("\nReached end of file")
    }
}