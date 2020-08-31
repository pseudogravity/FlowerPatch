# FlowerPatch

Using a circular buffer to find "flower seeds" independent of global coords.

Outputs DFZ values as measured from internal state 0. (As in, the number of random calls required to go from state 0 to the state used to generate the flowers.)

Inputs (which are currently hardcoded at the start):

- `blockstart`: the DFZ value of the start of the block.
- `offset`: since the code leapfrogs in units of 6 DFZ, we need to specify which of the 6 possible offsets the code will use.  So 6 runs are needed to cover a block completely.
- `blocklength`: the length of the block in DFZ.
- `center`: the coords of the center of the patch.  Only the distance relative to the list of flower coords matters.
