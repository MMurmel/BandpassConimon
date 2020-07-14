Testaufgabe Bandpassfilter
==========================

Aufgabe 1
---------
Siehe `Bandpass.java`.

Da `biz.source_code.dsp` schon alle Funktionalitäten bereitstellt, habe ich mich hier
dazu entschieden nur einen einfachen Wrapper zu schreiben.

Aufgabe 2
---------

![Signal](./graphics/signal.png "Signal aus 1Hz, 100Hz und 10kHz")

siehe `main.java` bzw. `SignalGenerator.java`.

Ein sehr einfacher Signalgenerator mit mehreren Einschränkungen:
* nur Sinussignale mit ω = 0° (keine Phase)
* nur ganzzahlige Frequenzen möglich
* Signallänge bei überlagerten Signalen beträgt immer 1 Sekunde, 
    statt der kleinsten möglichen Hyperperiode.
    
#### a
> Wie lang sollte das Signal sinnvollerweise sein? 

Mindestens eine Hyperperiode (in diesem Fall eine Sekunde).

#### b
> Welche Mindestabtastrate wird für das Testsignal benötigt?

Nach dem Abtasttheorem von Shannon/Nyquist muss gelten:
> 2 * f_max < f_abtast

In diesem Fall ist f_max = 10kHz, also muss mit **mehr** als 20kHz abgetastet werden.

Aufgabe 3
---------

![Signal](./graphics/filtered.png "Gefiltertes Signal")
siehe `main.java`

Aufgabe 4
---------
 
![Signal](./graphics/spectrum.png "Spektrum des Originalsignals")
![Signal](./graphics/filtered_spectrum.png "Spektrum des gefilterten Signals")


Aufgabe 6
---------
 
![Signal](./graphics/noise.png "Rauschsignal")
![Signal](./graphics/noise_spectrum.png "Rauschsignal Spektrum")
![Signal](./graphics/filtered_noise.png "Gefiltertes Rauschsignal")
![Signal](./graphics/filtered_noise_spectrum.png "Gefiltertes Rauschsignal Spektrum")