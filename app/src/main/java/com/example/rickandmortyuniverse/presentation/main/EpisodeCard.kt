package com.example.rickandmortyuniverse.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmortyuniverse.domain.entity.Episode
import org.w3c.dom.Text

@Composable
fun EpisodeCard(
    episode: Episode,
    onCardClickListener: (episode: Episode) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(8.dp)
            .clickable { onCardClickListener(episode) },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        //border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = episode.name,
                Modifier.weight(1f),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            Column {
                Text(text = episode.episodeNumber)
                Text(text = episode.date)
            }
        }
    }
}