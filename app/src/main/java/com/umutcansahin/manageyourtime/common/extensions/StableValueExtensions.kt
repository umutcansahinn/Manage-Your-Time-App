package com.umutcansahin.manageyourtime.common.extensions

val String.Companion.EMPTY: String by lazy { "" }
val String.Companion.START_TIME: String by lazy { "0" }
val String.Companion.END_TIME: String by lazy { "99999" }
val Long.Companion.THOUSAND: Long by lazy { 1_000 }
val Long.Companion.HUNDRED: Long by lazy { 100 }
val Long.Companion.TEN: Long by lazy { 10 }
val Long.Companion.ZERO: Long by lazy { 0 }
val String.Companion.YES: String by lazy { "YES" }
val String.Companion.NO: String by lazy { "NO" }