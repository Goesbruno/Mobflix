package com.goesbruno.mobflix.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.ui.theme.BlueDark


@Composable
fun CategoryTextField(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    selectedCategory: Category?,
    expanded: Boolean,
    onIconClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    isSetCategory: Boolean
) {

    val value = if (isSetCategory){
        selectedCategory.toString()
    } else ""

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        TextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            readOnly = true,
            placeholder = { Text(
                text = "Escolha uma categoria",
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            ) },
            trailingIcon = {
                IconButton(onClick = onIconClick) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = Color.LightGray,
                focusedContainerColor = BlueDark,
                unfocusedContainerColor = BlueDark,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedPlaceholderColor = Color.LightGray,
                focusedPlaceholderColor = Color.White
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            Modifier
                .fillMaxWidth(),
            containerColor = BlueDark
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(text = category.toString())
                    },
                    onClick = {
                        onCategorySelected(category)
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.White
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun CategoryTextFieldPreview() {
    CategoryTextField(
        categories = Category.entries.map { it }.toList(),
        onCategorySelected = { },
        selectedCategory = null,
        expanded = false,
        isSetCategory = false
    )
}

@Preview
@Composable
private fun CategoryTextFieldExpandedPreview() {
    CategoryTextField(
        categories = Category.entries.map { it}.toList(),
        onCategorySelected = {},
        selectedCategory = Category.MOBILE,
        expanded = true,
        isSetCategory = false
    )
}

