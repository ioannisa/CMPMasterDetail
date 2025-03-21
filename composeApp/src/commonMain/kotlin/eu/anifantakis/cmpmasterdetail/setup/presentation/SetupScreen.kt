package eu.anifantakis.cmpmasterdetail.setup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SetupScreen() {
    val scope = rememberCoroutineScope()
    val bottomBarState = LocalBottomBarState.current

    // Define your tabs dynamically
    val tabs = listOf(
        DynamicTabItem(
            title = "Intro",
            icon = AppIcons.car,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text("INTRO", fontSize = 42.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Button(
                        onClick = {
                            scope.launch {
                                bottomBarState.selectTab(0)
                                delay(50)
                                bottomBarState.hide()
                            }
                        }
                    ) {
                        Text("APPLY")
                    }
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)

                }
            }
        ),
        DynamicTabItem(
            title = "Settings",
            icon = AppIcons.settings,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text("SETTINGS", fontSize = 42.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Sed commodi voluptas et voluptas architecto et ipsum cupiditate aut dolores tenetur et dolorem soluta et recusandae deleniti?", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)

                }
            }
        ),
        DynamicTabItem(
            title = "Email",
            icon = AppIcons.email,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text("EMAIL", fontSize = 42.sp)
                    Text("Qui sint reprehenderit est explicabo libero non autem provident et praesentium velit quo voluptatem quisquam sit architecto maiores vel voluptatem officia.", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)

                }
            }
        ),
        DynamicTabItem(
            title = "Security",
            icon = AppIcons.padlock,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text("SECURITY", fontSize = 42.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                    Text("Lorem ipsum dolor sit amet. In expedita voluptatem qui perspiciatis dicta est accusamus explicabo qui repellendus ipsum est quia veritatis?", fontSize = 16.sp)
                    Text("Ut fugiat perspiciatis ut optio quam est nemo blanditiis non itaque minus aut officiis assumenda eos quia autem qui reiciendis veritatis!", fontSize = 16.sp)
                }
            }
        )
    )

    // Use the dynamic tab screen component
    MyAppTheme {
        SetupScreen(
            tabs = tabs,
            initialTabIndex = 0 // Start with the first tab selected
        )
    }
}