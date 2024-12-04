package com.goesbruno.mobflix.model

import androidx.compose.ui.graphics.Color
import com.goesbruno.mobflix.ui.theme.Blue
import com.goesbruno.mobflix.ui.theme.Green
import com.goesbruno.mobflix.ui.theme.Orange
import com.goesbruno.mobflix.ui.theme.Pink
import com.goesbruno.mobflix.ui.theme.Red
import com.goesbruno.mobflix.ui.theme.Yellow
import java.util.Locale

enum class Category(val color: Color) {
    FRONT_END(Blue) {
        override fun toString() = "Front End"
    },
    PROGRAMACAO(Green) {
        override fun toString() = "Programação"
    },
    MOBILE(Red) {
        override fun toString() = "Mobile"
    },
    DATA_SCIENCE(Yellow) {
        override fun toString() = "Data Science"
    },
    DEVOPS(Orange) {
        override fun toString() = "DevOps"
    },
    UX_DESIGN(Pink) {
        override fun toString() = "UX e Design"
    };

    override fun toString(): String {
        return super.toString().replace('_', ' ').lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}