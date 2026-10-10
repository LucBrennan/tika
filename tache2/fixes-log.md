# Correctifs appliqués aux tests Patterns générés par ChatUniTest

Chaque correctif est aussi marqué dans le code source du test par un commentaire
`// FIX (<type>): ...` (en anglais, comme dans les fichiers `MediaType_*_Test.java`).
Le bloc d'imports de chaque fichier a été réécrit pour respecter les règles checkstyle et
spotless de Tika (pas d'import `*`, pas d'import inutilisé, en-tête de licence Apache). Cette
réécriture n'est pas comptée comme un correctif et n'a pas de marqueur.

Types de correctifs :

| Type | Signification |
|---|---|
| `compile` | le code généré ne compile pas |
| `access` | le code généré lit un champ privé ; remplacé par le résultat observable de `matches()` |
| `setup` | la préparation (arrange) est fausse : stub impossible, enregistrement manquant |
| `oracle` | une assertion ou un `verify()` contredit le comportement réel de la classe |
| `test removed` | le test entier vérifie un comportement que la classe n'a pas et ne peut pas être sauvé |

Source : la dernière tentative de ChatUniTest pour chaque test,
`chatunitest-raw/patterns/error-message/*_CompilationError_2.txt`. Aucun des trois fichiers ne
compilait ; ChatUniTest a abandonné après 3 rondes pour chacun.

## `Patterns_add_0_0_Test` (8 tests générés, 7 conservés, 17 marqueurs)

| # | Test | Type | Correctif |
|---|---|---|---|
| 1-5 | testAddValidPattern, testAddValidJavaRegexPattern, testAddNamePattern, testAddExtensionPattern, testAddGlobPattern | oracle | stubs `when(type.getName())` / `when(type.getExtension())` et appels `verify()` correspondants retirés : `Patterns.add()` n'appelle jamais ces méthodes (le `verify()` échouait, et les stubs stricts de `MockitoExtension` signalaient les stubs inutilisés) |
| 6-10 | les mêmes 5 tests | access | `patterns.names.get(p)` (champ privé) remplacé par `patterns.matches(p)` |
| 11-15 | les mêmes 5 tests | oracle | `assertEquals(type, patterns.extensions.get("txt"))` retiré : les motifs utilisés (`text/plain`, `image/png`, ...) ne contiennent pas de `*`, ce sont des noms et non des extensions |
| 16 | testAddPatternWithInvalidCharacter | test removed | attendait une `MimeTypeException` pour `invalid*pattern` ; `add()` l'accepte comme glob et ne valide jamais les motifs. Le test passait aussi une `String` à `MimeType(MediaType)` |
| 17 | testAddPatternWithNullPattern | compile | `new MimeType("application/json")` remplacé par `new MimeType(MediaType.parse("application/json"))` |

Erreurs de compilation qui n'ont demandé aucun changement dans le test : `junit-jupiter-params`
et `mockito-junit-jupiter` ont été ajoutés à `tika-core/pom.xml` après la génération.

## `Patterns_add_1_0_Test` (13 tests générés, 9 conservés, 7 marqueurs)

| # | Test | Type | Correctif |
|---|---|---|---|
| 1 | testAddName | access | `patterns.names.get("example")` remplacé par `patterns.matches("example")` |
| 2 | testAddExtension | access | `patterns.extensions.get("jpg")` remplacé par `patterns.matches("jpg")`. Note : `"jpg"` n'a pas de `*`, il est donc stocké comme nom. L'oracle d'origine (`extensions`) était faux ; l'oracle plus faible `matches()` passe |
| 3 | testAddGlob | access | `patterns.globs.get("*.pdf")` remplacé par `patterns.matches("*.pdf")`. Note : `"*.pdf"` est stocké comme l'extension `.pdf`, pas comme un glob |
| 4 | testAddNameWithJavaRegex | access | `patterns.names.get("example")` remplacé par `patterns.matches("example")` (la regex est stockée comme glob) |
| 5 | testAddExtensionWithJavaRegex | access | `patterns.extensions.get("jpg")` remplacé par `patterns.matches("jpg")` (la regex est stockée comme glob) |
| 6 | testAddGlobWithJavaRegex | access + oracle | `patterns.globs.get("*.pdf")` remplacé par `patterns.matches(".pdf")` : la clé du glob est la regex `(?i)\.pdf$` elle-même, et `matches()` l'applique au nom entier, donc `.pdf` est le seul nom accepté |
| 7 | testAddNameWithInvalidRegex, testAddExtensionWithInvalidRegex, testAddGlobWithInvalidRegex, testAddNameWithEmptyString | 4 tests removed | les trois premiers attendaient une `MimeTypeException` pour `example*`, `jpg*` et `*.pdf*`, mais `add()` les accepte comme globs ; le dernier attendait une `IllegalArgumentException` pour `""`, mais `add()` ne rejette que `null` |

Le `MediaTypeRegistry` simulé (mock) a été conservé : son `isSpecializationOf()` renvoie `false`,
comme un registre vide, donc les trois tests de conflit passent sans changement.

## `Patterns_matches_5_0_Test` (6 tests générés, 6 conservés, 7 marqueurs)

| # | Test | Type | Correctif |
|---|---|---|---|
| 1 | setUp | setup | `mock(MediaTypeRegistry.class)` et ses deux stubs remplacés par `new MediaTypeRegistry()`. `when(registry.getDefaultRegistry())` tente de simuler une méthode statique, donc `when()` levait une exception avant chaque test ; `Patterns` n'appelle jamais `getTypes()` |
| 2-4 | testMatchesExactMatch, testMatchesExtensionMatch, testMatchesGlobMatch | compile | `throws MimeTypeException` ajouté, car la préparation appelle maintenant `add()` |
| 5 | testMatchesExactMatch | setup | les stubs sur `getName()` et `registry.getTypes()` remplacés par `patterns.add("example", mimeType)` |
| 6 | testMatchesExtensionMatch | setup | mêmes stubs (plus `getExtension()`) remplacés par `patterns.add("*.txt", mimeType)` |
| 7 | testMatchesGlobMatch | setup | mêmes stubs remplacés par `patterns.add("example.*", mimeType)`. Dans le fichier brut, ce test était une copie exacte du précédent |

L'import manquant `java.util.TreeSet` (la dernière erreur de compilation signalée) a disparu
avec les stubs qui l'utilisaient.
