package com.golfpvcc.memory
// Making a Simple Android Memory Game
// https://www.youtube.com/watch?v=B7--mnjOOI4&list=PL1YQsimP1H2bH3zppxEB9War9bqMgSDzI&index=47
// https://github.com/skyFishLLC/MemoryYouTubeTutorial/tree/db3d7ce893646114ae1bc1d1fc9fa47529f3083a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.golfpvcc.memory.ui.theme.MemoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}
