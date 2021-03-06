package com.tfandkusu.androidview.view.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfandkusu.androidview.ui.theme.MyTheme
import com.tfandkusu.androidview.viewcommon.R
import com.tfandkusu.androidview.viewmodel.error.ApiErrorState
import com.tfandkusu.androidview.viewmodel.error.ApiServerError

@Composable
fun ApiError(apiErrorState: ApiErrorState, reload: () -> Unit) {
    val errorMessage = if (apiErrorState.network) {
        stringResource(R.string.error_network)
    } else if (apiErrorState.server != null) {
        stringResource(
            R.string.error_server_error,
            apiErrorState.server.code,
            apiErrorState.server.message
        )
    } else {
        stringResource(R.string.error_unknown)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp).fillMaxWidth().fillMaxHeight()
    ) {
        Text(
            errorMessage,
            style = TextStyle(
                color = colorResource(R.color.textME),
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = reload
        ) {
            Text(stringResource(R.string.reload))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ApiErrorPreviewNetwork() {
    MyTheme {
        ApiError(ApiErrorState(network = true)) {
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ApiErrorPreviewServerError() {
    MyTheme {
        ApiError(
            ApiErrorState(
                server = ApiServerError(
                    503, "Service Unavailable"
                )
            )
        ) {
        }
    }
}
