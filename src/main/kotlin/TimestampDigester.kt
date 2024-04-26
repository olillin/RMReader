package com.olillin.rmreader

interface TimestampDigester {
    fun processTimestamp(timestamp: Int, line: Int)
}