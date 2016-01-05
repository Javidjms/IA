rm small_*.png
for img in large_*.png; do convert $img -resize 30% small_${img} ; done ; rename "s/small_large_/small_/" small_large_*.png
