package eu.anifantakis.cmpmasterdetail.setup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

/**
 * Data class representing a tab item with its title, icon, and content
 */
data class DynamicTabItem(
    val title: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
)

/**
 * A dynamic tab screen that can display any number of tabs with their respective content
 *
 * @param tabs List of DynamicTabItem objects containing tab information and content
 * @param modifier Optional modifier for the component
 * @param initialTabIndex The initial selected tab index (default: 0)
 * @param indicatorColor The color of the selected tab indicator (default: Yellow)
 * @param tabBarHeight The height of the tab bar (default: 56.dp)
 */
@OptIn(FlowPreview::class)
@Composable
fun AppTabScroller(
    tabs: List<DynamicTabItem>,
    modifier: Modifier = Modifier,
    initialTabIndex: Int = 0,
    backgroundColor: Color = Color(0xFF101010),
    activeColor: Color = Color(0xFFFFFFFF),
    inactiveColor: Color = Color(0xFF757575),
    indicatorColor: Color = activeColor,
    tabsPadding: PaddingValues = PaddingValues(0.dp),
    tabBarHeight: androidx.compose.ui.unit.Dp = 56.dp
) {
    require(tabs.isNotEmpty()) { "Tabs list cannot be empty" }
    val density = LocalDensity.current

    Box {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        var selectedTabIndex by rememberSaveable { mutableStateOf(initialTabIndex.coerceIn(0, tabs.size - 1)) }

        val sectionPositions = remember(tabs.size) { MutableList(tabs.size) { 0f } }
        val sectionHeights = remember(tabs.size) { MutableList(tabs.size) { 0 } }

        var tabsHaveBeenMeasured by remember { mutableStateOf(false) }

        // Flag to track if scroll was triggered by tab click
        var isProgrammaticScroll by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // Define a small threshold for "close enough" to the end
            val endScrollThreshold = with(density) { 64.dp.toPx() }

            snapshotFlow { scrollState.value }
                .sample(100L) // collect no faster than every 100ms
                .collect { currentScroll ->
                    // Only update selected tab based on scroll if not programmatic
                    if (!isProgrammaticScroll) {
                        // First check if we've reached the end of the scrollable content
                        if (scrollState.value >= scrollState.maxValue - endScrollThreshold) {
                            // We've reached the bottom, always select the last tab
                            if (selectedTabIndex != tabs.lastIndex) {
                                selectedTabIndex = tabs.lastIndex
                            }
                        } else {
                            // Normal tab selection logic based on scroll position
                            for (i in sectionPositions.indices) {
                                if (currentScroll >= sectionPositions[i] &&
                                    (i == sectionPositions.lastIndex || currentScroll < sectionPositions[i + 1])
                                ) {
                                    if (selectedTabIndex != i) {
                                        selectedTabIndex = i
                                    }
                                    break
                                }
                            }
                        }
                    }
                }
        }

        Box(modifier = modifier.fillMaxSize()) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = tabBarHeight) // Content starts below the tab bar
                    .verticalScroll(scrollState)
            ) {
                tabs.forEachIndexed { index, tabItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(tabsPadding)
                            .let {
                                if (!tabsHaveBeenMeasured) {
                                    it.onGloballyPositioned { coordinates ->
                                        sectionPositions[index] = coordinates.positionInParent().y
                                        sectionHeights[index] = coordinates.size.height
                                        println("POSITIONING")

                                        // Once all tabs have been measured, never measure again
                                        if (index == tabs.lastIndex) {
                                            tabsHaveBeenMeasured = true
                                        }
                                    }
                                } else {
                                    it
                                }
                            }
                    ) {
                        tabItem.content()
                    }
                }
            }

            // Tab Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabBarHeight)
                    .background(backgroundColor)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = activeColor,
                    containerColor = backgroundColor,
                    // Indicator for selected tab
                    indicator = { tabPositions ->
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(2.dp)
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = indicatorColor,
                                    shape = RoundedCornerShape(topStart = 1.dp, topEnd = 1.dp)
                                )
                        )
                    },
                    divider = { /* No divider */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, tabItem ->
                        // Create custom tab with its own indicator
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        coroutineScope.launch {
                                            // Disable auto-updates during programmatic scroll
                                            isProgrammaticScroll = true
                                            selectedTabIndex = index
                                            val targetPosition = sectionPositions[index].toInt().coerceAtLeast(0)

                                            // Calculate distance-based animation duration
                                            val currentPosition = scrollState.value
                                            val distanceToScroll = kotlin.math.abs(targetPosition - currentPosition)

                                            // Base duration plus additional time based on distance
                                            val baseDuration = 200 // minimum duration in milliseconds
                                            val distanceFactor = 0.2f // milliseconds per pixel
                                            val calculatedDuration = (baseDuration + (distanceToScroll * distanceFactor)).toInt()

                                            // Keep duration within reasonable bounds
                                            val animationDuration = calculatedDuration.coerceIn(200, 1500)

                                            scrollState.animateScrollTo(
                                                value = targetPosition,
                                                animationSpec = androidx.compose.animation.core.tween(
                                                    durationMillis = animationDuration,
                                                    easing = androidx.compose.animation.core.EaseOutQuad
                                                )
                                            )
                                            isProgrammaticScroll = false
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            // Tab content in a Column to add indicator at bottom
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Tab content (icon and text)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                                ) {
                                    Icon(
                                        imageVector = tabItem.icon,
                                        contentDescription = null,
                                        tint = if (selectedTabIndex == index) activeColor else inactiveColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = tabItem.title,
                                        fontSize = 10.sp,
                                        color = if (selectedTabIndex == index) activeColor else inactiveColor
                                    )
                                }

                                // Add gray line for unselected tabs
                                if (selectedTabIndex != index) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .padding(horizontal = 6.dp)
                                            .background(
                                                color = inactiveColor,
                                                shape = RoundedCornerShape(topStart = 1.dp, topEnd = 1.dp)
                                            )
                                    )
                                } else {
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}