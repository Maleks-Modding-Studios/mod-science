package malek.mod_science.components.player.timeout;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface TimeoutInterface extends Component {
    double getTimeOut();
    void setTimeOut(double timeOut);
    void setTime();
    boolean isTimeOutOver();
}
