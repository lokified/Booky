package com.loki.booko.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.loki.booko.domain.models.BookItem


@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: BookItem,
    onItemClick: (BookItem) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(elevation = 1.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable { onItemClick(book) }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            BookImage(
                bookUrl = book.formats.imagejpeg!!,
                modifier = Modifier.size(
                    height = 200.dp,
                    width = 150.dp
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.padding(vertical = 8.dp)) {

                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val subjects = firstFour(book.subjects)

                    subjects.forEach { subject ->
                        
                        Subjects(subject = subject)
                    }
                }

            }
        }
    }
}

fun firstFour(arr: List<String>) : List<String> {
    return arr.take(2)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BookImage(
    bookUrl: String,
    modifier: Modifier = Modifier
) {

    val painter = rememberImagePainter(
        data = bookUrl
    )

    Image(
        painter = painter,
        contentDescription = "Book_Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun Subjects(
    modifier: Modifier = Modifier,
    subject: String
) {

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = subject,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            color = MaterialTheme.colors.secondary,
            maxLines = 1
        )
    }
}