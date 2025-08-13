package com.example.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Holding
import com.example.ui.R
import com.example.ui.model.HoldingUIModel

@Composable
fun StockItem(
    modifier: Modifier = Modifier,
    uiModel: HoldingUIModel
) {

    Column(modifier = modifier) {
        Row {
            Text(
                fontWeight = Bold,
                style = typography.bodyLarge,
                text = uiModel.holding.symbol
            )
            Spacer(modifier = Modifier.weight(1F))
            Row(verticalAlignment = CenterVertically) {
                Text(
                    fontSize = 12.sp,
                    style = typography.titleSmall,
                    text = stringResource(R.string.ltp),
                    color = Color.Gray
                )
                Text(
                    fontSize = 16.sp,
                    style = typography.titleMedium,
                    text = uiModel.formattedLTP
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Row(verticalAlignment = CenterVertically) {
                Text(
                    fontSize = 12.sp,
                    style = typography.titleSmall,
                    text = stringResource(R.string.net_qty),
                    color = Color.Gray
                )
                Text(
                    fontSize = 16.sp,
                    style = typography.titleMedium,
                    text = uiModel.holding.quantity.toString()
                )
            }
            Spacer(modifier = Modifier.weight(1F))

            Row(verticalAlignment = CenterVertically) {
                Text(
                    fontSize = 12.sp,
                    style = typography.titleSmall,
                    text = stringResource(R.string.pl),
                    color = Color.Gray
                )
                Text(
                    fontSize = 16.sp,
                    style = typography.titleMedium,
                    text = uiModel.formattedPNL,
                    color = ColorComposable(uiModel.individualStockPNL)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StockItemPreview() {
    StockItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        uiModel = HoldingUIModel(
            holding = Holding(
                symbol = "MAHABANK",
                quantity = 990,
                ltp = 38.05,
                avgPrice = 35.0,
                close = 40.0
            ),
            individualStockPNL = 5727.0,
            formattedLTP = "111",
            formattedPNL = "222"
        )


    )
}