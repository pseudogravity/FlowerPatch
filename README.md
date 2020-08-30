# FlowerPatch

Using a circular buffer to find "flower seeds" independent of global coords.

The "time" variable is basically DFZ/6.

This would need to be run twice, to cover both even and odd DFZs, each run covering 2^47 seeds.

Also this will need to be run for each possible (relative) flower center coords.
