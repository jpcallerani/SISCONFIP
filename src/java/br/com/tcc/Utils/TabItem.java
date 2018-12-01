/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.Utils;

/**
 *
 * @author Jp
 */
public class TabItem {

    private String name;
    private String url;
    private int tabIndex;
    private String title;
    private boolean closable;

    //TODO assim que necessário colocar mais atributos nessa tab  
    public TabItem(String name, String url, int tabIndex, String title, boolean closable) {
        this.closable = closable;
        this.name = name;
        this.url = url;
        this.tabIndex = tabIndex;
        this.title = title;
    }

    /**
     * Retorna o nome da pagina
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Seta o nome da pagina
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o caminho da pagina
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Seta o caminho da pagina
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retorna o tabindex da pagina
     *
     * @return
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Seta o tabindex da pagina
     *
     * @param tabIndex
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * Retorna o titulo da pagina
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Seta o titulo da pagina
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retorna se a tab pode ser fechada
     *
     * @return
     */
    public boolean isClosable() {
        return closable;
    }

    /**
     * Seta se a tab pode ser fechada
     *
     * @param closable
     */
    public void setClosable(boolean closable) {
        this.closable = closable;
    }
}
