package ao.co.isptec.aplm.servicesimple;

import android.app.Application;

/**
 * Contexto global da aplicação: partilha o contador de arranques
 * entre a actividade e o serviço enquanto o processo existir.
 */
public class ServiceSimpleApp extends Application {
    private int startCount = 0;

    public int getStartCount() {
        return startCount;
    }

    public void incrementStartCount() {
        startCount++;
    }
}
