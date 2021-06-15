package malek.mod_science.components.world.ley_knots;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface LeyKnotInterface extends Component {
    int getX();
    int getZ();
    double getSap();
    double getSapRegenerationAmount();

}
