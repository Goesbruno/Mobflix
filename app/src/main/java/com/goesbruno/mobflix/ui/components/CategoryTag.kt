package com.goesbruno.mobflix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goesbruno.mobflix.model.Category

@Composable
fun CategoryTag(
    category: Category?,
    modifier: Modifier = Modifier
) {
    if(category != null) {
        val filter = category.toString()
        val color = category.color
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(40))
                .heightIn(32.dp)
                .background(color),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = filter,
                Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun CategoryTagPreview() {
    CategoryTag(
        Category.MOBILE
        )

}