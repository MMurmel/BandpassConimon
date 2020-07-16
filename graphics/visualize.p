#setting output type
set term png size 1600, 900

#all time domain graphics
set xlabel "time/s"
set ylabel "amplitude"

set output "signal.png"
plot "signal.data" notitle
set output "filtered.png"
plot "filtered.data" notitle
set output "noise.png"
plot "noise.data" notitle
set output "filtered_noise.png"
plot "filtered_noise.data" notitle


#all frequency domain graphics
set xlabel "frequency/Hz"
unset ylabel
set logscale x

set output "spectrum.png"
plot "spectrum.data" notitle with points pt 5 ps 1.5 
set output "filtered_spectrum.png"
plot "filtered_spectrum.data" notitle with points pt 5 ps 1.5
set output "filtered_noise_spectrum.png"
plot "filtered_noise_spectrum.data" notitle with points pt 5 ps 1.5
unset logscale x
set output "noise_spectrum.png"
plot "noise_spectrum.data" notitle with points pt 5 ps 1.5
