/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.DAO;

import br.com.tcc.modal.TblMovimento;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

/**
 *
 * @author Jp
 */
public class SysDAO {

    private Session sessao;
    private boolean retorno;
    private String erro;
    private List listagem;
    private Object objeto;
    private List<Object> objetos;

    /**
     *
     */
    public SysDAO() {
        this.erro = "";
        this.sessao = NewHibernateUtil.getSessionFactory().openSession();
    }

    /**
     *
     * @param obj
     * @return
     */
    public String save(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.saveOrUpdate(obj);
            this.sessao.getTransaction().commit();
        } catch (JDBCException e) {
            this.erro = e.getCause() + ": " + e.getMessage() + "/" + e.getErrorCode();
        } catch (Exception ec) {
            this.erro = ec.getCause() + ": " + ec.getMessage();
        }

        this.sessao.close();

        System.out.println(this.erro);
        return this.erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public boolean update(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.update(obj);
            this.sessao.getTransaction().commit();
            retorno = true;
        } catch (Exception ex) {
            retorno = false;
        }

        this.sessao.close();

        return retorno;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String delete(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.delete(obj);
            this.sessao.getTransaction().commit();
        } catch (JDBCException e) {
            this.erro = e.getCause() + ": " + e.getMessage();
        }

        this.sessao.close();

        return this.erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public List listagem(Object obj) {
        try {
            listagem = sessao.createCriteria(obj.getClass()).list();
        } catch (Exception ex) {
            System.out.println(ex.getCause() + "------" + ex.getMessage());
            listagem = null;

        }

        this.sessao.close();

        return listagem;
    }

    /**
     *
     * @param clazz
     * @param idValue
     * @return
     */
    public Object getById(Class clazz, Integer idValue) {
        return sessao.load(clazz, idValue);
    }

    /**
     *
     * @param obj
     * @param query
     * @return
     */
    public List<Object> returnMovimentosFilter(TblMovimento movimento) {
        String namedQuery = "";
        Query qy;
        try {
            if (movimento.getId() != null) {
                namedQuery = "TblMovimento.findById";
                qy = sessao.getNamedQuery(namedQuery)
                        .setParameter("id", movimento.getId());
            } else {
                namedQuery = "TblMovimento.findAll";
                qy = sessao.getNamedQuery(namedQuery);
            }
            objetos = qy.list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            objetos = null;
        }
        return objetos;
    }

    /**
     *
     * @return
     */
    public List<Object> returnPesquisaMovimentos(Date _data1, Date _data2) {
        String namedQuery = "";
        Query qy;
        try {
            if (_data1 != null && _data2 != null) {
                namedQuery = "TblMovimento.Pesquisa";
                qy = sessao.getNamedQuery(namedQuery);
                qy.setParameter("dataMovimento", _data1);
                qy.setParameter("dataMovimento2", _data2);
            } else {
                namedQuery = "TblMovimento.findAll";
                qy = sessao.getNamedQuery(namedQuery);
            }
            objetos = qy.list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            objetos = null;
        }
        return objetos;
    }

    /**
     *
     * @param obj
     * @param query
     * @return
     */
    public List<Object> busca(Object obj, String query) {
        try {
            Query qy = sessao.getNamedQuery(query);
            objetos = qy.list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            objetos = null;
        }

        this.sessao.close();

        return objetos;
    }

    /**
     *
     * @param query
     * @return
     */
    private List<Object> busca(String query) {
        try {
            Query qy = sessao.createQuery(query);
            objetos = qy.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            objetos = null;
        }
        this.sessao.close();

        return objetos;
    }

    /**
     *
     * @param clazz classe para select
     * @param args argumentos do criterion
     * @param rownum números de linho 0 = Todos
     * @param order orderna o select default = null
     * @return
     */
    public List<Object> busca(Class clazz, List<Criterion> args, Integer rownum, Order order, List<Projection> proList) {
        try {
            Criteria crit = sessao.createCriteria(clazz);
            for (Criterion c : args) {
                crit.add(c);
            }
            if (rownum > 0) {
                crit.setMaxResults(rownum);
            }
            if (order != null) {
                crit.addOrder(order);
            }
            if (proList != null) {
                for (Projection p : proList) {
                    crit.setProjection(p);
                }
            }

            objetos = crit.list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            objetos = null;
        }

        this.sessao.close();

        return objetos;
    }
}
