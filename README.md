# Cinema Trivia


(Alpha Release)
Cinema Trivia is the perfect mobile app for cinephiles and movie buffs who want to challenge each other with their knowledge of the silver screen!

This two-player app pits you and your friend against each other in a battle of wits and movie trivia. With over 1,000 questions spanning all genres and eras, you'll be tested on everything from classic films to modern blockbusters. Earn points for correct answers and compete to see who knows the most about movies.

Movie Trivia also offers a variety of special game modes, like movie or actor-specific rounds, and a lightning round where you'll have to answer as many questions as possible within a set amount of time. And with its beautiful and intuitive interface, you'll be able to seamlessly switch between questions and view your score at any time.

Download Movie Trivia now and become a film expert!

![Branching Modell](doc/getStarted.png) ![Branching Modell](doc/firstPage.png) ![Branching Modell](doc/firstPage2.png) ![Branching Modell](doc/trivia_modus.png) ![Branching Modell](doc/trivia_modus_anwer.png)

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

