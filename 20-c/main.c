#include <stdlib.h>
#include <stdio.h>

int min(int a, int b) {
    if (a < b) return a;
    return b;
}

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
            break;
        }
    }

    free(houses);
    houses = malloc(N/10 * sizeof(int));
    
    for (int i = 1; i < N/10; i++) {
        for (int j = i; j <= min(i*50, N/10); j += i) {
            houses[j] += 11*i;
        }
    }

    for (int i = 0; i < N/10; i++) {
        if (houses[i] >= N) {
            printf("Part 2: %d\n", i);
            break;
        }
    }

    free(houses);
}
