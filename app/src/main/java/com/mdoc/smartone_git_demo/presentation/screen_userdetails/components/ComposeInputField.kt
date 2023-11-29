package com.mdoc.smartone_git_demo.presentation.screen_userdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mdoc.smartone_git_demo.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun UserIdInputField(onSearch: (input: String) -> Unit) {
    var userId by remember {
        mutableStateOf("")
    }
    var isError by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.testTag(stringResource(R.string.search_text_field_tag)),
            value = userId,
            onValueChange = {
                userId = it
                if (it.isNotEmpty())
                    isError = false
            },
            singleLine = true,
            isError = isError,
            trailingIcon = {
                if (isError)
                    Icon(
                        Icons.Filled.Warning,
                        stringResource(R.string.input_text_error_icon),
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.empty_string_is_not_allowed),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = { Text(text = stringResource(R.string.txt_label_github_userid)) },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            if (userId.isNotEmpty()) {
                isError = false
                onSearch(userId)
            } else {
                isError = true
            }
            userId = ""
            keyboardController?.hide()
        }) {
            Text(text = stringResource(R.string.button_search))
        }

    }
}