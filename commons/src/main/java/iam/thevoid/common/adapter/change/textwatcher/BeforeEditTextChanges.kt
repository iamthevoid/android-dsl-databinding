package iam.thevoid.common.adapter.change.textwatcher

data class BeforeEditTextChanges(val s: CharSequence?, val start: Int, val count: Int, val after: Int)