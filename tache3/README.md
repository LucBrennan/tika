# Tâche 3

Brennan Luc (LucBrennan), Rabenantoandro Toky Erick (Toky5)

## 1. Gate Semgrep dans le build

Fichier : [`.github/workflows/tache2.yml`](../.github/workflows/tache2.yml)

À la tâche 2, les workflows de build d'origine de Tika (`main-jdk17/21/25-build.yml`) ont été mis en déclenchement manuel et `tache2.yml` est devenu le workflow de build du fork (build et tests de tika-core à chaque push). C'est donc ce workflow que nous avons modifié.

Choix de conception :

- Un job `semgrep` roule avant le job `build` (`needs: semgrep`). S'il échoue, le build ne roule pas et le workflow est en échec.
- Semgrep est installé avec pip et sa version est fixée (`semgrep==1.185.0`) pour que les résultats ne changent pas d'un run à l'autre. Règles : `p/java` (Tika est écrit en Java) et `p/security-audit`, parce que la version gratuite de `p/java` n'a pas certaines règles (aléa faible, injection de commande).
- Seules les **nouvelles** vulnérabilités font échouer le build. Tika en contient déjà 11 selon Semgrep (voir section 3) ; un gate qui échoue sur toutes les vulnérabilités serait toujours rouge. On utilise `--baseline-commit` : Semgrep ne rapporte que les résultats absents du commit de base, et `--error` fait échouer le job s'il en reste.
- Commit de base : le commit précédent le push (`github.event.before`), ou la branche de base pour une pull request. Si ce commit n'existe pas (premier push, lancement manuel), on prend `HEAD~1`.
- `fetch-depth: 0` dans le checkout, sinon le commit de base n'est pas disponible.

