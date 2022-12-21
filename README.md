# Cinema


(Alpha)
Cinema is the ultimate app for movie lovers! With Cinema, you can easily find upcoming movies and those currently playing in theaters, as well as the top 250 movies and most popular movies. 

The app also offers movie trailers, reviews and ratings, showtimes, and theater locations, so you can plan the perfect movie night. You can also add movies to your watch list, so you don't miss out on any of the latest releases. 

Cinema is the perfect app for movie lovers to stay up to date with the latest releases and plan the perfect movie night. Download Cinema today and find your movie!

![Branching Modell](doc/getStarted.png) ![Branching Modell](doc/firstPage.png) ![Branching Modell](doc/firstPage2.png) ![Branching Modell](doc/detailView.png)

## Branching-Modell

Die Entwickler erstellen für jedes Issue einen Feature-Branch direkt vom dev-branch.
Dabei wird das folgende Naming eingehalten: `feature/(IssueNr)-<branch name>`. Ausserdem wird für
Dokumentationen ein eigenes Branch eröffnet. Dabei wird das folgende Naming eingehalten: `doc/(IssueNr)-<branch name>`

Bevor ein Pull-Request vom Entwickler erstellt wird, müssen die folgenden Bedingungen erfüllt sein:

* Issue ist vollständig umgesetzt (in Ausnahmefällen mit `todo`)
* Clean-Code Regeln wurden eingehalten, Imports optimiert und Code formatiert (durch Intellij)
* Code ist kompilierbar

Nachdem der Pull-Request approved wurde, wird der Feature-Branch vom Ersteller
selbstständig in den dev-branch gemerged. Der Feature-Branch oder der Doc-Branch wird im Anschluss gelöscht.

![Branching Modell](doc/branching_modell.png)

## Arbeiten mit Git
1.) Kontrolle ob man die Aktuellste Version hat `Git update` oder `Fetch`

2.) Local seinen Branch erstellen. Passend zum Issue den Branch Name wählen

3.) Bevor man seine Änderungen commitet, nochmals kontrollieren, dass man die Aktuellste Version hat 
     ggf. mergen und wenn nötig Anpassungen vornehmen

4.) Für die commit message kurze und treffende Beschreibung geben was geändert wurde

5.) Pushen auf git

6.) Pull-request erstellen auf GitHub

7.) Nach dem der Code approved wurde eigenen Branch mergen

8.) Branch löschen auf GitHub

