package com.algaworks.brewer.service.event.cerveja;

import com.algaworks.brewer.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CervejaListener {

    @Autowired
    private FotoStorage fotoStorage;

    @EventListener(condition = "#evento.temFoto()")
    public void cervejaSalva(CervejaSalvaEvent evento) {
        System.out.println(">>>> Tem foto sim! -> " + evento.getCerveja().getFoto());
        fotoStorage.salvar(evento.getCerveja().getFoto());
    }
}
