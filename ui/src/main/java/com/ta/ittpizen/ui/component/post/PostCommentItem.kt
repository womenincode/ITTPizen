package com.ta.ittpizen.ui.component.post

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ta.ittpizen.domain.model.post.PostComment
import com.ta.ittpizen.ui.theme.ITTPizenTheme

@Composable
fun PostCommentItem(
    modifier: Modifier = Modifier,
    post: PostComment,
    onProfile: (PostComment) -> Unit = {}
) {
    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PostHeader(
            profile = post.user.photo,
            name = post.user.name,
            type = post.user.type,
            date = post.createdAt,
            onProfile = { onProfile(post) }
        )
        PostBody(text = post.comment)
    }
}

@Preview
@Composable
fun PreviewPostCommentItem() {
    ITTPizenTheme {
        Surface {
            val postItem = PostComment()
            PostCommentItem(
                post = postItem,
                modifier = Modifier.padding(all = 20.dp)
            )
        }
    }
}
