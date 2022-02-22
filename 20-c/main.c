#include <math.h>
#include <stdlib.h>
#include <stdio.h>

void main(int argc, char **argv) {
    const long N = 29000000;
    int *houses = malloc(N/10 * sizeof(int));
    
    for (int i = 1; i < N/10; i++) {
        for (int j = i; j < N/10; j += i) {
            houses[j] += 10*i;
        }
    }

    for (int i = 0; i < N/10; i++) {
        if (houses[i] >= N) {
            printf("Part 1: %d\n", i);
            free(houses);
            return;
        }
    }

    free(houses);
}
