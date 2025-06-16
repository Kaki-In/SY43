package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import e2su.tools.class_wrap.DEFAULT_EXPORTERS_MAPS
import org.json.JSONObject
import org.json.JSONTokener

@Composable
fun OverviewScreen(
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
            "children": [
              {
                "data": {
                  "children": [
                    {
                      "data": "Ceci est un texte brute dans un container vertical",
                      "type": "raw-text"
                    },
                    {
                      "data": "Qu'est-ce que le Lorem Ipsum ?\nLe Lorem Ipsum est simplement un faux texte utilisé dans l'industrie de l'impression et du traitement de texte. Depuis les années 1500, il est considéré comme le texte fictif standard, lorsque quelqu'un a pris une galère de caractères pour la mélanger et créer un livre d'exemple typographique. Il est parvenu à survivre non seulement cinq siècles, mais aussi l'entrée dans le traitement électronique du texte, sans être essentiellement modifié. Sa popularité a été renforcée dans les années 1960 avec la sortie des feuilles Letraset contenant des passages de Lorem Ipsum, et plus récemment grâce aux logiciels de publication assistée par ordinateur comme Aldus PageMaker qui incluent des versions de Lorem Ipsum.\nLe morceau standard de Lorem Ipsum utilisé depuis les années 1500 est reproduit ci-dessous pour ceux que cela intéresse. Les sections 1.10.32 et 1.10.33 de \"De Finibus Bonorum et Malorum\" par Cicéron y sont également reproduites dans leur forme originale exacte, accompagnées des versions anglaises traduites en 1914 par H. Rackham.",
                      "type": "raw-text"
                    },
                    {
                      "data": "\n                        <h2>\n                            <em>Ceci est un texte riche</em>\n                        </h2>\n                        <p>\n                            <strong>Charles Stein</strong> est un statisticien américain né le 22 mars 1920 et mort le 24 novembre 2016, professeur émérite de statistiques à <a href=\"https://fr.wikipedia.org/wiki/Université_Stanford\" target=\"_blank\"> Stanford</a>.\n                            Il est connu pour le <a href=\"https://fr.wikipedia.org/wiki/Paradoxe_de_Stein\" target=\"_blank\">paradoxe de Stein</a> en <a href=\"https://fr.wikipedia.org/wiki/Théorie_de_la_décision\" target=\"_blank\">théorie de la décision</a> et pour la <a href=\"https://fr.wikipedia.org/wiki/Méthode_de_Stein\" target=\"_blank\">méthode de Stein</a> qui permet de démontrer le <a href=\"https://fr.wikipedia.org/wiki/Théorème_central_limite\" target=\"_blank\"> central limite</a>.\n                        </p>\n                        <p>\n                            <strong>Auguste Van Assche</strong>, né le 5 juillet 1826 à <a href=\"https://fr.wikipedia.org/wiki/Gand\" target=\"_blank\">Gand</a> et mort le 24 février 1907, est un <a href=\"https://fr.wikipedia.org/wiki/Architecte\" target=\"_blank\">architecte</a> <a href=\"https://fr.wikipedia.org/wiki/Belgique\" target=\"_blank\">belge</a>, auteur de nombreuses restaurations et transformations de bâtiments médiévaux en Belgique.\n                        </p>\n                                ",
                      "type": "rich-text"
                    },
                    {
                      "data": "Bonjour",
                      "type": "raw-text"
                    }
                  ],
                  "is_wrapping": false,
                  "is_horizontal": false
                },
                "type": "container"
              },
              {
                "data": "Ce qui suit est un container horizontal avec wrap (miam miam)",
                "type": "raw-text"
              },
              {
                "data": {
                  "children": [
                    {
                      "data": {
                        "src": "https://tests-and-previews.flopcreation.fr/downloads/UTBM - Nooble/TD1.zip",
                        "filename": "TD1.zip",
                        "description": "TD1 : Validation/Remise à Niveau PHP/HTML/CSS Fichier"
                      },
                      "type": "file"
                    },
                    {
                      "data": {
                        "src": "https://tests-and-previews.flopcreation.fr/downloads/UTBM - Nooble/TD1.zip",
                        "filename": "TD2.zip",
                        "description": "TD2 : PHP objet, Javascript"
                      },
                      "type": "file"
                    },
                    {
                      "data": {
                        "src": "https://tests-and-previews.flopcreation.fr/downloads/UTBM - Nooble/TD1.zip",
                        "filename": "TD3.zip",
                        "description": "TD3 : Version finale du site \"sans javascript\""
                      },
                      "type": "file"
                    },
                    {
                      "data": {
                        "src": "https://tests-and-previews.flopcreation.fr/downloads/UTBM - Nooble/WE4A - Moodle Simplifié (devoir retiré).pdf",
                        "filename": "WE4A - Moodle Simplifié (devoir retiré).pdf",
                        "description": "Le cahier des charges : Le but du projet est de créer une version simplifiée de Moodle, et le cahier des charges vous aidera à mieux situer ce que nous voulons dire par là. Vous devez utiliser Symfony pour pouvoir avoir une note supérieure à 15/20."
                      },
                      "type": "file"
                    }
                  ],
                  "is_wrapping": true,
                  "is_horizontal": true
                },
                "type": "container"
              },
              {
                "data": {
                  "children": [
                    {
                      "data": "https://randomwordgenerator.com/img/picture-generator/55e2dc434c50a814f1dc8460962e33791c3ad6e04e50744074267bd19f4ac2_640.jpg",
                      "type": "image"
                    },
                    {
                      "data": "https://randomwordgenerator.com/img/picture-generator/5fe4d0474255b10ff3d8992cc12c30771037dbf85254784b772779d69f4e_640.jpg",
                      "type": "image"
                    }
                  ],
                  "is_wrapping": true,
                  "is_horizontal": true
                },
                "type": "container"
              },
              {
                "data": {
                  "id": 1,
                  "html": "<p>\n    Ceci est une activité interactive. \n</p>\n\n<button id=\"activity-button\">\n    !Cliquez-moi!\n</button>\n<br>\n",
                  "edit_html": "\n<p>\n    Ceci est une activité interactive. \n</p>\n\n<button id=\"activity-button\">\n    !Cliquez-moi!\n</button>\n<br>\n\n<p>\n    Cette activité est ici en mode édition (indépendant)\n</p>\n\n<button class=\"icon-button\">\n    <img src=\"/images/icons/settings.png\"/>\n    <span>Éditer...</span>\n</button>",
                  "javascript": "\nclass Activity\n{\n    constructor(id, args)\n    {\n        console.log(\"loading activity\");\n        this._text_url = args.text_url\n    }\n\n    onRender(div)\n    {\n        div.querySelector(\"button\").addEventListener(\"click\", async () => {\n            alert(await this.getDisplayedText());\n        })\n    }\n\n    async getDisplayedText()\n    {\n        let data = await fetch(this._text_url);\n        return await data.text();\n    }\n}",
                  "edit_javascript": "\nclass Activity\n{\n    constructor(id, args)\n    {\n        console.log(\"loading editable activity\");\n        this._text_url = args.text_url\n    }\n\n    onRender(div)\n    {\n        div.querySelector(\"button\").addEventListener(\"click\", async () => {\n            alert(await this.getDisplayedText());\n        })\n    }\n\n    async getDisplayedText()\n    {\n        let data = await fetch(this._text_url);\n        return await data.text();\n    }\n}",
                  "css": "",
                  "arguments": {
                    "text_url": "/activity-resources/click-me-activity/test"
                  }
                },
                "type": "activity"
              },
              {
                "data": {
                  "children": [
                    {
                      "data": {
                        "src": "https://www.youtube.com/embed/lJIrF4YjHfQ?si=bNKfRgR0zomAqao8",
                        "width": 560,
                        "height": 315,
                        "permissions": [
                          "accelerometer",
                          "autoplay",
                          "clipboard-write",
                          "encrypted-media",
                          "gyroscope",
                          "picture-in-picture",
                          "web-share",
                          "fullscreen"
                        ]
                      },
                      "type": "integration"
                    },
                    {
                      "data": "Qu'est-ce que le Lorem Ipsum ?\nLe Lorem Ipsum est simplement un faux texte utilisé dans l'industrie de l'impression et du traitement de texte. Depuis les années 1500, il est considéré comme le texte fictif standard, lorsque quelqu'un a pris une galère de caractères pour la mélanger et créer un livre d'exemple typographique. Il est parvenu à survivre non seulement cinq siècles, mais aussi l'entrée dans le traitement électronique du texte, sans être essentiellement modifié. Sa popularité a été renforcée dans les années 1960 avec la sortie des feuilles Letraset contenant des passages de Lorem Ipsum, et plus récemment grâce aux logiciels de publication assistée par ordinateur comme Aldus PageMaker qui incluent des versions de Lorem Ipsum.\nLe morceau standard de Lorem Ipsum utilisé depuis les années 1500 est reproduit ci-dessous pour ceux que cela intéresse. Les sections 1.10.32 et 1.10.33 de \"De Finibus Bonorum et Malorum\" par Cicéron y sont également reproduites dans leur forme originale exacte, accompagnées des versions anglaises traduites en 1914 par H. Rackham.",
                      "type": "raw-text"
                    }
                  ],
                  "is_wrapping": false,
                  "is_horizontal": true
                },
                "type": "container"
              },
              {
                "data": {
                  "children": [
                    {
                      "data": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                      "type": "video"
                    },
                    {
                      "data": {
                        "children": [
                          {
                            "data": "Qu'est-ce que le Lorem Ipsum ?\nLe Lorem Ipsum est simplement un faux texte utilisé dans l'industrie de l'impression et du traitement de texte. Depuis les années 1500, il est considéré comme le texte fictif standard, lorsque quelqu'un a pris une galère de caractères pour la mélanger et créer un livre d'exemple typographique. Il est parvenu à survivre non seulement cinq siècles, mais aussi l'entrée dans le traitement électronique du texte, sans être essentiellement modifié. Sa popularité a été renforcée dans les années 1960 avec la sortie des feuilles Letraset contenant des passages de Lorem Ipsum, et plus récemment grâce aux logiciels de publication assistée par ordinateur comme Aldus PageMaker qui incluent des versions de Lorem Ipsum.\nLe morceau standard de Lorem Ipsum utilisé depuis les années 1500 est reproduit ci-dessous pour ceux que cela intéresse. Les sections 1.10.32 et 1.10.33 de \"De Finibus Bonorum et Malorum\" par Cicéron y sont également reproduites dans leur forme originale exacte, accompagnées des versions anglaises traduites en 1914 par H. Rackham.",
                            "type": "raw-text"
                          },
                          {
                            "data": "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                            "type": "audio"
                          }
                        ],
                        "is_wrapping": false,
                        "is_horizontal": false
                      },
                      "type": "container"
                    }
                  ],
                  "is_wrapping": false,
                  "is_horizontal": true
                },
                "type": "container"
              },
              {
                "data": {
                  "children": [
                    {
                      "data": "J'en profite pour glisser mon album",
                      "type": "raw-text"
                    },
                    {
                      "data": {
                        "src": "https://open.spotify.com/embed/album/2UjSSrKiHFeFFb2WNMEv7X?utm_source=generator&theme=0",
                        "width": "auto",
                        "height": 352,
                        "permissions": [
                          "autoplay",
                          "clipboard-write",
                          "encrypted-media",
                          "fullscreen",
                          "picture-in-picture"
                        ]
                      },
                      "type": "integration"
                    },
                    {
                      "data": "Écoutez c'est cool",
                      "type": "raw-text"
                    }
                  ],
                  "is_wrapping": false,
                  "is_horizontal": false
                },
                "type": "container"
              }
            ],
            "is_wrapping": false,
            "is_horizontal": false
          }
        }
        """

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
    ActivityScreen()
}