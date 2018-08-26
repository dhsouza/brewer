package com.algaworks.brewer.repository.helper.cerveja;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;

import java.util.List;

public interface CervejasQueries {

    public List<Cerveja> filtrar(CervejaFilter filter);
}
