package e2su.utbm.sy43project.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import e2su.tools.class_wrap.DEFAULT_EXPORTERS_MAPS
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.ActivityPost
import e2su.utbm.sy43project.ui.components.HomeButton
import e2su.utbm.sy43project.ui.components.NoobleFooter
import e2su.utbm.sy43project.ui.components.NoobleHeader
import org.json.JSONObject
import org.json.JSONTokener

@Composable
fun ClassOverview(
    modifier: Modifier = Modifier
) {
    Column(){
        Text(
            text = "Titre UE",
            modifier = modifier.padding(16.dp)
        )
        Spacer(modifier = modifier.height(16.dp))

        // Création du cours
        val jsonData = """
            {
                "type": "container",
                "data": {
                    "is_horizontal": null,
                    "is_wrapping": null,
                    "children": [
                        {
                            "type": "raw-text",
                            "data": "Un premier texte"
                        },
                        {
                            "type": "container",
                            "data": {
                                "is_horizontal": null,
                                "is_wrapping": null,
                                "children": [
                                    {
                                        "type": "raw-text",
                                        "data": "Un second texte"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Encore un autre texte. Cette fois, c'est un texte giga long qui va forcément dépasser de la taille permise par la largeur de l'écran. Mais pas de panique, le texte effectue un retour à la ligne lui permettant de s'afficher correctement sans aucun problème. "
                                    },
                                    {
                                        "type": "file",
                                        "data": {
                                            "description": "Vla un fichier",
                                            "filename": "TP1.ZIP",
                                            "src": "UNKNOWN"
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            "type": "container",
                            "data": {
                                "is_horizontal": null,
                                "is_wrapping": null,
                                "children": [
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    },
                                    {
                                        "type": "raw-text",
                                        "data": "Des textes pour allonger et permettre le scroll"
                                    }
                                ]
                            }
                        }
                    ]
                }
            }
        """.trimMargin()

        Column (modifier = modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
        ) {

            DEFAULT_EXPORTERS_MAPS.createView(JSONObject(JSONTokener(jsonData)))

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityViewPreview() {
    ActivityView()
}