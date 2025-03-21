package eu.anifantakis.cmpmasterdetail.setup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.anifantakis.cmpmasterdetail.core.presentation.AppColor
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TabItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun SetupScreen() {

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        println("SetupScreen launched")
    }

    MyAppTheme {
        val bottomBarState = LocalBottomBarState.current

        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

        // Track section positions and heights
        val sectionPositions = remember { mutableStateListOf<Float>() }
        val sectionHeights = remember { mutableStateListOf<Int>() }

        // Define the tab bar height to use as offset
        val tabBarHeight = 56.dp
        val tabBarHeightPx = with(LocalDensity.current) { tabBarHeight.toPx() }

        // Initialize positions tracking
        while (sectionPositions.size < 4) {
            sectionPositions.add(0f)
            sectionHeights.add(0)
        }

        // Flag to track if scroll was triggered by tab click
        var isProgrammaticScroll by remember { mutableStateOf(false) }

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.value }
                .collect { currentScroll ->
                    // Only update selected tab based on scroll if not programmatic
                    if (!isProgrammaticScroll) {
                        for (i in sectionPositions.indices) {
                            // Here, you can adjust the condition if you want real-time updates while scrolling
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

        // Define tab items
        val tabs = listOf(
            TabItem("Intro", AppIcons.car),
            TabItem("tab2", AppIcons.settings),
            TabItem("tab3", AppIcons.email),
            TabItem("tab4", AppIcons.padlock)
        )

        Box(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = tabBarHeight) // Content starts below the tab bar
                    .verticalScroll(scrollState)
            ) {




                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(1200.dp)
                        //.background(Color.Red)
                        .onGloballyPositioned { coordinates ->
                            sectionPositions[0] = coordinates.positionInParent().y
                            sectionHeights[0] = coordinates.size.height
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("INTRO", fontSize = 42.sp)

                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)

                        Button(
                            onClick = {
                                scope.launch {
                                    bottomBarState.selectTab(0)
                                    // Give it a tiny delay to ensure navigation is processed
                                    delay(50)
                                    bottomBarState.hide()
                                }
                            }
                        ) {
                            Text("APPLY")
                        }

                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(1200.dp)
                        //.background(Color.Green)
                        .onGloballyPositioned { coordinates ->
                            sectionPositions[1] = coordinates.positionInParent().y
                            sectionHeights[1] = coordinates.size.height
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("TAB 2", fontSize = 42.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(1200.dp)
                        //.background(Color.Blue)
                        .onGloballyPositioned { coordinates ->
                            sectionPositions[2] = coordinates.positionInParent().y
                            sectionHeights[2] = coordinates.size.height
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("TAB 3", fontSize = 42.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)


                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.height(1200.dp)
                        //.background(Color.Cyan)
                        .onGloballyPositioned { coordinates ->
                            sectionPositions[3] = coordinates.positionInParent().y
                            sectionHeights[3] = coordinates.size.height
                        }
                ) {
                    Column {
                        Text("TAB 4", fontSize = 42.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                        Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis? Ut officia inventore a aliquid voluptatem et maiores aperiam ut excepturi magnam.", fontSize = 16.sp)
                        Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis! Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti? Quo rerum tenetur quo porro facere qui illo dolorem 33 voluptatibus totam et itaque necessitatibus ad voluptatem architecto ex maxime maiores. Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)

                    }
                }
            }



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabBarHeight)
                    .background(AppColor.DarkGrey)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = AppColor.White,
                    containerColor = AppColor.DarkGrey,
                    // Yellow indicator for selected tab
                    indicator = { tabPositions ->
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(2.dp)
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = Color(0xFFFFD700),
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
                                            scrollState.animateScrollTo(targetPosition)
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
                                        tint = if (selectedTabIndex == index) AppColor.White else AppColor.MidGrey,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = tabItem.title,
                                        fontSize = 14.sp,
                                        color = if (selectedTabIndex == index) AppColor.White else AppColor.MidGrey
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
                                                color = AppColor.MidGrey.copy(alpha = 0.5f),
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









