package com.ta.ittpizen.feature_onboarding_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ta.ittpizen.feature_onboarding_screen.component.OnboardingContent
import com.ta.ittpizen.feature_onboarding_screen.component.OnboardingFooter
import com.ta.ittpizen.feature_onboarding_screen.component.OnboardingHeader
import com.ta.ittpizen.feature_onboarding_screen.domain.Onboarding
import com.ta.ittpizen.ui.theme.ITTPizenTheme
import org.koin.androidx.compose.koinViewModel

@ExperimentalFoundationApi
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
    navigateToLoginScreen: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    val navigateToNextScreen: () -> Unit = {
        viewModel.updateFirstOpenState(false)
        navigateToLoginScreen()
    }

    val onBoardings = listOf(
        Onboarding(
            image = R.drawable.img_onboarding_1,
            title = "Hi ITTPizen, start now!",
            description = "Let’s connect, sharing information and job with students, alumni, lecturer and other civitas at ITTPizen now."
        ),
        Onboarding(
            image = R.drawable.img_onboarding_2,
            title = "Let’s Connect and Sharing",
            description = "Connect with all students, alumni, lecturers and staff at IT Telkom Purwokerto"
        ),
        Onboarding(
            image = R.drawable.img_onboarding_3,
            title = "Share your information and job with us",
            description = "With ITTPizen Apps you can share all your information academic or knowledge about jobs"
        )
    )
    val pagerState = rememberPagerState { onBoardings.size }
    val buttonSkipVisibility = remember(key1 = pagerState.currentPage) {
        pagerState.currentPage != onBoardings.lastIndex
    }
    val buttonFinishVisibility = remember(key1 = pagerState.currentPage) {
        pagerState.currentPage == onBoardings.lastIndex
    }

    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnboardingHeader(
                modifier = Modifier.align(Alignment.End),
                onSkipClick = navigateToNextScreen,
                buttonSkipVisibility = buttonSkipVisibility
            )
            Spacer(modifier = Modifier.height(52.dp))
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
                OnboardingContent(onboarding = onBoardings[it])
            }
            Spacer(modifier = Modifier.height(101.dp))
            OnboardingFooter(
                count = pagerState.pageCount,
                activePage = pagerState.currentPage,
                buttonFinishVisibility = buttonFinishVisibility,
                onFinishClick = navigateToNextScreen,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewOnboardingScreen() {
    ITTPizenTheme {
        Surface {
            OnboardingScreen()
        }
    }
}