Validation : sur un commit propre, le gate passe au vert ([run](https://github.com/LucBrennan/tika/actions/runs/43983569661), build : 833 tests, 0 échec). Sur un commit qui ajoute une nouvelle vulnérabilité, le job `semgrep` échoue et le build ne roule pas.

## 2. Vulnérabilités introduites

Les sept vulnérabilités ont été choisies parmi les types abordés dans les présentations de la semaine 7 sur le SAST et Semgrep, ainsi qu'en tenant compte des vulnérabilités déjà présentes dans le rapport initial. Chaque vulnérabilité a été introduite dans une nouvelle méthode, afin de ne pas modifier le comportement du code existant ni de faire échouer les tests existants. Un commit distinct a été créé pour chaque vulnérabilité.

| # | Fichier | Type (CWE) | Pourquoi c'est vulnérable | Résultat Semgrep (règle, ligne) |
| - | ------- | ---------- | ------------------------- | ------------------------------------ |
| 1 | `tika-eval/tika-eval-app/src/main/java/org/apache/tika/eval/app/db/JDBCUtil.java` | Injection SQL (CWE-89) | La requête SQL est construite par concaténation directe d'une entrée utilisateur (`matricule`). Une entrée malveillante pourrait modifier la requête exécutée. | `formatted-sql-string`, ligne 98. |
| 2 | `tika-core/src/main/java/org/apache/tika/digest/DigestHelper.java` | Hachage faible MD5 (CWE-328) | MD5 n'est plus considéré comme résistant aux collisions. Deux entrées différentes peuvent produire le même condensat, ce qui le rend inadapté aux usages nécessitant une fonction de hachage sécurisée. | `use-of-md5`, ligne 116. |
| 3 | `tika-core/src/main/java/org/apache/tika/detect/FileCommandDetector.java` | Injection de commande (CWE-78) | Une entrée utilisateur est concaténée directement à une commande système exécutée avec `Runtime.exec()`. Un utilisateur pourrait ainsi influencer la commande lancée. | `command-injection-formatted-runtime-call`, ligne 172. |
| 4 | `tika-core/src/main/java/org/apache/tika/utils/ProcessUtils.java` | Génération aléatoire insuffisante (CWE-330) | `java.util.Random` ne fournit pas de génération cryptographiquement sécurisée. Les valeurs produites peuvent être prévisibles et ne conviennent donc pas à un code de réinitialisation sensible. | `weak-random`, ligne 302. |
| 5 | `tika-core/src/main/java/org/apache/tika/utils/XMLReaderUtils.java` | XXE (CWE-611) | Le `DocumentBuilderFactory` est utilisé sans désactiver les déclarations DOCTYPE et les entités externes. Un document XML malveillant pourrait provoquer la lecture de ressources externes ou locales. | `documentbuilderfactory-disallow-doctype-decl-missing`, ligne 1315. |
| 6 | `tika-core/src/main/java/org/apache/tika/parser/NetworkParser.java` | Communication non chiffrée (CWE-319) | La méthode ouvre une connexion TCP avec `Socket` sans chiffrement TLS. Les données échangées peuvent être interceptées et lues pendant leur transmission. | `unencrypted-socket`, ligne 178. |
| 7 | `tika-core/src/main/java/org/apache/tika/parser/CryptoParser.java` | Chiffrement AES en mode ECB (CWE-327) | Le mode ECB chiffre chaque bloc indépendamment. Des blocs de texte clair identiques produisent des blocs chiffrés identiques, ce qui peut révéler des motifs dans les données. | `use-of-aes-ecb`, ligne 117. |

Les sept vulnérabilités ont été détectées dans le rapport après modification. Les règles indiquées correspondent aux identifiants des règles Semgrep ayant signalé les nouvelles méthodes.

## 3. Rapport Semgrep avant / après

Rapports complets produits par le workflow manuel [`semgrep-rapport.yml`](../.github/workflows/semgrep-rapport.yml), sur tout le dépôt.

- Avant nos vulnérabilités (`p/java` seul) : [`prevulnerabilitysemgrep.json`](prevulnerabilitysemgrep.json), 11 résultats.
- Dépôt actuel (`p/java` + `p/security-audit`) : [`postvulnerabilitysemgrep.json`](postvulnerabilitysemgrep.json), 26 résultats.

La hausse vient de deux choses : nos 7 nouvelles vulnérabilités, et l'ajout de `p/security-audit`, qui signale aussi du code d'origine de Tika (règles non présentes dans `p/java` gratuit). Le gate (section 1) isole les **nouvelles** vulnérabilités grâce à `--baseline-commit`, qui ne compare qu'avec le commit précédent ; c'est ce qui montre que chacune de nos 7 vulnérabilités fait échouer le build.

Les résultats d'origine viennent du code de Tika ; plusieurs sont probablement des faux positifs (par exemple, le format HWP impose AES/ECB).

## 4. Rickroll

Action réutilisable : [`.github/actions/rickroll/action.yml`](../.github/actions/rickroll/action.yml) (action composite). Elle affiche une erreur avec le lien du rickroll dans le log et la vidéo dans le résumé du job. Le message est un paramètre (`message`), passé par une variable d'environnement.

Elle est appelée dans le job `build` de `tache2.yml`, après l'étape « Build and test tika-core », avec `if: failure()`. Limite : une erreur de compilation la déclenche aussi, pas seulement un test qui échoue.

Démonstration : un test modifié pour échouer, puis remis en état.

## 5. Humour

<p align="center">
<a href="https://github.com/semgrep/semgrep">
<img src="https://github.com/semgrep/semgrep/raw/develop/images/semgrep-logo-dark.svg" height="100" alt="Logo de Semgrep"/>
</a>
</p>
<h3 align="center">« Code scanning at ludicrous speed. »</h3>
<p align="center"><em>Le slogan et le logo du dépôt open source de Semgrep (voir les crédits).</em></p>

<p align="center"><img src="https://i.pinimg.com/736x/f4/17/56/f417567d5da43b833144b04b5084846d.jpg" height="250" alt="Mème Ludicrous speed, tiré du film Spaceballs"/></p>

Semgrep analyse le code à la vitesse *ludicrous* ; notre CI, elle, échoue maintenant à la vitesse *ludicrous*. Mais un build rouge, c'est triste, alors nous avons voulu que la CI promette à ses développeurs de ne jamais les laisser tomber.

Crédits : le logo et le slogan viennent du dépôt [semgrep/semgrep](https://github.com/semgrep/semgrep) (licence LGPL 2.1). Le slogan fait référence au film *Spaceballs* de Mel Brooks (1987), d'où vient aussi le mème ci-dessus, tiré du blogue [efb28.wordpress.com](https://efb28.wordpress.com/wp-content/uploads/2014/04/spaceballs_ludicrous_speed.jpg).

## Utilisation de l'IA

Nous avons utilisé Claude (Anthropic) pour : finaliser le workflow `tache2.yml` et le job Semgrep ajouté ; `semgrep-rapport.yml` ; vérifier quels types de vulnérabilités les règles `p/java` détectent ; la traduction de l'anglais vers le français de la documentation des vulnérabilités (section 2) ; la structure de ce README. Nous avons choisi les types de vulnérabilités à partir des présentations de la semaine 7 (SAST, Semgrep) et du rapport « avant ». Les vulnérabilités ont été intégrées dans Tika par nous.
