package malek.mod_science.components.player.madness;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface MadnessInterface extends Component {
    double getMadness();

    void setMadness(double amount);

    default void setMadness(float amount) {
        setMadness((double) amount);
    }

    default void setMadness(int amount) {
        setMadness((double) amount);
    }

    default void increaseMadness(float amount) {
        increaseMadness((double) amount);
    }

    void increaseMadness(double amount);

    default void decreaseMadness(float amount) {
        decreaseMadness((double) amount);
    }

    void decreaseMadness(double amount);

    default void increaseMadness(int amount) {
        increaseMadness((double) amount);
    }

    default void decreaseMadness(int amount) {
        decreaseMadness((double) amount);
    }
}
