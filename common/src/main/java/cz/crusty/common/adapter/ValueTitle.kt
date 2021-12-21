package cz.crusty.common.adapter

import cz.crusty.common.util.ColorPalette

data class ValueTitle(
    val value: String?,
    val title: String,
    val colorPair: ColorPalette.ColorPair? = null
)
