package com.pstr.game.object;

import java.util.Set;

public interface FireStrategy {
    Set<GameObject> fire(Starship starship);
}
