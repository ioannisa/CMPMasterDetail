package eu.anifantakis.cmpmasterdetail.core.data

import co.touchlab.kermit.Logger

object Log {
    fun withTag(tag: String): Logger {
        return Logger.withTag(tag)
    }

    fun d(tag: String, message: String) {
        Logger.d(tag) { message }
    }

    fun i(tag: String, message: String) {
        Logger.i(tag) { message }
    }

    fun w(tag: String, message: String) {
        Logger.w(tag) { message }
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Logger.e(throwable, tag) { message }
    }
}