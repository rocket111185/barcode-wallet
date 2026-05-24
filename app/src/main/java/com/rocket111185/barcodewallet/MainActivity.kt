package com.rocket111185.barcodewallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.rocket111185.barcodewallet.data.BarcodeWalletDatabase
import com.rocket111185.barcodewallet.data.StoredCodeDao
import com.rocket111185.barcodewallet.data.StoredCodeEntity
import com.rocket111185.barcodewallet.data.StubCodeSeeder
import com.rocket111185.barcodewallet.ui.BarcodeBitmapRenderer
import com.rocket111185.barcodewallet.ui.theme.BarcodeWalletTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = BarcodeWalletDatabase.getInstance(applicationContext)
        val codeDao = database.storedCodeDao()

        lifecycleScope.launch {
            StubCodeSeeder.seedIfEmpty(codeDao)
        }

        setContent {
            BarcodeWalletTheme {
                BarcodeWalletApp(codeDao = codeDao)
            }
        }
    }
}

@Composable
fun BarcodeWalletApp(codeDao: StoredCodeDao) {
    val codes by produceState<List<StoredCodeEntity>>(
        initialValue = emptyList(),
        key1 = codeDao,
    ) {
        codeDao.observeActiveCodes().collect { value = it }
    }

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreen(
                codes = codes,
                modifier = Modifier.padding(20.dp),
            )
        }
    }
}

@Composable
private fun HomeScreen(
    codes: List<StoredCodeEntity>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Text(
            text = "Barcode Wallet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "A native Android wallet for QR codes and barcodes.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        if (codes.isEmpty()) {
            EmptyCodeList()
        } else {
            CodeGrid(
                codes = codes,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun EmptyCodeList() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Text(
            text = "No saved codes yet.",
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun CodeGrid(
    codes: List<StoredCodeEntity>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = codes,
            key = { it.id },
        ) { code ->
            CodeTile(code = code)
        }
    }
}

@Composable
private fun CodeTile(code: StoredCodeEntity) {
    val bitmap = remember(code.id, code.format, code.payload) {
        BarcodeBitmapRenderer.render(code.format, code.payload)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(196.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = code.title ?: code.format.storageValue,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(126.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                if (bitmap == null) {
                    Text(
                        text = "Preview unavailable",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    Image(
                        painter = BitmapPainter(bitmap.asImageBitmap()),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}
