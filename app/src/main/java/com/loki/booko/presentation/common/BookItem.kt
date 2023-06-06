package com.loki.booko.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.loki.booko.domain.models.BookItem
import com.loki.booko.util.TextUtils


@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: BookItem,
    onItemClick: (BookItem) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onItemClick(book) },
        shape = RoundedCornerShape(4.dp),
    ) {

        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            BookImage(
                bookUrl = book.formats.imagejpeg!!,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp)
            )


            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.weight(1f))

                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val subjects = TextUtils.getSubjectsAsString(book.subjects, 4)

                    subjects.forEach { subject ->
                        
                        Subjects(subject = subject)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

            }
        }
    }
}

@Composable
fun BookImage(
    bookUrl: String,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = bookUrl,
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
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = subject,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            maxLines = 1
        )
    }
}