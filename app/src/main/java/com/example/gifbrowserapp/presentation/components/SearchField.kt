package com.example.gifbrowserapp.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gifbrowserapp.R
import com.example.gifbrowserapp.presentation.design.AppTheme
import com.example.gifbrowserapp.presentation.ui.HomeScreen
import com.example.gifbrowserapp.presentation.utils.extensions.clickableNoRipple
import com.example.gifbrowserapp.presentation.utils.extensions.optionalComposable
import com.example.gifbrowserapp.presentation.utils.extensions.painter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    query: String,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    enabled: Boolean = true,
    placeholder: String? = null,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    leadingIcon: Painter = R.drawable.ic_search.painter,
    onLeadingClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { onActiveChange(false) },
        active = active,
        enabled = enabled,
        onActiveChange = onActiveChange,
        shape = AppTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { onActiveChange(it.hasFocus) },
        placeholder = placeholder?.optionalComposable {
            Placeholder(text = placeholder, textAlign = TextAlign.Start)
        },
        trailingIcon = {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                tint = AppTheme.colors.primary,
                modifier = Modifier
                    .size(20.dp)
                    .clickableNoRipple(onClick = onLeadingClick)
            )
        },
        leadingIcon = optionalComposable(shouldExecute = active) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.clickableNoRipple(onClick = onClear)
            )
        },
        tonalElevation = 1.dp,
        colors = SearchBarDefaults.colors(
            dividerColor = AppTheme.colors.divider,
            containerColor = AppTheme.colors.onPrimary,
        ),
        content = content
    )
}

@Composable
internal fun Placeholder(text: String, textAlign: TextAlign?) {
    Text(
        text = text,
        textAlign = textAlign,
        modifier = Modifier.fillMaxWidth(),
        color = AppTheme.colors.contentBorder
    )
}

@Composable
@Preview(showBackground = true)
private fun Preview() = SearchField( query = "",
    active = false,
    enabled = false,
    placeholder = "Search For Gifs",
    onQueryChange = {},
    onClear = {},
    onActiveChange = {}, content = {}
    )
