set term png size 1600, 900
set output "signal.png"
plot "signal.data"
set output "filtered.png"
plot "filtered.data"

set logscale x
set output "spectrum.png"
plot "spectrum.data"
set output "filtered_spectrum.png"
plot "filtered_spectrum.data"

unset logscale x
set output "noise.png"
plot "noise.data"
set output "filtered_noise.png"
plot "filtered_noise.data"

set output "noise_spectrum.png"
plot "noise_spectrum.data"
set logscale x
set output "filtered_noise_spectrum.png"
plot "filtered_noise_spectrum.data"
