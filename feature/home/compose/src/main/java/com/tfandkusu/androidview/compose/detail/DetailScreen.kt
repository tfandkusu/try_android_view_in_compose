package com.tfandkusu.androidview.compose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfandkusu.androidview.compose.NyTopAppBar
import com.tfandkusu.androidview.home.compose.R
import com.tfandkusu.androidview.ui.theme.MyTheme

@Composable
fun DetailScreen(backToHome: () -> Unit = {}) {
    Scaffold(
        topBar = {
            NyTopAppBar(
                title = {
                    Text(stringResource(R.string.title_detail))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backToHome()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.action_back_to_home)
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                stringResource(R.string.no_content),
                color = colorResource(R.color.textHE),
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview
fun DetailScreenPreview() {
    MyTheme {
        DetailScreen()
    }
}
