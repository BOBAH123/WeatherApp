package com.easyservice.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.easyservice.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchSubmit: () -> Unit
) {

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isCityNameValid by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shadowElevation = 18.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(size = 16.dp),
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isDropdownExpanded = focusState.isCaptured
                    },
                value = searchText,
                onValueChange = { text ->
                    onSearchTextChange(text)
                    isCityNameValid = text.isNotEmpty()
                },
                placeholder = {
                    Text(
                        text = "Search...",
                        style = TextStyle(color = Color.White)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (isCityNameValid) {
                                    onSearchSubmit()
                                }
                            },
                        painter = painterResource(R.drawable.ic_search),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Search Button",
                    )
                }
            )
        }
    }
}