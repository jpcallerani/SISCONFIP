/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblCategoria;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jp
 */
public class ControlCategoria {

    private String _erro;
    List<Criterion> parameters;

    public ControlCategoria() {
        parameters = new ArrayList<Criterion>();
    }

    /**
     * Retorna todas as contas do usuário.
     *
     * @param usuario
     * @return
     */
    public List<TblCategoria> buscaCategoriasReceber() {

        String subSelect = String.format("%s IN(SELECT %s FROM %s WHERE %s = %d)",
                "id_grupo_categoria",
                "id",
                "tbl_grupo_categoria",
                "id_tipo_movimento",
                2);
        parameters.add(Restrictions.sqlRestriction(subSelect));

        List<TblCategoria> categorias = (List<TblCategoria>) (List<?>) new SysDAO().busca(TblCategoria.class, parameters, 0, null, null);
        return categorias;
    }

    /**
     * Retorna todas as contas do usuário.
     *
     * @param usuario
     * @return
     */
    public List<TblCategoria> buscaCategoriasPagar() {

        String subSelect = String.format("%s IN(SELECT %s FROM %s WHERE %s = %d)",
                "id_grupo_categoria",
                "id",
                "tbl_grupo_categoria",
                "id_tipo_movimento",
                1);
        parameters.add(Restrictions.sqlRestriction(subSelect));

        List<TblCategoria> categorias = (List<TblCategoria>) (List<?>) new SysDAO().busca(TblCategoria.class, parameters, 0, null, null);
        return categorias;
    }
}
