Testaufgabe Bandpassfilter
==========================

Aufgabe 1
---------
Siehe `Bandpass.java`.

Da `biz.source_code.dsp` schon alle Funktionalitäten bereitstellt, habe ich mich hier
dazu entschieden nur einen einfachen Wrapper zu schreiben.

Aufgabe 2
---------
Siehe `Main.java` bzw. `SignalGenerator.java`.

Ein sehr einfacher Signalgenerator mit mehreren Einschränkungen:
* nur Sinussignale mit ω = 0° (keine Phase)
* nur ganzzahlige Frequenzen möglich
* Signallänge bei überlagerten Signalen beträgt immer 1 Sekunde, 
    statt der kleinsten möglichen Hyperperiode.

Das erzeugte Signal:
![Signal](./graphics/signal.png "Signal aus 1Hz, 100Hz und 10kHz")

#### a
> Wie lang sollte das Signal sinnvollerweise sein? 

Mindestens eine Hyperperiode (in diesem Fall eine Sekunde). 
Aber je länger das Signal ist, desto genauere Aussagen lassen sich über die anteiligen Frequenzen treffen.

#### b
> Welche Mindestabtastrate wird für das Testsignal benötigt?

Nach dem Abtasttheorem von Shannon/Nyquist muss gelten:
> 2 * f_max < f_abtast

In diesem Fall ist f_max = 10kHz, also muss mit **mehr** als 20kHz abgetastet werden.

Aufgabe 3
---------
siehe `Main.java` bzw. `Bandpass.java`.

Das gefilterte Signal:
![Signal](./graphics/filtered.png "Gefiltertes Signal")

Aufgabe 4
---------
siehe `Main.java`. Den Code für die FFT, sowie dessen dependencies habe ich von [hier](https://introcs.cs.princeton.edu/java/97data/FFT.java.html) übernommen.

Die Spektren werden hier der Anschaulichkeit halber mit logarithmischer x-Achse dargestellt.

Spektrum des ungefilterten Signals:
![Signal](./graphics/spectrum.png "Spektrum des Originalsignals")

Spektrum des gefilterten Signals:
![Signal](./graphics/filtered_spectrum.png "Spektrum des gefilterten Signals")

Aufgabe 5
---------
Siehe `Main.java`.

Messungen im Spektrum des (gefilterten) Signals haben folgende Werte ergeben:

|markante Frequenz|Amplitude ungefiltert|Amplitude gefiltert|tatsächliche Dämpfung|erwartete Dämpfung|
|-----------------|:-------------------:|:-----------------:|---------------------|------------------|
|1                |49344                |506                |-19.88 dB            |~ -40 dB          |
|118              |1173                 |1276               |0.36 dB              |0 dB              |
|13653            |43430                |766                |-17.53 dB            |~ -40 dB          |

Die Dämpfung des hier eingesetzten Filters im Sperrbereich entspricht nicht der gewünschten Dämpfung, 
die hier noch einmal um den Faktor 100 stärker wäre.
Im Durchlassbereich kann aber mit einer Verstärkung um 0.3 dB eine passable Leistung erzielt werden.

Aufgabe 6
---------
 Siehe `SignalGenerator.java`.
 
 Rauschsignal:
![Signal](./graphics/noise.png "Rauschsignal")

Aufgabe 7
---------
Filterung des Rauschsignals analog zur Signalfilterung in Aufgabe 3.

Gefiltertes Signal:
![Signal](./graphics/filtered_noise.png "Gefiltertes Rauschsignal")

#### Spektrenvergleich
Spektrum des Rauschsignals (mit linearer Achseneinteilung, weil logarithmische in diesem Fall unübersichtlicher ist):
![Signal](./graphics/noise_spectrum.png "Rauschsignal Spektrum")

Über das gesamte Spektrum des Rauschsignals hinweg zeigt sich eine chaotisch Verteilung von Datenpunkten.
Dabei ist insbesondere auch in kleinen Intervallen eine große Abdeckung des Wertebereiches zu beobachten.
Wie zu erwarten war, ist das Rauschsignal also eine Überlagerung einer Vielzahl von Signalen 
mit sehr unterschiedlichen Frequenzen.

Spektrum des gefilterten Rauschsignals:
![Signal](./graphics/filtered_noise_spectrum.png "Gefiltertes Rauschsignal Spektrum")

Beim gefilterten Rauschen hingegen lässt sich deutlich eine Verteilung erkennen, die einer Glockenkurve ähnelt.
Dass dabei die linke Seite so „dünn“ besetzt erscheint, ist der logarithmischen Skala geschuldet.
Weiterhin ist zu sehen, wie die Werte außerhalb des Intervalls (10,1000) stark abfallen, was den
Grenzfrequenzen des Bandpassfilters entspricht.
Im Gegensatz zum ungefilterten Rauschsignal kann man hier sagen, dass die einzelnen Frequenzanteile des
Signals großteils einem bestimmten Frequenzbereich zugeordnet werden können. Der Bandpass funktioniert! :)

offene Probleme
---------------
- Es wird keine Fensterfunktion verwendet.
- Die „interessanten“ Frequenzen scheinen verschoben zu sein. 
  Obwohl der n-te Index im Spektrum bei den genutzten Parametern (Signaldauer = 1 sec) der Frequenz n Hz
  entsprechen sollte, liegen die stärksten Frequezanteile laut FFT bei 1, 118 und 13.653 Hz statt bei
  1, 100 und 10.000 Hz.
- Der Bandpass leistet nur -20 dB/Dekade im Sperrbereich.